package com.library.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.books.Book;
import com.library.books.BookService;
import com.library.errors.ValidationError;

@WebServlet(urlPatterns = {"/order", "/order/*"})
public class UserOrderServlet extends HttpServlet {

	OrderService orderService = new OrderService();
	BookService bookService = new BookService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Order order = orderService.getOrder(request.getPathInfo());
		if(order != null){
			request.getSession().setAttribute("order", order);
			request.getRequestDispatcher("/views/order/order.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ValidationError error = orderService.saveOrder(request);
			if(error != null){
				ArrayList<Book> books = bookService.getBooks();
				request.setAttribute("books", books);
				request.getSession().setAttribute("orderError", error);
				request.getRequestDispatcher("/views/order/order-error.jsp").forward(request, response);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/office");
	}
}
