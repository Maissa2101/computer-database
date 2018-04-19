package com.excilys.java.formation.servlets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.pagination.PaginationComputer;
import com.excilys.java.formation.service.CompanyServiceSpring;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.service.ValidatorException;

@Controller
@Profile("!interface")
public class ComputerController {
	private Logger logger = LoggerFactory.getLogger(ComputerController.class);
	@Autowired
	private ComputerServiceSpring computerService;
	private ComputerDTOMapper computerMapper = ComputerDTOMapper.INSTANCE;
	@Autowired
	private CompanyServiceSpring companyService;

	@GetMapping(value = {"/","/dashboard"})
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
	
	@PostMapping(value = "/dashboard")
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
			logger.debug("Problem with the delete function", e);
		}
		return "dashboard";
	}
	
	@GetMapping("/addComputer")
	protected String doGetAdd(ModelMap model, @ModelAttribute("computerDTO") ComputerDTO computerDTO) {
		try {
			model.addAttribute("companyList", companyService.listCompanies(companyService.count(), 0));
		} catch (ServiceException e) {
			logger.debug("ServiceException in AddComputerServlet");
		}
		return "addComputer";
	}
	
	@PostMapping("/addComputer")
	protected String doPostAdd(ModelMap model, @ModelAttribute("computerDTO") ComputerDTO computerDTO) {
		Computer computer = computerMapper.getComputerFromComputerDTO(computerDTO);
		try {
			computerService.createComputer(computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer());
		} catch (ValidatorException | ServiceException e) {
			logger.debug("Problem in AddComputer", e);
		}
		model.addAttribute("computer", computer);
		return "addComputer";
	}
	
	@GetMapping("/editComputer")
	protected String doGetUpdate(ModelMap model, @RequestParam(value="id", required=false) String idStr) {
		Optional<Computer> computer = null;

		long id=0;
		try {
			id = Long.parseLong(idStr);
		} catch(NumberFormatException e) {
			logger.debug("id problem {}", id);
		}
		try {
			computer = computerService.getComputer(id);
			if(!computer.isPresent()) {
				return "dashboard";
			}
		} catch(ServiceException e) {
			logger.debug("ServiceException problem", e);
			return "dashboard";
		}
		
		ComputerDTO computerDTO = computerMapper.getComputerDTOFromComputer(computer.get());
		
		try {
			model.addAttribute("companyList", companyService.listCompanies(companyService.count(), 0));
		} catch (ServiceException e) {
			logger.debug("Problem in UpdateComputer", e);
		}
		model.addAttribute("computer", computerDTO);
		return "editComputer";
	}
	
	@PostMapping("/editComputer")
	protected String doPostUpdate(ModelMap model, @ModelAttribute("computerDTO") ComputerDTO computerDTO) {
		Computer computer = computerMapper.getComputerFromComputerDTO(computerDTO);
		try {
			computerService.updateComputer(computer.getId(), computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer());
		} catch (ServiceException | ValidatorException e) {
			logger.debug("Problem with the update function", e);
		}

		model.addAttribute("computer", computer);
		return "dashboard";
	}
}
