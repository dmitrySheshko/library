package com.library.exemplars;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.db.DBConnection;

public class DAOExemplar {
	private DBConnection dbConnection = DBConnection.getInstance();
	
	public List<String> findByBookId(int bookId) throws SQLException{
		List<String> exemplars = new ArrayList<String>();
		String sql = "SELECT * FROM exemplars WHERE book_id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, bookId);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()){
			exemplars.add(rs.getString("number"));
		}
		return exemplars;
	}
	
	public void saveExemplars(List<String> exemplars, int bookId) throws SQLException{
		String sql = "INSERT INTO exemplars (number, book_id) values (?, ?)";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		for(String exemplar : exemplars){
			pstm.setString(1, exemplar);
			pstm.setInt(2, bookId);
			pstm.addBatch();
		}
		pstm.executeBatch();//int[]
		pstm.close();
		dbConnection.closeConnection(con);
	}
}
