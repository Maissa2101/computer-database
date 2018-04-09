package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.portable.InputStream;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;


public class SQLConnectionTest {
	
	@BeforeClass
	public static void init() throws SQLException, IOException, ClassNotFoundException, DAOConfigurationException, SqlToolError {
        SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection();
                InputStream inputStream = (InputStream) SQLConnection.class.getResourceAsStream("/db/TEST.sql");) {
            SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream),
                    "init", System.out, "UTF-8", false, new File("."));
            sqlFile.setConnection(connection);
            sqlFile.execute();
            
        }
    }

	@AfterClass
	public static void destroy() throws SQLException, ClassNotFoundException, IOException, DAOConfigurationException {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection(); java.sql.Statement statement = connection.createStatement();) {
			statement.executeUpdate("DROP TABLE company");
			connection.commit();
		} 
	}



	private int getTotalRecords() throws ClassNotFoundException, DAOConfigurationException {
		SQLConnection.getInstance();
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
	public void getTotalRecordsTest() throws ClassNotFoundException, DAOConfigurationException {
		assertEquals(10, getTotalRecords());
	}

	@Test
	public void checkNameExistsTest() throws ClassNotFoundException, DAOConfigurationException {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection();
				java.sql.Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
			ResultSet result = statement.executeQuery("SELECT name FROM company");
			if (result.first()) {
				assertEquals("Apple Inc.", result.getString("name"));
			}
			if (result.last()) {
				assertEquals("Digital Equipment Corporation", result.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
