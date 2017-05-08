package com.library.office;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/office/orders")
public class OfficeOrdersServlet extends HttpServlet {
	private OfficeService officeService = new OfficeService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		officeService.getUserActiveOrders(request);
        request.getRequestDispatcher("/views/office/office-orders.jsp").forward(request, response);
	}
}
