package com.excilys.java.formation.servlets;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.pagination.PaginationComputer;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;


@Controller
@RequestMapping(value = {"/", "dashboard"})
public class DashboardServlet {
	private Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
	@Autowired
	private ComputerServiceSpring computerService;
	private ComputerDTOMapper computerMapper = ComputerDTOMapper.INSTANCE;

	
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model, @RequestParam(value="ComputerPage", required=false) PaginationComputer page, 
			@RequestParam(value="pageNumber", required=false) String pageNumberStr, 
			@RequestParam(value="limit", required=false) String limitStr, 
			@RequestParam(value="search", required=false) String search,
			@RequestParam(value="columnName", required=false) String columnName,
			@RequestParam(value="order", required=false) String order) {
		List<Computer> listSearch = null;
		int pageNumber = 1;
		int limit = 20;
		try {
			pageNumber = Integer.parseInt(pageNumberStr);
			limit = Integer.parseInt(limitStr);
		} catch(NumberFormatException e) {
			logger.debug("PageNumber and limit problem {} {} ", pageNumber, limit);
		}
		try {
			if (page == null) {
				page = new PaginationComputer(limit, computerService);
			}
		} catch (ServiceException e1) {
			logger.debug("Pagination error", e1);
		}
		try {
			int i = computerService.count();
			if(search == null){
				listSearch = computerService.listComputers(limit,limit*(pageNumber-1), columnName, order);
			}
			else {
				listSearch = computerService.search(search, columnName, order, limit, limit*(pageNumber-1));
				i = computerService.countAfterSearch(search);
			}		
			List<ComputerDTO> listDTOSearch = new ArrayList<>();
			for(Computer computerSearch : listSearch) {
				listDTOSearch.add(computerMapper.getComputerDTOFromComputer(computerSearch));
			}
			model.addAttribute("computerList", listDTOSearch);
			model.addAttribute("count", i);
			model.addAttribute("pagination", page);
			model.addAttribute("pageNumber", pageNumber);
			model.addAttribute("limit", limit);
			model.addAttribute("search", search);
			model.addAttribute("columnName", columnName);
			model.addAttribute("order", order);
			return "dashboard";
		} catch (ServiceException e) {
			logger.debug("Problem in my Dashboard", e);
		}
		return "404"; 
	}


	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model, @RequestParam(value="selection", required=false) String idStr) {
		String[] split = idStr.split(",");
		List<Long> listIDS = new ArrayList<>();
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
		return "dashboard";

	}
		
}

