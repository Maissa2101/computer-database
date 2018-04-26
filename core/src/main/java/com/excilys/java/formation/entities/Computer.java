package com.excilys.java.formation.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "computer" )
public class Computer {
	private long id;	
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	public Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public Computer() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "introduced", nullable = true)
	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	
	@Column(name = "discontinued", nullable = true)
	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDisconnected(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "company_id")
	public Company getManufacturer() {
		return company;
	}

	public void setManufacturer(Company manufacturer) {
		this.company = manufacturer;
	}

	@Override
	public String toString() {
		return "Computer " + id + ": name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", manufacturer=" + company;
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
		private Company company;

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

		public ComputerBuilder manufacturer(Company manufacturer) {
			this.company = manufacturer;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}
}



