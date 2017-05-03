package com.library.books;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.library.category.Category;
import com.library.errors.ValidationError;
import com.library.exemplars.DAOExemplar;

public class BookService {

	private DAOBook daoBook = new DAOBook();
	private DAOExemplar daoExemplar = new DAOExemplar();
	
	public ArrayList<Book> getBooks() throws SQLException{
		return daoBook.findAll();
	}
	
	public ArrayList<ValidationError> bookValidation(Book book){
		ArrayList<ValidationError> errors = new ArrayList<ValidationError>();
		//
		ValidationError titleError = isEmpty(book.getTitle(), "title", "Title field is empty!");
        if(titleError != null){
            errors.add(titleError);
        }
        //
        ValidationError authorError = isEmpty(book.getAuthor(), "author", "Author field is empty!");
        if(authorError != null){
            errors.add(authorError);
        }
        //
        ValidationError categoryError = checkCategory(book.getCategory());
        if(categoryError != null){
            errors.add(categoryError);
        }
        //
        ValidationError exemplarsError = checkExemplars(book.getExemplars());
        if(exemplarsError != null){
            errors.add(exemplarsError);
        }
		return errors;
	}
	
	public ValidationError isEmpty(String value, String fieldName, String errorMessage){
		if(value == null || value.trim().length() == 0){
            return new ValidationError(fieldName, errorMessage);
        }
		return null;
	}
	
	public ValidationError checkCategory(Category category){
		if(category.getId() == 0){
            return new ValidationError("category", "Please, choose category!");
        }
		return null;
	}
	
	public ValidationError checkExemplars(List<String> exemplars){
		if(exemplars == null || exemplars.size() == 0){
			return new ValidationError("exemplars", "Please, check exemplars numbers!");
		}
		else {
			for(String exemplar : exemplars){
				if(exemplar.trim().length() == 0){
					return new ValidationError("exemplars", "Please, check exemplars numbers!");
				}
			}
		}
		return null;
	}
	
	public ArrayList<ValidationError> create(Book book) throws SQLException{
		ArrayList<ValidationError> error = new ArrayList<ValidationError>();
		int bookId = daoBook.create(book);
		if(bookId != 0){
			try {
				daoExemplar.saveExemplars(book.getExemplars(), bookId);
			}
			catch(SQLException e){
				error.add(new ValidationError("exemplars", "Please, check book's exemplars numbers!"));
				daoBook.delete(bookId);
			}
		}
		else {
			error.add(new ValidationError("undefined", "Ups, something wrong!"));
		}
		return error;
	}
	
	public Book find(String urlPath){
		int bookId = getBookIdFromPath(urlPath);
		Book book = null;
		try {
			book = daoBook.find(bookId);
			book.setExemplars(getBookExemplars(bookId));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;
	}
	
	private List<String> getBookExemplars(int bookId) throws SQLException{
		return daoExemplar.findByBookId(bookId);
	}
	
	private int getBookIdFromPath(String urlPath){
		return Integer.parseInt(urlPath.substring(1));
	}
	
	public void editBook(HttpServletRequest request){
		Book newBook = new Book(request);
		Book oldBook = find(request.getPathInfo());
		int bookId = getBookIdFromPath(request.getPathInfo());
		newBook.setId(bookId);
		
		List<String> newExemplars = findNewBookExemplars(newBook.getExemplars(), oldBook.getExemplars());
		if(newExemplars.size() != 0) {
			try {
				daoExemplar.saveExemplars(newExemplars, bookId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		List<String> deletedExemplars = findDeletedBookExemplars(newBook.getExemplars(), oldBook.getExemplars());
		if(deletedExemplars.size() != 0) {
			
		}
		//save new exemplars
		//chack deleted
		//delete old exemplars
		
		try {
			daoBook.update(newBook);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List<String> findNewBookExemplars(List<String> newBookExemplars, List<String> oldBookExemplars){
		List<String> newExemplars = new ArrayList<String>();
		for(String exemplar : newBookExemplars){
			if(!oldBookExemplars.contains(exemplar)){
				newExemplars.add(exemplar);
			}
		}
		return newExemplars;
	}
	
	private List<String> findDeletedBookExemplars(List<String> newBookExemplars, List<String> oldBookExemplars){
		List<String> deletedExemplars = new ArrayList<String>();
		for(String exemplar : oldBookExemplars){
			if(!oldBookExemplars.contains(exemplar)){
				deletedExemplars.add(exemplar);
			}
		}
		return deletedExemplars;
	}
}
