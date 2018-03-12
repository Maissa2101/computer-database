package com.excilys.java.formation.persistence;



import java.sql.Connection;
import java.sql.SQLException;

public class Test {
	static void test() throws ClassNotFoundException, SQLException {
		Connection c = SQLConnection.getConnection();
		c.close();}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		test();
	}
}
