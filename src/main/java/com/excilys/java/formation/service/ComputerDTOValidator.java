package com.excilys.java.formation.service;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.java.formation.dto.ComputerDTO;

@Component
public class ComputerDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> computerDTO) {
		return ComputerDTO.class.equals(computerDTO);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ComputerDTO computerDTO = (ComputerDTO) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
		ValidationUtils.rejectIfEmpty(errors, "name", "name.required");

		if (((computerDTO.getDiscontinued() != null && computerDTO.getDiscontinued() != "") && (computerDTO.getIntroduced() != null && computerDTO.getIntroduced() != "")) && !(LocalDate.parse(computerDTO.getDiscontinued()).isAfter(LocalDate.parse(computerDTO.getIntroduced())))) {
			errors.rejectValue("discontinued", "computerDAO.discontinued.invalid");
		} 
	}
}