/* ******************************************
*  Yashit Maheshwary (2016123)
*  Kaustav Vats (2016048)
******************************************* */

import java.io.*;
import java.util.*;

class Transaction1 {

	/**
	 * Function to reserve a seat in a flight for a passenger
	 */
	public void Reserve(Flight flight, int passengerId) {
		flight.book(passengerId);
	}

	/**
	 * Function to cancel a reservation of a flight for a passenger
	 */
	public void Cancel(Flight flight, int passengerId) {
		flight.cancel(passengerId);
	}

	/**
	 * Function to print the flights of a particular passenger
	 */
	public void My_Flights(int passengerId) {
		// Get all flights of a passenger
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
		// Replace passenger from flight f1 to f2
	}
}

public class Main {


	public static ArrayList<Flight> flights = new ArrayList<>();

	public static void main(String[] args) throws IOException, InterruptedException {

		flights.add(new Flight(1, 10));
		flights.add(new Flight(2, 10));
		flights.add(new Flight(3, 10));
		flights.add(new Flight(4, 10));
		flights.add(new Flight(5, 10));
		flights.add(new Flight(6, 10));
		flights.add(new Flight(7, 10));
		flights.add(new Flight(8, 10));
		flights.add(new Flight(9, 10));
		flights.add(new Flight(10, 10));

		Passenger p1 = new Passenger(1);
		Passenger p2 = new Passenger(2);
		Passenger p3 = new Passenger(3);
		Passenger p4 = new Passenger(4);
		Passenger p5 = new Passenger(5);

		Transaction1 transaction = new Transaction1();
		transaction.Reserve(flights.get(0), 1);
		transaction.Total_Reservations();
		transaction.Cancel(flights.get(0), 1);
		transaction.Total_Reservations();

	}
}