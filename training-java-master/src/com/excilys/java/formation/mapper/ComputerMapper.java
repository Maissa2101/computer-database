package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComputerMapper {
	
	public static ComputerMapper computerMapper;
	
	public ComputerMapper() {
		
	}
	
	public List<Computer> getListComputerFromResultSet(ResultSet res) throws SQLException {
		
		List<Computer> computers = new ArrayList<Computer>();
		while(res.next()) {
			computers.add(new Computer(res.getLong(1), res.getString(2), res.getDate(3), res.getDate(4), res.getString(5)));
		}
		return computers;
		
	}
	
	public Computer getComputerDetailsFromResultSet(ResultSet res) throws SQLException {
		Computer c = null;
		
		if(res.next()) {
			c = new Computer(res.getLong(1), res.getString(2),res.getDate(3), res.getDate(4), res.getString(5));
		}
		return c;
	}
	
	public static ComputerMapper getComputerMapper() {
		
		if(computerMapper == null)
			computerMapper = new ComputerMapper();
	return computerMapper;
	}
}
