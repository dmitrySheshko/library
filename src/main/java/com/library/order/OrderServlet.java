package com.library.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/order/*"})
public class OrderServlet extends HttpServlet {

	OrderService orderService = new OrderService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Order order = orderService.getOrder(request.getPathInfo());
		if(order != null){
			request.getSession().setAttribute("order", order);
			request.getRequestDispatcher("/views/order/order.jsp").forward(request, response);
		}
	}
}
