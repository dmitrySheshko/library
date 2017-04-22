package com.library.users;

public class User {
	private int id;
	private String login;
	private String password;
	private String confirmPassword;
	private String firstName;
	private String lastName;
	private int role;

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

	public void setRole(int role) {
		this.role = role;
	}

	public int getRole() {
		return this.role;
	}
}
