package com.library.books;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/delete-book/*")
public class DeleteBookServlet extends HttpServlet {
	private BookService bookService = new BookService();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Error error = bookService.deleteBook(request);
		if(error != null){
			Book book = bookService.find(request.getPathInfo());
			request.setAttribute("error", error);
			request.setAttribute("book", book);
			request.setAttribute("categories", bookService.getCategories());
			response.setContentType("text/html");
	        request.getRequestDispatcher("/views/books/edit-book.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("/books");
		}
	}
}
