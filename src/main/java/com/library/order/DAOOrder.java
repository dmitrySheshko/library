package com.library.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import com.library.books.Book;
import com.library.db.DBConnection;
import com.library.users.User;

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
	
	public Order find(int orderId) throws SQLException {
		Order order = null;
		String sql = "SELECT o.id, o.create_date, o.out_date, o.return_date, o.order_type, o.status, reader.id as reader_id, reader.firstName, reader.lastName, b.id as book_id, b.title, b.author, lib.id as librarian_id, lib.firstName as librarian_firstName, lib.lastName as librarian_lastName FROM orders o LEFT JOIN users reader ON o.reader_id = reader.id LEFT JOIN books b ON b.id = o.book_id LEFT JOIN users lib ON lib.id = o.librarian_id WHERE o.id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, orderId);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()){
			order = new Order();
			//order data
			order.setId(rs.getInt("id"));
			order.setCreateDate(rs.getDate("create_date"));
			order.setOutDate(rs.getDate("out_date"));
			order.setReturnDate(rs.getDate("return_date"));
			order.setOrderType(rs.getInt("order_type"));
			order.setStatus(rs.getInt("status"));
			//reader data
			order.setReader(new User(rs.getInt("reader_id"), rs.getString("firstName"), rs.getString("lastName")));
			//librarian data
			order.setLibrarian(new User(rs.getInt("librarian_id"), rs.getString("librarian_firstName"), rs.getString("librarian_lastName")));
			//book data
			order.setBook(new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author")));
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return order;
	}
	
	public List<Order> findAll() throws SQLException {
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT o.id, o.create_date, o.status, reader.firstName, reader.lastName, b.title, b.author FROM orders o LEFT JOIN users reader ON o.reader_id = reader.id LEFT JOIN books b ON b.id = o.book_id ORDER BY o.create_date DESC";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()){
			Order order = new Order();
			//order data
			order.setId(rs.getInt("id"));
			order.setCreateDate(rs.getDate("create_date"));
			order.setStatus(rs.getInt("status"));
			//reader data
			User reader = new User();
			reader.setFirstName(rs.getString("firstName"));
			reader.setLastName(rs.getString("lastName"));
			order.setReader(reader);
			//book data
			Book book = new Book();
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			order.setBook(book);
			orders.add(order);
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return orders;
	}
	
	public List<Order> findOrdersByUser(int readerId) throws SQLException{
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT o.id, o.create_date, o.order_type, b.title, b.author FROM orders o LEFT JOIN books b ON b.id = o.book_id WHERE o.reader_id = ? AND o.status = 1 ORDER BY o.create_date DESC";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, readerId);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()){
			Order order = new Order();
			//order data
			order.setId(rs.getInt("id"));
			order.setOrderType(rs.getInt("order_type"));
			order.setCreateDate(rs.getDate("create_date"));
			//book data
			Book book = new Book();
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			order.setBook(book);
			orders.add(order);
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return orders;
	}
	
	public Boolean findOrdersByUserAndBookIds(int readerId, String[] bookIds) throws SQLException{
		boolean result = false;
		String ids = String.join(",", bookIds);
		String sql = "SELECT id FROM orders WHERE reader_id = ? AND status = 1 AND book_id IN (" + ids + ")";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, readerId);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()){
			result = true;
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return result;
	}
	
	public void issueBook(int orderId, int librarianId, int orderType) throws SQLException{
		String sql = "UPDATE orders SET librarian_id = ?, order_type = ?, out_date = ? WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, librarianId);
		pstm.setInt(2, orderType);
		pstm.setTimestamp(3, getTimestamp());
		pstm.setInt(4, orderId);
		pstm.executeUpdate();//int result = 
		pstm.close();
		dbConnection.closeConnection(con);
	}
	
	public void returnBook(int orderId) throws SQLException{
		String sql = "UPDATE orders SET status = 0, return_date = ? WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setTimestamp(1, getTimestamp());
		pstm.setInt(2, orderId);
		pstm.executeUpdate();//int result = 
		pstm.close();
		dbConnection.closeConnection(con);
	}
	
	private Timestamp getTimestamp(){
		Date today = new Date();
		return new Timestamp(today.getTime());
	}
	
	public List<Order> findActiveOrdersByBookId(int bookId) throws SQLException{
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM orders WHERE book_id = ? AND status = 1 AND order_type != 1";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, bookId);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()){
			Order order = new Order();
			order.setId(rs.getInt("id"));
			orders.add(order);
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return orders;
	}
	
	public void delete(int orderId) throws SQLException {
		String sql = "DELETE FROM orders WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, orderId);
		pstm.execute();
		pstm.close();
		dbConnection.closeConnection(con);
	}
	
	public void deleteAllByBookId(int bookId) throws SQLException{
		String sql = "DELETE FROM orders WHERE book_id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, bookId);
		pstm.execute();
		pstm.close();
		dbConnection.closeConnection(con);
	}
	
	public void changeStatus(int orderId) throws SQLException{
		String sql = "UPDATE orders SET status = 0 WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, orderId);
		pstm.executeUpdate();
		pstm.close();
		dbConnection.closeConnection(con);
	}
}
