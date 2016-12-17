package Messages;

import java.util.ArrayList;

import Agents.Passenger;
import jade.content.Predicate;
import jade.core.AID;

public class ProposeTaxi implements Predicate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AID aid;
	
	private int taxiID;
	private int PassengerID;
	
	private int currentPassengerX;
	private int currentPassengerY;
	
	private int currentTaxiX;
	private int currentTaxiY;

	private int finalX;
	private int finalY;

	private double distance;
	private double cost;
	
	private int numberPassengersTaxi;

	private ArrayList<Passenger> clients = new ArrayList<Passenger>();
	
	public AID getAid() {
		return aid;
	}

	public void setAid(AID aid) {
		this.aid = aid;
	}
	
	public int getPassengerID() {
		return PassengerID;
	}

	public void setPassengerID(int passengerID) {
		PassengerID = passengerID;
	}

	public int getCurrentPassengerX() {
		return currentPassengerX;
	}

	public void setCurrentPassengerX(int currentPassengerX) {
		this.currentPassengerX = currentPassengerX;
	}

	public int getCurrentPassengerY() {
		return currentPassengerY;
	}

	public void setCurrentPassengerY(int currentPassengerY) {
		this.currentPassengerY = currentPassengerY;
	}

	public int getTaxiID() {
		return taxiID;
	}

	public void setTaxiID(int taxiID) {
		this.taxiID = taxiID;
	}

	public int getCurrentTaxiX() {
		return currentTaxiX;
	}

	public void setCurrentTaxiX(int currentTaxiX) {
		this.currentTaxiX = currentTaxiX;
	}

	public int getCurrentTaxiY() {
		return currentTaxiY;
	}

	public void setCurrentTaxiY(int currentTaxiY) {
		this.currentTaxiY = currentTaxiY;
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

	public double getDistance() {
		return distance;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public ArrayList<Passenger> getPassengers() {
		return clients;
	}

	public void setPassengers(ArrayList<Passenger> clients) {
		this.clients = clients;
	}

	public int getNumberPassengersTaxi() {
		return numberPassengersTaxi;
	}

	public void setNumberPassengersTaxi(int numberPassengersTaxi) {
		this.numberPassengersTaxi = numberPassengersTaxi;
	}
	
}
