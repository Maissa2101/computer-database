package com.excilys.java.formation.persistence;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.excilys.java.formation.userDTO.Roles;
import com.excilys.java.formation.userDTO.Users;

@Repository
public class UserDAO {

	private SessionFactory factory;

	@Autowired
	public UserDAO(SessionFactory factory) {
		this.factory = factory;
	}

	public void addUserRole(Users user) {
		try (Session session = factory.openSession();) {
			Roles userRole = new Roles();
			userRole.setUser(user);
			session.save(userRole);
		}
	}

	public void addUser(Users user) {
		try (Session session = factory.openSession();) {
			session.save(user);
		}
	}

	public Optional<Users> getUser(String pseudo) {
		try (Session session = factory.openSession();) {
			TypedQuery<Users> query = session.createQuery("FROM " + Users.class.getName() + " users WHERE users.pseudo=?", Users.class)
					.setParameter(0, pseudo);
			Users user = (Users) ((Query<Users>) query).uniqueResult();
			return Optional.ofNullable(user);
		}
	}
	
	public String getUserRole(String pseudo) {
		try (Session session = factory.openSession();) {
			TypedQuery<Roles> query = session.createQuery("FROM " + Roles.class.getName() + " roles WHERE roles.user_pseudo=?", Roles.class)
					.setParameter(0, pseudo);
			Roles role = (Roles) ((Query<Roles>) query).uniqueResult();
			return role.getRole();
		}
	}
	
	public void updateUser(Users user) {
		try (Session session = factory.openSession();) {
			session.getTransaction().begin();
			session.update(user);
			session.getTransaction().commit();
		}
	}
}
