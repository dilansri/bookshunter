package com.nsbm.bytecode;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.nsbm.bytecode.data.BookContract;
import com.nsbm.bytecode.data.BookContract.AlreadyReadEntry;
/**
 * The fragment representing the already read books.
 * 
 * Books are taken from local database using a Content Provider.
 * 
 * Includes implemented cursor to get data from Content Provider. 
 *
 */
public class AlreadyReadFragment extends Fragment implements LoaderCallbacks<Cursor> {
	
	private SimpleCursorAdapter mAlreadyReadAdapter;
	
	private static final int ALREADY_READ_LOADER = 0;
	
	private static final String[] ALREADY_READ_COLUMNS = {
		BookContract.AlreadyReadEntry.TABLE_NAME + "." + BookContract.WantsToReadEntry._ID,
		BookContract.AlreadyReadEntry.COLUMN_TITLE,
		BookContract.AlreadyReadEntry.COLUMN_AUTHORS,
		BookContract.AlreadyReadEntry.COLUMN_DESCRIPTION,
		BookContract.AlreadyReadEntry.COLUMN_RATING,
		BookContract.AlreadyReadEntry.COLUMN_IMAGE
	};
	
	public static final int COL_BOOK_ID = 0;
	public static final int COL_BOOK_TITLE = 1;
	public static final int COL_BOOK_AUTHORS = 2;
	public static final int COL_BOOK_DESCRIPTION = 3;
	public static final int COL_BOOK_RATING = 4;
	public static final int COL_BOOK_IMAGE = 5;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(ALREADY_READ_LOADER, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		Uri uri = BookContract.AlreadyReadEntry.CONTENT_URI;
		String sortOrder = BookContract.AlreadyReadEntry._ID + " DESC";
		
		Log.d("AlreadyReadFragment","URI: "+uri.toString());
		
		return new CursorLoader(
				getActivity(),
				uri,
				ALREADY_READ_COLUMNS,
				null,
				null,
				sortOrder
				);
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.already_read,
				container, false);
		
		mAlreadyReadAdapter = new SimpleCursorAdapter(
				getActivity(),
				R.layout.book_view,
				null,
				new String[]{
					BookContract.AlreadyReadEntry.COLUMN_TITLE,
					BookContract.AlreadyReadEntry.COLUMN_AUTHORS,
					BookContract.AlreadyReadEntry.COLUMN_DESCRIPTION,
					BookContract.AlreadyReadEntry.COLUMN_RATING,
					BookContract.AlreadyReadEntry.COLUMN_IMAGE
				},
				new int[]{
					R.id.book_title,
					R.id.book_authors,
					R.id.book_description,
					R.id.book_ratingBar,
					R.id.book_coverImage
				},
				0
				);
		mAlreadyReadAdapter.setViewBinder(new AlreadyReadBookViewBinder());
		
		ListView listView = (ListView) rootView.findViewById(R.id.alreadyReadList);
		registerForContextMenu(listView);
		listView.setAdapter(mAlreadyReadAdapter);
		
		return rootView;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getActivity().getMenuInflater();
	    inflater.inflate(R.menu.library_book_already_context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();		
	    switch (item.getItemId()) {
	        case R.id.menu_libraryAlreadyRemove:{	
	        	Cursor cursor = (Cursor)mAlreadyReadAdapter.getItem(info.position);
	        	long id = cursor.getLong(COL_BOOK_ID);
	        	Uri uri = AlreadyReadEntry.buildAlreadyReadReadUri(id);
	        	getActivity().getContentResolver().delete(uri, AlreadyReadEntry.TABLE_NAME+"._ID=?",new String[]{Long.toString(id)});
	        	Toast.makeText(getActivity().getApplicationContext(),cursor.getString(COL_BOOK_TITLE)+" removed from the library", 
						   Toast.LENGTH_LONG).show();
	            return true;
	        }
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAlreadyReadAdapter.swapCursor(data);		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAlreadyReadAdapter.swapCursor(null);
		
	}

}
