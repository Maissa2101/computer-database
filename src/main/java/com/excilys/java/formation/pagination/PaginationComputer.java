package com.excilys.java.formation.pagination;

import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;

public class PaginationComputer extends Page {

	private List<Computer> computers;
	private ComputerService computerService;


	public PaginationComputer(int limit) throws ServiceException {
		this.offset = 0;
		this.limit = limit;
		this.computerService = ComputerService.INSTANCE;
		this.dbSize = computerService.count();
		this.computers = computerService.listComputers(limit, offset);
	}
	
	/**
	 * Update the list of computers with limit and offset
	 * @throws ServiceException
	 */
	private void updateComputer() throws ServiceException  {
		this.computers = computerService.listComputers(limit, offset);
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

	public List<Integer> getPageToGo(){
		List<Integer> list = new ArrayList<Integer>();
		int count = ComputerService.INSTANCE.count();
		list.add(1);
		for(int i=-2; i>2;i++) {
			if(i>1 && i < count/limit + 1 && i != 0) 
			{
				list.add(i+pageNumber);
			}
		}
		list.add(count/limit+1);
		return list;
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
