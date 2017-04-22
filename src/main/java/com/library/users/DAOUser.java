package com.library.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.library.db.DBConnection;

public class DAOUser {
	DBConnection dbConnection = DBConnection.getInstance();

	// find an user by id
	public User find(int id) throws SQLException {
		User user = null;
		String sql = "SELECT * from users WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, id);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			user = new User();
			user.setId(rs.getInt("id"));
			user.setLogin(rs.getString("login"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setRole(rs.getInt("role"));
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return user;
	}

	// find an user by login/password
	public User find(String login, String password) throws SQLException {
		User user = null;
		String sql = "SELECT * from users WHERE login = ? AND password = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setString(1, login);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			user = new User();
			user.setId(rs.getInt("id"));
			user.setLogin(rs.getString("login"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setRole(rs.getInt("role"));
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return user;
	}

	// find all users
	public ArrayList<User> findAll() {
		ArrayList<User> users = new ArrayList<User>();

		return users;
	}

	// create (registration) a new user
	public Boolean create(User user) throws SQLException {
		String sql = "INSERT INTO users (firstName, lastName, login, password, role) values (?, ?, ?, ?, ?)";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setString(1, user.getFirstName());
		pstm.setString(2, user.getLastName());
		pstm.setString(3, user.getLogin());
		pstm.setString(4, user.getPassword());
		pstm.setInt(5, user.getRole());
		Boolean result = pstm.execute();
		pstm.close();
		dbConnection.closeConnection(con);
		return result;
	}

	// update (registration) an old user
	public int update(User user) throws SQLException {
		String sql = "UPDATE users SET firstName = ?, lastName = ?, password = ?, role = ? WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setString(1, user.getFirstName());
		pstm.setString(2, user.getLastName());
		pstm.setString(3, user.getPassword());
		pstm.setInt(4, user.getRole());
		pstm.setInt(5, user.getId());
		int result = pstm.executeUpdate();
		pstm.close();
		dbConnection.closeConnection(con);
		return result;
	}

	// create (registration) a new user
	public Boolean delete(int id) throws SQLException {
		String sql = "DELETE FROM users WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, id);
		Boolean result = pstm.execute();
		pstm.close();
		dbConnection.closeConnection(con);
		return result;
	}
}
