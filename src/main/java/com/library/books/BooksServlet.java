package com.library.books;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/books")
public class BooksServlet extends HttpServlet {
	
	BookService bookService = new BookService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> books = bookService.getBooks(request);
		request.setAttribute("books", books);
		request.setAttribute("categories", bookService.getCategories());
        request.getRequestDispatcher("/views/books/books.jsp").forward(request, response);
	}
}
