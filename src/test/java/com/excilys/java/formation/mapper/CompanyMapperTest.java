package com.excilys.java.formation.mapper;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.SQLConnection;

public class CompanyMapperTest {
	
	static Logger logger = LoggerFactory.getLogger(CompanyMapperTest.class);
	
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
			
			connection.commit();
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
	
	@Test
	public void testGetListCompanyFromResultSet() throws SQLException, DAOConfigurationException, ClassNotFoundException {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT count(*) as total FROM company");	
			if (result.next()) {
				assertEquals(6, result.getInt("total"));
			}
		} catch (SQLException e) {
			logger.debug("problem in getTotalRecords", e);
		}
	}
}
