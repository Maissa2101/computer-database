package com.excilys.java.formation.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;



import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;

public class CompanyDAOTest {

	@Test
	public void testGetListCompany() {
		CompanyDAO cd = CompanyDAO.INSTANCE;

		try {
			List<Company> list = cd.getListCompany(42, 1);

			for(Company company: list) {
				if(company.getId() == 42) {
					assertEquals("Research In Motion", company.getName());
				}
			}

		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

}