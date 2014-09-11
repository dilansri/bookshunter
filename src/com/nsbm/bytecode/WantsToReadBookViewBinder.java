package com.nsbm.bytecode;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.nsbm.bytecode.data.BookContract;

/**
 * Binding rating's value and image byte stream to book_view.xml views.
 * Separate class for Already Read Books is used for extensibility.
 *
 */
public class WantsToReadBookViewBinder implements SimpleCursorAdapter.ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		if (columnIndex == cursor.getColumnIndex(BookContract.WantsToReadEntry.COLUMN_IMAGE)){
			byte[] imgBytes = cursor.getBlob(columnIndex);
			Bitmap imageBitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
			ImageView coverImage = (ImageView) view.findViewById(R.id.book_coverImage);
			if( imageBitmap != null){
				coverImage.setImageBitmap(imageBitmap);
			}else{
				coverImage.setImageResource(R.drawable.ic_book);
			}
			
			Log.d("ViewBinder",columnIndex+":index");
			
			return true;
		}else if(columnIndex == cursor.getColumnIndex(BookContract.WantsToReadEntry.COLUMN_RATING)){
			float rating = cursor.getFloat(columnIndex);
			RatingBar ratingBar = (RatingBar)view.findViewById(R.id.book_ratingBar);
			ratingBar.setRating(rating);
			return true;
		}
		return false;
	}

}
