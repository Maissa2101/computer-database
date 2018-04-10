package com.excilys.java.formation.mapper;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.SQLConnection;

public class ComputerMapperTest {
	
	static Logger logger = LoggerFactory.getLogger(ComputerMapperTest.class);

	@Test
	public void testGetListComputerFromResultSet() throws Exception {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT count(*) as total FROM computer");	
			if (result.next()) {
				assertEquals(6, result.getInt("total"));
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
			ResultSet result = statement.executeQuery("SELECT name FROM computer WHERE id = 1");	
			Computer computer= cm.getComputerDetailsFromResultSet(result);
			if (result.next()) {
				assertEquals("MacBook Pro 15.4 inch", computer.getName());
			}
		} catch (SQLException e) {
			logger.debug("problem in getTotalRecords", e);
		}
	}

}
