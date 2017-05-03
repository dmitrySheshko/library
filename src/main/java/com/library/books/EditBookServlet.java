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

@WebServlet("/book/*")
public class EditBookServlet extends HttpServlet {
	private DAOCategory daoCategory = new DAOCategory();
	private BookService bookService = new BookService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Book book = bookService.find(request.getPathInfo());
		ArrayList<Category> categories = null;
		try {
			categories = daoCategory.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("book", book);
		request.setAttribute("categories", categories);
		response.setContentType("text/html");
        request.getRequestDispatcher("/views/books/edit-book.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		bookService.editBook(request);
		response.sendRedirect("/books");
		
		/*Book book = bookService.find(request.getPathInfo());
		ArrayList<Category> categories = null;
		try {
			categories = daoCategory.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("book", book);
		request.setAttribute("categories", categories);
		response.setContentType("text/html");
        request.getRequestDispatcher("/views/books/edit-book.jsp").forward(request, response);*/
	}
}
