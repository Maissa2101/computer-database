package com.excilys.java.formation.pagination;

import java.sql.SQLException;

public abstract class Page {
	int offset;
	int limit;
	int dbSize;

	public abstract void getNext() throws ClassNotFoundException, SQLException;
	public abstract void getPrevious() throws ClassNotFoundException, SQLException;
	public abstract void printPage();

	public void getNextOffset() {
		if( offset + limit <= dbSize) {
			offset = offset + limit;
		}
	}

	public void getPreviousOffset() {
		if( offset - limit >= 0) {
			offset = offset - limit;
		}
		else {
			offset = 0;
		}
	}
}
