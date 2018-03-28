package com.excilys.java.formation.persistence;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.service.CompanyService;
import com.zaxxer.hikari.HikariDataSource;

public class SQLConnection {

	static Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private static final String FICHIER_PROPERTIES = "dao";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_USER = "user";
	private static final String PROPERTY_PASSWORD = "passwd";
	
	private static SQLConnection instance= null;

	private static String url;
	private static String user;
	private static String passwd;
	
    private static HikariDataSource ds = new HikariDataSource();
 
	SQLConnection( ) {
		ds.setJdbcUrl(url);
		ds.setUsername(user);
		ds.setPassword(passwd);
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
	}

	public static SQLConnection getInstance() throws DAOConfigurationException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");	
		ResourceBundle fichierProperties = ResourceBundle.getBundle( FICHIER_PROPERTIES, Locale.getDefault());
		if ( fichierProperties == null ) {
			throw new DAOConfigurationException( "The file properties " + FICHIER_PROPERTIES + " was not found." );
		}
		
		url = fichierProperties.getString( PROPERTY_URL );
		user = fichierProperties.getString( PROPERTY_USER );
		passwd = fichierProperties.getString( PROPERTY_PASSWORD );

		if(instance == null) {
			instance = new SQLConnection();
		}
		return instance;
	}

	synchronized public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	/**
	 * Close the connection to the DB
	 * @throws SQLException in case of a database access error
	 */

	public static void closeConnection(Connection conn) throws SQLException {
		if(conn != null) {
			try {
				conn.close();
			} catch ( SQLException e) {
				logger.error("Problem in closing the connection", e);
			}
		}
	}
}

