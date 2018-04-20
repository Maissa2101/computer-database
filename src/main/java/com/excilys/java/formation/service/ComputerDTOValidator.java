package com.excilys.java.formation.service;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.java.formation.dto.ComputerDTO;

public class ComputerDTOValidator implements Validator {
	ComputerDTO computerDTO;

	@Override
	public boolean supports(Class<?> computerDTO) {
		return ComputerDTO.class.equals(computerDTO);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
		ValidationUtils.rejectIfEmpty(errors, "name", "name.required");

		if (((computerDTO.getDiscontinued() != null) && (computerDTO.getIntroduced() != null)) && !(LocalDate.parse(computerDTO.getDiscontinued()).isAfter(LocalDate.parse(computerDTO.getIntroduced())))) {
			errors.rejectValue("introduced", "computerDAO.introduced.invalid");
			errors.rejectValue("discontinued", "computerDAO.discontinued.invalid");
		} 
	}
}