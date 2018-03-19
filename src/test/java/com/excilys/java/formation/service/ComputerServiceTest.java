package com.excilys.java.formation.service;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.SQLConnection;

public class ComputerServiceTest {

	@Test(expected = SQLException.class)
	public void testListComputers() throws ClassNotFoundException, SQLException {
		ComputerService cs = ComputerService.INSTANCE;
		List<Computer> computers = cs.listComputers();
		
		SQLConnection.getInstance();
		Connection connection = SQLConnection.getConnection();
		java.sql.Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT count(*) as total FROM computer");

		int total = 0;
		 if (result.next()) {
            total = result.getInt("total");
         }
		assertEquals(computers.size(), total );
	}

	@Test(expected = ValidatorException.class)
	public void testComputerDetails() throws ClassNotFoundException, SQLException, ValidatorException {
		fail("Not yet implemented");
		
	}

	@Test(expected = ValidatorException.class)
	public void testCreateComputer() {
		fail("Not yet implemented");
	}

	@Test(expected = ValidatorException.class)
	public void testUpdateComputer() {
		fail("Not yet implemented");
	}

	@Test(expected = ValidatorException.class)
	public void testDeleteComputer() {
		fail("Not yet implemented");
	}

}
