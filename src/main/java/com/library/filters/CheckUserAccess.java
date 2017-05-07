package com.library.filters;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.library.users.User;

public class CheckUserAccess {
	List<String> guestAccessUrls;
	List<String> readerAccessUrls;
	List<String> librarianAccessUrls;
	List<String> adminAccessUrls;
	
	public CheckUserAccess() {
		setGuestUrls();
		setReaderUrls();
		setLibrarianUrls();
	}
	
	public Boolean checkAccess(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest)request;
		User user = getUser(req);
		String url = req.getRequestURI();
		Boolean result = false;
		if(user != null) {
			switch(user.getRole()){
				case 1:
					result = check(adminAccessUrls, url);
					break;
				case 2:
					result = check(librarianAccessUrls, url);
					break;
				case 3:
					result = check(readerAccessUrls, url);
					break;
				default:
					result = check(guestAccessUrls, url);
					break;
			}
		}
		else {
			result = check(guestAccessUrls, url);
		}
		
		return result;
	}
	
	private User getUser(HttpServletRequest request) {
		return (User)request.getSession().getAttribute("sessionUser");
	}
	
	private Boolean check(List<String> urls, String url) {
		return urls.contains(url);
	}
	
	private List<String> setCommonUrls(List<String> urls) {
		urls = new ArrayList<String>();
		urls.add("/");
		urls.add("/books");
		urls.add("/auth/sign-in");
		urls.add("/auth");
		urls.add("/auth/sign-up");
		urls.add("/styles/main.css");
		urls.add("/styles/bootstrap.min.css");
		return urls;
	}
	
	private void setGuestUrls() {
		guestAccessUrls = setCommonUrls(guestAccessUrls);
	}
	
	private void setReaderUrls() {
		readerAccessUrls = setCommonUrls(readerAccessUrls);
		readerAccessUrls.add("/office");
		readerAccessUrls.add("/office/orders");
		readerAccessUrls.add("/order-handler");
		readerAccessUrls.add("/order");
		readerAccessUrls.add("/auth/sign-out");
		readerAccessUrls.add("/edit-profile");
	}
	
	private void setLibrarianUrls() {
		librarianAccessUrls = setCommonUrls(librarianAccessUrls);
		librarianAccessUrls.add("/office");
		librarianAccessUrls.add("/office/orders");
		librarianAccessUrls.add("/order-handler");
		librarianAccessUrls.add("/order");//TODO
		librarianAccessUrls.add("/auth/sign-out");
		librarianAccessUrls.add("/edit-profile");
		librarianAccessUrls.add("/orders");
		librarianAccessUrls.add("/add-book");
		librarianAccessUrls.add("/edit-book");
		librarianAccessUrls.add("/book");//TODO
	}
}
