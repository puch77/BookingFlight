package t4WS3Server;

public class Seat {
	private String id;
	private Passenger passenger;
	
	public Seat(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	
}
