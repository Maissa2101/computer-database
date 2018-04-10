package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hsqldb.cmdline.SqlToolError;


public class SQLConnectionTest {

	static Logger logger = LoggerFactory.getLogger(SQLConnectionTest.class);

	@BeforeClass
	public static void init() throws SQLException, IOException, ClassNotFoundException, DAOConfigurationException, SqlToolError {
		try (Connection connection = SQLConnection.getInstance().getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			statement.execute("CREATE TABLE company (id BIGINT NOT NULL identity, name VARCHAR(255),"
					+ "constraint pk_company PRIMARY KEY (id));");

			statement.execute("CREATE TABLE computer (id BIGINT NOT NULL identity, name VARCHAR(255), "
					+ "introduced DATE NULL, discontinued DATE NULL, "
					+ "company_id BIGINT default NULL,"
					+ "constraint pk_computer PRIMARY KEY (id));");
			connection.commit();
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Apple Inc.');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Thinking Machines');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('RCA');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Netronics');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Tandy Corporation');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Commodore International');");

			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('MacBook Pro 15.4 inch',null,null,1);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('CM-2a',null,null,2);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('CM-200',null,null,2);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('CM-5e',null,null,2);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('CM-5','1991-01-01',null,2);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('MacBook Pro','2006-01-10',null,1);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('Apple IIe',null,null,null);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('Apple IIGS',null,null,null);");
			statement.executeUpdate("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('Apple IIc Plus',null,null,null);");
			
			connection.commit();
		}
	}

	@AfterClass
	public static void destroy() throws SQLException, ClassNotFoundException, IOException, DAOConfigurationException {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection(); java.sql.Statement statement = connection.createStatement();) {
			statement.executeUpdate("DROP TABLE company");
			statement.executeUpdate("DROP TABLE computer");
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
			logger.debug("problem in getTotalRecords", e);
		}
		return 0;
	}

	@Test
	public void getTotalRecordsTest() throws ClassNotFoundException, DAOConfigurationException {
		assertEquals(6, getTotalRecords());
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
				assertEquals("Commodore International", result.getString("name"));
			}
		} catch (SQLException e) {
			logger.debug("problem in checkNameExistsTest", e);
		}
	}

}
