package Messages;

import java.io.Serializable;

public class RequestAccepted implements Serializable {

	/**
	 *
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idPassenger;
	private int idTaxi;

	public int getIdPassenger() {
		return idPassenger;
	}

	public void setIdPassenger(int idPassenger) {
		this.idPassenger = idPassenger;
	}

	public int getIdTaxi() {
		return idTaxi;
	}

	public void setIdTaxi(int idTaxi) {
		this.idTaxi = idTaxi;
	}

}
