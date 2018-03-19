package com.excilys.java.formation.mapper;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.SQLConnection;

public class CompanyMapperTest {

	@Test(expected = SQLException.class)
	public void testGetListCompanyFromResultSet() throws SQLException {
		CompanyMapper cm = CompanyMapper.INSTANCE;
		SQLConnection.getInstance();
		Connection connection = SQLConnection.getConnection();
		java.sql.Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT count(*) as total FROM company");
		List<Company> companies = cm.getListCompanyFromResultSet(result);
		int total = 0;
		 if (result.next()) {
            total = result.getInt("total");
         }
		 assertEquals(total, companies.size());
		 connection.close();
	}

}
