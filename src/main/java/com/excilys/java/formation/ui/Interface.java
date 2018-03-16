package com.excilys.java.formation.ui;



import java.sql.SQLException;
import java.util.Scanner;

import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ValidatorException;


public class Interface {

	
	/**
	 * Method to choose and execute an action
	 * @throws ClassNotFoundException when no definition for the class with the specified name could be found
	 * @throws SQLException in case of a database access error
	 * @throws ValidatorException 
	 */
	public static void listFeatures() throws ClassNotFoundException, SQLException, ValidatorException {
	
	 while(true) {
			System.out.println("Select an action :\n");
			System.out.println("1. List computers");
			System.out.println("2. List companies");
			System.out.println("3. Show computer details");
			System.out.println("4. Create a computer");
			System.out.println("5. Update a computer");
			System.out.println("6. Delete a computer");
			System.out.println("7. Quit");
			System.out.println("\n");
			
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println("Veuillez choisir une action :");
			int action = sc.nextInt();
			switch(Feature.values()[action-1]) {
				case LIST_COMPUTERS :	
					listComputers(); 
					break;
					
				case LIST_COMPANIES :
					listCompanies();
					break;
					
				case SHOW_COMPUTER_DETAILS :
					computerDetails();
					break;
					
				case CREATE_COMPUTER :
					createComputer();
					break;
					
				case UPDATE_COMPUTER : 
					updateComputer();
					break;
					
				case DELETE_COMPUTER :
					deleteComputer();
					break;
				case QUIT :
					return;
				default :
					System.out.println("Choississez une action valide!");
					break;
			}
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
	 * @throws ValidatorException 
	 */
	private static void computerDetails() throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerService computerS = ComputerService.INSTANCE;
		computerS.computerDetails();
	}
	
	/**
	 * Method to create a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ValidatorException 
	 */
	private static void createComputer() throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerService computerS = ComputerService.INSTANCE;
		computerS.createComputer();
	}
	
	/**
	 * Method to update a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ValidatorException 
	 */
	private static void updateComputer() throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerService computerS = ComputerService.INSTANCE;
		computerS.updateComputer();
	}
	
	/**
	 * Method to delete a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ValidatorException 
	 */
	private static void deleteComputer() throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerService computerS = ComputerService.INSTANCE;
		computerS.deleteComputer();
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ValidatorException  {
			Interface.listFeatures();
	}
	
}
