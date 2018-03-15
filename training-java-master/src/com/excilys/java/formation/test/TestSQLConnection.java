package com.excilys.java.formation.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.excilys.java.formation.persistence.SQLConnection;

public class TestSQLConnection {
	
	static void test() throws ClassNotFoundException, SQLException {
		SQLConnection.getInstance();
		Connection c = SQLConnection.getConnection();
		c.close();}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		test();
	}
}

