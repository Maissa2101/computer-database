package com.excilys.java.formation.entities;

import java.time.LocalDate;

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
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;	
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	@ManyToOne(optional = true)
	@JoinColumn(name = "company_id")
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

	public Company getManufacturer() {
		return company;
	}

	public void setManufacturer(Company manufacturer) {
		this.company = manufacturer;
	}

	@Override
	public String toString() {
		String nameCompany = null;
		if (company != null) {
			nameCompany = company.getName();
		}
		return "Computer " + id + ": name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", manufacturer=" + nameCompany;
			
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	private boolean equalsDate(Object obj) {
		if (this.getIntroduced() != null) {
			if (!this.getIntroduced().equals(((Computer) obj).getIntroduced())) {
				return false;
			}
		} else if (((Computer) obj).getIntroduced()!=null){
			return false;
		}
		if (this.getDiscontinued()!=null) {
			if (!this.getDiscontinued().equals(((Computer) obj).getDiscontinued())) {
				return false;
			}
		}else if (((Computer) obj).getDiscontinued()!=null){
			return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (!this.getName().equals(((Computer) obj).getName())) {
			return false;
		}
		if (!this.equalsDate(obj)) {
			return false;
		}
		if ( this.getManufacturer() != null) {
			return this.getManufacturer().equals(((Computer) obj).getManufacturer());
		}else {
			return ((Computer) obj).getManufacturer()==null;
		}
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



