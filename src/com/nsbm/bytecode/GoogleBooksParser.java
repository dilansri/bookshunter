package com.nsbm.bytecode;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class GoogleBooksParser {
	
	private JSONObject response;
	private List<Book> resultBooks;
	private int resultSize =0;
	
	public final static int GENRE_RESULT_SIZE = 5;
	
	public GoogleBooksParser(JSONObject res)
	{
		this.response = res;
		resultBooks = new ArrayList<Book>();
		resultSize = findResultSize();
	}

	public JSONObject getResponse() {
		return response;
	}

	public void setResponse(JSONObject response) {
		this.response = response;
	}

	public List<Book> getResultBooks() {
		return resultBooks;
	}
	
	public List<Book> parse(){		
		int size = resultSize > GENRE_RESULT_SIZE ? GENRE_RESULT_SIZE : resultSize;
		
		try{
	        JSONArray itemsArray = response.getJSONArray("items");	        	        
	        for(int i=0;i<size;i++){	
	        	JSONObject item = itemsArray.getJSONObject(i);
	        	JSONObject volumeInfo = item.getJSONObject("volumeInfo");
	        	String title = volumeInfo.optString("title");
	        	String authors = volumeInfo.getJSONArray("authors").toString();
	        	String description = volumeInfo.optString("description");
	        	Float rating = (float) volumeInfo.optDouble("averageRating");
	        	String imageUrl = volumeInfo.getJSONObject("imageLinks").optString("thumbnail");
	        	Bitmap image = getImageFromUrl(imageUrl);
	        	
	        	Book bookItem = new Book(title,authors,description,rating,image);	        	
	        	resultBooks.add(bookItem);
	        }
	        
		}catch(JSONException je){
			Log.e(getClass().getName(),je.getMessage());
		}
        //Log.d(getClass().getName(), "Total Items: " + totalItems);
        //Log.d(getClass().getName(), "Title: " + title);
        
		return resultBooks;
	}

	private int findResultSize() {
		try{
			resultSize = Integer.parseInt(response.optString("totalItems"));
		}catch(Exception e){
			Log.e(getClass().getName(),e.getMessage());
			return 0;
		}
		
		return resultSize;
	}
	
	private Bitmap getImageFromUrl(String url){
    	Bitmap bitmap = null;
    	
    	try {
			bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return bitmap;
    }

}