package com.excilys.java.formation.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Company;

@Repository
public class CompanyDAOSpring {

	private static final String SELECT_REQUEST_LIST = "FROM COMPANY LIMIT ? OFFSET ?;";
	private static final String COUNT = "SELECT count(*) as total FROM company;";
	private static final String SELECT_REQUEST_DETAILS = "FROM company WHERE id=?;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?;";
	
	private SessionFactory factory;
	private ComputerDAOSpring computerDAO;
	
	@Autowired
	public CompanyDAOSpring(SessionFactory factory, ComputerDAOSpring computerDAO) {
		this.computerDAO = computerDAO;
		this.factory = factory;
	}

	public List<Company> getListCompany(int limit, int offset) {
		Session session = factory.openSession();
		TypedQuery<Company> query = session.createQuery(SELECT_REQUEST_LIST, Company.class)
				.setParameter(0, limit)
				.setParameter(1, offset);
		List<Company> result = query.getResultList();
		session.close();
		return result;
	}

	public Optional<Company> getCompany(long id) {
		Session session = factory.openSession();
		TypedQuery<Company> query = session.createQuery(SELECT_REQUEST_DETAILS, Company.class).setParameter(0, id);
		Company company = (Company) ((Query) query).uniqueResult();
		session.close();
		return Optional.ofNullable(company);
	}

	public int count() {
		Session session = factory.openSession();
		TypedQuery<Company> query = session.createQuery(COUNT, Company.class);
		int count = (int)((Query) query).uniqueResult();
		session.close();
		return count;
	}

	public void deleteCompany(long id)  {
		Session session = factory.openSession();
		computerDAO.deleteTransactionCompany(id);
		TypedQuery<Company> query = session.createQuery(DELETE_COMPANY, Company.class).setParameter(0, id);
		query.executeUpdate();
		session.close();
	}

}