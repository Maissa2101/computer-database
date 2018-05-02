package com.excilys.java.formation.servlets;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Profile("!interface")
public class UserController {

	@GetMapping(value =  "/login")
	public String login() {
		return "login";
	}
	

}
