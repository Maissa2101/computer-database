package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class SQLConnectionTest {
		
	
	@BeforeClass
	     public static void init() throws SQLException, ClassNotFoundException, IOException {
		 	Class.forName("org.hsqldb.jdbc.JDBCDriver");
		 	initDatabase();
	     }

	
	 @AfterClass
	     public static void destroy() throws SQLException, ClassNotFoundException, IOException {
		 	try (Connection connection = getConnection(); java.sql.Statement statement = connection.createStatement();) {
			             statement.executeUpdate("DROP TABLE company");
			             connection.commit();
		 	} 
	 	}
	 
	 private static void initDatabase() throws SQLException {
		         try (Connection connection = getConnection(); java.sql.Statement statement = connection.createStatement();) {
		             statement.execute("CREATE TABLE company (id BIGINT NOT NULL identity, name VARCHAR(255),"
		                     + "constraint pk_computer PRIMARY KEY (id));");
		             connection.commit();
		             statement.executeUpdate("INSERT INTO company (name) VALUES ('Apple Inc.');");
		             statement.executeUpdate("INSERT INTO company (name) VALUES ('Thinking Machines');");
		             statement.executeUpdate("INSERT INTO company (name) VALUES ('RCA');");
		             connection.commit();
		         }
		     }

	 

	 
	 	private static Connection getConnection() throws SQLException {
		         return DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "");
		     }

	 
	 	private int getTotalRecords() {
			 try (Connection connection = getConnection(); java.sql.Statement statement = connection.createStatement();) {
				 ResultSet result = statement.executeQuery("SELECT count(*) as total FROM company");
			     	if (result.next()) {
			     		return result.getInt("total");
			        }
			 } catch (SQLException e) {
				 e.printStackTrace();
			   }
			   	 return 0;
	 	}
		  
		@Test
		public void getTotalRecordsTest() {
			assertEquals(3, getTotalRecords());
		}
		  
		@Test
		 public void checkNameExistsTest() {
			try (Connection connection = getConnection();
					java.sql.Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
					ResultSet result = statement.executeQuery("SELECT name FROM company");
					if (result.first()) {
						assertEquals("Apple Inc.", result.getString("name"));
		             }
		            if (result.last()) {
		            	assertEquals("RCA", result.getString("name"));
		             }
		    } catch (SQLException e) {
		             e.printStackTrace();
		      }
		 }

}

