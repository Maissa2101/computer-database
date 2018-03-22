package com.excilys.java.formation.entities;


import java.time.LocalDate;


public class Computer {
	private long id;	
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private String manufacturer;

	public Computer(long id, String name, LocalDate introduced, LocalDate discontinued, String manufacturer) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.manufacturer = manufacturer;
	}

	public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}

	public Computer(String name, LocalDate introduced, LocalDate discontinued, String manufacturer) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.manufacturer = manufacturer;
	}

	public Computer() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDisconnected(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String toString() {
		return "Computer " + id + ": name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", manufacturer=" + manufacturer;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public boolean equals(Computer computer) {
		if(computer.getId() == this.getId())
			return true;
		return false;
	}



}



