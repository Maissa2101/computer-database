package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import com.excilys.java.formation.mapper.Company;
import com.excilys.java.formation.pagination.Pagination;
import com.excilys.java.formation.persistence.CompanyDAO;

public class CompanyService {
	
	/**
	 * The instance is initialized when the class is first referenced
	 */
	private final static CompanyService companyService = new CompanyService();
	
	private CompanyService() {
		
	}
	
	/**
	 * controls the access to the unique instance of the CompanyService class
	 * @return the unique instance of the CompanyService class
	 */
	public static CompanyService getCompanyService() {
		return companyService;
	}
	
	/**
	 * Method to show the list of companies
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void listCompanies() throws SQLException, ClassNotFoundException {
		CompanyDAO companies = CompanyDAO.getCompanyDAO();
		List<Company> sourceList = new ArrayList<Company>();
		int page = 1;
		int i = 0;
		

		@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in);
		
		System.out.println("\n Companies : Press n to see the next page, p to see the previous page and q to quit : \\n");
		for(Company company : companies.getListCompany()) {
			sourceList.add(company);
		}
		
		
		while (true)
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
			System.out.println("no more companies to show");
			break;
			}
		 	break;
		 	
		 case "p" :
			 	if(i>=1)
			 	page--;
			 	break;
		 case "q" : 
			 	break;
		 }
		}
	}
}
