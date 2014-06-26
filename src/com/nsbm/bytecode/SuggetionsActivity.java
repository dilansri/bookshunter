package com.nsbm.bytecode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SuggetionsActivity extends Activity {
	
	List<Book> books = new ArrayList<Book>();
	BookAdapter adapter;
	private SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggetions);
		adapter = new BookAdapter(this, R.layout.book_view, books);
		//populateSuggetionBooks();
		ListView suggestionsList = (ListView) findViewById(R.id.suggestionList);
		suggestionsList.setAdapter(adapter);
		settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		Set<String> genresSet =  settings.getStringSet("genres",null);
		String[] genresList = genresSet.toArray(new String[]{});
		
		if(genresList.length == 0){
			Toast.makeText(getApplicationContext(), "Please select your book genres", 
					   Toast.LENGTH_LONG).show();
			return;
		}
		
		for(int i=0;i<genresList.length;i++){
			Log.v(getClass().getName(), genresList[i]);
		}
		
		(new SuggetionBooksLoader()).execute(genresList);
		
	}
	
	
	//testing method
	private void populateSuggetionBooks()
	{
		/*
		for(int i=0;i<10;i++){
			books.add(new Book("Game of Thrones "+i+1,"Authors: George R.R. Matin",
					"This is a test description about game of thrones "+i+1,4.5F,"dummy"));			
		}
		
		*/
		
		ArrayAdapter<Book> adapter = new BookAdapter(this, R.layout.book_view, books);		
		ListView suggestionsList = (ListView) findViewById(R.id.suggestionList);		
		suggestionsList.setAdapter(adapter);
		
		
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggetions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.suggetions_action_settings:
			startActivity(new Intent(this, SettingsActivity.class));			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class SuggetionBooksLoader extends AsyncTask<String, Void, List<Book>> {
        private final ProgressDialog dialog = new ProgressDialog(SuggetionsActivity.this);
        
        @Override
        protected void onPreExecute() {       
            super.onPreExecute();
            dialog.setMessage("Downloading books...");
            dialog.show();           
        }
        
        @Override
        protected void onPostExecute(List<Book> result) {           
            super.onPostExecute(result);
            dialog.dismiss();
            adapter.setItemList(result);
            adapter.notifyDataSetChanged();
        }    
        
        @Override
		protected List<Book> doInBackground(String... params) {
        	List<Book> books = new ArrayList<Book>();
        	/*
        	for(int i=0;i<10;i++){
    			books.add(new Book("Game of Thrones "+i+1,"Authors: George R.R. Matin",
    					"This is a test description about game of thrones "+i+1,4.5F,"dummy"));			
    		}
        	*/
        	
        	try {
        		String[] urls = prepareGoogleBooksURLs(params);
        		
				URL u = new URL(urls[0]);
				HttpURLConnection conn = (HttpURLConnection) u.openConnection();
				conn.setRequestMethod("GET");
				conn.setReadTimeout(5000); // 5 seconds
                conn.setConnectTimeout(5000); // 5 seconds

				conn.connect();
				
				int responseCode = conn.getResponseCode();
				
				if(responseCode != 200){
	                Log.w(getClass().getName(), "GoogleBooksAPI request failed. Response Code: " + responseCode);
	                conn.disconnect();
	                return null;
	            }				
				Log.w(getClass().getName(), "GoogleBooksAPI request. Response Code: " + responseCode);
				
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
	            //Log.d(getClass().getName(), "Response String: " + responseString);
	            JSONObject responseJson = new JSONObject(responseString);
	            
	            GoogleBooksParser gParser = new GoogleBooksParser(responseJson);	            
	            books.addAll(gParser.parse());
	            
        	}catch(Throwable t){
        		Log.w(getClass().getName(),t.getMessage());
        	}
        	
        	return books;
        }

		private String[] prepareGoogleBooksURLs(String[] params) {
			String[] url = new String[params.length];
			
			for(int i=0;i<params.length;i++)
			{
				url[i] = "https://www.googleapis.com/books/v1/volumes?q=subject:"+params[i]+"&maxResults="+GoogleBooksParser.GENRE_RESULT_SIZE+"&orderBy=newest";
			}
			return url;
		}
    }

}