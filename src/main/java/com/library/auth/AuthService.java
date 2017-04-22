package com.library.auth;

import java.sql.SQLException;
import java.util.ArrayList;

import com.library.errors.ValidationError;
import com.library.users.DAOUser;
import com.library.users.User;

public class AuthService {
	private DAOUser daoUser = new DAOUser();
	
	public User findUser(String login, String password) throws SQLException{
		return daoUser.find(login, password);
	}
	
	public ArrayList<ValidationError> signInValidation(String login, String password){
		ArrayList<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError loginError = checkLoginForEmpty(login);
        if(loginError != null){
            errors.add(loginError);
        }
        ValidationError passwordError = checkPassword(password);
        if(passwordError != null){
            errors.add(passwordError);
        }
		return errors;
	}
	
	private ValidationError checkLoginForEmpty(String login){
        if(login == null || login.trim().length() == 0){
            return new ValidationError("login", "Field is empty!");
        }
        return null;
    }
	
	private ValidationError checkPassword(String password) {
        if(password == null || password.trim().length() == 0){
            return new ValidationError("password", "Field is empty!");
        }
        return null;
    }
}
