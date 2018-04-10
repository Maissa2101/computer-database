package com.excilys.java.formation.persistence;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.CompanyDAO;

public class CompanyDAOTest {
	
	static Logger logger = LoggerFactory.getLogger(CompanyDAOTest.class);
	
	@BeforeClass
	public static void init() throws SQLException, IOException, ClassNotFoundException, DAOConfigurationException, SqlToolError {
		try (Connection connection = SQLConnection.getInstance().getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			statement.execute("CREATE TABLE company (id BIGINT NOT NULL identity, name VARCHAR(255),"
					+ "constraint pk_company PRIMARY KEY (id));");
			connection.commit();
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Apple Inc.');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Thinking Machines');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('RCA');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Netronics');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Tandy Corporation');");
			statement.executeUpdate("INSERT INTO company (name) VALUES ('Commodore International');");

			connection.commit();
		}
	}

	@AfterClass
	public static void destroy() throws SQLException, ClassNotFoundException, IOException, DAOConfigurationException {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection(); java.sql.Statement statement = connection.createStatement();) {
			statement.executeUpdate("DROP TABLE company");
			connection.commit();
		} 
	}
	
	@Test
	public void testGetListCompany() {
		CompanyDAO cd = CompanyDAO.INSTANCE;
		try {
			List<Company> list = cd.getListCompany(3, 1);
			for(Company company: list) {
				if(company.getId() == 1) {
					assertEquals("Thinking Machines", company.getName());
				}
			}
		} catch (DAOException e) {
			logger.debug("problem in testGetListCompany", e);
		}
	}

}
