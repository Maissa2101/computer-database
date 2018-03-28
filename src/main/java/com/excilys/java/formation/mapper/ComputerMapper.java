package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.entities.Computer;


public enum ComputerMapper {

	INSTANCE;

	/**
	 * Method to get the list of computers from a ResultSet
	 * @param res is the result set
	 * @return the list of computers
	 * @throws SQLException in case of a database access error
	 */
	public List<Computer> getListComputerFromResultSet(ResultSet res) throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();
		while(res.next()) {
			Computer.ComputerBuilder computerBuilder = new Computer.ComputerBuilder(res.getLong(1), res.getString(2))
					.manufacturer(res.getString(5));
			
			if (res.getDate(4) != null){
				computerBuilder = computerBuilder.discontinued(res.getDate(4).toLocalDate());
			}
			if(res.getDate(3) != null) {
				computerBuilder = computerBuilder.introduced(res.getDate(3).toLocalDate());
			}
			computers.add(computerBuilder.build());
			
		}
		return computers;
	}

	/**
	 * Method to get the details of a computer from a ResultSet
	 * @param res is the result set
	 * @return the details of a computer 
	 * @throws SQLException in case of a database access error
	 */
	public Computer getComputerDetailsFromResultSet(ResultSet res) throws SQLException {
		if(res.next()) {
			Computer.ComputerBuilder computerBuilder = new Computer.ComputerBuilder(res.getLong(1), res.getString(2))
					.manufacturer(res.getString(5));
			
			if (res.getDate(4) != null){
				computerBuilder = computerBuilder.discontinued(res.getDate(4).toLocalDate());
			}
			if(res.getDate(3) != null) {
				computerBuilder = computerBuilder.introduced(res.getDate(3).toLocalDate());
			}
			return computerBuilder.build();
		}
		return null;
		
	}
}
