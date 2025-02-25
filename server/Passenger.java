package t4WS3Server;

public class Passenger {

	private String name;
	private Seat seat;
	
	public Passenger(String name, Seat seat) {
		super();
		this.name = name;
		this.seat = seat;
	}

	public String getName() {
		return name;
	}

	public Seat getSeat() {
		return seat;
	}

	
}
