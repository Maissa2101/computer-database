package com.excilys.java.formation.servlets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Profile("!interface")
public class ErrorController {

	@GetMapping(value = "errors")
	protected String renderErrorPage(HttpServletRequest httpRequest) {
		String page = "";
		int httpErrorCode = getErrorCode(httpRequest);

		switch (httpErrorCode) {
		case 403: {
			page = "403";
			break;
		}
		case 404: {
			page = "404";
			break;
		}
		case 500: {
			page = "500";
			break;
		}
		}
		return page;
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest.getAttribute("com.excilys.java.formation.servlets.ComputerController");
	}

}