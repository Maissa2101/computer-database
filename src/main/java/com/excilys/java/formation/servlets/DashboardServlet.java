package com.excilys.java.formation.servlets;

import java.io.IOException;
import java.sql.SQLException;
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
import com.excilys.java.formation.pagination.PaginationComputer;
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
	Logger logger = LoggerFactory.getLogger(CompanyService.class);

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


		PaginationComputer page = (PaginationComputer) request.getAttribute("ComputerPage");

		List<Computer> list = null;
		String offsetStr = request.getParameter("offset");
		String limitStr = request.getParameter("limit");

		int offset = 0;
		int limit = 0;
		try {
			offset = Integer.parseInt(offsetStr);
			limit = Integer.parseInt(limitStr);
		} catch(NumberFormatException e) {
			logger.error("offset and limit problem "+offset+"  "+limit);
		}

		try {
			if (page == null) {
				page = new PaginationComputer(limit);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			logger.error("Pagination error");
		}

		try {
			int i = computer_service.count();
			list = computer_service.listComputers(limit,offset);
			List<ComputerDTO> listDTO = new ArrayList<ComputerDTO>();
			for(Computer computer : list) {
				listDTO.add(computer_mapper.getComputerDTOFromComputer(computer));
			}

			request.setAttribute("computerList", listDTO);
			request.setAttribute("count", i);
			request.setAttribute("pagination", page.getComputers());

			RequestDispatcher dispatcher = request.getSession().getServletContext().getRequestDispatcher("/dashboard.jsp");
			dispatcher.forward(request, response);
		} catch (DAOConfigurationException | DAOException e) {
			logger.error("Problem in my Dashboard");
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
