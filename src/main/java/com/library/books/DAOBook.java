package com.library.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.library.db.DBConnection;

public class DAOBook {
	
	DBConnection dbConnection = DBConnection.getInstance();
	
	public ArrayList<Book> findAll() throws SQLException {
		ArrayList<Book> books = new ArrayList<Book>();
		String sql = "SELECT b.*, c.name FROM books b, categories c WHERE b.category = c.id";
		Connection con = dbConnection.getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
        	//TODO: like this or by constructor?
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setCount(rs.getInt("count"));
            book.setCategory(rs.getString("name"));
            books.add(book);
        }
        rs.close();
        pstm.close();
        dbConnection.closeConnection(con);
		return books;
	}
}
