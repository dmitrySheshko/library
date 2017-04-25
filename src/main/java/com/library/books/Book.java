package com.library.books;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.library.category.Category;

public class Book {
	private int id;
    private String title;
    private String author;
    private List<String> exemplars;
    private Category category;
    
    public Book(){}
    
    public Book(HttpServletRequest request){
    	this.title = request.getParameter("title");
    	this.author = request.getParameter("author");
    	this.category = new Category(Integer.parseInt(request.getParameter("category")), "");
    	String[] exemplars = request.getParameterValues("exemplarNumber");
    	if(exemplars != null){
    		this.exemplars = Arrays.asList(exemplars);
    	}
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
}
