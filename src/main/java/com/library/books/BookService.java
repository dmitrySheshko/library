package com.library.books;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.library.category.Category;
import com.library.category.DAOCategory;
import com.library.errors.ValidationError;
import com.library.exemplars.DAOExemplar;
import com.library.exemplars.Exemplar;
import com.library.order.DAOOrder;

public class BookService {

	private DAOBook daoBook = new DAOBook();
	private DAOExemplar daoExemplar = new DAOExemplar();
	private DAOOrder daoOrder = new DAOOrder();
	private DAOCategory daoCategory = new DAOCategory();
	
	public List<Book> getBooks(HttpServletRequest request) {
		List<Book> books = new ArrayList<Book>();
		BookFilter bookFilter = new BookFilter(request);
		//if is set filter params
		if(isSetFilterParams(bookFilter)){
			try {
				books = daoBook.findAll(getWhereString(bookFilter));
				request.setAttribute("filter", bookFilter);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				books = daoBook.findAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return books;
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
        //check exemplars
        Error error = checkExemplarsNumbers(book.getExemplars());
        if(error != null){
        	errors.add(new ValidationError("exemplars", error.getMessage()));
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
		Error error = null;
		Book oldBook = find(request.getPathInfo());
		Book newBook = new Book(request);
		if(oldBook == null){
			error = new Error("The book wasn't found!");
			request.setAttribute("error", error);
			request.setAttribute("book", newBook);
			return;
		}
		newBook.setId(oldBook.getId());
		newBook.setAuthor(oldBook.getAuthor());
		newBook.setTitle(oldBook.getTitle());
		//find and delete old exemplars if this ones don't used!
		error = editBookDeleteOldExemplars(newBook, oldBook);
		if(error != null) {
			newBook.setExemplars(oldBook.getExemplars());
			request.setAttribute("error", error);
			request.setAttribute("book", newBook);
			return;
		}
		//save new book's exemplars
		error = editBookSaveNewExemplars(newBook, oldBook);
		if(error != null) {
			request.setAttribute("error", error);
			request.setAttribute("book", newBook);
			return;
		}
		//save a book
		try {
			daoBook.update(newBook);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("book", newBook);
		request.setAttribute("message", "The book was edited!");
	}
	
	private List<String> findNewBookExemplars(List<String> newBookExemplars, List<String> oldBookExemplars){
		List<String> newExemplars = new ArrayList<String>();
		if(newBookExemplars != null) {
			for(String exemplar : newBookExemplars){
				if(!oldBookExemplars.contains(exemplar)){
					newExemplars.add(exemplar);
				}
			}
		}
		return newExemplars;
	}
	
	private List<String> findDeletedBookExemplars(List<String> newBookExemplars, List<String> oldBookExemplars){
		List<String> deletedExemplars = new ArrayList<String>();
		if(newBookExemplars == null){
			deletedExemplars = oldBookExemplars;
		}
		else {
			for(String exemplar : oldBookExemplars){
				if(!newBookExemplars.contains(exemplar)){
					deletedExemplars.add(exemplar);
				}
			}
		}
		return deletedExemplars;
	}
	
	private int getBookActiveOrders(int bookId){
		int count = 0;
		try {
			count = daoOrder.findActiveOrdersByBookId(bookId).size();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return count;
	}
	
	private Error editBookSaveNewExemplars(Book newBook, Book oldBook){
		List<String> newExemplars = findNewBookExemplars(newBook.getExemplars(), oldBook.getExemplars());
		Error error = null;
		if(newBook.getExemplars() != null && newBook.getExemplars().size() != 0) {
			error = checkExemplarsNumbers(newExemplars);
		}
		if(newExemplars.size() != 0 && error == null) {
			try {
				daoExemplar.saveExemplars(newExemplars, newBook.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return error;
	}
	
	private Error checkExemplarsNumbers(List<String> newExemplars){
		Error error = null;
		try {
			List<Exemplar> checkNewExemplars = daoExemplar.findAllByNumbers(newExemplars);
			if(checkNewExemplars.size() != 0) {
				error = new Error("One or more exemplar numbers are already present in the system!");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return error;
	}
	
	private Error editBookDeleteOldExemplars(Book newBook, Book oldBook){
		List<String> deletedExemplars = findDeletedBookExemplars(newBook.getExemplars(), oldBook.getExemplars());
		Error error = null;
		int bookActiveOrders = getBookActiveOrders(newBook.getId());
		if(deletedExemplars.size() != 0) {
			int oldExemplarsCount = 0;
			if(oldBook.getExemplars() != null) {
				oldExemplarsCount = oldBook.getExemplars().size();
			}
			if((oldExemplarsCount - deletedExemplars.size() - bookActiveOrders) >= 0) {
				try {
					daoExemplar.deleteByNumbers(deletedExemplars);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				error = new Error("One or more exemplars are using!");
			}
		}
		return error;
	}
	
	public List<Category> getCategories (){
		ArrayList<Category> categories = null;
		try {
			categories = daoCategory.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categories;
	}
	
	public Error deleteBook(HttpServletRequest request){
		int bookId = getBookIdFromPath(request.getPathInfo());
		if(getBookActiveOrders(bookId) != 0) {
			return new Error("You can't delete the book! Where is one or more active orders!");
		}
		//delete orders
		try {
			daoOrder.deleteAllByBookId(bookId);
		} catch (SQLException e2) {
			return new Error("Opps, something was wrong! Delete orders");
		}
		//delete exemplars
		try {
			daoExemplar.deleteAllByBookId(bookId);
		} catch (SQLException e1) {
			return new Error("Opps, something was wrong! Delete exemplars");
		}
		
		//delete book
		try {
			daoBook.delete(bookId);
		} catch (SQLException e) {
			return new Error("Opps, something was wrong! Delete book");
		}
		return null;
	}
	
	private Boolean isSetFilterParams(BookFilter bookFilter){
		return checkFilterParam(bookFilter.getTitle()) || checkFilterParam(bookFilter.getAuthor()) || checkFilterParam(bookFilter.getCategory());
	}
	
	private Boolean checkFilterParam(String param){
		return param != null && param.trim().length() != 0;
	}
	
	private Boolean checkFilterParam(int param){
		return param != 0;
	}
	
	private String getWhereString(BookFilter bookFilter){
		List<String> temp = new ArrayList<String>(); //[]
		if(checkFilterParam(bookFilter.getTitle())){
			temp.add("b.title LIKE '%" + bookFilter.getTitle() + "%'");
		}
		if(checkFilterParam(bookFilter.getAuthor())){
			temp.add("b.author LIKE '%" + bookFilter.getAuthor() + "%'");
		}
		if(checkFilterParam(bookFilter.getCategory())){
			temp.add("b.category = " + bookFilter.getCategory());
		}
		return String.join(" AND ", temp);
	}
}
