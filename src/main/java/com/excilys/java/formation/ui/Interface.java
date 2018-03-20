package com.excilys.java.formation.ui;



import java.sql.Date;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.java.formation.pagination.PaginationCompany;
import com.excilys.java.formation.pagination.PaginationComputer;
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
			System.out.println("Choose an action :");
			int action = 8;

			try {
				action = sc.nextInt();
			} catch (InputMismatchException e) {
			}

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
			case DEFAULT : 
				System.out.println("Choississez une action valide! \n");
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
		System.out.println(" Computers : Press n to see the next page, p to see the previous page and q to quit : ");	
		@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in);
		PaginationComputer pagination = new PaginationComputer(50);
		ETQ:	while (true)
		{
			pagination.printPage();
			String scanner = sc.nextLine();
			switch (scanner) {
			case "n" : 	
				pagination.getNext();
				break;			 	
			case "p" :
				pagination.getPrevious();
				break;
			case "q" : 
				System.out.println("Quit");
				break ETQ;
			default : 
				System.out.println("Invalid input, Press n to see the next page, p to see the previous page and q to quit : \n ");
				break;
			}
		}
	}

	/**
	 * Method to show the list of companies
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static void listCompanies() throws ClassNotFoundException, SQLException {

		@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in);
		System.out.println("\n Companies : Press n to see the next page, p to see the previous page and q to quit :");
		PaginationCompany pagination = new PaginationCompany(15);
		ETQ:	while (true){	
			pagination.printPage();
			String scanner = sc.nextLine();
			switch (scanner) {
			case "n" : 	
				pagination.getNext();
				break;
			case "p" :
				pagination.getPrevious();
				break;
			case "q" : 
				System.out.println("Quit");
				break ETQ;
			default : 
				System.out.println("Invalid input, Press n to see the next page, p to see the previous page and q to quit : \n ");
				break;
			}
		}
	}

	/**
	 * Method to show a computer details giving its ID
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ValidatorException 
	 */
	private static void computerDetails() throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerService computerS = ComputerService.INSTANCE;

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the id : ");
		Long id = sc.nextLong();	

		System.out.println(computerS.computerDetails(id));
	}

	/**
	 * Method to create a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ValidatorException 
	 */
	private static void createComputer() throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerService computerS = ComputerService.INSTANCE;

		System.out.println("Add a computer : ");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the name : ");
		String name = sc.nextLine();  

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

		System.out.println("give the manufacturer : ");
		String manufacturer = sc.nextLine(); 

		if(manufacturer.toLowerCase().equals("")) {
			manufacturer = null;
		} 

		computerS.createComputer(name, tm1, tm2, manufacturer);
	}

	/**
	 * Method to update a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ValidatorException 
	 */
	private static void updateComputer() throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerService computerS = ComputerService.INSTANCE;

		System.out.println("Update a computer : ");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the id of the computer to Update : ");
		Long id_update = sc.nextLong();

		System.out.println("give the new name : ");
		sc.nextLine();
		String new_name = sc.nextLine();  


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

		computerS.updateComputer(id_update, new_name, new_date, new_date2);
	}

	/**
	 * Method to delete a computer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ValidatorException 
	 */
	private static void deleteComputer() throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerService computerS = ComputerService.INSTANCE;

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("give the id of the computer to delete : ");	
		Long id_delete = scanner.nextLong();

		computerS.deleteComputer(id_delete);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ValidatorException  {
		Interface.listFeatures();
	}
}
