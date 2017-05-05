package com.library.books;

import javax.servlet.http.HttpServletRequest;

public class BookFilter {
	private String title;
	private String author;
	private int category;
	
	public BookFilter(){}
	
	public BookFilter(String title, String author, int category){
		this.title = title;
		this.author = author;
		this.category = category;
	}
	
	public BookFilter(HttpServletRequest request){
		this.title = request.getParameter("title");
		this.author = request.getParameter("author");
		if(request.getParameter("category") != null) {
			this.category = Integer.parseInt(request.getParameter("category"));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
}
