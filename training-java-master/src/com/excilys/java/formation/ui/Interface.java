package com.excilys.java.formation.ui;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

import com.excilys.java.formation.mapper.Company;
import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.persistence.CompanyDAO;
import com.excilys.java.formation.persistence.ComputerDAO;
import com.excilys.java.formation.persistence.SQLConnection;

public class Interface {
	
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
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
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
					    
					    /*System.out.println("introduced date : ");
					    Timestamp tm = Timestamp.valueOf(sc1.nextLine());
						
					    
					    System.out.println("give the manufacturer : ");
						sc1.nextLine();
					    String manufacturer = sc1.nextLine(); 
					    */
					    computers.createComputer(id_add, name, null, null, null);
						
						
						break;
				
			case 5 : break;
				
			case 6 : break;
				
			default : System.out.println("Choississez une action valide!");
		}
	}
}
