package Messages;

import java.io.Serializable;

import jade.core.AID;

public class RequestTaxi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idPassenger;

	private int currentX;
	private int currentY;

	private int finalX;
	private int finalY;

	private int numPassengers;

	private AID agentIdentifier;
	
	public int getIdPassenger() {
		return idPassenger;
	}

	public void setIdPassenger(int idPassenger) {
		this.idPassenger = idPassenger;
	}

	public AID getAID() {
		return agentIdentifier;
	}

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setAID(AID aid) {
		agentIdentifier = aid;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	public int getFinalX() {
		return finalX;
	}

	public void setFinalX(int finalX) {
		this.finalX = finalX;
	}

	public int getFinalY() {
		return finalY;
	}

	public void setFinalY(int finalY) {
		this.finalY = finalY;
	}

	public int getNumPassengers() {
		return numPassengers;
	}

	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}

}
