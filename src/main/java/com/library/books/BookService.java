package com.library.books;

import java.util.ArrayList;

public class BookService {

	private DAOBook daoBook = new DAOBook();
	
	public ArrayList<Book> getBooks(){
		return daoBook.findAll();
	}
}
