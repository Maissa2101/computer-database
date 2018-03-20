package com.excilys.java.formation.pagination;

import java.sql.SQLException;
import java.util.List;
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.service.CompanyService;

public class PaginationCompany extends Page{
	private List<Company> companies;
	private CompanyService cs;

	public PaginationCompany(int limit) throws SQLException, ClassNotFoundException {
		this.offset = 0;
		this.limit = limit;
		this.cs = CompanyService.INSTANCE;
		this.dbSize = cs.count();
		this.companies = cs.listCompanies(limit, offset);
	}

	private void updateCompany() throws ClassNotFoundException, SQLException {
		this.companies = cs.listCompanies(limit, offset);
	}

	@Override
	public void getNext() throws ClassNotFoundException, SQLException {
		dbSize = cs.count();
		super.getNextOffset();
		updateCompany();

	}

	@Override
	public void getPrevious() throws ClassNotFoundException, SQLException {
		dbSize = cs.count();
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
