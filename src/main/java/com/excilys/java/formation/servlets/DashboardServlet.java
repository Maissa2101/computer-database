package com.excilys.java.formation.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService computer_service = ComputerService.INSTANCE;
		ComputerDTOMapper computer_mapper = ComputerDTOMapper.INSTANCE;

		List<Computer> list = null;

		Logger logger = LoggerFactory.getLogger(CompanyService.class);

		String froms = request.getParameter("from");
		String tos = request.getParameter("to");

		int from = 0;
		try {
			from = Integer.parseInt(froms);
		} catch (NumberFormatException e) {
			logger.error("from Not valid");
		}

		int to = from + 15;

		try {
			to = Integer.parseInt(tos);
		} catch (NumberFormatException e) {
			logger.error("to Not valid");
		}

		try {
			int i = computer_service.count();
			list = computer_service.listComputers(to - from,from);
			List<ComputerDTO> listDTO = new ArrayList<ComputerDTO>();
			for(Computer computer : list) {
				listDTO.add(computer_mapper.getComputerDTOFromComputer(computer));
			}

			to = Integer.min(to,i);
			from = Integer.max(from,  0);

			request.setAttribute("computerList", listDTO);
			request.setAttribute("count", i);
			request.setAttribute("to", new Integer(to));
			request.setAttribute("from", new Integer(from));

			RequestDispatcher dispatcher = request.getSession().getServletContext().getRequestDispatcher("/dashboard.jsp");
			dispatcher.forward(request, response);
		} catch (DAOConfigurationException | DAOException e) {
			logger.error("");
		} 


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
