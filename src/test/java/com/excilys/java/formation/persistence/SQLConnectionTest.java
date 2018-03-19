package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.java.formation.persistence.SQLConnection;


public class SQLConnectionTest {
		
	 @SuppressWarnings("unused")
	@BeforeClass
	     public static void init() throws SQLException, ClassNotFoundException, IOException {
	         SQLConnection.getInstance();
			 Connection con = SQLConnection.getConnection();
	     }

	
	 @AfterClass
	     public static void destroy() throws SQLException, ClassNotFoundException, IOException {
	         try (Connection connection = SQLConnection.getConnection()){
	             connection.close();
	         }
	     } 
	 
	 	
	 	private int getTotalRecords() {
			 try (Connection connection = SQLConnection.getConnection(); java.sql.Statement statement = connection.createStatement();) {
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
			assertEquals(42, getTotalRecords());
		}
		  
		@Test
		 public void checkNameExistsTest() {
			try (Connection connection = SQLConnection.getConnection();
					java.sql.Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
					ResultSet result = statement.executeQuery("SELECT name FROM company");
					if (result.first()) {
						assertEquals("Apple Inc.", result.getString("name"));
		             }
		            if (result.last()) {
		            	assertEquals("Samsung Electronics", result.getString("name"));
		             }
		    } catch (SQLException e) {
		             e.printStackTrace();
		      }
		 }

}

