package Messages;

import java.util.concurrent.CyclicBarrier;

import javax.swing.JOptionPane;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Second extends Agent {
	
	@Override
	protected void setup() {
		
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				// Receive the another agent message
				ACLMessage msg = receive();
				
				if(msg!=null){
					JOptionPane.showMessageDialog(null, "Message Received " + msg.getContent());
				}else block();
			}
		});
		
	}

}
