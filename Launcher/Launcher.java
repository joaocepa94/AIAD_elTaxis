package Launcher;

import java.io.IOException;
import java.util.ArrayList;

import Agents.*;
import gui.Simulation;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Launcher {

	private Simulation simulation;

	private int numberTaxisAgents;
	private int numberPassengerAgents;

	private ArrayList<Taxi> taxisAvaible;
	private ArrayList<Passenger> passengers;
	private ContainerController mainContainer;

	public Launcher() throws IOException {

		this.simulation = new Simulation();

		this.numberTaxisAgents = 6;
		this.numberPassengerAgents = 1;

	}

	public void begin() throws StaleProxyException {

		buildModel();
		buildDisplay();
		// buildSchedule(); refresh time

	}

	public void buildModel() throws StaleProxyException {

		this.taxisAvaible = new ArrayList<Taxi>();
		this.passengers = new ArrayList<Passenger>();

		// creating taxi service

		TaxiCenter taxiCenter = new TaxiCenter();
		AgentController taxiService;

		taxiService = this.mainContainer.acceptNewAgent("CentralTaxi", taxiCenter);
		taxiService.start();

		// creating taxis
		for (int i = 0; i < this.numberTaxisAgents; i++) {

			int numPassengers = 4; // (Math.random() * 4 + 0);

			int x = (int) (Math.random() * 20 + 1);
			int y = (int) (Math.random() * 20 + 1);

			Taxi taxi = new Taxi(x, y, numPassengers);

			for (int j = 0; j < numPassengers; j++) {
				
				int xPassenger = (int) (Math.random() * x + y);
				int yPassenger = (int) (Math.random() * x + y);

				PassengerTaxi p = new PassengerTaxi(xPassenger, yPassenger);
				
				taxi.addPassenger(p);
		
			}
		
			AgentController taxiAgent;
			taxiAgent = this.mainContainer.acceptNewAgent("TaxiAgent" + i, taxi);
			taxiAgent.start();

			taxisAvaible.add(taxi);
		}

		// creating passengers
		for (int i = 0; i < this.numberPassengerAgents; i++) {

			Passenger passenger = new Passenger(10, 10, false);

			AgentController passengerAgent;
			passengerAgent = this.mainContainer.acceptNewAgent("PassengerAgent" + i, passenger);
			passengerAgent.start();

			passengers.add(passenger);

		}

	}

	public void buildDisplay() {

		startFrame();

	}

	public void launchJade() throws StaleProxyException {

		Runtime rt = Runtime.instance();
		Profile p1 = new ProfileImpl();

		p1.setParameter(Profile.MAIN_HOST, "localhost");
		p1.setParameter(Profile.GUI, "true");

		this.mainContainer = rt.createMainContainer(p1);

		begin();

	}

	/*
	 * Starting demonstration
	 */
	public void startFrame() {

		this.simulation.startSimulation();

	}

	/*
	 * Launching Service Taxi
	 */

	public static void main(String args[]) throws IOException, StaleProxyException {

		Launcher n = new Launcher();
		n.launchJade();

	}

}