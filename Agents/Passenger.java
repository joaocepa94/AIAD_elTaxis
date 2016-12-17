package Agents;

import java.awt.Image;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Messages.RequestTaxi;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Passenger extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int counter = 0;

	private static Image img;
	private Integer idPassenger;

	private int x;
	private int y;

	private int finalx;
	private int finaly;

	private Date initialTime;
	private Date finalTime;

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

	public int getFinalx() {
		return finalx;
	}

	public void setFinalx(int finalx) {
		this.finalx = finalx;
	}

	public int getFinaly() {
		return finaly;
	}

	public void setFinaly(int finaly) {
		this.finaly = finaly;
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

	@SuppressWarnings("serial")
	@Override
	protected void setup() {

		System.out.print("\n\n" + " PASSENGER > ID: " + this.idPassenger + " | X: " + x + " Y: " + y + " | BAGGAGE: "
				+ this.hasBaggage);

		addBehaviour(new OneShotBehaviour() {

			@Override
			public void action() {

				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

				RequestTaxi request = new RequestTaxi();

				request.setIdPassenger(idPassenger);

				request.setAID(myAgent.getAID());

				request.setCurrentX(x);
				request.setCurrentY(y);

				request.setFinalX(2);
				request.setFinalY(2);

				request.setNumPassengers(1);

				try {
					msg.setContentObject(request);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				msg.addReceiver(new AID("CentralTaxi", AID.ISLOCALNAME));

				send(msg);

				initialTime = new Date();

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				this.myAgent.addBehaviour(new CyclicBehaviour() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void action() {

						MessageTemplate taxiCenterTemplate = MessageTemplate.or(
								MessageTemplate.MatchPerformative(ACLMessage.CONFIRM),
								MessageTemplate.MatchPerformative(ACLMessage.DISCONFIRM));

						ACLMessage taxiCenterResponse = myAgent.receive(taxiCenterTemplate);

						if (taxiCenterResponse != null) {

							if (taxiCenterResponse.getPerformative() == ACLMessage.CONFIRM) {

							} else if (taxiCenterResponse.getPerformative() == ACLMessage.DISCONFIRM)
								System.out.println(
										"\n\n TAXISERVICE > REJECTED PASSENGER_X: " + x + " PASSENGER_Y: " + y);

							finalTime = new Date();

							System.out.println("\n TAXISERVICE > PASSENGER_ID: " + idPassenger + " TRIP TAKED "
									+ getDateDiff(initialTime, finalTime, TimeUnit.MILLISECONDS) + " MILISECONDS");

						}

					}

				});

			}
		});

	}

	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {

		long diffInMillies = date2.getTime() - date1.getTime();

		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

}
