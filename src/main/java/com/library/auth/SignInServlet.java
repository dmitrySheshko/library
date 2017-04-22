package com.library.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.errors.ValidationError;
import com.library.users.User;

@WebServlet("/auth/sign-in")
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AuthService authService = new AuthService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/auth/sign-in.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		ArrayList<ValidationError> errors = authService.signInValidation(login, password);
		if(errors.size() != 0){
			request.setAttribute("errors", errors);
			request.setAttribute("login", login);
			doGet(request, response);
		}
		else {
			try {
				User user = authService.findUser(login, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
