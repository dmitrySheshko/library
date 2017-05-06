package com.library.order;

import javax.servlet.http.HttpServletRequest;

public class OrderFilter {
	private String readerFullName;
	private String title;
	private String author;
	private int status;
	
	public OrderFilter(){}
	
	public OrderFilter(String readerFullName, String title, String author, int status){
		this.readerFullName = readerFullName;
		this.title = title;
		this.author = author;
		this.status = status;
	}
	
	public OrderFilter(HttpServletRequest request){
		this.readerFullName = request.getParameter("reader");
		this.title = request.getParameter("title");
		this.author = request.getParameter("author");
		if(request.getParameter("status") != null) {
			this.status = Integer.parseInt(request.getParameter("status"));
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

	public String getReaderFullName() {
		return readerFullName;
	}

	public void setReaderFullName(String readerFullName) {
		this.readerFullName = readerFullName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
