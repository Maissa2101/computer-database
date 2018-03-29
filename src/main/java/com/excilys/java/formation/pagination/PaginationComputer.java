package com.excilys.java.formation.pagination;


import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;

public class PaginationComputer extends Page {

	private List<Computer> computers;
	private ComputerService computerService;
	String order;
	String columnName;


	public PaginationComputer(int limit) throws ServiceException {
		this.offset = 0;
		this.limit = limit;
		this.computerService = ComputerService.INSTANCE;
		this.dbSize = computerService.count();
		this.computers = computerService.listComputers(limit, offset, columnName, order);
	}
	
	/**
	 * Update the list of computers with limit and offset
	 * @throws ServiceException
	 */
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
