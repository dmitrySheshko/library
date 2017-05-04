package com.library.exemplars;

public class Exemplar {
	private int id;
	private String number;
	private int bookId;
	private int status;
	
	public Exemplar(){}
	
	public Exemplar(int id, String number, int bookId){
		this.id = id;
		this.number = number;
		this.bookId = bookId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
