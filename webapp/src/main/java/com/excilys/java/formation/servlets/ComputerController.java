package com.excilys.java.formation.servlets;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.binding.ComputerDTOMapper;
import com.excilys.java.formation.pagination.PaginationComputer;
import com.excilys.java.formation.service.CompanyServiceSpring;
import com.excilys.java.formation.service.ComputerDTOValidator;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.service.ValidatorException;
import java.util.Locale;

@Controller
@Profile("!interface")
public class ComputerController {
	private static final String PAGE500 = "500";
	private static final String PAGE_DASHBOARD = "dashboard";
	private Logger logger = LoggerFactory.getLogger(ComputerController.class);
	private ComputerServiceSpring computerService;
	private ComputerDTOMapper computerMapper;
	private CompanyServiceSpring companyService;
	private ComputerDTOValidator validator;

	@Autowired
	public ComputerController(ComputerDTOMapper computerMapper, ComputerServiceSpring computerService, CompanyServiceSpring companyService, ComputerDTOValidator validator) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.validator = validator;
		this.computerMapper = computerMapper;
	}

	@InitBinder("computerDTO")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@GetMapping(value = {"/","/dashboard"})
	protected String doGet(Locale locale, ModelMap model, @RequestParam(value="ComputerPage", required=false) PaginationComputer page, 
			@RequestParam(value="pageNumber", required=false) String pageNumberStr, 
			@RequestParam(value="limit", required=false) String limitStr, 
			@RequestParam(value="search", required=false) String search,
			@RequestParam(value="columnName", required=false) String columnName,
			@RequestParam(value="order", required=false) String order) {

		Locale currentLocale = LocaleContextHolder.getLocale();
		model.addAttribute("locale", currentLocale);

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
			if(search == "" || search == null){
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
			return PAGE_DASHBOARD;
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
			return "404";
		}
		return PAGE_DASHBOARD;
	}

	@GetMapping(value = "/addComputer")
	protected String doGetAdd(Locale locale, ModelMap model,@ModelAttribute("computerDTO") ComputerDTO computerDTO) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		model.addAttribute("locale", currentLocale);
		try {
			model.addAttribute("companyList", companyService.listCompanies(companyService.count(), 0));
		} catch (ServiceException e) {
			logger.debug("ServiceException in AddComputerServlet");
			return PAGE500;
		}
		return "addComputer";
	}

	@PostMapping(value = "/addComputer")
	protected String doPostAdd(ModelMap model,@Validated @ModelAttribute("computerDTO") ComputerDTO computerDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return PAGE500;
		}
		Computer computer = computerMapper.getComputerFromComputerDTO(computerDTO);
		try {
			computerService.createComputer(computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer());
		} catch (ValidatorException | ServiceException e) {
			logger.debug("Problem in AddComputer", e);
			return PAGE500;
		}
		model.addAttribute("computer", computer);
		return "addComputer";
	}

	@GetMapping(value = "/editComputer")
	protected String doGetUpdate(Locale locale, ModelMap model, @RequestParam(value="id", required=false) String idStr) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		model.addAttribute("locale", currentLocale);
		Optional<Computer> computer;

		long id=0;
		try {
			id = Long.parseLong(idStr);
		} catch(NumberFormatException e) {
			logger.debug("id problem {}", id);
		}
		try {
			computer = computerService.getComputer(id);
			if(!computer.isPresent()) {
				return PAGE_DASHBOARD;
			}
		} catch(ServiceException e) {
			logger.debug("ServiceException problem", e);
			return PAGE_DASHBOARD;
		}

		ComputerDTO computerDTO = computerMapper.getComputerDTOFromComputer(computer.get());

		try {
			model.addAttribute("companyList", companyService.listCompanies(companyService.count(), 0));
		} catch (ServiceException e) {
			logger.debug("Problem in UpdateComputer", e);
			return PAGE500;
		}
		model.addAttribute("computer", computerDTO);
		return "editComputer";
	}

	@PostMapping(value = "/editComputer")
	protected String doPostUpdate(ModelMap model,@Validated @ModelAttribute("computerDTO") ComputerDTO computerDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return PAGE500;
		}
		Computer computer = computerMapper.getComputerFromComputerDTO(computerDTO);
		try {
			computerService.updateComputer(computer.getId(), computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getManufacturer());
		} catch (ServiceException | ValidatorException e) {
			logger.debug("Problem with the update function", e);
			return PAGE500;
		}

		model.addAttribute("computer", computer);
		return PAGE_DASHBOARD;
	}

	@GetMapping(value = {"/403"})
	@PostMapping(value = {"/403"})
	public String denied() {
		return "403";
	}
}
