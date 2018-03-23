package com.excilys.java.formation.pagination;

import java.sql.SQLException;

public abstract class Page {
	int offset;
	int limit;
	int dbSize;
	int pageNumber;
	
	public abstract void getNext() throws ClassNotFoundException, SQLException;
	public abstract void getPrevious() throws ClassNotFoundException, SQLException;
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
