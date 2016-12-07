package Launcher;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

import Agents.*;
import gui.Map;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Launcher extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map map;

	private int numberTaxisAgents;
	private int numberPassengerAgents;

	private ArrayList<Taxi> taxisAvaible;
	private ArrayList<Passenger> passengers;
	private ContainerController mainContainer;

	public Launcher() throws IOException {

		this.map = new Map();

		this.numberTaxisAgents = 15;
		this.numberPassengerAgents = 5;

		getContentPane().add(map);

	}

	public void begin() throws StaleProxyException {

		buildModel();
		buildDisplay();
		// buildSchedule(); refresh time

	}

	public void buildModel() throws StaleProxyException {

		this.taxisAvaible = new ArrayList<Taxi>();
		this.passengers = new ArrayList<Passenger>();

		for (int i = 0; i < this.numberTaxisAgents; i++) {

			int x = 1;
			int y = 1;

			Taxi taxi = new Taxi(x, y);

			AgentController taxiAgent;
			taxiAgent = this.mainContainer.acceptNewAgent("TaxiAgent" + i, taxi);
			taxiAgent.start();

			taxisAvaible.add(taxi);

		}

		for (int i = 0; i < this.numberPassengerAgents; i++) {

			int x = 1;
			int y = 1;

			Passenger passenger = new Passenger(x, y, false);

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

		this.mainContainer = rt.createMainContainer(p1);

		begin();

	}

	/*
	 * Starting demonstration
	 */
	public void startFrame() {
		setSize(700, 490);
		setVisible(true);
	}

	/*
	 * Launching Service Taxi
	 */
	public static void main(String args[]) throws IOException, StaleProxyException {

		Launcher n = new Launcher();
		n.launchJade();

	}

}
