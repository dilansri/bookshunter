package com.nsbm.bytecode.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nsbm.bytecode.data.BookContract.AlreadyReadEntry;
import com.nsbm.bytecode.data.BookContract.WantsToReadEntry;

/**
 * Creating SQLite database with two tables.
 *
 */
public class BookDbHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "book.db";

	public BookDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		
		final String SQL_CREATE_ALREADY_READ_BOOK_TABLE =
				"CREATE TABLE "+AlreadyReadEntry.TABLE_NAME + " (" +
						AlreadyReadEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
						AlreadyReadEntry.COLUMN_TITLE +" TEXT NOT NULL," +
						AlreadyReadEntry.COLUMN_AUTHORS + " TEXT NOT NULL,"+
						AlreadyReadEntry.COLUMN_DESCRIPTION + " TEXT," +
						AlreadyReadEntry.COLUMN_RATING + " REAL,"+
						AlreadyReadEntry.COLUMN_IMAGE + " BLOB);";
		
		final String SQL_CREATE_WANTS_TO_READ_BOOK_TABLE =
				"CREATE TABLE "+WantsToReadEntry.TABLE_NAME + " (" +
						WantsToReadEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
						WantsToReadEntry.COLUMN_TITLE +" TEXT NOT NULL," +
						WantsToReadEntry.COLUMN_AUTHORS + " TEXT NOT NULL,"+
						WantsToReadEntry.COLUMN_DESCRIPTION + " TEXT," +
						WantsToReadEntry.COLUMN_RATING + " REAL,"+
						WantsToReadEntry.COLUMN_IMAGE + " BLOB);";
		
		db.execSQL(SQL_CREATE_ALREADY_READ_BOOK_TABLE);
		db.execSQL(SQL_CREATE_WANTS_TO_READ_BOOK_TABLE);
						
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+AlreadyReadEntry.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+WantsToReadEntry.TABLE_NAME);		
		onCreate(db);
	}
	
}
