package com.library.office;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/edit-profile")
public class OfficeEditProfileServlet extends HttpServlet {
	private OfficeService officeService = new OfficeService();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Error error = officeService.editProfileData(request);
		if(error == null) {
			response.sendRedirect("/office");
		}
		else {
			officeService.getUser(request);
			request.setAttribute("error", error);
			response.setContentType("text/html");
	        request.getRequestDispatcher("/views/office/office.jsp").forward(request, response);
		}
	}
}
