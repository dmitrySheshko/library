package com.library.books;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
