package com.excilys.java.formation.persistence;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.service.CompanyService;

public class SQLConnection {

	static Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private static final String FICHIER_PROPERTIES = "com.excilys.java.formation.persistence.dao";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_USER = "user";
	private static final String PROPERTY_PASSWORD = "passwd";

	private static String url;
	private static String user;
	private static String passwd;


	SQLConnection( String url, String username, String password ) {
		SQLConnection.url = url;
		SQLConnection.user = username;
		SQLConnection.passwd = password;
	}

	public static SQLConnection getInstance() throws DAOConfigurationException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		ResourceBundle fichierProperties = ResourceBundle.getBundle( FICHIER_PROPERTIES );

		if ( fichierProperties == null ) {
			throw new DAOConfigurationException( "The file properties " + FICHIER_PROPERTIES + " was not found." );
		}

		url = fichierProperties.getString( PROPERTY_URL );
		user = fichierProperties.getString( PROPERTY_USER );
		passwd = fichierProperties.getString( PROPERTY_PASSWORD );


		SQLConnection instance = new SQLConnection( url, user, passwd );
		return instance;
	}

	synchronized public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, passwd);
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
				logger.info(e.getMessage());
			}
		}
	}
}

