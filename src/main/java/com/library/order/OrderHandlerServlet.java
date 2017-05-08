package com.library.order;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.errors.ValidationError;
import com.library.users.User;

@WebServlet("/order-handler")
public class OrderHandlerServlet extends HttpServlet {
	OrderService orderService = new OrderService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		orderService.orderHandler(request);
		User user = (User)request.getSession().getAttribute("sessionUser");
		if(user != null) {
			if(user.getRole() == 3) {
				response.sendRedirect("/office/orders");
			}
			else {
				response.sendRedirect("/orders");			
			}
		}
		else {
			response.sendRedirect("/auth/sign-in");	
		}		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ValidationError error = orderService.saveOrder(request);
			if(error != null){
				request.getSession().setAttribute("orderError", error);
				request.getRequestDispatcher("/views/order/order-error.jsp").forward(request, response);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/office/orders");
	}
}
