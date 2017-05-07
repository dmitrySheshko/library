package com.library.order;

import javax.servlet.http.HttpServletRequest;

public class OrderFilter {
	private String readerFirstName;
	private String readerLastName;
	private String title;
	private String author;
	private int status;
	
	public OrderFilter(){}
	
	public OrderFilter(String readerFirstName, String readerLastName, String title, String author, int status){
		this.readerFirstName = readerFirstName;
		this.readerLastName = readerLastName;
		this.title = title;
		this.author = author;
		this.status = status;
	}
	
	public OrderFilter(HttpServletRequest request){
		this.readerFirstName = request.getParameter("readerFirstName");
		this.readerLastName = request.getParameter("readerLastName");
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReaderFirstName() {
		return readerFirstName;
	}

	public void setReaderFirstName(String readerFirstName) {
		this.readerFirstName = readerFirstName;
	}

	public String getReaderLastName() {
		return readerLastName;
	}

	public void setReaderLastName(String readerLastName) {
		this.readerLastName = readerLastName;
	}
}
