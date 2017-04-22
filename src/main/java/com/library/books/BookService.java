package com.library.books;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookService {

	private DAOBook daoBook = new DAOBook();
	
	public ArrayList<Book> getBooks() throws SQLException{
		return daoBook.findAll();
	}
}
