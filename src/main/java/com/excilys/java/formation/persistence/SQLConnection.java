package com.excilys.java.formation.persistence;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SQLConnection {
		
	 	private static final String FICHIER_PROPERTIES = "com.excilys.java.formation.persistence.dao";
	    private static final String PROPERTY_URL = "url";
	    private static final String PROPERTY_DRIVER = "driver";
	    private static final String PROPERTY_USER = "user";
	    private static final String PROPERTY_PASSWORD = "passwd";

	    private static String url;
	    private static String user;
	    private static String passwd;
	    private static String driver;
	
	    SQLConnection( String url, String username, String password ) {
	        SQLConnection.url = url;
	        SQLConnection.user = username;
	        SQLConnection.passwd = password;
	    }
	    
	    
	    public static SQLConnection getInstance() throws DAOConfigurationException {

	        ResourceBundle fichierProperties = ResourceBundle.getBundle( FICHIER_PROPERTIES );

	        if ( fichierProperties == null ) {
	            throw new DAOConfigurationException( "The file properties " + FICHIER_PROPERTIES + " is not found." );
	        }
	        
	            url = fichierProperties.getString( PROPERTY_URL );
	            driver = fichierProperties.getString( PROPERTY_DRIVER );
	            user = fichierProperties.getString( PROPERTY_USER );
	            passwd = fichierProperties.getString( PROPERTY_PASSWORD );
	        
	        try {
	            Class.forName( driver );
	        } catch ( ClassNotFoundException e ) {
	            throw new DAOConfigurationException( "The driver does not exist in the classpath.", e );
	        }

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
	    
	public static void closeConnection() throws SQLException {
		if(getConnection() != null) {
			try {
	        	getConnection().close();
	        } catch ( SQLException e) {
	        	System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
	        }
		}
	}
		

	}

