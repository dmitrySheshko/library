package com.library.books;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.category.Category;
import com.library.category.DAOCategory;
import com.library.errors.ValidationError;

@WebServlet("/add-book")
public class AddBookServlet extends HttpServlet {
	private DAOCategory daoCategory = new DAOCategory();
	private BookService bookService = new BookService();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Category> categories = null;
		try {
			categories = daoCategory.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("categories", categories);
		response.setContentType("text/html");
        request.getRequestDispatcher("/views/books/add-book.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Book book = new Book(request);
		ArrayList<ValidationError> errors = bookService.bookValidation(book);
		if(errors.size() != 0){
			request.setAttribute("errors", errors);
			request.setAttribute("book", book);
			doGet(request, response);
		}
		else {
			try {
				errors = bookService.create(book);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if(errors != null && errors.size() != 0){
				request.setAttribute("book", book);
				request.setAttribute("errors", errors);
				doGet(request, response);
			}
			else {
				request.setAttribute("message", "The book was created!");
				doGet(request, response);
			}
		}
	}
	
}
