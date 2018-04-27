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

	private static final String SELECT_REQUEST_LIST = "FROM " + Company.class.getName();
	private static final String COUNT = "SELECT count(*) FROM " + Company.class.getName();
	private static final String SELECT_REQUEST_DETAILS = "FROM " + Company.class.getName() +" company WHERE company.id=?";

	private SessionFactory factory;
	private ComputerDAOSpring computerDAO;

	@Autowired
	public CompanyDAOSpring(SessionFactory factory, ComputerDAOSpring computerDAO) {
		this.computerDAO = computerDAO;
		this.factory = factory;
	}

	public List<Company> getListCompany(int limit, int offset) {
		Session session = factory.openSession();
		List<Company> query = session.createQuery(SELECT_REQUEST_LIST, Company.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.list();
		session.close();
		return query;
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
		TypedQuery<Long> query = session.createQuery(COUNT, Long.class);
		Long count = (Long)((Query) query).uniqueResult();
		session.close();
		return count.intValue();
	}

	public void deleteCompany(long id)  {
			Session session = factory.openSession();
			computerDAO.deleteTransactionCompany(id);
			session.beginTransaction();
			Optional<Company> company = getCompany(id);
			session.delete(company.get());
			session.getTransaction().commit();	
			session.close();
	}

}