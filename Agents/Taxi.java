package Agents;

import java.io.IOException;
import java.util.ArrayList;

import Messages.PassengerInfo;
import Messages.ProposeTaxi;
import Messages.RequestTaxi;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class Taxi extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int counter = 0;
	private int idTaxi;
	private int x, y;
	private int numberPassengers;

	private ArrayList<PassengerTaxi> passengers;

	public Taxi(int x, int y, int numPassengers) {

		this.idTaxi = counter++;
		this.x = x;
		this.y = y;
		this.numberPassengers = numPassengers;

		this.passengers = new ArrayList<PassengerTaxi>();

	}

	public int getID() {
		return this.idTaxi;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getNumberPassengers() {
		return numberPassengers;
	}

	public void addPassenger(PassengerTaxi t) {
		passengers.add(t);
	}

	public ArrayList<PassengerTaxi> getPassengers() {
		return passengers;
	}

	public void setNumberPassengers(int numberPassengers) {
		this.numberPassengers = numberPassengers;
	}

	@Override
	protected void setup() {

		System.out.print("\n\n TAXI >  ID: " + this.idTaxi + " | X: " + x + " Y: " + y + " | CURRENT_NUM_PASSENGERS: "
				+ numberPassengers);

		register(createInitialDFAgentDescription());

		addBehaviour(new CyclicBehaviour() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			private int step = 1;

			@Override
			public void action() {

				switch (step) {

				case 1:
					// Receiving request for central and sending propose

					MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

					ACLMessage message = myAgent.receive(messageTemplate);

					if (message != null) {

						RequestTaxi messageContent = null;

						try {
							messageContent = (RequestTaxi) message.getContentObject();
						} catch (UnreadableException e) {
							e.printStackTrace();
						}

						if (messageContent instanceof RequestTaxi) {

							ACLMessage reply = message.createReply();

							/* Verifies if the passenger has space */

							if (numberPassengers < 4) {

								reply.setPerformative(ACLMessage.PROPOSE);

								ProposeTaxi propose = new ProposeTaxi();

								propose.setTaxiID(idTaxi);

								propose.setAid(getAID());

								propose.setPassengerID(messageContent.getIdPassenger());

								propose.setCurrentPassengerX(messageContent.getCurrentX());
								propose.setCurrentPassengerY(messageContent.getCurrentY());

								propose.setDistance(
										distance(messageContent.getCurrentX(), messageContent.getCurrentY(), x, y));

								propose.setFinalX(messageContent.getFinalX());
								propose.setFinalY(messageContent.getFinalY());

								propose.setCost(func(messageContent.getCurrentX(), messageContent.getCurrentY(),
										messageContent.getFinalX(), messageContent.getFinalY()));

								propose.setCurrentTaxiX(x);
								propose.setCurrentTaxiX(y);

								propose.setNumberPassengersTaxi(numberPassengers);

								try {
									reply.setContentObject(propose);
								} catch (IOException e) {
									e.printStackTrace();
								}

								System.out.print("\n\n TAXI > TAXI_ID: " + idTaxi
										+ " RESPONSE TO REQUEST FROM PASSENGER_ID: " + messageContent.getIdPassenger()
										+ " | TAXI_X: " + x + " TAXI_Y: " + y + " | CURRENT_NUM_PASSANGERS: "
										+ numberPassengers + " | DISTANCE TO PASSENGER: " + propose.getDistance()
										+ " | COST: " + propose.getCost());

							} else {

								/* Passenger doesn't have enough space */
								System.out.println(
										"\n\n TAXI > TAXI_ID: " + idTaxi + " > REJECT REQUEST FROM PASSENGER_ID: "
												+ ((RequestTaxi) messageContent).getIdPassenger()
												+ " (DOESNT HAVE ENOUGH SPACE)");

								reply.setPerformative(ACLMessage.REFUSE);
							}

							send(reply);

						}
					}

					step = 2;

					break;

				case 2:

					MessageTemplate templateResponseForCentral = MessageTemplate.or(
							MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
							MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL));

					ACLMessage response = myAgent.receive(templateResponseForCentral);

					if (response != null) {

						if (response.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {

							System.out.println("\n TAXI > TAXI_ID: " + idTaxi
									+ " RECEIVED REQUEST FROM CENTRAL_TAXI TO GET PASSENGER_ID: " + 0);

							PassengerInfo infoNewPassenger = null;

							try {
								infoNewPassenger = (PassengerInfo) response.getContentObject();
							} catch (UnreadableException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							x = infoNewPassenger.getCurrentX();
							y = infoNewPassenger.getCurrentY();

							System.out.println("\n TAXI > TAXI_ID: " + idTaxi + " ARRIVED TO THE PASSENGER_ID: "
									+ infoNewPassenger.getPassengerID());

							int currentX = x;
							int currentY = y;
							
							PassengerTaxi p =  new PassengerTaxi(infoNewPassenger.getFinalX(), infoNewPassenger.getFinalY());
							
							p.setId(0);
							
							passengers.add(p);

							while (passengers.size() != 0) {

								int index = getNearPoint(currentX, currentY, passengers);

								currentX = passengers.get(index).getFinalX();
								currentY = passengers.get(index).getFinalY();

								System.out.println("\n TAXI > TAXI_ID: " + idTaxi + " DELIVERED THE PASSENGER_ID: "
										+ passengers.get(index).getId() + " ON POSITION_X: "
										+ passengers.get(index).getFinalX() + " POSITION_Y: "
										+ passengers.get(index).getFinalY());

								passengers.remove(index);

							}

						} else if (response.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
							
							int currentX = x;
							int currentY = y;
							
							while (passengers.size() != 0) {

								int index = getNearPoint(currentX, currentY, passengers);

								currentX = passengers.get(index).getFinalX();
								currentY = passengers.get(index).getFinalY();

								System.out.println("\n TAXI > TAXI_ID: " + idTaxi + " DELIVERED THE PASSENGER_ID: "
										+ passengers.get(index).getId() + " ON POSITION_X: "
										+ passengers.get(index).getFinalX() + " POSITION_Y: "
										+ passengers.get(index).getFinalY());

								passengers.remove(index);

							}
							
						}

					}

					step = 1;
					break;

				} /* Closing switch */

			}
		});

	}

	protected double distance(int currentx, int currenty, int finalx, int finaly) {
		return Math.sqrt(Math.pow(currentx - finalx, 2) + Math.pow(currenty - finaly, 2));
	}

	protected DFAgentDescription createInitialDFAgentDescription() {

		DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.setName(this.getAID());

		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType("TaxiAgent");
		serviceDescription.setName(this.getLocalName());

		Property passengers = new Property("Baggages", 5);

		serviceDescription.addProperties(passengers);

		agentDescription.addServices(serviceDescription);

		return agentDescription;
	}

	protected double func(int currentPassengerX, int currentPassengerY, int finalX, int finalY) {

		double result = 0.0;

		Boolean canTakeThePassenger = true;

		double distanceTaxiToPassenger = distance(currentPassengerX, currentPassengerY, x, y);

		for (int i = 0; i < passengers.size(); i++) {

			double distancePointOfActualPassenger = distance(x, y, passengers.get(i).getFinalX(),
					passengers.get(i).getFinalY());

			if (distanceTaxiToPassenger + distance(finalX, finalY, passengers.get(i).getFinalX(),
					passengers.get(i).getFinalY()) >= distancePointOfActualPassenger
							+ distancePointOfActualPassenger * 0.3) {
				canTakeThePassenger = false;
				break;
			}

		}

		if (canTakeThePassenger) {

			ArrayList<PassengerTaxi> finalPoints = passengers;

			finalPoints.add(new PassengerTaxi(finalX, finalY));

			int currentX = currentPassengerX;
			int currentY = currentPassengerY;

			result += distanceTaxiToPassenger;

			while (finalPoints.size() != 0) {

				int index = getNearPoint(currentPassengerX, currentPassengerY, finalPoints);

				result += distance(currentX, currentY, finalPoints.get(index).getFinalX(),
						finalPoints.get(index).getFinalY());

				currentX = finalPoints.get(index).getFinalX();
				currentY = finalPoints.get(index).getFinalY();

				finalPoints.remove(index);

			}

		}

		if (result == 0.0) {
			result = 1000;
		}

		return result;

	}

	protected int getNearPoint(int x, int y, ArrayList<PassengerTaxi> finalPoints) {

		double distance = 1000;

		int index = 0;

		for (int i = 0; i < passengers.size(); i++) {

			double new_distance = distance(x, y, passengers.get(i).getFinalX(), passengers.get(i).getFinalX());

			if (distance > new_distance)
				index = i;

		}

		return index;

	}

	protected void register(DFAgentDescription dfd) {
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

}
