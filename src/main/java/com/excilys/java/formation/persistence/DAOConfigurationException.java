package com.excilys.java.formation.persistence;

public class DAOConfigurationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOConfigurationException( String message ) {
		super( message );
	}

	public DAOConfigurationException( String message, Throwable cause ) {
		super( message, cause );
	}

	public DAOConfigurationException( Throwable cause ) {
		super( cause );
	}
}