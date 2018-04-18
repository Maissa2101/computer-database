package com.excilys.java.formation.ui;

import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.excilys.java.formation.pagination.PaginationCompany;
import com.excilys.java.formation.pagination.PaginationComputer;
import com.excilys.java.formation.service.CompanyServiceSpring;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.service.ValidatorException;


@Controller
public class Interface {
	private Logger logger = LoggerFactory.getLogger(Interface.class);
	@Autowired
	private CompanyServiceSpring companyService;
	@Autowired
	private ComputerServiceSpring computerService;
	
	/**
	 * Method to choose and execute an action
	 */
	public void listFeatures() {

		while(true) {
			System.out.println("Select an action :\n");
			System.out.println("1. List computers");
			System.out.println("2. List companies");
			System.out.println("3. Show computer details");
			System.out.println("4. Create a computer");
			System.out.println("5. Update a computer");
			System.out.println("6. Delete a computer");
			System.out.println("7. Delete a company");
			System.out.println("8. Quit");
			System.out.println("\n");

			Scanner sc = new Scanner(System.in);
			System.out.println("Choose an action :");
			int action = 8;

			try {
				action = sc.nextInt();
			} catch (InputMismatchException e) {
				logger.debug("listFeatures problem");
			}

			try {
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
				case DELETE_COMPANY :
					deleteCompany();
					break;
				case QUIT :
					return;
				case DEFAULT : 
					System.out.println("Choississez une action valide! \n");
					break;
				}
			} catch(ValidatorException | ServiceException e) {
				logger.debug("Problem in list features", e);
			}
		}
	}

	/**
	 * Method to show the list of computers
	 * @throws ServiceException
	 */
	private void listComputers() throws ServiceException {
		System.out.println(" Computers : Press n to see the next page, p to see the previous page and q to quit : ");	
		Scanner sc= new Scanner(System.in);
		PaginationComputer pagination = new PaginationComputer(50, computerService);
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
	private void listCompanies() throws ServiceException {
		Scanner sc= new Scanner(System.in);
		System.out.println("\n Companies : Press n to see the next page, p to see the previous page and q to quit :");
		PaginationCompany pagination = null;
		pagination = new PaginationCompany(15, companyService);

		ETQ: while (true){	
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
	private void computerDetails() throws ServiceException, ValidatorException {
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
	private void createComputer() throws ServiceException, ValidatorException {
		System.out.println("Add a computer : ");
		Scanner sc = new Scanner(System.in);
		System.out.println("give the name : ");
		String name = sc.nextLine();  
		System.out.println("introduced date : ");
		String time = sc.nextLine();
		LocalDate introduced;
		if (time.equalsIgnoreCase("")) 
		{
			introduced = null;
		}
		else {
			introduced = Date.valueOf(time).toLocalDate();
		}
		System.out.println("discontinued date : ");
		String time2 = sc.nextLine();

		LocalDate discontinued;
		if (time2.equalsIgnoreCase("")) 
		{
			discontinued = null;
		}
		else 
		{
			discontinued = Date.valueOf(time2).toLocalDate();
		}
		System.out.println("give the manufacturer : ");
		String manufacturer = sc.nextLine(); 
		if(manufacturer.equalsIgnoreCase("")) 
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
	private void updateComputer() throws ServiceException, ValidatorException {
		System.out.println("Update a computer : ");
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
		if (newTime2.equalsIgnoreCase("")) 
		{
			newDate2 = null;
		}
		else {
			newDate2 = Date.valueOf(newTime2).toLocalDate();
		}
		System.out.println("give the new company : ");
		String manufacturer = sc.nextLine();
		if (manufacturer.equalsIgnoreCase("")) 
		{
			manufacturer = null;
		}

		computerService.updateComputer(idUpdate, newName, newDate, newDate2, manufacturer);
	}

	/**
	 * Method to delete a computer
	 * @throws ServiceException
	 * @throws ValidatorException 
	 */
	private void deleteComputer() throws ServiceException, ValidatorException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("give the id of the computer to delete : ");	
		long idDelete = scanner.nextLong();
		computerService.deleteComputer(idDelete);
	}
	
	private void deleteCompany() throws ServiceException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("give the id of the company to delete : ");	
		long idDelete = scanner.nextLong();
		companyService.deleteTransactionCompany(idDelete);
	}
	

	public static void main(String[] args) {
		Interface ui = new Interface();
	    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml", Interface.class);
		ui = context.getBean(Interface.class);
		context.close();
		ui.listFeatures();
	}
}
