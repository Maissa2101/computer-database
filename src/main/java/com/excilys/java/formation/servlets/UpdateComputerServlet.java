package com.excilys.java.formation.servlets;

import java.io.IOException;
import java.util.Optional;

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
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.service.ValidatorException;

/**
 * Servlet implementation class UpdateComputerServlet
 */
@WebServlet("/editComputer")
public class UpdateComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	ComputerService computerService = ComputerService.INSTANCE;
	ComputerDTOMapper computerDTOMapper = ComputerDTOMapper.INSTANCE;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateComputerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = (String) request.getParameter("id");
		Optional<Computer> computer = null;

		long id=0;
		try {
			id = Long.parseLong(idStr);
		} catch(NumberFormatException e) {
			logger.debug("id problem {}", id);
		}
		computer = computerService.getComputer(id);
		if(!computer.isPresent()) {
			response.sendRedirect(request.getServletPath() + "/DashboardServlet");
			return;
		}
		try {
			request.setAttribute("companyList", CompanyService.INSTANCE.listCompanies(CompanyService.INSTANCE.count(), 0));
		} catch (DAOException e) {
			logger.debug("Problem in get : AddComputerServlet");
		}
	
		request.setAttribute("computer", computer.get());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/editComputer.jsp");
		dispatcher.forward(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = (String) request.getParameter("id");
		String name = (String) request.getParameter("name");
		String introduced = (String) request.getParameter("introduced");
		String discontinued = (String) request.getParameter("discontinued");
		String manufacturer = (String) request.getParameter("manufacturer");
		
		long id = 0;
		try {
			id = Long.parseLong(idStr);
		} catch(NumberFormatException e) {
			logger.debug("id problem {}", id);
		}
		
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(id);
		computerDTO.setName(name);
		computerDTO.setIntroduced(introduced);
		computerDTO.setDiscontinued(discontinued);
		computerDTO.setManufacturer(manufacturer);
		Computer computer = computerDTOMapper.getComputerFromComputerDTO(computerDTO);

		try {
			computerService.updateComputer(computer.getId(), computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer());
		} catch (ServiceException | ValidatorException e) {
			logger.debug("Problem with the update function in the Servlet", e);
		}

		request.setAttribute("computer", computer);
		response.sendRedirect(request.getContextPath() + "/DashboardServlet");
 
	}

}
