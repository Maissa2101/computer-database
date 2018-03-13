package com.excilys.java.formation.mapper;

import java.sql.Date;


public class Computer {
	private long id;
	
    private String name;

    private Date introduced;

    private Date discontinued;

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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDisconnected(Date discontinued) {
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
