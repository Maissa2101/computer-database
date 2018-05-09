package com.excilys.java.formation.console;

import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.java.formation.binding.ComputerDTOMapper;
import com.excilys.java.formation.console.configuration.InterfaceConfiguration;
import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.pagination.PaginationCompany;
import com.excilys.java.formation.pagination.PaginationComputer;
import com.excilys.java.formation.service.CompanyServiceSpring;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.service.ValidatorException;

@Component
public class Interface {
	private Logger logger = LoggerFactory.getLogger(Interface.class);
	private CompanyServiceSpring companyService;
	private ComputerServiceSpring computerService;
	private ComputerDTOMapper mapper;

	@Autowired
	public Interface(ComputerDTOMapper mapper, CompanyServiceSpring companyService, ComputerServiceSpring computerService) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.mapper = mapper;
	}

	private static final String REST_URI = "http://localhost:8080/webapp/";

	private WebTarget client = ClientBuilder.newClient().target(REST_URI);

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


	private void listComputers() throws ServiceException {
		System.out.println(" Computers : Press n to see the next page, p to see the previous page and q to quit : ");	
		Scanner sc= new Scanner(System.in);

		int pageNumber = 1;
		int limit = 20;

		ETQ:	while (true) {
			client
			.path("pagination/" + limit + "/" + pageNumber + "/null" + "/null")
			.request(MediaType.APPLICATION_JSON)
			.get(new GenericType<List<ComputerDTO>>() {}).forEach(System.out::println);
			String scanner = sc.nextLine();
			switch (scanner) {
			case "n" : 	
				pageNumber++;
				break;
			case "p" :
				pageNumber--;
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

	private void listCompanies() throws ServiceException {
		Scanner sc= new Scanner(System.in);
		System.out.println("\n Companies : Press n to see the next page, p to see the previous page and q to quit :");

		int pageNumber = 1;
		int limit = 20;

		ETQ: while (true){	
			client
			.path("paginationCompany/" + limit + "/" + pageNumber)
			.request(MediaType.APPLICATION_JSON)
			.get(new GenericType<List<CompanyDTO>>() {}).forEach(System.out::println);
			String scanner = sc.nextLine();
			switch (scanner) {
			case "n" : 	
				pageNumber++;
				break;
			case "p" :
				pageNumber--;
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

	private void computerDetails() throws ServiceException, ValidatorException {
		Scanner sc = new Scanner(System.in);
		System.out.println("give the id : ");
		long id = sc.nextLong();	
		ComputerDTO computer = client
				.path("getComputer/" + String.valueOf(id))
				.request(MediaType.APPLICATION_JSON)
				.get(ComputerDTO.class);
		System.out.println(computer.toString());

	}


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
		Company company = new Company();
		if(manufacturer != null) {
			company.setId(Integer.parseInt(manufacturer));
		} else {
			company = null;
		}
		Computer computerBuilder = new Computer.ComputerBuilder(name).introduced(introduced).discontinued(discontinued).manufacturer(company).build();
		ComputerDTO computerDTO = mapper.getComputerDTOFromComputer(computerBuilder);

		client
		.path("addComputer/")
		.request(MediaType.APPLICATION_JSON)
		.post(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
	}

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
		Company company = new Company();
		if(manufacturer != null) {
			company.setId(Integer.parseInt(manufacturer));
		}
		else {
			company = null;
		}

		Computer computerBuilder = new Computer.ComputerBuilder(idUpdate, newName).introduced(newDate).discontinued(newDate2).manufacturer(company).build();
		ComputerDTO computerDTO = mapper.getComputerDTOFromComputer(computerBuilder);

		client
		.path("updateComputer/")
		.request(MediaType.APPLICATION_JSON)
		.put(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
	}

	private void deleteComputer() throws ServiceException, ValidatorException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("give the id of the computer to delete : ");	
		long idDelete = scanner.nextLong();

		client
		.path("delete/" + String.valueOf(idDelete))
		.request(MediaType.APPLICATION_JSON)
		.delete();
	}

	private void deleteCompany() throws ServiceException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("give the id of the company to delete : ");	
		long idDelete = scanner.nextLong();
		client
		.path("deleteCompany/" + String.valueOf(idDelete))
		.request(MediaType.APPLICATION_JSON)
		.delete();
	}


	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(InterfaceConfiguration.class);
		context.getBean(Interface.class).listFeatures();
	}
}
