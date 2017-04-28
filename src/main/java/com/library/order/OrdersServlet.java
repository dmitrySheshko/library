package com.library.order;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
	OrderService orderService = new OrderService();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Order> orders = orderService.getOrders();
		if(orders != null){
			request.setAttribute("orders", orders);
		}
		request.getRequestDispatcher("/views/order/orders.jsp").forward(request, response);
	}
}
