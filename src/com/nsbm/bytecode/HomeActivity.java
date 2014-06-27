package com.nsbm.bytecode;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeActivity extends Activity {

	LinearLayout suggestions;
	LinearLayout search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		suggestions = (LinearLayout) findViewById(R.id.suggestions);
		search = (LinearLayout) findViewById(R.id.search);

		suggestions.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "SUggestions",Toast.LENGTH_SHORT).show();
				Intent suggestionsActivity = new Intent(HomeActivity.this,SuggetionsActivity.class);
				startActivity(suggestionsActivity);
			}
		});

		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent suggestionsActivity = new Intent(HomeActivity.this,SearchActivity.class);
				startActivity(suggestionsActivity);
				//Toast.makeText(getApplicationContext(), "Search",Toast.LENGTH_SHORT).show();
			}
		});		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	// Called when an options item is clicked
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {	    
		startActivity(new Intent(this, SettingsActivity.class));
	    return true;
	  }

}
