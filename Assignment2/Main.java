/* ******************************************
*  Yashit Maheshwary (2016123)
*  Kaustav Vats (2016048)
******************************************* */

import java.io.*;
import java.util.*;

class Transaction implements Runnable {

	private ArrayList<Flight> flights;
	private ArrayList<Passenger> passengers;

	public Transaction(){
		this.flights = Main.getFlights();
		this.passengers = Main.getPassengers();
	}
	/**
	 * Function to reserve a seat in a flight for a passenger
	 */
	public void Reserve(Flight flight, int passengerId) {
		if(flight.book(passengerId)) {
			passengers.get(passengerId).addBookedFlight(flight);
		}
	}

	/**
	 * Function to cancel a reservation of a flight for a passenger
	 */
	public void Cancel(Flight flight, int passengerId) {
		if(flight.cancel(passengerId)) {
			passengers.get(passengerId).removeBookedFlight(flight);
		}
	}

	/**
	 * Function to print the flights of a particular passenger
	 */
	public void My_Flights(int passengerId) {
		passengers.get(passengerId).getAllFlights();
	}

	/**
	 * Function to print the total number of reservations made in all the flights
	 */
	public void Total_Reservations() {
		int totalReservations = 0;
		for(Flight flight: flights) {
			if(flight.getNumReserved() > 0) {
				totalReservations += flight.getNumReserved();
			}
		}
		System.out.println("Total Number of Reservations are: " + String.valueOf(totalReservations));
		
	}

	/**
	 * Function to Transfer the user from one flight to the other
	 */
	public void Transfer(Flight f1, Flight f2, int passengerId) {
		if(f1.getPassenger(passengerId) != null) {
			if(f2.getPassengers().size() < f2.getSeats()) {
				f1.cancel(passengerId);
				f2.book(passengerId);
			}
			else {
				System.out.println("No more seats available");
			}
		}
		else {
			System.out.println("Passenger hasn't booked this flight");
		}
	}
}

public class Main implements Serializable {

	public static ArrayList<Flight> flights = new ArrayList<>();
	public static ArrayList<Passenger> passengers = new ArrayList<>();
	// private ArrayList<Flight> flights = new ArrayList<>();
	// private ArrayList<Passenger> passengers = new ArrayList<>();

	private static int getRand(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public static ArrayList<Flight> getFlights() {
		return flights;
	}

	public static ArrayList<Passenger> getPassengers() {
		return passengers;
	}

	public static void serialize(Main main) throws IOException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("Program_1_data.txt"));
			out.writeObject(main);
		}
		finally {
			out.close();
		}
	}

	public static Main deserialize() throws IOException, ClassNotFoundException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream("Program_1_data.txt"));
			Main a = (Main) in.readObject();
		}
		finally {
			in.close();
		}
		return a;
	}

	public void 

	public static void main(String[] args) throws IOException {
		
		Main Database = new Main();
		ArrayList<Flight> flights_local = Main.getFlights();
		ArrayList<Passenger> passengers_local = Main.getPassengers();

		for(int i=0; i<=10; i++) {
			flights_local.add(new Flight(i,2));
			passengers_local.add(new Passenger(i));
		}
		int counter = 0;

		ExecutorService exec = Executors.newFixedThreadPool(3);

		while(true) {

			int randomNum = getRand(1,5);
			System.out.println("randomNum: "+randomNum);

			int randomFlight = getRand(0, flights_local.size()-1);
			Flight selectedFlight = flights_local.get(randomFlight);

			int randomPassenger = getRand(0, passengers_local.size()-1);
			Passenger selectedPassenger = passengers_local.get(randomPassenger);
			
			Transaction transaction = new Transaction();

			if(randomNum == 1) {
				transaction.Reserve(selectedFlight, selectedPassenger.getId());
			}
			else if(randomNum == 2) {
				transaction.Cancel(selectedFlight, selectedPassenger.getId());
			}
			else if(randomNum == 3) {
				transaction.My_Flights(selectedPassenger.getId());
			}
			else if(randomNum == 4) {
				transaction.Total_Reservations();
			}
			else if(randomNum == 5) {
				int randomFlight2 = getRand(0, flights.size() - 1);
				Flight selectedFlight2 = flights_local.get(randomFlight);
				transaction.Transfer(selectedFlight, selectedFlight2, selectedPassenger.getId());
			}
			else {
				System.out.println("Invalid Condition");
			}

			if(counter == 10) {
				break;
			}
			counter += 1;
		}	
	}
}