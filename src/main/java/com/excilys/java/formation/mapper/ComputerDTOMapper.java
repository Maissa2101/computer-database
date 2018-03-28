package com.excilys.java.formation.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.entities.Computer;

public enum ComputerDTOMapper {
	INSTANCE;
	Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);
	private static final String NULL ="";
	
	/**
	 * Method to convert computer to a computerDTO
	 * @param computer the computer to convert
	 * @return computerDTO 
	 */
	public ComputerDTO getComputerDTOFromComputer(Computer computer) {
		String introduced = null;
		String discontinued = null;
		if(computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toString();
		}
		if(computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toString();
		}
		long id = computer.getId();
		String name = computer.getName();
		String manufacturer = computer.getManufacturer();
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setIntroduced(introduced);
		computerDTO.setDiscontinued(discontinued);
		computerDTO.setId(id);
		computerDTO.setName(name);
		computerDTO.setManufacturer(manufacturer);
		return computerDTO;    
	}
	
	/**
	 * Method to convert computerDTO to a computer
	 * @param computerDTO the computerDTO to convert
	 * @return computer
	 */
	public Computer getComputerFromComputerDTO(ComputerDTO computerDTO) {
		LocalDate introduced = null; 
		LocalDate discontinued = null;

		try{
			if(!computerDTO.getIntroduced().equals(NULL) && !computerDTO.getDiscontinued().equals(NULL)) {
				introduced = LocalDate.parse(computerDTO.getIntroduced()); 
				discontinued = LocalDate.parse(computerDTO.getDiscontinued());
			}
			else if(computerDTO.getIntroduced().equals(NULL) && !computerDTO.getDiscontinued().equals(NULL)) {
				discontinued = LocalDate.parse(computerDTO.getDiscontinued());
			} else if(!computerDTO.getIntroduced().equals(NULL) && computerDTO.getDiscontinued().equals(NULL)) {
				introduced = LocalDate.parse(computerDTO.getIntroduced()); 
			}
		}
		catch(DateTimeParseException e) {
			logger.debug("Problem in Parser", e);
		}
		long id = computerDTO.getId();
		String name = computerDTO.getName();
		String manufacturer = computerDTO.getManufacturer();
		Computer computer = new Computer();
		computer.setIntroduced(introduced);
		computer.setDisconnected(discontinued);
		computer.setId(id);
		computer.setName(name);
		computer.setManufacturer(manufacturer);
		return computer; 
	}
}
