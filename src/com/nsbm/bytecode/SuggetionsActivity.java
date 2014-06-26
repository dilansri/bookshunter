package com.nsbm.bytecode;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SuggetionsActivity extends Activity {
	
	List<Book> books = new ArrayList<Book>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggetions);
		
		populateSuggetionBooks();
		
	}
	
	private void populateSuggetionBooks()
	{
		for(int i=0;i<10;i++){
			books.add(new Book("Game of Thrones "+i+1,"Authors: George R.R. Matin",
					"This is a test description about game of thrones "+i+1,4.5F,"dummy"));			
		}
		
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
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
