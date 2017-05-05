package com.library.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.library.category.Category;
import com.library.db.DBConnection;
import com.mysql.jdbc.Statement;

public class DAOBook {

	private DBConnection dbConnection = DBConnection.getInstance();
	
	public Book find(int bookId) throws SQLException{
		Book book = null;
		String sql = "SELECT * FROM books WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, bookId);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			// TODO: like this or by constructor?
			book = new Book();
			book.setId(rs.getInt("id"));
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			book.setCategory(new Category(rs.getInt("category"), ""));
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return book;
	}

	public ArrayList<Book> findAll() throws SQLException {
		ArrayList<Book> books = new ArrayList<Book>();
		String sql = "SELECT b.*, c.id as categoryId, c.name, (SELECT COUNT(*) FROM exemplars e WHERE e.book_id = b.id) AS count, (SELECT COUNT(*) FROM orders o WHERE o.book_id = b.id AND order_type != 1) AS issue_count, (SELECT COUNT(*) FROM orders o WHERE o.book_id = b.id AND order_type = 1) AS wait_count FROM books b LEFT JOIN categories c ON b.category = c.id ORDER BY b.create_date DESC";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			// TODO: like this or by constructor?
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			book.setCategory(new Category(rs.getInt("categoryId"), rs.getString("name")));
			book.setCount(rs.getInt("count"));
			book.setIssueCount(rs.getInt("issue_count"));
			book.setWaitCount(rs.getInt("wait_count"));
			books.add(book);
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return books;
	}

	public ArrayList<Book> findAll(String whereString) throws SQLException {
		ArrayList<Book> books = new ArrayList<Book>();
		String sql = "SELECT b.*, c.id as categoryId, c.name, (SELECT COUNT(*) FROM exemplars e WHERE e.book_id = b.id) AS count, (SELECT COUNT(*) FROM orders o WHERE o.book_id = b.id AND order_type != 1) AS issue_count, (SELECT COUNT(*) FROM orders o WHERE o.book_id = b.id AND order_type = 1) AS wait_count FROM books b LEFT JOIN categories c ON b.category = c.id WHERE " + whereString + " ORDER BY b.create_date DESC";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			// TODO: like this or by constructor?
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			book.setCategory(new Category(rs.getInt("categoryId"), rs.getString("name")));
			book.setCount(rs.getInt("count"));
			book.setIssueCount(rs.getInt("issue_count"));
			book.setWaitCount(rs.getInt("wait_count"));
			books.add(book);
		}
		rs.close();
		pstm.close();
		dbConnection.closeConnection(con);
		return books;
	}

	// create a new book
	public int create(Book book) throws SQLException {
		String sql = "INSERT INTO books (title, author, category) values (?, ?, ?)";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstm.setString(1, book.getTitle());
		pstm.setString(2, book.getAuthor());
		pstm.setInt(3, book.getCategory().getId());
		int affectedRows = pstm.executeUpdate();
		int id = 0;
		if (affectedRows != 0) {
			ResultSet generatedKeys = pstm.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = generatedKeys.getInt(1);
			}
		}
		pstm.close();
		dbConnection.closeConnection(con);
		return id;
	}
	
	public void delete(int bookId) throws SQLException{
		String sql = "DELETE FROM books WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, bookId);
		pstm.execute();
		pstm.close();
		dbConnection.closeConnection(con);
	}
	
	public void update(Book book) throws SQLException{
		String sql = "UPDATE books SET category = ? WHERE id = ?";
		Connection con = dbConnection.getConnection();
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, book.getCategory().getId());
		pstm.setInt(2, book.getId());
		pstm.execute();
		pstm.close();
		dbConnection.closeConnection(con);
	}
}
