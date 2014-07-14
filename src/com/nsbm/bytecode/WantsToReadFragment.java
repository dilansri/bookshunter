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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nsbm.bytecode.data.BookContract;

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
					BookContract.WantsToReadEntry.COLUMN_IMAGE
				},
				new int[]{
					R.id.book_title,
					R.id.book_authors,
					R.id.book_description,
					R.id.book_coverImage
				},
				0
				);
		mWantsToReadAdapter.setViewBinder(new WantsToReadBookViewBinder());
		
		ListView listView = (ListView) rootView.findViewById(R.id.wantsToReadList);
		listView.setAdapter(mWantsToReadAdapter);
		return rootView;
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
