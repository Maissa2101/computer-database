package com.excilys.java.formation.pagination;


import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerServiceSpring;
import com.excilys.java.formation.service.ServiceException;

public class PaginationComputer extends Page {

	private List<Computer> computers;
	private ComputerServiceSpring computerService;
	private String order;
	private String columnName;


	public PaginationComputer(int limit, ComputerServiceSpring computerService) throws ServiceException {
		this.offset = 0;
		this.limit = limit;
		this.dbSize = computerService.count();
		this.computerService = computerService;
		this.computers = computerService.listComputers(limit, offset, columnName, order);
	}
	
	private void updateComputer() throws ServiceException  {
		this.computers = computerService.listComputers(limit, offset, columnName, order);
	}

	@Override
	public void getNext() throws ServiceException {
		dbSize = computerService.count();
		super.getNextOffset();
		updateComputer();
	}

	@Override
	public void getPrevious() throws ServiceException {
		dbSize = computerService.count();
		super.getPreviousOffset();
		updateComputer();
	}

	public List<Computer> getComputers(){
		return this.computers;
	}

	@Override
	public void printPage() {
		for(Computer computer : this.computers) {
			System.out.println(computer);
		}
	}
}
