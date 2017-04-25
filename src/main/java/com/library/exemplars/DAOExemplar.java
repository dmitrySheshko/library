package com.library.exemplars;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.library.db.DBConnection;

public class DAOExemplar {
	DBConnection dbConnection = DBConnection.getInstance();
	
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
