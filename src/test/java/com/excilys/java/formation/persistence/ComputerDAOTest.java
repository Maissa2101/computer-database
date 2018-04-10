package com.excilys.java.formation.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAO;

public class ComputerDAOTest {

	static Logger logger = LoggerFactory.getLogger(ComputerDAOTest.class);

	@Test
	public void testGetListComputer() {
		ComputerDAO cd = ComputerDAO.INSTANCE;
		try {
			List<Computer> list = cd.getListComputer(9,0, "", "");
			for(Computer computer : list) {
				if(computer.getId() == 2) {
					assertEquals("CM-2a", computer.getName());
					assertNull(computer.getIntroduced());
					assertNull(computer.getDiscontinued());
					assertEquals("2", computer.getManufacturer());
				}
			}
		} catch (DAOException e) {
			logger.debug("problem in testGetListComputer", e);
		}
	} 

	@Test
	public void testGetComputer() {
		ComputerDAO cd = ComputerDAO.INSTANCE;
		try {
			Optional<Computer> details = cd.getComputer(6L);
			assertEquals("MacBook Pro", details.get().getName());
			assertEquals(Date.valueOf("2006-01-10").toLocalDate(), details.get().getIntroduced());
			assertNull(details.get().getDiscontinued());
			assertEquals("1", details.get().getManufacturer());
		} catch (DAOException e) {
			logger.debug("problem in testGetComputer", e);
		}

	}

	@Test
	public void testCreateComputer() {
		ComputerDAO cd = ComputerDAO.INSTANCE;	

		try {
			Long id = cd.createComputer("ASUS", Date.valueOf("2008-01-04").toLocalDate(), Date.valueOf("2018-01-01").toLocalDate(), "1");
			List<Computer> list = cd.getListComputer(9,0, "", "");
			for(Computer computer : list) {
				if(computer.getId() == id) {
					assertEquals("ASUS", computer.getName());
					assertEquals(Date.valueOf("2008-01-04").toLocalDate(), computer.getIntroduced());
					assertEquals(Date.valueOf("2018-01-01").toLocalDate(), computer.getDiscontinued());
					assertEquals("1", computer.getManufacturer());
				}
			}
		}	
		catch (DAOException e) {
			logger.debug("problem in testCreateComputer", e);
		}

	} 

	@Test
	public void testUpdateComputer() {
		ComputerDAO cd = ComputerDAO.INSTANCE;

		try {
			cd.updateComputer(3L, "HP", Date.valueOf("2008-01-04").toLocalDate(), null, null);
			Optional<Computer> c = cd.getComputer(3L);
			assertEquals("HP", c.get().getName());	
			assertEquals(Date.valueOf("2008-01-04").toLocalDate(), c.get().getIntroduced());	
			assertEquals(null, c.get().getDiscontinued());
			assertEquals(null, c.get().getManufacturer());
		} catch (DAOException e) {
			logger.debug("problem in testUpdateComputer", e);
		}
	}

	@Test
	public void testDeleteComputer() {
		ComputerDAO cd = ComputerDAO.INSTANCE;
		try {
			cd.deleteComputer(3L);
			List<Computer> list = cd.getListComputer(9,0, "", "");
			for(Computer computer : list) {
				if(computer.getId() == 3L) {
					fail("Computer still exists");				
				}
			}
		} catch (DAOException e) {
			logger.debug("problem in testDeleteComputer", e);
		}
	} 

}