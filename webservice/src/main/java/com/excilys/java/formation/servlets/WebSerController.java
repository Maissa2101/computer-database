package com.excilys.java.formation.servlets;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.java.formation.binding.ComputerDTOMapper;
import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;

@RestController
public class WebSerController {
	private Logger logger = LoggerFactory.getLogger(WebSerController.class);
	private ComputerServiceSpring computerService;
	private ComputerDTOMapper computerDTOMapper;
	
	@Autowired
	public WebSerController (ComputerServiceSpring computerService, ComputerDTOMapper computerDTOMapper) {
		this.computerService = computerService;
		this.computerDTOMapper = computerDTOMapper;
	}
	
	@GetMapping(value = "/{limit}/{offset}/{columnName}/{order}")
	protected List<ComputerDTO> getListComputers(@PathVariable Integer limit,@PathVariable Integer offset,@PathVariable String columnName,@PathVariable String order) {
		List<Computer> liste = null;
		List<ComputerDTO> listeResult = new ArrayList<ComputerDTO>();
		try {
			liste = computerService.listComputers(limit, offset, columnName, order);
			for(Computer computer : liste) {
				listeResult.add(computerDTOMapper.getComputerDTOFromComputer(computer));
			}
		} catch (ServiceException e) {
			logger.debug("Problem in webService list Computers {}", e);
		}
		return listeResult;
	}

	@GetMapping(value = "/{id}")
	protected ComputerDTO getComputer(@PathVariable Long id) {
		ComputerDTO computerDto = null;
		Computer computer;
		try {
			computer = computerService.getComputer(id).get();
			computerDto = computerDTOMapper.getComputerDTOFromComputer(computer);
		} catch (ServiceException e) {
			logger.debug("Problem in webService in get computer by id {}", e);
		}
		return computerDto;
	}
}
