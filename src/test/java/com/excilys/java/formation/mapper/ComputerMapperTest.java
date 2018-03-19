package com.excilys.java.formation.mapper;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.SQLConnection;

public class ComputerMapperTest {

	@Test(expected = SQLException.class)
	public void testGetListComputerFromResultSet() throws SQLException {
		ComputerMapper cm = ComputerMapper.INSTANCE;
		SQLConnection.getInstance();
		Connection connection = SQLConnection.getConnection();
		java.sql.Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT count(*) as total FROM computer");
		List<Computer> computers= cm.getListComputerFromResultSet(result);
		int total = 0;
		 if (result.next()) {
            total = result.getInt("total");
         }
		 assertEquals(total, computers.size());
		 connection.close();
	}

	@Test(expected = SQLException.class)
	public void testGetComputerDetailsFromResultSet() throws SQLException {
		ComputerMapper cm = ComputerMapper.INSTANCE;
		SQLConnection.getInstance();
		Connection connection = SQLConnection.getConnection();
		java.sql.Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT name FROM computer WHERE id = 1");
		Computer computer= cm.getComputerDetailsFromResultSet(result);
		assertEquals(computer.getName(), "MacBook Pro 15.4 inch ");
		
	}

}
