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
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.nsbm.bytecode.data.BookContract;
import com.nsbm.bytecode.data.BookContract.AlreadyReadEntry;
import com.nsbm.bytecode.data.BookContract.WantsToReadEntry;

public class WantsToReadFragment extends Fragment implements LoaderCallbacks<Cursor> {
	
	private SimpleCursorAdapter mWantsToReadAdapter;
	
	private static final int WANTS_TO_READ_LOADER = 0;
	
	private static final String[] WANTS_TO_READ_COLUMNS = {
		BookContract.WantsToReadEntry.TABLE_NAME + "." + BookContract.WantsToReadEntry._ID,
		BookContract.WantsToReadEntry.COLUMN_TITLE,
		BookContract.WantsToReadEntry.COLUMN_AUTHORS,
		BookContract.WantsToReadEntry.COLUMN_DESCRIPTION,
		BookContract.WantsToReadEntry.COLUMN_RATING,
		BookContract.WantsToReadEntry.COLUMN_IMAGE
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
		getLoaderManager().initLoader(WANTS_TO_READ_LOADER, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		Uri uri = BookContract.WantsToReadEntry.CONTENT_URI;
		String sortOrder = BookContract.WantsToReadEntry._ID + " DESC";
		
		Log.d("WantsToReadFragment","URI: "+uri.toString());
		
		return new CursorLoader(
				getActivity(),
				uri,
				WANTS_TO_READ_COLUMNS,
				null,
				null,
				sortOrder
				);
	}
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.wants_to_read,
				container, false);
		
		mWantsToReadAdapter = new SimpleCursorAdapter(
				getActivity(),
				R.layout.book_view,
				null,
				new String[]{
					BookContract.WantsToReadEntry.COLUMN_TITLE,
					BookContract.WantsToReadEntry.COLUMN_AUTHORS,
					BookContract.WantsToReadEntry.COLUMN_DESCRIPTION,
					BookContract.WantsToReadEntry.COLUMN_RATING,
					BookContract.WantsToReadEntry.COLUMN_IMAGE
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
		mWantsToReadAdapter.setViewBinder(new WantsToReadBookViewBinder());
		
		ListView listView = (ListView) rootView.findViewById(R.id.wantsToReadList);
		registerForContextMenu(listView);
		listView.setAdapter(mWantsToReadAdapter);
		
		return rootView;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getActivity().getMenuInflater();
	    inflater.inflate(R.menu.library_book_wants_context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();		
	    switch (item.getItemId()) {
	        case R.id.menu_libraryWantsRemove:{		        	
	        	Cursor cursor = (Cursor)mWantsToReadAdapter.getItem(info.position);
	        	long id = cursor.getLong(COL_BOOK_ID);
	        	Uri uri = WantsToReadEntry.buildWantsToReadUri(id);
	        	Log.d("WANTSTOREAD DELETE","URI:"+uri);
	        	getActivity().getContentResolver().delete(uri, WantsToReadEntry.TABLE_NAME+"._ID=?",new String[]{Long.toString(id)});
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
		mWantsToReadAdapter.swapCursor(data);		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mWantsToReadAdapter.swapCursor(null);
		
	}

}
