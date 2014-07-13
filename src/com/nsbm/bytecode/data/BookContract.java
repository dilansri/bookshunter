package com.nsbm.bytecode.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class BookContract {
	
	public static final String CONTENT_AUTHORITY = "com.nsbm.bytecode.bookshunter";
	
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
	
	public static final String PATH_ALREADY_READ_BOOK = "already_read";
	
	public static final String PATH_WANTS_TO_READ_BOOK = "wants_to_read";

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
		
		public static Uri buildAlreadyReadReadUri(long id){
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}	
	}
	
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
		
		public static Uri buildWantsToReadUri(long id){
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}		
		
	}

}
