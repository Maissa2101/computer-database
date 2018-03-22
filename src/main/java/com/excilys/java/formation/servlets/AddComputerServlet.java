package com.excilys.java.formation.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ValidatorException;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setAttribute("companyList", CompanyService.INSTANCE.listCompanies(CompanyService.INSTANCE.count(), 0));
		} catch (ClassNotFoundException | DAOConfigurationException | SQLException e) {
			logger.info("Problem in get : AddComputerServlet");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/addComputer.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService computer_service = ComputerService.INSTANCE;
		ComputerDTOMapper computer_dto_mapper = ComputerDTOMapper.INSTANCE;
		
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String manufacturer = request.getParameter("companyId");

		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName(name);
		computerDTO.setIntroduced(introduced);
		computerDTO.setDiscontinued(discontinued);
		computerDTO.setManufacturer(manufacturer);
		
		Computer computer = computer_dto_mapper.getComputerFromComputerDTO(computerDTO);
		try {
			computer_service.createComputer(computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer());
		} catch (ClassNotFoundException | SQLException | ValidatorException e) {
			logger.info("Problem in AddCompanyServlet");
		}
		request.setAttribute("computer", computer);
		response.sendRedirect(request.getContextPath() + "/addComputer.jsp");

	}

}
