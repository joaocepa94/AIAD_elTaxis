package Agents;

import java.awt.Image;
import java.util.Vector;
import jade.core.Agent; //

public class Taxi extends Agent {

	/**
	 * 
	 */
	/*private static final long serialVersionUID = 1L;

	private static int counter = 0;

	private static Image img;
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
	
	public static Image getImg() {
		return img;
	}

	public static void setImg(Image img) {
		Taxi.img = img;
	}*/
	
	@Override
	protected void setup() {
		System.out.println("Hello!!");
	}
	
	
}