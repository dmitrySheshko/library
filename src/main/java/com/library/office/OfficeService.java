package com.library.office;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.library.order.DAOOrder;
import com.library.order.Order;
import com.library.users.DAOUser;
import com.library.users.User;

public class OfficeService {
	private DAOUser daoUser = new DAOUser();
	private DAOOrder daoOrder = new DAOOrder();
	
	public void getUser(HttpServletRequest request) {
		User user = getUserFromDB(request);
		if(user == null){
			request.setAttribute("message", "User not found!");
		}
		else {
			request.setAttribute("user", user);
		}
	}
	
	public Error editProfileData(HttpServletRequest request) {
		Error error = null;
		User oldUserInfo = getUserFromDB(request);
		if(oldUserInfo != null) {
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			String confirmPassword = request.getParameter("confirmPassword");
			//если пароль был изменен, то проверяем его
			if(isPasswordChange(oldPassword, newPassword, confirmPassword)) {
				//правильно ли он ввел свой старый пароль
				if(oldPassword.equals(oldUserInfo.getPassword())) {
					//на совпадение newPassword и confirmPassword
					if(newPassword.equals(confirmPassword)) {
						//сохранить в базу новый пароль
						try {
							oldUserInfo.setPassword(newPassword);
							daoUser.update(oldUserInfo);
						} catch (SQLException e) {
							error = new Error("Uppps, something is wrong!");
						}
					}
					else {
						error = new Error("A confirm password don't match to a new password!");
					}
				}
				else {
					error = new Error("You entered a wrong old password!");
				}
			}
		}
		else {
			error = new Error("User not found!");
		}
		return error;
	}
	
	private User getUserFromDB(HttpServletRequest request) {
		User sessionUser = (User)request.getSession().getAttribute("sessionUser");
		User user = null;
		if(sessionUser != null) {
			try {
				user = daoUser.find(sessionUser.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	private Boolean isPasswordChange(String oldPassword, String newPassword, String confirmPassword) {
		return checkRequestData(oldPassword) || checkRequestData(newPassword) || checkRequestData(confirmPassword);
	}
	
	private Boolean checkRequestData(String param) {
		return param != null && param.trim().length() != 0;
	}
	
	public void getUserActiveOrders(HttpServletRequest request) {
		User user = getUserFromDB(request);
		if(user != null){
			try {
				List<Order> userActiveOrders = daoOrder.findOrdersByUser(user.getId());
				request.setAttribute("orders", userActiveOrders);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
