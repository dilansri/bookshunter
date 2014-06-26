package com.nsbm.bytecode;

public class Book {
	private String title;
	private String authors;
	private String description;
	private float rating;
	private String imageUrl;	
	
	public Book(String title, String authors, String description, float rating,
			String imageUrl) {
		super();
		this.title = title;
		this.authors = authors;
		this.description = description;
		this.rating = rating;
		this.imageUrl = imageUrl;
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
	
	public String getImageUrl() {
		return imageUrl;
	}
	
}
