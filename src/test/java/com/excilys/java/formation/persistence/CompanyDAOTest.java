package com.excilys.java.formation.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;

public class CompanyDAOTest {
	
	static Logger logger = LoggerFactory.getLogger(CompanyDAOTest.class);
	
	@Test
	public void testGetListCompany() {
		CompanyDAO cd = CompanyDAO.INSTANCE;
		try {
			List<Company> list = cd.getListCompany(3, 1);
			for(Company company: list) {
				if(company.getId() == 1) {
					assertEquals("Apple Inc.", company.getName());
				}
			}
		} catch (DAOException e) {
			logger.debug("problem in testGetListCompany", e);
		}
	}

}
