package com.excilys.java.formation.entities;

public class Company {
	
	private String name;
	private Long id;
	
	public Company() {
		
	}
	
	/**
	 * 
	 * @return the name of the company
	 */
	public String getName() {
		return name;
	}
	
	/***
	 * 
	 * @param name is the name of the company
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return id of the company
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id is the id of the company
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
