package com.excilys.java.formation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.java.formation.entities.Users;
import com.excilys.java.formation.persistence.UserDAO;

@Service
@Transactional
public class UserService implements UserDetailsService{

	private UserDAO userDAO;

	@Autowired
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void addUser(Users user) {
		userDAO.addUser(user);
		userDAO.addUserRole(user);
	}

	public void updateUser(Users user) {
		userDAO.updateUser(user);
	}

	public Optional<Users> getUser(String pseudo) {
		return userDAO.getUser(pseudo);
	}

	@Override
	public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
		Optional<Users> user = userDAO.getUser(pseudo);
		Users users = user.get();
		return User.withDefaultPasswordEncoder().username(pseudo).password(users.getPassword()).roles(userDAO.getUserRole(pseudo)).build(); 
	}
}
