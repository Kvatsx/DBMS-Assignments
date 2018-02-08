/* ******************************************
*  Yashit Maheshwary (2016123)
*  Kaustav (2016048)
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
		// Get all reservations in all flights
	}

	public void Transfer(Flight f1, Flight f2, int passengerId) {
		// Replace passenger from flight f1 to f2
	}
}

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String[] inputString = input.readLine().split(" ");
		
	}
}