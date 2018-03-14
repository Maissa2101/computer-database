package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ComputerMapper {
	
	private final static ComputerMapper computerMapper = new ComputerMapper();
	
	
	private ComputerMapper() {
		
	}
	
	/**
	 * Method to get the list of computers from a ResultSet
	 * @param res
	 * @return the list of computers
	 * @throws SQLException
	 */
	public List<Computer> getListComputerFromResultSet(ResultSet res) throws SQLException {
		
		List<Computer> computers = new ArrayList<Computer>();
		while(res.next()) {
			computers.add(new Computer(res.getLong(1), res.getString(2), res.getDate(3), res.getDate(4), res.getString(5)));
		}
		return computers;
		
	}
	
	/**
	 * Method to get the details of a computer from a ResultSet
	 * @param res
	 * @return the details of a computer 
	 * @throws SQLException
	 */
	public Computer getComputerDetailsFromResultSet(ResultSet res) throws SQLException {
		Computer c = null;

		if(res.next()) {
			
			c = new Computer(res.getLong(1), res.getString(2),res.getDate(3), res.getDate(4), res.getString(5));
		}
		return c;
	}
	
	/**
	 * 
	 * @return the computerMapper
	 */
	public static ComputerMapper getComputerMapper() {
		return computerMapper;
	}
}
