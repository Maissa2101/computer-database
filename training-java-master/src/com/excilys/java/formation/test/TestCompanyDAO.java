package com.excilys.java.formation.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;

public class TestCompanyDAO {

	@Test
	public void testGetListCompany() {
		CompanyDAO cd = CompanyDAO.INSTANCE;
		
		try {
			List<Company> list = cd.getListCompany();
			
			for(Company company: list) {
				if(company.getId() == 42) {
					assertEquals("Research In Motion", company.getName());
				}
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
