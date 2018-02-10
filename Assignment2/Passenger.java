/* ******************************************
*  Yashit Maheshwary (2016123)
*  Kaustav Vats (2016048)
******************************************* */

import java.io.*;
import java.util.*;

public class Passenger implements Serializable {

	private volatile int id;
	private volatile ArrayList<Flight> bookedFlights;

	// Constructor

	public Passenger(int id) {
		this.id = id;
		bookedFlights = new ArrayList<>();
	}

	// Getters and Setters

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the bookedFlights
	 */
	public ArrayList<Flight> getBookedFlights() {
		return bookedFlights;
	}

	/**
	 * @param bookedFlights the bookedFlights to set
	 */
	public void setBookedFlights(ArrayList<Flight> bookedFlights) {
		this.bookedFlights = bookedFlights;
	}

	// Functions

	/**
	 * Function to add a booked flight for a passenger
	 */
	public void addBookedFlight(Flight flight) {
		this.bookedFlights.add(flight);
	}

	/**
	 * Function to remove a booked flight for a passenger
	 */
	public void removeBookedFlight(Flight flight) {
		if(this.bookedFlights.contains(flight)) {
			this.bookedFlights.remove(flight);
		}
		else {
			System.out.println("The passenger hasn't booked this Flight.");
			
		}
	}

	public void getAllFlights() {
		System.out.println("Flight Details: ");
		
		if(this.getBookedFlights().size() > 0) {
			for(Flight flight : this.getBookedFlights()) {
				System.out.println(flight.getId());
			}
		}
		else {
			System.out.println("No Flights Booked");
		}
	}
	
}