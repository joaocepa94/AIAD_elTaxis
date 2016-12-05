package Launcher;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import uchicago.src.reflector.ListPropertyDescriptor;
import uchicago.src.sim.engine.BasicAction;
import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimModelImpl;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.gui.DisplaySurface;
import uchicago.src.sim.gui.Object2DDisplay;
import uchicago.src.sim.space.Object2DTorus;
import uchicago.src.sim.util.Random;
import uchicago.src.sim.util.SimUtilities;


import Agents.*;

public class Launcher extends SimModelImpl {

	// Agents
	private ArrayList<Taxi> taxiAgentsList;
	private ArrayList<Passenger> passengerAgentsList;

	private Schedule schedule;
	private DisplaySurface dsurf;
	private Object2DTorus space;

	public static enum MovingMode {
		Walk, Jump
	};

	private int numberOfTaxiAgents, numberOfPassengerAgents, spaceSize;

	private MovingMode movingMode;

	private Hashtable<Color, Integer> taxiAgentColors;
	private Hashtable<Color, Integer> passengerAgentColors;

	/**
	 * 
	 */
	public Launcher() {
		this.numberOfTaxiAgents = 10;
		this.numberOfPassengerAgents = 150;
		this.spaceSize = 100;
		this.movingMode = MovingMode.Walk;
	}

	public String getName() {
		return "Taxi Service";
	}

	public String[] getInitParam() {
		return new String[] { "numberOfAgents", "spaceSize", "movingMode" };
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public int getNumberOfTaxiAgents() {
		return numberOfTaxiAgents;
	}

	public void setNumberOfTaxiAgents(int numberOfAgents) {
		this.numberOfTaxiAgents = numberOfAgents;
	}
	
	public int getNumberOfPassengerAgents() {
		return numberOfPassengerAgents;
	}

	public void setNumberOfPassengerAgents(int numberOfAgents) {
		this.numberOfPassengerAgents = numberOfAgents;
	}

	public int getSpaceSize() {
		return spaceSize;
	}

	public void setSpaceSize(int spaceSize) {
		this.spaceSize = spaceSize;
	}

	public void setMovingMode(MovingMode movingMode) {
		this.movingMode = movingMode;
	}

	public MovingMode getMovingMode() {
		return movingMode;
	}

	@SuppressWarnings("unchecked")
	public void setup() {

		schedule = new Schedule();

		if (dsurf != null)
			dsurf.dispose();

		dsurf = new DisplaySurface(this, "Color Picking Display");
		registerDisplaySurface("Color Picking Display", dsurf);

		// property descriptors
		Vector<MovingMode> vMM = new Vector<MovingMode>();
		for (int i = 0; i < MovingMode.values().length; i++)
			vMM.add(MovingMode.values()[i]);

		descriptors.put("MovingMode", new ListPropertyDescriptor("MovingMode", vMM));
	}

	public void begin() {
		buildModel();
		buildDisplay();
		buildSchedule();
	}

	public void buildModel() {

		space = new Object2DTorus(spaceSize, spaceSize);

		/*
		 * Taxis
		 */

		this.taxiAgentsList = new ArrayList<Taxi>();

		for (int i = 0; i < numberOfTaxiAgents; i++) {

			int x, y;

			do {

				x = Random.uniform.nextIntFromTo(0, space.getSizeX() - 1);
				y = Random.uniform.nextIntFromTo(0, space.getSizeY() - 1);

			} while (space.getObjectAt(x, y) != null);

			Taxi agent = new Taxi(x, y, space);

			space.putObjectAt(x, y, agent);

			this.taxiAgentsList.add(agent);
		}

		/*
		 * Passengers
		 */

		this.passengerAgentsList = new ArrayList<Passenger>();

		for (int i = 0; i < numberOfPassengerAgents; i++) {

			int x, y;

			do {

				x = Random.uniform.nextIntFromTo(0, space.getSizeX() - 1);
				y = Random.uniform.nextIntFromTo(0, space.getSizeY() - 1);

			} while (space.getObjectAt(x, y) != null);

			Passenger agent = new Passenger(x, y, space);
			
			space.putObjectAt(x, y, agent);
			
			this.passengerAgentsList.add(agent);

		}

	}

	private void buildDisplay() {
		
		/* Display taxis */
		Object2DDisplay displayTaxis = new Object2DDisplay(space);
		displayTaxis.setObjectList(this.taxiAgentsList);
		dsurf.addDisplayableProbeable(displayTaxis, "Taxis");
		addSimEventListener(dsurf);
		
		/* Display passengers*/
		Object2DDisplay displayPassengers = new Object2DDisplay(space);
		displayPassengers.setObjectList(this.passengerAgentsList);
		dsurf.addDisplayableProbeable(displayPassengers, "Passengers");
		addSimEventListener(dsurf);
		
		dsurf.display();
	}

	private void buildSchedule() {
		schedule.scheduleActionBeginning(0, new MainAction());
		schedule.scheduleActionAtInterval(1, dsurf, "updateDisplay", Schedule.LAST);
	}

	class MainAction extends BasicAction {

		public void execute() {

			// prepare agent colors hash table
			taxiAgentColors = new Hashtable<Color, Integer>();
			passengerAgentColors = new Hashtable<Color, Integer>();

			// shuffle agents
			SimUtilities.shuffle(taxiAgentsList);
			SimUtilities.shuffle(passengerAgentsList);

			// iterate through all taxi agents
			for (int i = 0; i < taxiAgentsList.size(); i++) {

				Taxi taxiagent = taxiAgentsList.get(i);

				// Move agent
				taxiagent.jump();

				Color c = taxiagent.getColor();
				int nAgentsWithColor = (taxiAgentColors.get(c) == null ? 1 : taxiAgentColors.get(c) + 1);
				taxiAgentColors.put(c, nAgentsWithColor);

			}

			// iterate through all passengers agents
			for (int i = 0; i < passengerAgentsList.size(); i++) {

				Passenger passangeragent = passengerAgentsList.get(i);

				Color c = passangeragent.getColor();
				int nAgentsWithColor = (passengerAgentColors.get(c) == null ? 1 : passengerAgentColors.get(c) + 1);
				passengerAgentColors.put(c, nAgentsWithColor);

			}

		}

	}

	/**
	 * Initialize the taxi service
	 */
	public static void main(String[] args) {
		SimInit init = new SimInit();
		init.loadModel(new Launcher(), null, false);
	}

}
