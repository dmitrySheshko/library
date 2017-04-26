package com.library.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.library.db.DBConnection;

public class DAOCategory {
	
	private DBConnection dbConnection = DBConnection.getInstance();
	
	public void find(){
		
	}
	
	public ArrayList<Category> findAll() throws SQLException{
		ArrayList<Category> categories = new ArrayList<Category>();
		String sql = "SELECT * FROM categories";
    	Connection con = dbConnection.getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);
    	ResultSet rs = pstm.executeQuery();
    	while (rs.next()) {
    		Category category = new Category();
    		category.setId(rs.getInt("id"));
    		category.setName(rs.getString("name"));
    		categories.add(category);
        }
    	rs.close();
    	pstm.close();
    	dbConnection.closeConnection(con);
    	return categories;
	}
	
}
