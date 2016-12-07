package Messages;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class First extends Agent {
	
	@Override
	protected void setup() {
		
		addBehaviour(new OneShotBehaviour() {
			
			@Override
			public void action() {
				// send message to the other agent
				jade.lang.acl.ACLMessage msg = new jade.lang.acl.ACLMessage(ACLMessage.INFORM);
				msg.setContent("Send");
				msg.addReceiver(new AID("second",AID.ISLOCALNAME));
				send(msg);
				
			}
		});
		
	}
	

}
