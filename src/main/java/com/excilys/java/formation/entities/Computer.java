package com.excilys.java.formation.entities;


import java.time.LocalDate;


public class Computer {
	private long id;	
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private String manufacturer;

	public Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.manufacturer = builder.manufacturer;
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

	@Override
	public boolean equals(Object computer) {
		boolean result = false;
		if(((Computer) computer).getId() == this.getId())
			result = true;
		return result;
	}

	public static class ComputerBuilder {
		private long id;	
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private String manufacturer;

		public ComputerBuilder(long id, String name) {
			this.id = id;
			this.name = name;
		}

		public ComputerBuilder(String name) {
			this.name = name;
		}

		public ComputerBuilder introduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder discontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerBuilder manufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}
}



