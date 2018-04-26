package com.excilys.java.formation.persistence;

import java.sql.Date;
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

	private static final String SELECT_REQUEST_LIST = "FROM computer LEFT JOIN company ON computer.company_id=company.id ORDER BY";
	private static final String SELECT_REQUEST_DETAILS = "FROM computer WHERE id=?;";
	private static final String INSERT_REQUEST = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private static final String UPDATE_REQUEST = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	private static final String DELETE_REQUEST = "DELETE FROM computer WHERE id=?;";
	private static final String COUNT = "SELECT count(*) as total FROM computer;";
	private static final String SEARCH = "FROM computer LEFT JOIN company ON" +
			" computer.company_id=company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY";
	private static final String COUNT_SEARCH = "SELECT count(*) as total FROM computer LEFT JOIN company ON" + 
			" computer.company_id=company.id WHERE computer.name LIKE ? OR company.name LIKE ?;";
	private static final String DELETE_COMPUTERS_COMPANY = "DELETE FROM computer WHERE company_id=?;";

	private SessionFactory factory;

	@Autowired
	public ComputerDAOSpring(SessionFactory factory) {
		this.factory = factory;
	}

	public List<Computer> getListComputer(int limit, int offset, String columnName, String order) {
		Session session = factory.openSession();
		TypedQuery<Computer> query = session.createQuery(SELECT_REQUEST_LIST + " " + columnName + " " + order + " LIMIT ? OFFSET ?;", Computer.class)
				.setParameter(0, limit)
				.setParameter(1, offset);
		List<Computer> result = query.getResultList();
		session.close();
		return result;

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
		Session session = factory.openSession();
		TypedQuery<Computer> query = session.createQuery(INSERT_REQUEST, Computer.class)
				.setParameter(0, computer.getName())
				.setParameter(1, (computer.getIntroduced() == null) ? null : Date.valueOf(computer.getIntroduced()))
				.setParameter(2, (computer.getDiscontinued() == null) ? null : Date.valueOf(computer.getDiscontinued()))
				.setParameter(3, (computer.getManufacturer() == null || computer.getManufacturer().equals("null")) ? null : computer.getManufacturer());
		query.executeUpdate();
		session.close();
		return computer.getId();
	}

	public void updateComputer(long id, String name, LocalDate intro, LocalDate discontinued, Company manufacturer) {
		Computer computer = new Computer.ComputerBuilder(id, name).introduced(intro).discontinued(discontinued).manufacturer(manufacturer).build();
		Session session = factory.openSession();
		TypedQuery<Computer> query = session.createQuery(UPDATE_REQUEST, Computer.class)
				.setParameter(0, computer.getName())
				.setParameter(1, (computer.getIntroduced() == null) ? null : Date.valueOf(computer.getIntroduced()))
				.setParameter(2, (computer.getDiscontinued() == null) ? null : Date.valueOf(computer.getDiscontinued()))
				.setParameter(3, (computer.getManufacturer() == null || computer.getManufacturer().equals("null")) ? null : computer.getManufacturer())
				.setParameter(4, id);
		query.executeUpdate();
		session.close();
	}

	public void deleteComputer(long id) {
		Session session = factory.openSession();
		TypedQuery<Computer> query = session.createQuery(DELETE_REQUEST, Computer.class).setParameter(0, id);
		query.executeUpdate();
		session.close();
	}

	public int count() {
		Session session = factory.openSession();
		TypedQuery<Computer> query = session.createQuery(COUNT, Computer.class);
		int count = (int)((Query) query).uniqueResult();
		session.close();
		return count;

	}

	public int countAfterSearch(String search) {
		Session session = factory.openSession();
		TypedQuery<Computer> query = session.createQuery(COUNT_SEARCH, Computer.class)
				.setParameter(0, search + '%')
				.setParameter(1, '%' + search + '%');
		int count = (int)((Query) query).uniqueResult();
		session.close();
		return count;
	}

	public void deleteTransaction(List<Long> ids) {
		Session session = factory.openSession();
		session.beginTransaction();
		for(Long id : ids) {
			TypedQuery<Computer> query = session.createQuery(DELETE_REQUEST, Computer.class).setParameter(0, id);
			query.executeUpdate();
		}
		session.getTransaction().commit();	
		session.close();
	}

	public List<Computer> search(String search, String columnName, String order, int limit, int offset) {
		Session session = factory.openSession();
		TypedQuery<Computer> query = session.createQuery(SEARCH + " " + columnName + " " + order + " LIMIT ? OFFSET ?;", Computer.class)
				.setParameter(0, '%' + search + '%')
				.setParameter(1, '%' + search + '%')
				.setParameter(2, limit)
				.setParameter(3, offset);
		List<Computer> result = query.getResultList();
		session.close();
		return result;
	}

	public void deleteTransactionCompany(long id) {
		Session session = factory.openSession();
		session.beginTransaction();
		TypedQuery<Computer> query = session.createQuery(DELETE_COMPUTERS_COMPANY, Computer.class).setParameter(0, id);
		query.executeUpdate();
		session.getTransaction().commit();	
		session.close();
	}

}
