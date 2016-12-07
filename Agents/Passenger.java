package Agents;

import java.awt.Image;

import jade.core.Agent;

public class Passenger extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int counter = 0;

	private static Image img;
	private Integer idPassenger;
	private Integer x, y;
	private Boolean hasBaggage;

	public Passenger(Integer x, Integer y, Boolean baggage) {
		this.idPassenger = counter++;
		this.x = x;
		this.y = y;
		this.hasBaggage = baggage;
	}

	public int getID() {
		return this.idPassenger;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Boolean hasBaggage() {
		return hasBaggage;
	}

	public static Image getImg() {
		return img;
	}

	public static void setImg(Image img) {
		Passenger.img = img;
	}
	
	@Override
	protected void setup() {
		System.out.println("Passenger: " + this.idPassenger + " Position: " + x + ", "+ y + " Baggage: " + this.hasBaggage);
	}

}
