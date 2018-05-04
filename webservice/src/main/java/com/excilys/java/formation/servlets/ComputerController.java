package com.excilys.java.formation.servlets;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;

@RestController
public class ComputerController {
	private Logger logger = LoggerFactory.getLogger(ComputerController.class);
	private ComputerServiceSpring computerService;
	
	@GetMapping(value = "/{limit}/{offset}/{columnName}/{order}")
	protected List<Computer> doGet(@PathVariable int limit, int offset, String columnName, String order) {
		List<Computer> liste = null;
		try {
			liste = computerService.listComputers(limit, offset, columnName, order);
		} catch (ServiceException e) {
			logger.debug("Problem in webService list Computers {}", e);
		}
		return liste;
	}
}
