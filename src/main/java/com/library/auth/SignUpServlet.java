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

@WebServlet(urlPatterns = {"/auth", "/auth/sign-up"})
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AuthService authService = new AuthService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/auth/sign-up.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User(request);
		ArrayList<ValidationError> errors = authService.signUpValidation(user);
		if(errors.size() != 0){
			request.setAttribute("errors", errors);
			request.setAttribute("user", user);
			doGet(request, response);//TODO answer a question
		}
		else {
			try {
				int newUserId = authService.create(user); 
				if(newUserId != 0) {
					User admin = (User)request.getSession().getAttribute("sessionUser");//is ADMIN
	                if(admin == null){
	                    user.setId(newUserId);
	                	request.getSession().setAttribute("sessionUser", user);
	                }
				}
				else {
					//TODO message something wrong!
				}
			} catch (SQLException e) {
				//TODO message something wrong!
			}
			if(user.getRole() == 3){
                response.sendRedirect("/books");//redirect to books page if a reader
            }
            else {
            	doGet(request, response);//TODO: show a message "An user was created!"
            }
		}
	}

}
