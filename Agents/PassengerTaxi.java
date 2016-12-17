package Agents;

public class PassengerTaxi {

	private static int counter = 0;

	private int id;

	private int finalX;
	private int finalY;

	public PassengerTaxi(int finalX, int finalY) {

		this.id = counter++;

		this.finalX = finalX;
		this.finalY = finalY;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
