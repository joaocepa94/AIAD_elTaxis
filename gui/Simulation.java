package gui;

import java.io.IOException;

import javax.swing.JFrame;

public class Simulation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map map;

	public Simulation() throws IOException {

		this.map = new Map();
		getContentPane().add(this.map);

	}

	public void startSimulation() {
		setSize(700, 490);
		setVisible(true);
	}
}
