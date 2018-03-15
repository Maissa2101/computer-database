package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.pagination.Pagination;
import com.excilys.java.formation.persistence.CompanyDAO;

public enum CompanyService {
	
	INSTANCE;
	
	/**
	 * Method to show the list of companies
	 * @throws SQLException in case of a database access error
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 */
	public void listCompanies() throws SQLException, ClassNotFoundException {
		CompanyDAO companies = CompanyDAO.INSTANCE;
		List<Company> sourceList = new ArrayList<Company>();
		int page = 1;
		int i = 0;
		

		@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in);
		
		System.out.println("\n Companies : Press n to see the next page, p to see the previous page and q to quit : \\n");
		for(Company company : companies.getListCompany()) {
			sourceList.add(company);
		}
		
		
		ETQ:	while (true)
		{
			List<Company> s = Pagination.getPage(sourceList, page, 30);
		
		
			ListIterator<Company> li = s.listIterator();
			while(li.hasNext()){
				System.out.println(li.next());
				i++;
			}
		 
		 String scanner = sc.nextLine();
		 switch (scanner) {
		 case "n" : 	
		 	page++;
		 	if(i>=sourceList.size()) {
		 		final Logger LOG = LoggerFactory.getLogger(CompanyService.class);
		 		LOG.info("no more companies to show");
			break;
			}
		 	break;
		 	
		 case "p" :
			 	if(i>=1)
			 	page--;
			 	break;
		 case "q" : 
			 	System.out.println("Quit");
			 	break ETQ;
		 }
		}
	}
}
