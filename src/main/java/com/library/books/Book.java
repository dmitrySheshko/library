package com.library.books;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.library.category.Category;

public class Book {
	private int id;
    private String title;
    private String author;
    private List<String> exemplars;
    private Category category;
    private int count; 
    private int issueCount;
    private int waitCount;
    
    public Book(){}
    
    public Book(HttpServletRequest request){
    	this.title = request.getParameter("title");
    	this.author = request.getParameter("author");
    	this.category = new Category(Integer.parseInt(request.getParameter("category")), "");
    	String[] exemplars = request.getParameterValues("exemplarNumber");
    	
    	if(exemplars != null && exemplars.length != 0){
    		this.exemplars = new ArrayList<String>();
    		for(String exemplar : exemplars) {
    			if(exemplar.trim().length() != 0) {
    				this.exemplars.add(exemplar);
    			}
    		}
    	}
    }
    
    public Book(int id, String title, String author){
    	this.id = id;
    	this.title = title;
    	this.author = author;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setExemplars(List<String> exemplars){
        this.exemplars = exemplars;
    }

    public List<String> getExemplars(){
        return this.exemplars;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public Category getCategory(){
        return this.category;
    }

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}

	public int getWaitCount() {
		return waitCount;
	}

	public void setWaitCount(int waitCount) {
		this.waitCount = waitCount;
	}
}
