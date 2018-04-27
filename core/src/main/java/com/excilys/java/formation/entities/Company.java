package com.excilys.java.formation.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "company" )
public class Company {	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;

	public Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}

	public Company() {
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
	public void setId(long id2) {
		this.id = id2;
	}
	@Override
	public String toString() {
		return "Company " + id + ": name=" + name;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
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
		if (!(this.getName().equals(((Company) obj).getName()))) {
			return false;
		}
		if ((!(this.getId() == (((Company) obj).getId())))) {
			return false;
		}
		return true;
	}

	public static class CompanyBuilder {
		private long id;
		private String name;

		public CompanyBuilder(long id, String name) {
			this.id = id;
			this.name = name;
		}

		public Company build() {
			return new Company(this);
		}
	}
}
