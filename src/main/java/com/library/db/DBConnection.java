package com.library.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnection {
	private static String dbUrl = "jdbc:mysql://localhost:3306/library";
    private static String DBUser = "root";
    private static String DBPassword = "root";
    private int maxConnectionsSize = 10;
    private ArrayList<Connection> pool = new ArrayList<Connection>();
    
    private DBConnection(){
        setDriver();
    }
    
    private static class DBaseHolder {
        private final static DBConnection db = new DBConnection();
    }
    
    public static DBConnection getInstance(){
        return DBaseHolder.db;
    }
    
    private void setDriver(){
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public synchronized Connection getConnection() throws SQLException {
    	Connection connection = null;
    	if (!pool.isEmpty()){
    		connection = (Connection) pool.get(pool.size()-1);
    		pool.remove(connection);
		    if (connection.isClosed()){
		    	System.out.println("Connection is closed");
		    	connection = getConnection();// Try again get connection
		    }
		    else {
		    	System.out.println("Get old connection");
		    }
    	}
    	else {
    		System.out.println("Create new connection");
    		connection = getNewConnection();
    	}
    	return connection;
    }
    
    private Connection getNewConnection() throws SQLException{
    	return DriverManager.getConnection(dbUrl, DBUser, DBPassword);
    }

    public void closeConnection(Connection connection) throws SQLException{
        if(connection != null){
        	if(pool.size() <= maxConnectionsSize){
        		System.out.println("Return connection to pool");
        		pool.add(connection);
        	}
        	else {
        		System.out.println("Close connection");
        		connection.close();
        	}
        }
    }
}
