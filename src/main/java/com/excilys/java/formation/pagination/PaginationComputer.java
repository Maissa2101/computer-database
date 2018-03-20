package com.excilys.java.formation.pagination;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerService;

public class PaginationComputer extends Page {
	
	private List<Computer> computers;
	private ComputerService cs;
	
	public PaginationComputer(int limit) throws SQLException, ClassNotFoundException {
		this.offset = 0;
		this.limit = limit;
		this.cs = ComputerService.INSTANCE;
		this.dbSize = cs.count();
		this.computers = cs.listComputers(limit, offset);
	}
	
	private void updateComputer() throws ClassNotFoundException, SQLException {
		this.computers = cs.listComputers(limit, offset);
	}
	

	@Override
	public void getNext() throws ClassNotFoundException, SQLException {
		super.getNextOffset();
		updateComputer();
		
	}

	@Override
	public void getPrevious() throws ClassNotFoundException, SQLException {
		super.getPreviousOffset();
		updateComputer();
		
	}


	@Override
	public void printPage() {
		for(Computer computer : this.computers) {
			System.out.println(computer);
		}
		
	}
	
	
	
}
