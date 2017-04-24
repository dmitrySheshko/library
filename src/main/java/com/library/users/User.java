package com.library.users;

import javax.servlet.http.HttpServletRequest;

public class User {
	private int id;
	private String login;
	private String password;
	private String confirmPassword;
	private String firstName;
	private String lastName;
	private int role;
	
	public User(){}
	
	public User(HttpServletRequest request){
		this.login = request.getParameter("login");
        this.password = request.getParameter("password");
        this.confirmPassword = request.getParameter("confirmPassword");
        this.firstName = request.getParameter("firstName");
        this.lastName = request.getParameter("lastName");
        this.setRole(request.getParameter("role"));
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return this.login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setConfirmPassword(String cPassword) {
		this.confirmPassword = cPassword;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setRole(String role) {
		if(role == null){
			this.role = 3;//READER
		}
		else {
			this.role = Integer.parseInt(role);
		}
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getRole() {
		return this.role;
	}
}
