package com.excilys.java.formation.mapper;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.java.formation.persistence.DAOConfigurationException;
import com.excilys.java.formation.persistence.SQLConnection;

public class CompanyMapperTest {
	
	static Logger logger = LoggerFactory.getLogger(CompanyMapperTest.class);
	
	@Test
	public void testGetListCompanyFromResultSet() throws SQLException, DAOConfigurationException, ClassNotFoundException {
		SQLConnection.getInstance();
		try (Connection connection = SQLConnection.getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT count(*) as total FROM company");	
			if (result.next()) {
				assertEquals(6, result.getInt("total"));
			}
		} catch (SQLException e) {
			logger.debug("problem in getTotalRecords", e);
		}
	}
}
