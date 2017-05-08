package com.library.books;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/book/*")
public class EditBookServlet extends HttpServlet {
	
	private BookService bookService = new BookService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Book book = bookService.find(request.getPathInfo());	
		request.setAttribute("book", book);
		request.setAttribute("categories", bookService.getCategories());
        request.getRequestDispatcher("/views/books/edit-book.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		bookService.editBook(request);
		request.setAttribute("categories", bookService.getCategories());
        request.getRequestDispatcher("/views/books/edit-book.jsp").forward(request, response);
	}
}
