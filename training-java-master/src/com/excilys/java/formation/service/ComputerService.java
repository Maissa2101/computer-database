package com.excilys.java.formation.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.pagination.Pagination;
import com.excilys.java.formation.persistence.ComputerDAO;

public class ComputerService {
	
	private final static ComputerService computerService = new ComputerService();
	
	private ComputerService() {
		
	}
	
	public static ComputerService getComputerService() {
		return computerService;
	}

	public void listComputers() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		List<Computer> sourceList = new ArrayList<Computer>();
		int page = 1;
		int i = 0;
		
		@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in);
		
		
		System.out.println(" Computers : Press n to see the next page, p to see the previous page and q to quit : \n");
		for(Computer computer : computers.getListComputer()) {
			sourceList.add(computer);}
			
			
		ETQ:	while (true)
			{
				List<Computer> s = Pagination.getPage(sourceList, page, 30);
				ListIterator<Computer> li = s.listIterator();
				while(li.hasNext()){
					System.out.println(li.next());
					i++;
				}
			 
			 String scanner = sc.nextLine();
			 switch (scanner) {
			 case "n" : 	
			 	page++;
			 	if(i>=sourceList.size()) {
				System.out.println("no more computers to show");
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
	
	public void computerDetails() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the id : ");
		Long id = sc.nextLong();
		System.out.println("\n Computer "+ computers.getComputer(id));
	}
	
	public void createComputer() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		ComputerValidator computerV = ComputerValidator.getComputerValidator();
		CompanyValidator companyV = CompanyValidator.getCompanyValidator();
		
		System.out.println("Add a computer : ");
		@SuppressWarnings("resource")
		Scanner sc1 = new Scanner(System.in);
		System.out.println("give the id : ");
		Long id_add = sc1.nextLong();
		System.out.println("give the name : ");
		sc1.nextLine();
	    String name = sc1.nextLine();  
	    
	    
	    while(!computerV.nameValidator(name)) {
	    	name = sc1.nextLine();
	    }	
	   
	    
        System.out.println("introduced date : ");
        String time = sc1.nextLine();
        
        Date tm1;
        if (time.toLowerCase().equals("")) {
        	tm1 = null;
        }
        else {
        	tm1 = Date.valueOf(time);
        }
        
        
        System.out.println("discontinued date : ");
        String time2 = sc1.nextLine();
        
        Date tm2;
        if (time.toLowerCase().equals("")) {
        	tm2 = null;
        }
        else {
        	tm2 = Date.valueOf(time2);
        }
        
        System.out.println("give the manufacturer : ");
        String manufacturer = sc1.nextLine(); 
        while(!companyV.idCompanyValidator(manufacturer)) {
        	manufacturer = sc1.nextLine(); 
        }
	   
	    
	    
	    computers.createComputer(id_add, name, tm1, tm2, manufacturer);
	}
	
	public void updateComputer() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		System.out.println("Update a computer : ");
		@SuppressWarnings("resource")
		Scanner sc2 = new Scanner(System.in);
		System.out.println("give the id of the computer to Update : ");
		Long id_update = sc2.nextLong();
		System.out.println("give the new name : ");
		sc2.nextLine();
		String new_name = sc2.nextLine();  
		
		while(new_name.equals("")) {
	    	System.out.println("name of the computer is mandatory");
	    	new_name = sc2.nextLine();
	    }
		
		System.out.println("give the new introduced date : ");
        String new_time = sc2.nextLine();
        
        Date new_date;
        if (new_time.toLowerCase().equals("")) {
        	new_date = null;
        }
        else {
        	new_date = Date.valueOf(new_time);
        }
        
        System.out.println("give the new discontinued date : ");
        String new_time2 = sc2.nextLine();
        
        Date new_date2;
        if (new_time2.toLowerCase().equals("")) {
        	new_date2 = null;
        }
        else {
        	new_date2 = Date.valueOf(new_time2);
        }

		computers.updateComputer(id_update, new_name,new_date, new_date2);
	}
	
	public void deleteComputer() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("give the id of the computer to delete : ");
		Long id_delete = scanner.nextLong();
		computers.deleteComputer(id_delete);
	}
}
