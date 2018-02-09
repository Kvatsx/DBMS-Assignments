/* ******************************************
*  Yashit Maheshwary (2016123)
*  Kaustav Vats (2016048)
******************************************* */

import java.io.*;
import java.util.*;

class Transaction {

	/**
	 * Function to reserve a seat in a flight for a passenger
	 */
	public void Reserve(Flight flight, int passengerId) {
		if(flight.book(passengerId)) {
			Main.passengers.get(passengerId).addBookedFlight(flight);
		}
	}

	/**
	 * Function to cancel a reservation of a flight for a passenger
	 */
	public void Cancel(Flight flight, int passengerId) {
		if(flight.cancel(passengerId)) {
			Main.passengers.get(passengerId).removeBookedFlight(flight);
		}
	}

	/**
	 * Function to print the flights of a particular passenger
	 */
	public void My_Flights(int passengerId) {
		Main.passengers.get(passengerId).getAllFlights();
	}

	/**
	 * Function to print the total number of reservations made in all the flights
	 */
	public void Total_Reservations() {
		int totalReservations = 0;
		for(Flight flight: Main.flights) {
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

public class Main {

	public static ArrayList<Flight> flights = new ArrayList<>();
	public static ArrayList<Passenger> passengers = new ArrayList<>();

	private static int getRand(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public static void main(String[] args) throws IOException {
		
		Transaction transaction = new Transaction();

		for(int i=0; i<=10; i++) {
			flights.add(new Flight(i,2));
			passengers.add(new Passenger(i));
		}

		int counter = 0;

		while(true) {

			int randomNum = getRand(1,5);

			int randomFlight = getRand(0, flights.size()-1);
			Flight selectedFlight = flights.get(randomFlight);

			int randomPassenger = getRand(0, passengers.size()-1);
			Passenger selectedPassenger = passengers.get(randomPassenger);
			
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
				Flight selectedFlight2 = flights.get(randomFlight);
				transaction.Transfer(selectedFlight, selectedFlight2, selectedPassenger.getId());
			}
			else {
				System.out.println("Invalid Condition");
			}

			if(counter == 100) {
				break;
			}
			counter += 1;
		}	
	}
}