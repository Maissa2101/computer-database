package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;


public class SQLConnectionTest {

	static Logger logger = LoggerFactory.getLogger(SQLConnectionTest.class);

	@BeforeClass
	public static void init() throws SQLException, IOException, ClassNotFoundException, DAOConfigurationException, SqlToolError {
		try (Connection connection = SQLConnection.getInstance().getConnection(); 
				java.sql.Statement statement = connection.createStatement();
				InputStream inputStream = SQLConnection.class.getResourceAsStream("/TEST.sql"); ) {
		           SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
		                   new File("."));
		           sqlFile.setConnection(connection);
		           sqlFile.execute();
		} catch (SQLException e) {
			logger.debug("problem in init", e);
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
			logger.debug("problem in getTotalRecords", e);
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
			logger.debug("problem in checkNameExistsTest", e);
		}
	}

}
