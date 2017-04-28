package com.library.order;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.library.errors.ValidationError;
import com.library.users.User;

public class OrderService {
	
	private DAOOrder daoOrder = new DAOOrder();

	public ValidationError saveOrder(HttpServletRequest request) throws SQLException {
		String[] bookIds = getBookIds(request);
		User reader = getUserFromSession(request);
		if(reader == null){
			return new ValidationError("user", "Sign in, please!");
		}
		if(bookIds.length == 0){
			return new ValidationError("books", "Choose one or more books, please!");
		}
		if(checkForSameBooks(reader.getId(), bookIds)){
			return new ValidationError("books", "One or more books were selected earlier!");
		}
		daoOrder.create(reader.getId(), bookIds);
		return null;
	}

	public Boolean checkForSameBooks(int readerId, String[] bookIds) throws SQLException {
		return daoOrder.findOrdersByUserAndBookIds(readerId, bookIds);
	}
	
	public User getUserFromSession(HttpServletRequest request){
		return (User)request.getSession().getAttribute("sessionUser");
	}
	
	public String[] getBookIds(HttpServletRequest request){
		/*String[] bookIds = request.getParameterValues("bookIds");
		int[] ids = new int[bookIds.length];
		for(int i = 0; i < bookIds.length; i++){
			ids[i] = Integer.parseInt(bookIds[i]);
		}
		return ids;*/
		return request.getParameterValues("bookIds");
	}
	
	public ArrayList<Order> getOrders(){
		try {
			return daoOrder.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Order getOrder(String urlPath){
		int orderId = getOrderIdFromPath(urlPath);
		try {
			return daoOrder.find(orderId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private int getOrderIdFromPath(String urlPath){
		return Integer.parseInt(urlPath.substring(1));
	}
}
