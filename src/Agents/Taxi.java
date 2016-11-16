package Agents;

import java.util.Vector;
import jade.core.Agent; //

import uchicago.src.sim.gui.SimGraphics;

public class Taxi extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int counter = 0;

	private Integer idPassenger;
	private Integer x, y;
	private Vector<Passenger> passengers = new Vector<Passenger>(4);
	private Integer numberBaggages;

	public Taxi(Integer x, Integer y) {
		this.idPassenger = counter++;
		this.x = x;
		this.y = y;
	}

	public Integer getNumberBaggages() {
		return this.numberBaggages;		
	}
	
	public void addPassenger(Passenger p){
		passengers.add(p);
	}
	
	public void removePassenger(Passenger p){
		passengers.remove(p);
	}
	
	
	

}
