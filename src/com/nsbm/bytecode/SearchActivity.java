package com.nsbm.bytecode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nsbm.bytecode.data.BookContract.AlreadyReadEntry;
import com.nsbm.bytecode.data.BookContract.WantsToReadEntry;
import com.nsbm.bytecode.util.BookUtilities;

/**
 * Searching books activity.
 * Query text will be passed to Google Books API and results will be parsed using Google Books Parser. * 
 *  
 * Long click on displayed book item will show the context menu to save books.
 * 
 * Books are inserted to local database using Content Provider insert method.
 */
public class SearchActivity extends Activity {
	
	List<Book> books = new ArrayList<Book>();
	BookAdapter adapter;
	EditText searchText;
	Button searchButton;
	ListView searchListView;	
	int searchResultSize =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		adapter = new BookAdapter(this, R.layout.book_view, books);
		searchListView = (ListView) findViewById(R.id.searchListView);
		searchListView.setAdapter(adapter);
		registerForContextMenu(searchListView);
		
		searchText = (EditText) findViewById(R.id.searchText);
		searchButton = (Button) findViewById(R.id.searchButton);	
		
		searchButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		       String searchQuery = searchText.getText().toString();
		       (new SearchBooksLoader()).execute(searchQuery);
		       //Log.v(getClass().getName(),"CLICKECK");
		    }
		});
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.book_context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {	    
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.menu_wantsToRead:{
	        	Book book = adapter.getItem(info.position);	 
	        	addWantsToReadList(book);	        	
	        	Toast.makeText(getApplicationContext(), book.getTitle()+" "+getResources().getString(R.string.wants_to_read_add), 
						   Toast.LENGTH_LONG).show();
	            return true;
	        }
	        case R.id.menu_alreadyRead:{
	        	Book book = adapter.getItem(info.position);	 
	        	addAlreadyReadList(book);
	        	Toast.makeText(getApplicationContext(), book.getTitle()+" " + getResources().getString(R.string.already_read_add), 
						   Toast.LENGTH_LONG).show();
	            return true;
	        }
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	private void addAlreadyReadList(Book book) {
		ContentValues values = BookUtilities.getContentValues(book);
		getContentResolver().insert(AlreadyReadEntry.CONTENT_URI, values);
	}

	private void addWantsToReadList(Book book) {
		ContentValues values = BookUtilities.getContentValues(book);
		getContentResolver().insert(WantsToReadEntry.CONTENT_URI, values);
	}
	
	private class SearchBooksLoader extends AsyncTask<String, Void, List<Book>> {
        private final ProgressDialog dialog = new ProgressDialog(SearchActivity.this);
       
        @Override
        protected void onPreExecute() {       
            super.onPreExecute();
            dialog.setMessage("Searching books...");
            dialog.show();           
        }
        
        @Override
        protected void onPostExecute(List<Book> result) {           
            super.onPostExecute(result);
            dialog.dismiss();
            adapter.setItemList(result);
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), searchResultSize+"Book(s) found for your query", 
					   Toast.LENGTH_LONG).show();
        }
        
        @Override
		protected List<Book> doInBackground(String... params) {
        	List<Book> books = new ArrayList<Book>();
        	
        	try{
        		String url = prepareGoogleBooksURLs(params);
        		URL u = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) u.openConnection();
				conn.setRequestMethod("GET");
				conn.setReadTimeout(5000); // 5 seconds
                conn.setConnectTimeout(5000); // 5 seconds

				conn.connect();
				
				int responseCode = conn.getResponseCode();
				
				if(responseCode != 200){
	                Log.w(getClass().getName(), "GoogleBooksAPI SEARCH request failed. Response Code: " + responseCode);
	                conn.disconnect();
	                return null;
	            }				
				Log.w(getClass().getName(), "GoogleBooksAPI SEARCH request. Response Code: " + responseCode);
				
				// Read data from response.
	            StringBuilder builder = new StringBuilder();
	            BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = responseReader.readLine();
	            while (line != null){
	                builder.append(line);
	                line = responseReader.readLine();
	            }
	            
	            String responseString = builder.toString();
	            conn.disconnect();
	            
	            JSONObject responseJson = new JSONObject(responseString);
	            
	            GoogleBooksParser gParser = new GoogleBooksParser(responseJson);	            
	            books.addAll(gParser.parse(GoogleBooksParser.SEARCH_RESULT_SIZE));
	            searchResultSize = gParser.getResultSize();
        		
        	}catch(Throwable t){
        		Log.w(getClass().getName(),t.getMessage());
        	}
        	
        	return books;
        	
        }

		private String prepareGoogleBooksURLs(String[] params) {
			
			String searchQuery = params[0].trim();
			
			searchQuery = searchQuery.replace(' ', '+');
			
			String url = "https://www.googleapis.com/books/v1/volumes?q="+searchQuery+"&maxResults="+GoogleBooksParser.SEARCH_RESULT_SIZE;
			
			return url;
		}
	}
}
