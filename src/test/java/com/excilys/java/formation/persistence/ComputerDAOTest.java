package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.interfaceDAO.ComputerDAOInterface;

public class ComputerDAOTest {
/*
	@Test
	public void testGetListComputer() {
		ComputerDAOInterface cd = ComputerDAO.INSTANCE;


		try {
			List<Computer> list = cd.getListComputer(500,0);

			for(Computer computer : list) {
				if(computer.getId() == 512) {
					assertEquals("iPad", computer.getName());
					assertEquals(Date.valueOf("2010-01-01").toLocalDate(), computer.getIntroduced());
					assertEquals(Date.valueOf("2011-03-02").toLocalDate(), computer.getDiscontinued());
					assertEquals("1", computer.getManufacturer());
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	} 


	public void testGetComputer() {
		ComputerDAOInterface cd = ComputerDAO.INSTANCE;

		try {
			Optional<Computer> details = cd.getComputer(512L);
			assertEquals("iPad", details.get().getName());
			assertEquals(Date.valueOf("2010-01-01").toLocalDate(), details.get().getIntroduced());
			assertEquals(Date.valueOf("2011-03-02").toLocalDate(), details.get().getDiscontinued());
			assertEquals("1", details.get().getManufacturer());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testCreateComputer() {
		ComputerDAOInterface cd = ComputerDAO.INSTANCE;	

		try {
			Long id = cd.createComputer("ASUS", Date.valueOf("2008-01-04").toLocalDate(), Date.valueOf("2018-01-01").toLocalDate(), "1");

			List<Computer> list = cd.getListComputer(500,0);

			for(Computer computer : list) {
				if(computer.getId() == id) {
					assertEquals("ASUS", computer.getName());
					assertEquals(Date.valueOf("2008-01-04").toLocalDate(), computer.getIntroduced());
					assertEquals(Date.valueOf("2018-01-01").toLocalDate(), computer.getDiscontinued());
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
		ComputerDAOInterface cd = ComputerDAO.INSTANCE;

		try {
			cd.updateComputer(572L, "HP", Date.valueOf("2008-01-04").toLocalDate(), null);
			Optional<Computer> c = cd.getComputer(572L);
			assertEquals("HP", c.get().getName());	
			assertEquals(Date.valueOf("2008-01-04").toLocalDate(), c.get().getIntroduced());	
			assertEquals(null, c.get().getDiscontinued());


		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteComputer() {
		ComputerDAOInterface cd = ComputerDAO.INSTANCE;


		try {
			cd.deleteComputer(771L);

			List<Computer> list = cd.getListComputer(500,0);

			for(Computer computer : list) {
				if(computer.getId() == 771L) {
					fail("Computer still exists");				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	} 
*/
}