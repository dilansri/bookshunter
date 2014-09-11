package com.nsbm.bytecode.util;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.graphics.Bitmap;

import com.nsbm.bytecode.Book;
import com.nsbm.bytecode.data.BookContract.AlreadyReadEntry;

/**
 * Helper utilities for book class.
 *
 */
public class BookUtilities {
	public static ContentValues getContentValues(Book book) {
		ContentValues values = new ContentValues();
		byte[] imgBytes = getByteArrayFromBitmap(book.getImage());	
		values.put(AlreadyReadEntry.COLUMN_TITLE, book.getTitle());
		values.put(AlreadyReadEntry.COLUMN_AUTHORS, book.getAuthors());
		values.put(AlreadyReadEntry.COLUMN_DESCRIPTION, book.getDescription());
		values.put(AlreadyReadEntry.COLUMN_RATING, book.getRating());
		values.put(AlreadyReadEntry.COLUMN_IMAGE,imgBytes);
		return values;
	}

	public static byte[] getByteArrayFromBitmap(Bitmap image) {		
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();		
	}
}
