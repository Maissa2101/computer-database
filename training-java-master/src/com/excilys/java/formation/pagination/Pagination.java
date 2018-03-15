package com.excilys.java.formation.pagination;

import java.util.Collections;
import java.util.List;

public class Pagination {
	
	/**
	 * Method that returns the pages to show
	 * @param sourceList the list to show
	 * @param page number of the page
	 * @param pageSize the number of elements allowed in a page
	 * @return the sublist of the list of the elements to show
	 */
	public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
	    if(pageSize <= 0 || page <= 0) {
	        throw new IllegalArgumentException("invalid page size: " + pageSize);
	    }

	    int fromIndex = (page - 1) * pageSize;
	    if(sourceList == null || sourceList.size() < fromIndex){
	        return Collections.emptyList();
	    }

	   
	    return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
	}
	
	
	
}
