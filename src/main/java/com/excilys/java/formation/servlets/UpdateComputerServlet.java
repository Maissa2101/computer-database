package com.excilys.java.formation.servlets;

import java.util.Optional;
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
import com.excilys.java.formation.service.CompanyServiceSpring;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.service.ValidatorException;

@Controller
@RequestMapping(value = {"editComputer"})
public class UpdateComputerServlet {
	private Logger logger = LoggerFactory.getLogger(UpdateComputerServlet.class);
	@Autowired
	private ComputerServiceSpring computerService;
	private ComputerDTOMapper computerDTOMapper = ComputerDTOMapper.INSTANCE;
	@Autowired
	private CompanyServiceSpring companyService;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model, @RequestParam(value="id", required=false) String idStr) {
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
		
		ComputerDTO computerDTO = computerDTOMapper.getComputerDTOFromComputer(computer.get());
		
		try {
			model.addAttribute("companyList", companyService.listCompanies(companyService.count(), 0));
		} catch (ServiceException e) {
			logger.debug("Problem in UpdateComputerServlet", e);
		}
		model.addAttribute("computer", computerDTO);
		return "editComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model, @RequestParam(value="id", required=false) String idStr,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="introduced", required=false) String introduced,
			@RequestParam(value="discontinued", required=false) String discontinued,
			@RequestParam(value="manufacturer", required=false) String manufacturer) {
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

		model.addAttribute("computer", computer);
		return "dashboard";
	}

}
