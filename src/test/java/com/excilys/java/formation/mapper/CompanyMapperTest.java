package com.excilys.java.formation.mapper;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.hsqldb.persist.HsqlDatabaseProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CompanyMapperTest {
	
	static Logger logger = LoggerFactory.getLogger(CompanyMapperTest.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void init() throws SQLException, IOException, SqlToolError, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connection = DataSourceUtils.getConnection(dataSource);
		InputStream inputStream = HsqlDatabaseProperties.class.getResourceAsStream("/TEST.sql");
		SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
				new File("."));
		sqlFile.setConnection(connection);
		sqlFile.execute();
    }
	
	@Test
	public void testGetListCompanyFromResultSet() throws SQLException, ClassNotFoundException {
		try (Connection connection = dataSource.getConnection(); 
				java.sql.Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT count(*) as total FROM company");	
			if (result.next()) {
				assertEquals(10, result.getInt("total"));
			}
		} catch (SQLException e) {
			logger.debug("problem in getTotalRecords", e);
		}
	}
}
