package com.excilys.java.formation.pagination;


import java.util.List;


import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.service.CompanyServiceSpring;
import com.excilys.java.formation.service.ServiceException;

public class PaginationCompany extends Page{
	private List<Company> companies;
	private CompanyServiceSpring companyService;

	public PaginationCompany(int limit, CompanyServiceSpring companyService) throws ServiceException {
		this.offset = 0;
		this.limit = limit;
		this.dbSize = companyService.count();
		this.companyService = companyService;
		this.companies = companyService.listCompanies(limit, offset);
	}
	
	/**
	 * Update the list of companies to show with the limit and offset
	 * @throws ServiceException
	 */
	private void updateCompany() throws ServiceException {
		this.companies = companyService.listCompanies(limit, offset);
	}

	@Override
	public void getNext() throws ServiceException {
		dbSize = companyService.count();
		super.getNextOffset();
		updateCompany();

	}

	@Override
	public void getPrevious() throws ServiceException {
		dbSize = companyService.count();
		super.getPreviousOffset();
		updateCompany();
	}


	@Override
	public void printPage() {
		for(Company company : this.companies) {
			System.out.println(company);
		}

	}
}
