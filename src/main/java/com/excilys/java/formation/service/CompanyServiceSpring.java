package com.excilys.java.formation.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAOSpring;

@Service
@EnableTransactionManagement
public class CompanyServiceSpring {
	
	@Autowired
	private CompanyDAOSpring companyDAOSpring;
	
	
	public List<Company> listCompanies(int limit, int offset) throws ServiceException {
		return companyDAOSpring.getListCompany(limit, offset);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteTransactionCompany(long id) throws ServiceException {
			companyDAOSpring.deleteCompany(id);
	}

	/**
	 * counts the number of companies in the DB
	 * @return the number of companies in the DB
	 * @throws ServiceException
	 */
	public int count() throws ServiceException {
		return companyDAOSpring.count();
	
	}
}
