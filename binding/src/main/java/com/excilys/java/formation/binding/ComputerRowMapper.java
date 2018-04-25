package com.excilys.java.formation.binding;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.java.formation.entities.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet res, int rowNum) throws SQLException {
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
	

}
