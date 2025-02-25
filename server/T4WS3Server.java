package t4WS3Server;

import java.io.IOException;
import java.util.ArrayList;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class T4WS3Server {
	private ArrayList<Flight> flights = new ArrayList<Flight>();

	@WebMethod
	public String[] getDestinationList() {
		return flights.stream().map(Flight::getTo).distinct().sorted().toArray(String[]::new);
	}

	@WebMethod
	public String[] getFlightList(String destn) {
		return flights.stream().filter(f -> f.getTo().equals(destn)).map(Flight::getId).toArray(String[]::new);
	}

	@WebMethod
	public String[] getEmptySeatList(String fid) {
		Flight flight = flights.stream().filter(f -> f.getId().equals(fid)).findFirst().orElse(null);

	    if (flight != null) {
	        return flight.getSeats().stream()
	                .filter(seat -> seat.getPassenger() == null)
	                .map(Seat::getId)
	                .toArray(String[]::new);
	    } else {
	        return null; 
	    }
	}

	@WebMethod
	public boolean addPassenger(String fid, String n, String sid) {
		try {
			Flight flight = flights.stream().filter(f -> f.getId().equals(fid)).findFirst().orElse(null);

			if (flight == null) {
				System.out.println("Flight not found : " + fid);
				return false;
			} else
				System.out.println("Flight ID: " + fid);

			Seat seat = flight.getSeats().stream().filter(s -> s.getId().equals(sid)).findFirst().orElse(null);

			if (seat == null) {
				System.out.println("Seat not found for ID: " + sid);
				return false;
			} else
				System.out.println("Seat: " + sid);

			Passenger newPass = new Passenger(n, seat);

			if (flight.getPassengers().add(newPass)) {
				System.out.println("Passenger added: " + n);
				flight.getSeats().remove(seat);
				return true;
			} else {
				System.out.println("Failed to add passenger: " + n);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@WebMethod
	public String[] getPassengerList(String fid) {
	    return flights.stream()
	            .filter(f -> f.getId().equals(fid))
	            .flatMap(flight -> flight.getPassengers().stream())
	            .map(passenger -> passenger.getName() + ", " + passenger.getSeat().getId())
	            .toArray(String[]::new);
	}



	public T4WS3Server() {
		flights.add(new Flight("Wien", "London", "WI 123", "13.12.2023"));
		flights.add(new Flight("Wien", "London", "WI 213", "14.12.2023"));
		flights.add(new Flight("Wien", "London", "WI 230", "16.12.2023"));
		flights.add(new Flight("Wien", "Paris", "WI 561", "13.12.2023"));
		flights.add(new Flight("Wien", "Rom", "WI 852", "15.12.2023"));
		flights.add(new Flight("Wien", "Chicago", "WI 369", "17.12.2023"));
		flights.add(new Flight("Wien", "Warsaw", "WI 456", "13.12.2023"));
		flights.add(new Flight("Wien", "New York", "WI 753", "19.12.2023"));
	}

	public static void main(String[] args) {
		Endpoint endpoint = Endpoint.publish("http://localhost:4711/T4WS3Service", new T4WS3Server());
		System.out.println("Eingabetaste drücken zum Beenden des Server");
		// http://localhost:4711/T4WS3Service?wsdl -> here we see the generated XML
		try {
			System.in.read();
		} catch (IOException e) {
		}
		endpoint.stop();

	}

}
