package com.excilys.java.formation.mapper;

import java.sql.Timestamp;

public class Computer {
	private long id;
	
    private String name;

    private Timestamp introduced;

    private Timestamp discontinued;

    private String manufacturer;
    
    /**
     * Constructor of computer
     * @param l
     * @param name
     */
    public Computer(long l, String name) {
    	this.id = l;
    	this.name = name;
    }

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDisconnected(Timestamp discontinued) {
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
    
	
    
}
