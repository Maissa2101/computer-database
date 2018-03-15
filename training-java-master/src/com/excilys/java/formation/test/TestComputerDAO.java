package com.excilys.java.formation.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;

public class TestComputerDAO {

	@Test
	public void testGetListComputer() {
		ComputerDAO cd = ComputerDAO.getComputerDAO();
		
		
		try {
			List<Computer> list = cd.getListComputer();
			
			for(Computer computer : list) {
				if(computer.getId() == 512) {
					assertEquals("iPad", computer.getName());
					assertEquals(Date.valueOf("2010-01-01"), computer.getIntroduced());
					assertEquals(Date.valueOf("2011-03-02"), computer.getDiscontinued());
					assertEquals("1", computer.getManufacturer());
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetComputer() {
		ComputerDAO cd = ComputerDAO.getComputerDAO();
		
		try {
			Computer details = cd.getComputer(512L);
			assertEquals("iPad", details.getName());
			assertEquals(Date.valueOf("2010-01-01"), details.getIntroduced());
			assertEquals(Date.valueOf("2011-03-02"), details.getDiscontinued());
			assertEquals("1", details.getManufacturer());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testCreateComputer() {
		ComputerDAO cd = ComputerDAO.getComputerDAO();	
		
		try {
			Long id = cd.createComputer("ASUS", Date.valueOf("2008-01-04"), Date.valueOf("2018-01-01"), "1");

			List<Computer> list = cd.getListComputer();
			
			for(Computer computer : list) {
				if(computer.getId() == id) {
					assertEquals("ASUS", computer.getName());
					assertEquals(Date.valueOf("2008-01-04"), computer.getIntroduced());
					assertEquals(Date.valueOf("2018-01-01"), computer.getDiscontinued());
					assertEquals("1", computer.getManufacturer());
				}
			}
		}	
		 catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testUpdateComputer() {
		ComputerDAO cd = ComputerDAO.getComputerDAO();	
		
		try {
			cd.updateComputer(572L, "HP", Date.valueOf("2008-01-04"), null);
			
			Computer c = cd.getComputer(572L);
			assertEquals("HP", c.getName());
			assertEquals(Date.valueOf("2008-01-04"), c.getIntroduced());
			assertEquals(null, c.getDiscontinued());

			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteComputer() {
		ComputerDAO cd = ComputerDAO.getComputerDAO();	
		
		
		try {
			cd.deleteComputer(771L);
			
			List<Computer> list = cd.getListComputer();
			
			for(Computer computer : list) {
				if(computer.getId() == 771L) {
					fail("Computer still exists");				}
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}