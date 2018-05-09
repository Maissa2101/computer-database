package com.excilys.java.formation.binding;

import org.springframework.stereotype.Component;

import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.entities.Company;

@Component
public class CompanyDTOMapper {
	
	public CompanyDTO getCompanyDTOFromCompany(Company company) {
		long id = company.getId();
		String name = company.getName();
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(id);
		companyDTO.setName(name);
		return companyDTO;    
	}
	
	public Company getCompanyFromCompanyDTO(CompanyDTO companyDTO) {
		long id = companyDTO.getId();
		String name = companyDTO.getName();
		Company company = new Company();
		company.setId(id);
		company.setName(name);
		return company;    
	}
}
