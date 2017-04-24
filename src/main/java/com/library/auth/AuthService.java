package com.library.auth;

import java.sql.SQLException;
import java.util.ArrayList;

import com.library.errors.ValidationError;
import com.library.users.DAOUser;
import com.library.users.User;

public class AuthService {
	private DAOUser daoUser = new DAOUser();
	
	public ArrayList<ValidationError> signInValidation(String login, String password){
		ArrayList<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError loginError = checkLogin(login);
        if(loginError != null){
            errors.add(loginError);
        }
        ValidationError passwordError = checkPassword(password);
        if(passwordError != null){
            errors.add(passwordError);
        }
		return errors;
	}
	
	public ArrayList<ValidationError> signUpValidation(User user){
		ArrayList<ValidationError> errors = new ArrayList<ValidationError>();
		//check an user first name
		ValidationError firstNameError = checkFirstName(user.getFirstName());
        if(firstNameError != null){
            errors.add(firstNameError);
        }
		//check an user last name
        ValidationError lastNameError = checkLastName(user.getLastName());
        if(lastNameError != null){
            errors.add(lastNameError);
        }
		//check an user login
		ValidationError loginError = checkLogin(user.getLogin());
        if(loginError != null){
            errors.add(loginError);
        }
        else {
        	//check login in a DB
            try {
				User dbUser = findUser(user.getLogin());
				if(dbUser != null){
					errors.add(new ValidationError("login", "User with current login is already exists!"));
				}
			} catch (SQLException e) {
				errors.add(new ValidationError("Undefined", "An undefined error, try again, please!"));
			}
        }
        //check an user password
        ValidationError passwordError = checkPassword(user.getPassword());
        if(passwordError != null){
            errors.add(passwordError);
        }
        //check confirm password
        ValidationError confPasswordError = checkPassword(user.getPassword(), user.getConfirmPassword());
        if(confPasswordError != null){
            errors.add(confPasswordError);
        }
		return errors;
	}
	
	public User findUser(String login, String password) throws SQLException{
		return daoUser.find(login, password);
	}
	
	public User findUser(String login) throws SQLException{
		return daoUser.find(login);
	}
	
	private ValidationError checkFirstName(String firstName){
        if(firstName == null || firstName.trim().length() == 0){
            return new ValidationError("firstName", "First name field is empty!");
        }
        return null;
    }
	
	private ValidationError checkLastName(String lastName){
        if(lastName == null || lastName.trim().length() == 0){
            return new ValidationError("lastName", "Last name field is empty!");
        }
        return null;
    }
	
	private ValidationError checkLogin(String login){
        if(login == null || login.trim().length() == 0){
            return new ValidationError("login", "Login field is empty!");
        }
        return null;
    }
	
	private ValidationError checkPassword(String password) {
        if(password == null || password.trim().length() == 0){
            return new ValidationError("password", "Password field is empty!");
        }
        return null;
    }
	
	private ValidationError checkPassword(String password, String confirmPassword) {
        if(!password.equals(confirmPassword)){
            return new ValidationError("password", "A confirm password doesn't match with a password!");
        }
        return null;
    }
	
	public int create(User user) throws SQLException{
		return daoUser.create(user);
	}
}
