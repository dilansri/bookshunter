package com.nsbm.bytecode;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class BookAdapter extends ArrayAdapter<Book> {
	
	Context context;
	List<Book> books;

	public BookAdapter(Context context, int textViewResourceId,
			List<Book> books) {
		super(context, textViewResourceId, books);		
		this.context = context;
		this.books = books;
	}
	
	public int getCount() {
        if (books != null)
            return books.size();
        return 0;
    }
 
    public Book getItem(int position) {
        if (books != null)
            return books.get(position);
        return null;
    }
 
    public long getItemId(int position) {
        if (books != null)
            return books.get(position).hashCode();
        return 0;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		View bookView = convertView;
		
		if(bookView == null){
			bookView = ((Activity)context).getLayoutInflater().inflate(R.layout.book_view,parent,false);
		}
		
		Book currentBook = books.get(position);
		
		ImageView coverImage = (ImageView)bookView.findViewById(R.id.book_coverImage);
		Bitmap coverImageBitmap =currentBook.getImage();
		if( coverImageBitmap != null){
			coverImage.setImageBitmap(coverImageBitmap);
		}else{
			coverImage.setImageResource(R.drawable.ic_book);
		}
		
		
		TextView title = (TextView)bookView.findViewById(R.id.book_title);
		RatingBar rating = (RatingBar)bookView.findViewById(R.id.book_ratingBar);
		TextView authors = (TextView)bookView.findViewById(R.id.book_authors);
		TextView description = (TextView)bookView.findViewById(R.id.book_description);
		
		title.setText(currentBook.getTitle());
		rating.setRating(currentBook.getRating());
		authors.setText(currentBook.getAuthors());
		description.setText(currentBook.getDescription());
		
		return bookView;
	}
	
	public List<Book> getItemList() {
        return books;
    }
 
    public void setItemList(List<Book> books) {
        this.books = books;
    }    
    

}