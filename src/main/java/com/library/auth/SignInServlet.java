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
			User user = new User();
			try {
				user = authService.findUser(login, password);
			} catch (SQLException e) {
				e.printStackTrace();
				//TODO db error
				errors.add(new ValidationError("Undefined", "An undefined error, try again, please!"));
				request.setAttribute("errors", errors);
				request.setAttribute("login", login);
				doGet(request, response);
			}
			if(user != null){
                request.getSession().setAttribute("sessionUser", user);
                switch (user.getRole()){
                case 1://ADMIN
                    response.sendRedirect("/auth/sign-up");//redirect to books page if a reader
                    break;
                case 2://LIBRARIAN
                    response.sendRedirect("/orders");//redirect to books page if a reader
                    break;
                case 3://READER
                    response.sendRedirect("/books");//redirect to books page if a reader
                    break;
                default:
                    response.sendRedirect("/");//redirect to main page
                    break;
            }
            }
			else {
				//TODO User not found
				errors.add(new ValidationError("login", "User with current login and password wasn't found"));
				request.setAttribute("errors", errors);
				request.setAttribute("login", login);
				doGet(request, response);
			}
		}
	}

}
