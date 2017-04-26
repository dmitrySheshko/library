package com.library.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.db.DBConnection;

public class DAOOrder {
	private DBConnection dbConnection = DBConnection.getInstance();
	
	public void create(int readerId, String[] bookIds) throws NumberFormatException, SQLException{
		String sql = "INSERT INTO orders (reader_id, book_id, order_type) values (?, ?, 1)";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		for(String id : bookIds){
			pstm.setInt(1, readerId);
			pstm.setInt(2, Integer.parseInt(id));
			pstm.addBatch();
		}
		pstm.executeBatch();
		pstm.close();
		dbConnection.closeConnection(con);
	}
	
	public Boolean findOrdersByUserAndBookIds(int readerId, String[] bookIds) throws SQLException{
		boolean result = false;
		String ids = String.join(",", bookIds);
		String sql = "SELECT id FROM orders WHERE reader_id = ? AND status = 1 AND book_id IN (" + ids + ")";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, readerId);
		//pstm.setString(2, ids);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()){
			result = true;
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return result;
	}
}
