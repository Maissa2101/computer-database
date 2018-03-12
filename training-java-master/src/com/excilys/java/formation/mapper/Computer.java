package com.excilys.java.formation.mapper;

import java.sql.Timestamp;

public class Computer {
	private long id;
	
    private String name;

    private Timestamp introduced;

    private Timestamp disconnected;

    private String manufacturer;
    
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

	public Timestamp getDisconnected() {
		return disconnected;
	}

	public void setDisconnected(Timestamp disconnected) {
		this.disconnected = disconnected;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String toString() {
		return "Computer " + id + ": name=" + name + ", introduced=" + introduced + ", disconnected=" + disconnected
				+ ", manufacturer=" + manufacturer;
	}
    
	
    
}
