package Messages;

import java.io.Serializable;

public class PassengerInfo  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int passengerID;

	private int currentX;
	private int currentY;

	private int finalX;
	private int finalY;

	public PassengerInfo(int id, int x, int y, int finalX, int finalY) {

		this.passengerID = id;

		this.currentX = x;
		this.currentY = y;

		this.finalX = finalX;
		this.finalY = finalY;

	}

	public int getPassengerID() {
		return passengerID;
	}

	public void setPassengerID(int passengerID) {
		this.passengerID = passengerID;
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

	public void setCurrentY(int curretnY) {
		this.currentY = curretnY;
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

}
