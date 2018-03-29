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
import com.excilys.java.formation.pagination.PaginationComputer;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(CompanyService.class);
	ComputerService computerService = ComputerService.INSTANCE;
	ComputerDTOMapper computerMapper = ComputerDTOMapper.INSTANCE;


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
		PaginationComputer page = (PaginationComputer) request.getAttribute("ComputerPage");
		List<Computer> listSearch = null;
		String PageNumberStr = request.getParameter("pageNumber");
		String limitStr = request.getParameter("limit");
		String search = request.getParameter("search");
		String columnName = request.getParameter("columnName");
		String order = request.getParameter("order");
		int pageNumber = 1;
		int limit = 20;
		try {
			pageNumber = Integer.parseInt(PageNumberStr);
			limit = Integer.parseInt(limitStr);
		} catch(NumberFormatException e) {
			logger.debug("PageNumber and limit problem {} {} ", pageNumber, limit);
		}
		try {
			if (page == null) {
				page = new PaginationComputer(limit);
			}
		} catch (ServiceException e1) {
			logger.debug("Pagination error", e1);
		}

		try {
			int i = computerService.count();
			if(search == null && columnName == null && order == null){
				listSearch = computerService.listComputers(limit,limit*(pageNumber-1)); 
			}
			else {
				listSearch = computerService.search(search, columnName, order, limit, limit*(pageNumber-1));
			}		
			List<ComputerDTO> listDTOSearch = new ArrayList<ComputerDTO>();
			for(Computer computerSearch : listSearch) {
				listDTOSearch.add(computerMapper.getComputerDTOFromComputer(computerSearch));
			}
			request.setAttribute("computerList", listDTOSearch);
			request.setAttribute("count", i);
			request.setAttribute("pagination", page);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("limit", limit);
			request.setAttribute("search", search);
			request.setAttribute("columnName", columnName);
			request.setAttribute("order", order);
			RequestDispatcher dispatcher = request.getSession().getServletContext().getRequestDispatcher("/dashboard.jsp");
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			logger.debug("Problem in my Dashboard", e);
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = (String) request.getParameter("selection");
		String[] split = idStr.split(",");
		List<Long> listIDS = new ArrayList<Long>();
		try {
			for (int i = 0; i < split.length; i++) {
				listIDS.add(Long.parseLong(split[i]));
			}
		} catch (NumberFormatException e) {
			logger.debug("Problem in parse String to Long", e);
		}
		try {
			computerService.deleteTransaction(listIDS);
		} catch (ServiceException e) {
			logger.debug("Problem with the delete function in the Servlet", e);
		}
		response.sendRedirect(request.getContextPath() + "/DashboardServlet");

	}

}
