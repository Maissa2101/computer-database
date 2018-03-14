package com.excilys.java.formation.servlets;

import java.awt.Color; 
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Fenetre(){
		this.setTitle("Application");
	    this.setSize(500, 500);
	    this.setLocationRelativeTo(null);     
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	 
	    //Instanciation d'un objet JPanel
	    JPanel pan = new JPanel();
	    //Définition de sa couleur de fond
	    pan.setBackground(Color.WHITE);        
	    //On prévient notre JFrame que notre JPanel sera son content pane
	    this.setContentPane(pan);               
	    this.setVisible(true);
	  }       
}