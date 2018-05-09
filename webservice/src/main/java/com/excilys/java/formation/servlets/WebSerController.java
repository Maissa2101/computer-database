package com.excilys.java.formation.servlets;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.java.formation.binding.ComputerDTOMapper;
import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.pagination.PaginationComputer;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.service.ValidatorException;

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

	@GetMapping(value = "listComputer/{limit}/{offset}/{columnName}/{order}")
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

	@GetMapping(value = "getComputer/{id}")
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

	@PostMapping(value= "addComputer")
	protected void addComputer(@RequestBody ComputerDTO computerDTO) {
		Computer computer = computerDTOMapper.getComputerFromComputerDTO(computerDTO);
		try {
			computerService.createComputer(computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer());
		} catch (ValidatorException | ServiceException e) {
			logger.debug("Problem in adding a computer", e);
		}
	}

	@PutMapping(value= "updateComputer")
	protected void updateComputer(@RequestBody ComputerDTO computerDTO) {
		Computer computer = computerDTOMapper.getComputerFromComputerDTO(computerDTO);
		try {
			computerService.updateComputer(computer.getId(), computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer());
		} catch (ServiceException | ValidatorException e) {
			logger.debug("Problem with the update function", e);
		}
	}

	@GetMapping(value = "search/{search}/{columnName}/{order}/{limit}/{offset}")
	protected List<ComputerDTO> getListAfterSearch(@PathVariable String search,@PathVariable String columnName,@PathVariable String order,@PathVariable Integer limit, @PathVariable Integer offset) {
		List<Computer> listSearch = null;
		List<ComputerDTO> listDTOSearch = new ArrayList<>();
		try {
			listSearch = computerService.search(search, columnName, order, limit, offset);		
			for(Computer computerSearch : listSearch) {
				listDTOSearch.add(computerDTOMapper.getComputerDTOFromComputer(computerSearch));
			}
		} catch (ServiceException e) {
			logger.debug("Problem in the search", e);
		}
		return listDTOSearch;
	}

	@DeleteMapping(value = "delete/{id}")
	protected void delete(@PathVariable Long id) {
		try {
			computerService.deleteComputer(id);
		} catch (ServiceException | ValidatorException e) {
			logger.debug("Problem in the delete one computer", e);
		}
	}

	@DeleteMapping(value = "deleteList")
	protected void deleteList(@RequestBody List<Long> ids) {
		try {
			computerService.deleteTransaction(ids);
		} catch (ServiceException e) {
			logger.debug("Problem in the delete a list of computers", e);
		}
	}

	@GetMapping(value= "pagination/{limit}/{pageNumber}/{columnName}/{order}")
	protected List<ComputerDTO> pagination(@PathVariable Integer limit,@PathVariable Integer pageNumber, @PathVariable String columnName, @PathVariable String order) {
		
		List<Computer> liste = null;
		List<ComputerDTO> listeResult = new ArrayList<ComputerDTO>();
		try {
			PaginationComputer page = new PaginationComputer(limit, computerService);
			liste = computerService.listComputers(limit, limit*(pageNumber-1), columnName, order);
			for(Computer computer : liste) {
				listeResult.add(computerDTOMapper.getComputerDTOFromComputer(computer));
			}
		} catch (ServiceException e) {
			logger.debug("Problem in webService list Computers {}", e);
		}
		return listeResult;
	}
}
