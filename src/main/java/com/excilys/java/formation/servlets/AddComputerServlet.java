package com.excilys.java.formation.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
@RequestMapping(value = {"addComputer"})
@Profile("!interface")
public class AddComputerServlet {
	private Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	@Autowired
	private ComputerServiceSpring computerService;
	private ComputerDTOMapper computerDTOMapper = ComputerDTOMapper.INSTANCE;
	@Autowired
	private CompanyServiceSpring companyService;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model) {
		try {
			model.addAttribute("companyList", companyService.listCompanies(companyService.count(), 0));
		} catch (ServiceException e) {
			logger.debug("ServiceException in AddComputerServlet");
		}
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model, @RequestParam(value="computerName", required=false) String name,
			@RequestParam(value="introduced", required=false) String introduced,
			@RequestParam(value="discontinued", required=false) String discontinued,
			@RequestParam(value="manufacturer", required=false) String manufacturer)  {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName(name);
		computerDTO.setIntroduced(introduced);
		computerDTO.setDiscontinued(discontinued);
		computerDTO.setManufacturer(manufacturer);
		Computer computer = computerDTOMapper.getComputerFromComputerDTO(computerDTO);
		try {
			computerService.createComputer(computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer());
		} catch (ValidatorException | ServiceException e) {
			logger.debug("Problem in AddComputerServlet", e);
		}
		model.addAttribute("computer", computer);
		return "addComputer";
	}

}
