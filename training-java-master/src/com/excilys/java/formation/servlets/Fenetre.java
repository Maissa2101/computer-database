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
		this.setTitle("Ma première fenêtre Java");
	    this.setSize(400, 100);
	    this.setLocationRelativeTo(null);     
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	 
	    //Instanciation d'un objet JPanel
	    JPanel pan = new JPanel();
	    //Définition de sa couleur de fond
	    pan.setBackground(Color.GRAY);        
	    //On prévient notre JFrame que notre JPanel sera son content pane
	    this.setContentPane(pan);               
	    this.setVisible(true);
	  }       
}