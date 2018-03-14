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
	
	/**
	 * The instance is initialized when the class is first referenced
	 */
	private final static ComputerService computerService = new ComputerService();
	
	private ComputerService() {
		
	}
	
	/**
	 * controls the access to the unique instance of the ComputerService class
	 * @return the unique instance of the ComputerService class
	 */
	public static ComputerService getComputerService() {
		return computerService;
	}
	
	/**
	 * Method to show the list of computers
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	
	/**
	 * Method to show the details of a computer given its ID
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void computerDetails() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		ComputerValidator computerV = ComputerValidator.getComputerValidator();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the id : ");
		Long id = sc.nextLong();	
		while(!computerV.idValidator(id)) {
			id = sc.nextLong();
		}
		System.out.println("\n Computer "+ computers.getComputer(id));
	}
	
	/**
	 * Method to create a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void createComputer() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		ComputerValidator computerV = ComputerValidator.getComputerValidator();
		CompanyValidator companyV = CompanyValidator.getCompanyValidator();
		
		System.out.println("Add a computer : ");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the id : ");
		Long id_add = sc.nextLong();
		System.out.println("give the name : ");
		sc.nextLine();
	    String name = sc.nextLine();  
	    
	    
	    while(!computerV.nameValidator(name)) {
	    	name = sc.nextLine();
	    }	
	   
	    
        System.out.println("introduced date : ");
        String time = sc.nextLine();
        
        Date tm1;
        if (time.toLowerCase().equals("")) {
        	tm1 = null;
        }
        else {
        	tm1 = Date.valueOf(time);
        }
        
        
        System.out.println("discontinued date : ");
        String time2 = sc.nextLine();
        
        Date tm2;
        if (time.toLowerCase().equals("")) {
        	tm2 = null;
        }
        else {
        	tm2 = Date.valueOf(time2);
        }
        
        if (!computerV.DateValidator(tm1, tm2) && (tm2 != null)) {
        	System.out.println("give a valid discontinued date : ");
        	time2 = sc.nextLine();
        	tm2 = Date.valueOf(time2);
        }
        
        System.out.println("give the manufacturer : ");
        String manufacturer = sc.nextLine(); 
       
        if(manufacturer.toLowerCase().equals("")) {
        	manufacturer = null;
        } 
        else {
        	 while(!companyV.idCompanyValidator(manufacturer)) {
             	manufacturer = sc.nextLine(); 
        	 }
        }
	    
	    computers.createComputer(id_add, name, tm1, tm2, manufacturer);
	}
	
	/**
	 * Method to update a computer details given its ID
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateComputer() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		ComputerValidator computerV = ComputerValidator.getComputerValidator();
		
		System.out.println("Update a computer : ");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the id of the computer to Update : ");
		Long id_update = sc.nextLong();
		
		while(!computerV.idValidator(id_update)) {
	    	id_update = sc.nextLong();
	    }	

		System.out.println("give the new name : ");
		sc.nextLine();
		String new_name = sc.nextLine();  
		
		while(!computerV.nameValidator(new_name)) {
	    	new_name = sc.nextLine();
	    }
		
		System.out.println("give the new introduced date : ");
        String new_time = sc.nextLine();
        
        Date new_date;
        if (new_time.toLowerCase().equals("")) {
        	new_date = null;
        }
        else {
        	new_date = Date.valueOf(new_time);
        }
        
        System.out.println("give the new discontinued date : ");
        String new_time2 = sc.nextLine();
        
        Date new_date2;
        if (new_time2.toLowerCase().equals("")) {
        	new_date2 = null;
        }
        else {
        	new_date2 = Date.valueOf(new_time2);
        }
        
        if (!computerV.DateValidator(new_date, new_date2) && (new_date2 != null)) {
        	System.out.println("give a valid discontinued date : ");
        	new_time2 = sc.nextLine();
        	new_date2 = Date.valueOf(new_time2);
        }

		computers.updateComputer(id_update, new_name,new_date, new_date2);
	}
	
	/**
	 * Method to delete a computer given its ID
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteComputer() throws ClassNotFoundException, SQLException {
		ComputerDAO computers = ComputerDAO.getComputerDAO();
		ComputerValidator computerV = ComputerValidator.getComputerValidator();
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("give the id of the computer to delete : ");	
		Long id_delete = scanner.nextLong();
		
		while(!computerV.idValidator(id_delete)) {
	    	id_delete = scanner.nextLong();
	    }	
		
		computers.deleteComputer(id_delete);
	}
}
