package com.nsbm.bytecode.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Database and Content Provider contracts.
 * Defines the database tables,columns.
 *
 */
public class BookContract {
	
	/**
	 * Content Provider unique authority string. 
	 */
	public static final String CONTENT_AUTHORITY = "com.nsbm.bytecode.bookshunter";	
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);	
	
	/**
	 * Path for already_read books table from content provider
	 */
	public static final String PATH_ALREADY_READ_BOOK = "already_read";	
	
	/**
	 * Path for wants_to_read books table from content provider
	 */
	public static final String PATH_WANTS_TO_READ_BOOK = "wants_to_read";
	
	/**
	 * Already Read books table with Base Columns _ID and _COUNT
	 *
	 */
	public static final class AlreadyReadEntry implements BaseColumns{
		
		public static final Uri CONTENT_URI =
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_ALREADY_READ_BOOK).build();
		
		public static final String CONTENT_TYPE = 
				"vnd.android.cursor.dir/"+CONTENT_AUTHORITY + "/"+
							PATH_ALREADY_READ_BOOK;
		public static final String CONTENT_ITEM_TYPE = 
				"vnd.android.cursor.item/"+CONTENT_AUTHORITY + "/"+
							PATH_ALREADY_READ_BOOK;
		
		public static final String TABLE_NAME = "already_read_book";
		
		public static final String COLUMN_TITLE = "title";
		
		public static final String COLUMN_AUTHORS = "authors";
		
		public static final String COLUMN_DESCRIPTION = "description";
		
		public static final String COLUMN_RATING = "rating";
		
		public static final String COLUMN_IMAGE = "image";
		
		/**
		 * Creating the specific Uri for a book from its _ID
		 * @param id _ID of the book
		 * @return content provider Uri for the book
		 */
		public static Uri buildAlreadyReadReadUri(long id){
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}	
	}
	
	/**
	 * Wants To Read Book table with its columns
	 *
	 */
	public static final class WantsToReadEntry implements BaseColumns{
		
		public static final Uri CONTENT_URI =
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_WANTS_TO_READ_BOOK).build();
		
		public static final String CONTENT_TYPE = 
				"vnd.android.cursor.dir/"+CONTENT_AUTHORITY + "/"+
							PATH_WANTS_TO_READ_BOOK;
		public static final String CONTENT_ITEM_TYPE = 
				"vnd.android.cursor.item/"+CONTENT_AUTHORITY + "/"+
							PATH_WANTS_TO_READ_BOOK;
		
		public static final String TABLE_NAME = "wants_to_read_book";
		
		public static final String COLUMN_TITLE = "title";
		
		public static final String COLUMN_AUTHORS = "authors";
		
		public static final String COLUMN_DESCRIPTION = "description";
		
		public static final String COLUMN_RATING = "rating";
		
		public static final String COLUMN_IMAGE = "image";
		
		/**
		 * Creating the specific Uri for a book from its _ID
		 * @param id _ID of the book
		 * @return content provider Uri for the book
		 */
		public static Uri buildWantsToReadUri(long id){
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}		
		
	}

}
