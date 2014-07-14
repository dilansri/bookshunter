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
		mAlreadyReadAdapter.setViewBinder(new AlreadyReadBookViewBinder());
		
		ListView listView = (ListView) rootView.findViewById(R.id.alreadyReadList);
		listView.setAdapter(mAlreadyReadAdapter);
		return rootView;
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
