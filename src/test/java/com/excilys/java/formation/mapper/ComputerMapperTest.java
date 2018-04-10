package com.excilys.java.formation.mapper;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
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
	
	@Test
	public void testGetListComputerFromResultSet() throws Exception {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT count(*) as total FROM computer");	
			if (result.next()) {
				assertEquals(20, result.getInt("total"));
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
			ResultSet result = statement.executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=2");	
			Computer computer= cm.getComputerDetailsFromResultSet(result);
			if (result.next()) {
				assertEquals("CM-2a", computer.getName());
				assertNull(computer.getIntroduced());
				assertNull(computer.getDiscontinued());
				assertEquals("2", computer.getManufacturer());
			}
		} catch (SQLException e) {
			logger.debug("problem in getTotalRecords", e);
		}
	}

}
