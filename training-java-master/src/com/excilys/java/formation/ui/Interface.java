package com.excilys.java.formation.ui;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						System.out.println("Add a computer : ");
						Scanner sc1 = new Scanner(System.in);
						System.out.println("give the id : ");
						Long id_add = sc1.nextLong();
						System.out.println("give the name : ");
						sc1.nextLine();
					    String name = sc1.nextLine();  
	    
						
			            System.out.println("introduced date : ");
			            String time = sc1.nextLine();
			            java.util.Date date = dateFormat.parse(time);
			            Timestamp tm1 = new Timestamp(date.getTime());
			            
			            System.out.println("disconnected date : ");
			            String time2 = sc1.nextLine();
			            java.util.Date date2 = dateFormat.parse(time2);
			            Timestamp tm2 = new Timestamp(date2.getTime());
			            
			            
					    System.out.println("give the manufacturer : ");
					    String manufacturer = sc1.nextLine(); 
					    
					    computers.createComputer(id_add, name, tm1, tm2, manufacturer);
						
						
						break;
				
			case 5 : 
						SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						System.out.println("Update a computer : ");
						Scanner sc2 = new Scanner(System.in);
						System.out.println("give the id of the computer to Update : ");
						Long id_update = sc2.nextLong();
						System.out.println("give the new name : ");
						sc2.nextLine();
						String new_name = sc2.nextLine();  
						
						System.out.println("give the new introduced date : ");
			            String new_time = sc2.nextLine();
			            java.util.Date new_date = dateFormat2.parse(new_time);
			            Timestamp tm3 = new Timestamp(new_date.getTime());
			            
			            System.out.println("give the new disconnected date : ");
			            String new_time2 = sc2.nextLine();
			            java.util.Date new_date2 = dateFormat2.parse(new_time2);
			            Timestamp tm4 = new Timestamp(new_date2.getTime());
						
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
