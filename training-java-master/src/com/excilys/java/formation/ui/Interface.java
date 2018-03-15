package com.excilys.java.formation.ui;



import java.sql.SQLException;
import java.util.Scanner;

import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;


public class Interface {
	
	
	/**
	 * Method to choose and execute an action
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 */
	public static void listFeatures() throws ClassNotFoundException, SQLException {
		System.out.println("Select an action :\n");
		System.out.println("1. List computers");
		System.out.println("2. List companies");
		System.out.println("3. Show computer details");
		System.out.println("4. Create a computer");
		System.out.println("5. Update a computer");
		System.out.println("6. Delete a computer");
		System.out.println("\n");
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez choisir une action :");
		int action = sc.nextInt();
		switch(action) {
			case 1 :	
				listComputers(); 
				break;
				
			case 2 :
				listCompanies();
				break;
				
			case 3 :
				computerDetails();
				break;
				
			case 4 :
				createComputer();
				break;
				
			case 5 : 
				updateComputer();
				break;
				
			case 6 :
				deleteComputer();
				break;
				
			default :
				System.out.println("Choississez une action valide!");
				break;
		}
	}
	
	/**
	 * Method to show the list of computers
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void listComputers() throws ClassNotFoundException, SQLException {
		ComputerService computerS = ComputerService.INSTANCE;
		computerS.listComputers();
	}
	
	/**
	 * Method to show the list of companies
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static void listCompanies() throws ClassNotFoundException, SQLException {
		CompanyService companyS = CompanyService.INSTANCE;
		companyS.listCompanies();
	}
	
	/**
	 * Method to show a computer details giving its ID
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void computerDetails() throws ClassNotFoundException, SQLException {
		ComputerService computerS = ComputerService.INSTANCE;
		computerS.computerDetails();
	}
	
	/**
	 * Method to create a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void createComputer() throws ClassNotFoundException, SQLException {
		ComputerService computerS = ComputerService.INSTANCE;
		computerS.createComputer();
	}
	
	/**
	 * Method to update a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void updateComputer() throws ClassNotFoundException, SQLException {
		ComputerService computerS = ComputerService.INSTANCE;
		computerS.updateComputer();
	}
	
	/**
	 * Method to delete a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void deleteComputer() throws ClassNotFoundException, SQLException {
		ComputerService computerS = ComputerService.INSTANCE;
		computerS.deleteComputer();
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException  {
			Interface.listFeatures();
	}
	
}
