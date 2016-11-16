package Agents;

public class Passenger {
	
	private static int counter = 0;
	
	private Integer idPassenger;
	private Integer x, y;
	private Boolean hasBaggage;

	public Passenger(Integer x, Integer y, Boolean baggage){
		this.idPassenger = counter++;
		this.x = x;
		this.y = y;
		this.hasBaggage = baggage;
	}
	
	
	public Boolean hasBaggage(){
		return hasBaggage;
	}
	
	
}
