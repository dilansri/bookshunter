package com.nsbm.bytecode;

import android.graphics.Bitmap;

public class Book {
	private String title;
	private String authors;
	private String description;
	private float rating;
	private Bitmap image;	
	
	public Book(String title, String authors, String description, float rating,
			Bitmap imageUrl) {
		super();
		this.title = title;
		this.authors = authors;
		this.description = description;
		this.rating = rating;
		this.image = imageUrl;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthors() {
		return authors;
	}
	
	public String getDescription() {
		return description;
	}	
	
	public float getRating() {
		return rating;
	}
	
	public Bitmap getImage() {
		return image;
	}
	
}
