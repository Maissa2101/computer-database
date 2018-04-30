package com.excilys.java.formation.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;

@Repository
public class ComputerDAOSpring {

	private static final String SELECT_REQUEST_LIST = "FROM " + Computer.class.getName() +" computer ORDER BY";
	private static final String SELECT_REQUEST_DETAILS = "FROM " + Computer.class.getName() +" computer WHERE computer.id=?";
	private static final String COUNT = "SELECT count(*) FROM " + Computer.class.getName();
	private static final String SEARCH = "FROM " + Computer.class.getName() +" computer WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY";
	private static final String COUNT_SEARCH = "SELECT count(*) as total FROM " + Computer.class.getName() +" computer WHERE computer.name LIKE ? OR company.name LIKE ?";
	private static final String COMPUTERS_TO_DELETE = "FROM "+ Computer.class.getName() +" computer WHERE computer.company.id=?";

	private SessionFactory factory;

	@Autowired
	public ComputerDAOSpring(SessionFactory factory) {
		this.factory = factory;
	}

	public List<Computer> getListComputer(int limit, int offset, String columnName, String order) {
		Session session = factory.openSession();
		List<Computer> query = session.createQuery(SELECT_REQUEST_LIST + " " + columnName + " " + order, Computer.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.list();
		session.close();
		return query;

	}

	public Optional<Computer> getComputer(long id) {
		Session session = factory.openSession();
		TypedQuery<Computer> query = session.createQuery(SELECT_REQUEST_DETAILS, Computer.class).setParameter(0, id);
		Computer computer = (Computer) ((Query<Computer>) query).uniqueResult();
		session.close();
		return Optional.ofNullable(computer);
	}

	public long createComputer( String name, LocalDate intro, LocalDate discontinued, Company manufacturer ) {
		Computer computer = new Computer.ComputerBuilder(name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();
		try (Session session = factory.openSession();) {
			return (long) session.save(computer); 
		} 
	}

	public void updateComputer(long id, String name, LocalDate intro, LocalDate discontinued, Company manufacturer) {
		Computer computer = new Computer.ComputerBuilder(id, name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();
		try (Session session = factory.openSession();) {
			session.getTransaction().begin();
			session.update(computer);
			session.getTransaction().commit();
		}
	}

	public void deleteComputer(long id) {
		Optional<Computer> computer = getComputer(id);
		try (Session session = factory.openSession();) {
			if(computer.isPresent()) {
				session.getTransaction().begin();
				session.delete(computer.get());
			}
			session.getTransaction().commit();
		}
	}

	public int count() {
		Session session = factory.openSession();
		TypedQuery<Long> query = session.createQuery(COUNT, Long.class);
		Long count = (Long)((Query) query).uniqueResult();
		session.close();
		return count.intValue();

	}

	public int countAfterSearch(String search) {
		Session session = factory.openSession();
		TypedQuery<Long> query = session.createQuery(COUNT_SEARCH, Long.class)
				.setParameter(0, search + '%')
				.setParameter(1, '%' + search + '%');
		Long count = (Long)((Query) query).uniqueResult();
		session.close();
		return count.intValue();
	}

	public void deleteTransaction(List<Long> ids) {
		try(Session session = factory.openSession();) {
			session.beginTransaction();
			for(Long id : ids) {
				Optional<Computer> computer = getComputer(id);
				session.delete(computer.get());	
			}
			session.getTransaction().commit();
		}
	}

	public List<Computer> search(String search, String columnName, String order, int limit, int offset) {
		Session session = factory.openSession();
		List<Computer> query = session.createQuery(SEARCH + " " + columnName + " " + order, Computer.class)
				.setParameter(0, '%' + search + '%')
				.setParameter(1, '%' + search + '%')
				.setMaxResults(limit)
				.setFirstResult(offset)
				.list();
		session.close();
		return query;
	}

	public void deleteTransactionCompany(long id, Session session) {
		if(session != null) {
			List<Computer> query = session.createQuery(COMPUTERS_TO_DELETE, Computer.class)
					.setParameter(0, id)
					.list();
			for(Computer ids : query) {
				Optional<Computer> computer = getComputer(ids.getId());
				session.flush();
				session.clear();
				session.delete(computer.get());	
			}
		}
	}

}
