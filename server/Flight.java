package t4WS3Server;

import java.util.ArrayList;

public class Flight {
	private String from;
	private String to;
	private String id;
	private String date;
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private ArrayList<Seat> seats = new ArrayList<Seat>();

	public Flight(String from, String to, String id, String date) {
		super();
		this.from = from;
		this.to = to;
		this.id = id;
		this.date = date;
		for (int i = 1; i <= 5; i++)
			for (int j = 0; j <= 3; j++)
				seats.add(new Seat(Integer.toString(i) + (char) (j + 'A')));
	}

	public String getTo() {
		return to;
	}

	public String getId() {
		return id;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}

}
