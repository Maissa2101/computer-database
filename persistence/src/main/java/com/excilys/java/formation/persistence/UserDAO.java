package com.excilys.java.formation.persistence;

import java.util.Optional;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.java.formation.entities.Roles;
import com.excilys.java.formation.entities.Users;

@Repository
public class UserDAO {

	private SessionFactory factory;
	private static final String SELECT_USERS = "FROM " + Users.class.getName() + " users WHERE users.pseudo=";
	private static final String SELECT_ROLES = "FROM " + Roles.class.getName() + " roles WHERE roles.user.pseudo=";
	
	@Autowired
	public UserDAO(SessionFactory factory) {
		this.factory = factory;
	}

	public void addUserRole(Users user) {
		try (Session session = factory.openSession();) {
			Roles role = new Roles();
			role.setUser(user);
			session.save(role);
		}
	}

	public void addUser(Users user) {
		try (Session session = factory.openSession();) {
			session.save(user);
		}
	}

	public Optional<Users> getUser(String pseudo) {
		try (Session session = factory.openSession()) {
			TypedQuery<Users> query = session.createQuery(SELECT_USERS + "'" + pseudo + "'", Users.class);
			Users user = (Users) ((Query<Users>) query).uniqueResult();
			return Optional.ofNullable(user);
		}
	}

	public String getUserRole(String pseudo) {
		try (Session session = factory.openSession()) {
			TypedQuery<Roles> query = session.createQuery(SELECT_ROLES + "'" + pseudo + "'", Roles.class);
			Roles role = (Roles) ((Query<Roles>) query).uniqueResult();
			return role.getRole();
		}
	}

	public void updateUser(Users user) {
		try (Session session = factory.openSession()) {
			session.getTransaction().begin();
			session.update(user);
			session.getTransaction().commit();
		}
	}
}
