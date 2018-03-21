package com.excilys.java.formation.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ValidatorException;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/addComputer.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

		String name = (String) request.getParameter("name");
		String introducedStr =  (String) request.getParameter("introduced");
		String discontinuedStr =  (String) request.getParameter("discontinued");
		String manufacturer = (String) request.getParameter("price");
		List<Company> list = null;
		Date introduced = null, discontinued = null;
		try {
			introduced = Date.valueOf(introducedStr);
			discontinued = Date.valueOf(discontinuedStr);

		} catch (Exception e) {
			logger.info("Date invalid !");
		}
		Computer computer = new Computer(name, introduced, discontinued, manufacturer);
		ComputerService cs = ComputerService.INSTANCE;
		CompanyService company_service = CompanyService.INSTANCE;
		
		
		int i;
		try {
			i = company_service.count();
			list = company_service.listCompanies(i,0);
		} catch (DAOConfigurationException | ClassNotFoundException | SQLException e1) {
			logger.info("PB");
		}
		
		try {
			cs.createComputer(name, introduced, discontinued, manufacturer);
		} catch (ClassNotFoundException | SQLException | ValidatorException e) {
			logger.info("Erreur");
		}
		request.setAttribute("companyList", list);
		request.setAttribute("computer", computer);

	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/addComputer.jsp");
		dispatcher.forward(request, response);
	
}

}
