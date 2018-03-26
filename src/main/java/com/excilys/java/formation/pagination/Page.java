package com.excilys.java.formation.pagination;


import com.excilys.java.formation.service.ServiceException;

public abstract class Page {
	int offset;
	int limit;
	int dbSize;
	int pageNumber;
	
	public abstract void getNext() throws ServiceException;
	public abstract void getPrevious() throws ServiceException;
	public abstract void printPage();

	public void getNextOffset() {
		if( offset + limit <= dbSize) {
			offset = offset + limit;
			pageNumber++;
		}
	}

	public void getPreviousOffset() {
		if( offset - limit >= 0) {
			offset = offset - limit;
			pageNumber--;
		}
		else {
			offset = 0;
		}
	}
}
