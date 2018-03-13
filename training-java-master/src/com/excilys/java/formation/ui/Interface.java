package com.excilys.java.formation.ui;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import com.excilys.java.formation.mapper.Company;
import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.SQLConnection;

public class Interface {
	
	/**
	 * Method to choose the action to execute
	 * @return number of the action to execute 
	 */
	public static int listFeatures() {
		System.out.println("Select an action :\n");
		System.out.println("1. List computers");
		System.out.println("2. List companies");
		System.out.println("3. Show computer details");
		System.out.println("4. Create a computer");
		System.out.println("5. Update a computer");
		System.out.println("6. Delete a computer");
		System.out.println("\n \n");
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez choisir une action :");
		int action = sc.nextInt();
		return action;
		
		
	}
	

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		ComputerDAO computers = new ComputerDAO(SQLConnection.getConnection());
		CompanyDAO companies = new CompanyDAO(SQLConnection.getConnection());
		
		switch (Interface.listFeatures()) {
			case 1 : 	
						System.out.println(" Computers :");
						for(Computer computer : computers.getListComputer()) {
							System.out.println(computer);
						}
						break;
			
			case 2 : 	
						System.out.println("\n Companies :");
						for(Company company : companies.getListCompany()) {
							System.out.println(company);
						}
						break;
			
			case 3 : 
						Scanner sc = new Scanner(System.in);
						System.out.println("give the id : ");
						Long id = sc.nextLong();
						System.out.println("\n Computer "+ computers.getComputer(id));
						break;
				
			case 4 : 
						System.out.println("Add a computer : ");
						Scanner sc1 = new Scanner(System.in);
						System.out.println("give the id : ");
						Long id_add = sc1.nextLong();
						System.out.println("give the name : ");
						sc1.nextLine();
					    String name = sc1.nextLine();  
	    
					    
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
					    
					    computers.createComputer(id_add, name, tm1, tm2, manufacturer);
						
						
						break;
				
			case 5 : 
						System.out.println("Update a computer : ");
						Scanner sc2 = new Scanner(System.in);
						System.out.println("give the id of the computer to Update : ");
						Long id_update = sc2.nextLong();
						System.out.println("give the new name : ");
						sc2.nextLine();
						String new_name = sc2.nextLine();  
						
						System.out.println("give the new introduced date : ");
			            String new_time = sc2.nextLine();
			            Date new_date = Date.valueOf(new_time);
			            Date tm3 = new Date(new_date.getTime());
			            
			            System.out.println("give the new discontinued date : ");
			            String new_time2 = sc2.nextLine();
			            Date new_date2 = Date.valueOf(new_time2);
			            Date tm4 = new Date(new_date2.getTime());
						
						computers.updateComputer(id_update, new_name,tm3, tm4);
						break;
				
			case 6 : 
						Scanner scanner = new Scanner(System.in);
						System.out.println("give the id of the computer to delete : ");
						Long id_delete = scanner.nextLong();
						computers.deleteComputer(id_delete);
						break;
				
			default : System.out.println("Choississez une action valide!");
		}
	}
}
