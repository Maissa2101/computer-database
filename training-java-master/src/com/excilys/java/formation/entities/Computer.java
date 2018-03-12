package com.excilys.java.formation.entities;


import java.sql.Timestamp;


public class Computer {

		private Long id;
		
	    private String name;

	    private Timestamp introduced;

	    private Timestamp disconnected;
	
	    private String manufacturer;
	    
	    /**
	     * 
	     * @return the id of the computer
	     */
	    public Long getId() {
			return id;
		}
	    
	    /**
	     * 
	     * @param id of the computer to set
	     */
		public void setId(Long id) {
			this.id = id;
		}
	    
	    /**
	     * 
	     * @return name of the computer
	     */
		public String getName() {
			return name;
		}
		
		/**
		 * 
		 * @param name is the name of the computer
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * 
		 * @return date introduction of the computer
		 */
		public Timestamp getDateIntro() {
			return introduced;
		}
		
		/**
		 * 
		 * @param dateIntro is the date of introduction of the computer
		 */
		public void setDateIntro(Timestamp dateIntro) {
			this.introduced = dateIntro;
		}
		
		/***
		 *
		 * @return date of disconnect of the computer
		 */
		public Timestamp getDateDisconnect() {
			return disconnected;
		}
		
		/**
		 * 
		 * @param dateDisconnect is the date of disconnection of the computer
		 */
		public void setDateDisconnect(Timestamp dateDisconnect) {
			this.disconnected = dateDisconnect;
		}
		
		/**
		 * 
		 * @return the manufacturer 
		 */
		public String getManufacturer() {
			return manufacturer;
		}
		
		/**
		 * 
		 * @param manufacturer is the manufacturer of the computer
		 */
		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}

	   
}
	


