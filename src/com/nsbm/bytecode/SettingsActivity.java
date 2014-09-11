package com.nsbm.bytecode;

import android.os.Bundle;
import android.preference.PreferenceActivity;
/**
 * Settings of the application.
 * Name, Caching results and list of book genres.
 *
 */
public class SettingsActivity extends PreferenceActivity {
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();
	  }
}
