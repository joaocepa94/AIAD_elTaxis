package Agents;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import onto.ServiceOntology;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;

import Messages.*;
import behaviours.MyReceiver;

public class TaxiCenter extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AgentContainer container;

	private Codec codec;
	private Ontology serviceOntology;

	private AID clientAID;

	private ArrayList<ProposeTaxi> taxiProposes = new ArrayList<ProposeTaxi>();

	@Override
	protected void setup() {

		System.out.print(" > WELCOME, TO TAXICENTER! MY NAME IS: " + this.getLocalName() + " < ");

		codec = new SLCodec();

		serviceOntology = ServiceOntology.getInstance();

		getContentManager().registerLanguage(codec);

		getContentManager().registerOntology(serviceOntology);

		this.addBehaviour(new CyclicBehaviour() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

				ACLMessage message = myAgent.receive(messageTemplate);

				if (message != null) {

					Object messageContent = null;

					try {
						messageContent = message.getContentObject();
					} catch (UnreadableException e) {
						e.printStackTrace();
					}

					if (messageContent instanceof RequestTaxi) {

						clientAID = ((RequestTaxi) messageContent).getAID();

						System.out.print("\n\n TAXISERVICE > REQUEST RECEIVED FROM PASSENGER_ID: "
								+ ((RequestTaxi) messageContent).getIdPassenger() + " | PASSENGER_X: "
								+ ((RequestTaxi) messageContent).getCurrentX() + " PASSENGER_Y: "
								+ ((RequestTaxi) messageContent).getCurrentY());

						// search provider DFAgentDescription
						DFAgentDescription agentDescription = new DFAgentDescription();
						ServiceDescription serviceDescription = new ServiceDescription();

						serviceDescription.setType("TaxiAgent");
						agentDescription.addServices(serviceDescription);

						SearchConstraints sc = new SearchConstraints();
						sc.setMaxResults(new Long(-1));

						DFAgentDescription[] result = null;

						try {
							result = DFService.search(myAgent, agentDescription, sc);
						} catch (FIPAException e) {
							e.printStackTrace();
						}

						AID[] taxiAgents = new AID[result.length];

						ACLMessage request = new ACLMessage(ACLMessage.REQUEST);

						for (int i = 0; i < result.length; i++)
							taxiAgents[i] = result[i].getName();

						try {
							request.setContentObject((RequestTaxi) messageContent);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// Template for taxi response
						MessageTemplate taxiResponse = MessageTemplate.or(
								MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
								MessageTemplate.MatchPerformative(ACLMessage.REFUSE));

						// Adding SequentialBehaviour to the agent
						SequentialBehaviour sequentialBehaviour = new SequentialBehaviour();
						addBehaviour(sequentialBehaviour);

						ParallelBehaviour parallelBehaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
						sequentialBehaviour.addSubBehaviour(parallelBehaviour);

						for (int i = 0; i < taxiAgents.length; i++) {

							request.addReceiver(taxiAgents[i]);

							parallelBehaviour.addSubBehaviour(new MyReceiver(myAgent, 1000, taxiResponse) {

								/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

								public void handle(ACLMessage msg) {

									if (msg != null) {

										if (msg.getPerformative() == ACLMessage.PROPOSE) {

											ProposeTaxi proposeReceived = null;

											try {
												proposeReceived = (ProposeTaxi) msg.getContentObject();
											} catch (UnreadableException e) {
												e.printStackTrace();
											}

											taxiProposes.add(proposeReceived);

										}
									}

								}

							});
						}

						send(request);

						// change to allow no viable options
						sequentialBehaviour.addSubBehaviour(new OneShotBehaviour() {

							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							@Override
							public void action() {

								ACLMessage clientReply;

								if (taxiProposes.size() > 0) {

									clientReply = new ACLMessage(ACLMessage.CONFIRM);

									double currentMin = 1000000;

									int index = 0;

									for (int i = 0; i < taxiProposes.size(); i++)
										if (taxiProposes.get(i).getCost() < currentMin) {
											index = i;
											currentMin = taxiProposes.get(i).getCost();
										}

									System.out.println("\n\n TAXISERVICE > ACCEPTED PASSENGER_ID: "
											+ taxiProposes.get(index).getPassengerID() + " | TAXI_ID: "
											+ taxiProposes.get(index).getTaxiID() + " IS ON THE WAY");

									clientReply.addReceiver(clientAID);

									ACLMessage taxiChosen = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);

									ACLMessage otherTaxis = new ACLMessage(ACLMessage.REJECT_PROPOSAL);

									for (int i = 0; i < taxiProposes.size(); i++)
										if (i != index)
											otherTaxis.addReceiver(taxiProposes.get(i).getAid());

									send(otherTaxis);

									PassengerInfo info = new PassengerInfo(taxiProposes.get(index).getPassengerID(),
											taxiProposes.get(index).getCurrentPassengerX(),
											taxiProposes.get(index).getCurrentPassengerY(),
											taxiProposes.get(index).getFinalX(), taxiProposes.get(index).getFinalY());

									try {
										taxiChosen.setContentObject(info);
									} catch (IOException e) {
										e.printStackTrace();
									}

									taxiChosen.addReceiver(taxiProposes.get(index).getAid());

									send(taxiChosen);

								} else {

									clientReply = new ACLMessage(ACLMessage.DISCONFIRM);

									clientReply.addReceiver(clientAID);

								}

								send(clientReply);

								taxiProposes.clear();

							}
						});

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

			}

		});

	}

	protected double distance(int currentx, int currenty, int finalx, int finaly) {
		return Math.sqrt(Math.pow(currentx - finalx, 2) - Math.pow(currenty - finaly, 2));
	}

}
