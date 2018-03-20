package com.excilys.java.formation.entities;

public class Company {	
	private long id;
	private String name;

	public Company(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Company " + id + ": name=" + name;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public boolean equals(Company company) {
		return (company.getId() == this.getId());
	}






}
