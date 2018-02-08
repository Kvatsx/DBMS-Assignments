/* ******************************************
*  Yashit Maheshwary (2016123)
*  Kaustav Vats (2016048)
******************************************* */

import java.io.*;
import java.util.*;

class Transaction {
	public void Reserve(Flight flight, int passengerId) {
		flight.book(passengerId);
	}

	public void Cancel(Flight flight, int passengerId) {
		flight.cancel(passengerId);
	}

	public void My_Flights(int passengerId) {
		// Get all flights of a passenger
	}

	public void Total_Reservations() {
		int totalReservations = 0;
		for(Flight flight: Main.flights) {
			if(flight.getNumReserved() > 0) {
				totalReservations += flight.getNumReserved();
			}
		}
	}

	public void Transfer(Flight f1, Flight f2, int passengerId) {
		// Replace passenger from flight f1 to f2
	}
}

public class Main {


	public static ArrayList<Flight> flights = new ArrayList<>();

	public static void main(String[] args) throws IOException {

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

		Transaction transaction = new Transaction();
		transaction.Total_Reservations();


	}
}