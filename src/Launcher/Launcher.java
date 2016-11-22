package Launcher;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;


class Launcher{

	public static void main(String args[]) {

		Runtime rt = Runtime.instance();

		Profile p = new ProfileImpl();
		p.setParameter(Profile.MAIN_HOST, "localhost");
		p.setParameter(Profile.GUI, "true");

		ContainerController cc = rt.createMainContainer(p);

		for(int i=0; i<5; i++){

			AgentController ac;

			try {
				ac=cc.createNewAgent("TaxiAgent"+i, "Agents.Taxi", null);
				ac.start();
			} catch (StaleProxyException e) {
				e.printStackTrace();
			}

		}

	}
}
