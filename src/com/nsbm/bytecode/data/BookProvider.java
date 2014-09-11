package com.nsbm.bytecode.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Content Provider to query, insert , and delete books from the local storage.
 * @author Dilan
 *
 */
public class BookProvider extends ContentProvider {
	
	private static final int ALREADY_READ_BOOK = 100;
	private static final int ALREADY_READ_BOOK_ID = 101;
	
	private static final int WANTS_TO_READ_BOOK = 300;
	private static final int WANTS_TO_READ_BOOK_ID = 301;
	
	
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	
	private BookDbHelper mOpenHelper;
	
	private static UriMatcher buildUriMatcher() {
		
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		final String authority = BookContract.CONTENT_AUTHORITY;
		
		matcher.addURI(authority, BookContract.PATH_ALREADY_READ_BOOK, ALREADY_READ_BOOK);
		matcher.addURI(authority, BookContract.PATH_ALREADY_READ_BOOK+"/#", ALREADY_READ_BOOK_ID);
		
		matcher.addURI(authority, BookContract.PATH_WANTS_TO_READ_BOOK, WANTS_TO_READ_BOOK);
		matcher.addURI(authority, BookContract.PATH_WANTS_TO_READ_BOOK+"/#", WANTS_TO_READ_BOOK_ID);
		
		return matcher;
		
	}
	
	@Override
	public boolean onCreate() {
		mOpenHelper = new BookDbHelper(getContext());
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		Cursor retCursor;
		
		switch(sUriMatcher.match(uri)){
		
			case ALREADY_READ_BOOK :
			{
				retCursor = mOpenHelper.getReadableDatabase().query(
							BookContract.AlreadyReadEntry.TABLE_NAME,
							projection,
							selection,
							selectionArgs,
							null,
							null,
							sortOrder
						);
				break;
			}
			case ALREADY_READ_BOOK_ID:
			{
				retCursor = null;
				break;
			}
			
			case WANTS_TO_READ_BOOK :
			{
				retCursor = mOpenHelper.getReadableDatabase().query(
						BookContract.WantsToReadEntry.TABLE_NAME,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder
					);
				break;
			}
			
			case WANTS_TO_READ_BOOK_ID:
			{
				retCursor = null;
				break;
			}
			
			default:
				throw new UnsupportedOperationException("Unknow uri:"+uri);
		}
		retCursor.setNotificationUri(getContext().getContentResolver(), uri);
		return retCursor;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		
		Uri returnUri;
		switch(match){
			case ALREADY_READ_BOOK:
			{
				long _id = db.insert(BookContract.AlreadyReadEntry.TABLE_NAME, null, contentValues);
				
				if(_id > 0)
					returnUri = BookContract.AlreadyReadEntry.buildAlreadyReadReadUri(_id);
				else
					throw new android.database.SQLException("Failed to insert row into "+uri);
				break;
			}
			case WANTS_TO_READ_BOOK:
			{
				long _id = db.insert(BookContract.WantsToReadEntry.TABLE_NAME, null, contentValues);
				
				if(_id > 0)
					returnUri = BookContract.WantsToReadEntry.buildWantsToReadUri(_id);
				else
					throw new android.database.SQLException("Failed to insert row into "+uri);
				break;
			}
			default:
				throw new UnsupportedOperationException("Unsupported URI:"+uri);
		}
		return returnUri;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count = 0;
		switch(sUriMatcher.match(uri)){
			case ALREADY_READ_BOOK_ID:{
				count = db.delete(BookContract.AlreadyReadEntry.TABLE_NAME, where, whereArgs);
				break;
			}
			case WANTS_TO_READ_BOOK_ID:{
				count = db.delete(BookContract.WantsToReadEntry.TABLE_NAME, where, whereArgs);
				break;
			}
		}		
		getContext().getContentResolver().notifyChange(uri, null);		
		
		return count;
	}

	

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
