package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	private static Connection conn = null;
	
	/**
	 * Constructor of SQLConnection 
	 * 
	 */
	public SQLConnection() {}
	
	/**
	 * Create the connection to the DB
	 * @return the connection to the DB 
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 */
	synchronized public static Connection getConnection() throws ClassNotFoundException, SQLException {    
		
		try {
			Class.forName("com.mysql.jdbc.Driver");	
			String url = "jdbc:mysql://localhost:3306/computer-database-db";		
			String user = "admincdb";			
			String passwd = "qwerty1234"; 
			
			conn = DriverManager.getConnection( url, user, passwd );

			} catch ( SQLException e ) {
			} 
		return conn;
	}
	
	/**
	 * Close the connection to the DB
	 * @throws SQLException in case of a database access error
	 */
	public static void closeConnection() throws SQLException {
		if ( conn != null )
	        try {
	            conn.close();
	        } catch ( SQLException ignore ) {
	           
	        }
	}
}
