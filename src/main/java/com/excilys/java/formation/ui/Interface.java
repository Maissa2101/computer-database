package com.excilys.java.formation.ui;



import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.pagination.PaginationCompany;
import com.excilys.java.formation.pagination.PaginationComputer;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.service.ValidatorException;


public class Interface {
	static Logger logger = LoggerFactory.getLogger(Interface.class);

	/**
	 * Method to choose and execute an action
	 * @throws ServiceException
	 * @throws ValidatorException 
	 */
	public static void listFeatures() throws ServiceException, ValidatorException {

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
	 * @throws ServiceException
	 */
	private static void listComputers() throws ServiceException {
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
	 * @throws ServiceException
	 */
	private static void listCompanies() throws ServiceException {
		@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in);
		System.out.println("\n Companies : Press n to see the next page, p to see the previous page and q to quit :");
		PaginationCompany pagination = null;
		try {
			pagination = new PaginationCompany(15);
		} catch (ClassNotFoundException | SQLException e) {
			logger.debug("listCompanies problem", e);
		}
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
	 * @throws ServiceException
	 * @throws ValidatorException 
	 */
	private static void computerDetails() throws ServiceException, ValidatorException {
		ComputerService computerService = ComputerService.INSTANCE;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the id : ");
		long id = sc.nextLong();	
		System.out.println(computerService.computerDetails(id));
	}

	/**
	 * Method to create a computer
	 * @throws ServiceException
	 * @throws ValidatorException 
	 */
	private static void createComputer() throws ServiceException, ValidatorException {
		ComputerService computerService = ComputerService.INSTANCE;
		System.out.println("Add a computer : ");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the name : ");
		String name = sc.nextLine();  
		System.out.println("introduced date : ");
		String time = sc.nextLine();
		LocalDate introduced;
		if (time.toLowerCase().equals("")) 
		{
			introduced = null;
		}
		else {
			introduced = Date.valueOf(time).toLocalDate();
		}
		System.out.println("discontinued date : ");
		String time2 = sc.nextLine();

		LocalDate discontinued;
		if (time2.toLowerCase().equals("")) 
		{
			discontinued = null;
		}
		else 
		{
			discontinued = Date.valueOf(time2).toLocalDate();
		}
		System.out.println("give the manufacturer : ");
		String manufacturer = sc.nextLine(); 
		if(manufacturer.toLowerCase().equals("")) 
		{
			manufacturer = null;
		} 
		computerService.createComputer(name, introduced, discontinued, manufacturer);
	}

	/**
	 * Method to update a computer
	 * @throws ServiceException
	 * @throws ValidatorException 
	 */
	private static void updateComputer() throws ServiceException, ValidatorException {
		ComputerService computerService = ComputerService.INSTANCE;
		System.out.println("Update a computer : ");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("give the id of the computer to Update : ");
		long idUpdate = sc.nextLong();
		System.out.println("give the new name : ");
		sc.nextLine();
		String newName = sc.nextLine();  
		System.out.println("give the new introduced date : ");
		String newTime = sc.nextLine();
		LocalDate newDate;
		if (newTime.toLowerCase().equals("")) 
		{
			newDate = null;
		}
		else 
		{
			newDate = Date.valueOf(newTime).toLocalDate();
		}
		System.out.println("give the new discontinued date : ");
		String newTime2 = sc.nextLine();
		LocalDate newDate2;
		if (newTime2.toLowerCase().equals("")) 
		{
			newDate2 = null;
		}
		else {
			newDate2 = Date.valueOf(newTime2).toLocalDate();
		}
		computerService.updateComputer(idUpdate, newName, newDate, newDate2);
	}

	/**
	 * Method to delete a computer
	 * @throws ServiceException
	 * @throws ValidatorException 
	 */
	private static void deleteComputer() throws ServiceException, ValidatorException {
		ComputerService computerService = ComputerService.INSTANCE;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("give the id of the computer to delete : ");	
		long idDelete = scanner.nextLong();
		computerService.deleteComputer(idDelete);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ValidatorException  {
		Interface.listFeatures();
	}
}
