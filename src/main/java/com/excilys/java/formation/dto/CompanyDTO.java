package com.excilys.java.formation.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CompanyDTO {
	private long id;
	@NotNull
    @Size(min=2, max=60)
    @Pattern(regexp="^[\\wÀ-ÿ]+[\\wÀ-ÿ_\\-'\\+\\*. ]+$")
	private String name;

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
}
