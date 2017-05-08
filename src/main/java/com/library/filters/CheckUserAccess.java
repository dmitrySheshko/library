package com.library.filters;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.library.users.User;

public class CheckUserAccess {
	
	public User getUser(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest)request;
		return (User)req.getSession().getAttribute("sessionUser");
	}
}
