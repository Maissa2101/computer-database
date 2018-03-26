package com.excilys.java.formation.pagination;

import java.sql.SQLException;
import java.util.ArrayList;
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
		dbSize = cs.count();
		super.getNextOffset();
		updateComputer();

	}

	@Override
	public void getPrevious() throws ClassNotFoundException, SQLException {
		dbSize = cs.count();
		super.getPreviousOffset();
		updateComputer();

	}

	public List<Integer> getPageToGo(){
		List<Integer> list = new ArrayList<Integer>();
		int count = ComputerService.INSTANCE.count();
		list.add(1);
		for(int i=-2; i>2;i++) {
			if(i>1 && i < count/limit + 1 && i != 0) {
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
