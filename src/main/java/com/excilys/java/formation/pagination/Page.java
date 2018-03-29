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
	
	/**
	 * Method to change the offset to the next one in the page 
	 */
	public void getNextOffset() {
		if( offset + limit <= dbSize) {
			offset = offset + limit;
			pageNumber++;
		}
	}
	
	/**
	 * Method to change the offset to the previous one in the page 
	 */
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
