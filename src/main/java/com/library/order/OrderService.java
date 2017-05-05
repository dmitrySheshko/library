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
		if(bookIds == null || bookIds.length == 0){
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
	
	public void orderHandler(HttpServletRequest request){
		User librarian = getUserFromSession(request);
		String requestType = (String)request.getParameter("type");
		int orderType = 0;
		String reqOrderType = request.getParameter("orderType");
		if(reqOrderType != null){
			orderType = Integer.parseInt(request.getParameter("orderType"));
		}
		int orderId = Integer.parseInt(request.getParameter("id"));
		if(requestType.equals("issue")){
			try {
				daoOrder.issueBook(orderId, librarian.getId(), orderType);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(requestType.equals("return")){
			try {
				daoOrder.returnBook(orderId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(requestType.equals("cancel")){
			try {
				daoOrder.changeStatus(orderId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
