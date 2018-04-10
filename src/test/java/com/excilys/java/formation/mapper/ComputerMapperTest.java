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

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.SQLConnection;

public class ComputerMapperTest {
	
	static Logger logger = LoggerFactory.getLogger(ComputerMapperTest.class);
	
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
	
	@Test
	public void testGetListComputerFromResultSet() throws Exception {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT count(*) as total FROM computer");	
			if (result.next()) {
				assertEquals(9, result.getInt("total"));
			}
		} catch (SQLException e) {
			logger.debug("problem in getTotalRecords", e);
		}
	}

	@Test
	public void testGetComputerDetailsFromResultSet() throws SQLException, DAOConfigurationException, ClassNotFoundException {
		ComputerMapper cm = ComputerMapper.INSTANCE;
		try (Connection connection = SQLConnection.getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT name FROM computer WHERE id=2");	
			Computer computer= cm.getComputerDetailsFromResultSet(result);
			if (result.next()) {
				assertEquals("CM-200", computer.getName());
			}
		} catch (SQLException e) {
			logger.debug("problem in getTotalRecords", e);
		}
	}

}
