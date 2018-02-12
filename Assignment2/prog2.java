/* ******************************************
*  Yashit Maheshwary (2016123)
*  Kaustav Vats (2016048)
******************************************* */

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;


class Transaction implements Runnable {

	private Flight f1, f2;
	private int passengerId;
	private int option;
	public static int transaction_count = 0;

	public Transaction(){
		this.option = -1;
	}

	public void putOption(int i)
	{
		this.option = i;
	}

	public void putFlight1(Flight f1)
	{
		this.f1 = f1;
	}
	public void putFlight2(Flight f2)
	{
		this.f2 = f2;
	}
	public void putPassengerId(int i)
	{
		this.passengerId = i;
	}
	@Override
	public void run() {

		if(option == 1) {
			Reserve(f1, passengerId);
			
		}
		else if(option == 2) {
			Cancel(f1, passengerId);
				
		}
		else if(option == 3) {
			My_Flights(passengerId);
		}
		else if(option == 4) {
			Total_Reservations();
		}
		else if(option == 5) {
			
			Transfer(f1, f2, passengerId);
		}
		else {
			System.out.println("Invalid Condition");
		}
		transaction_count++;
	}

	/**
	 * Function to reserve a seat in a flight for a passenger
	 */
	public void Reserve(Flight flight, int passengerId) {
		int i = prog2.flights.indexOf(flight);
		int j = passengerId;
		prog2.flights_lock.get(i).lock();
		prog2.passengers_lock.get(j).lock();
		try{
			if(flight.book(passengerId)) {
				prog2.passengers.get(passengerId).addBookedFlight(flight);
			}
		}
		finally {
			prog2.passengers_lock.get(j).unlock();
			prog2.flights_lock.get(i).unlock();
		}
	}

	/**
	 * Function to cancel a reservation of a flight for a passenger
	 */
	public void Cancel(Flight flight, int passengerId) {
		int i = prog2.flights.indexOf(flight);
		int j = passengerId;
		prog2.flights_lock.get(i).lock();
		prog2.passengers_lock.get(j).lock();
		try {
			if(flight.cancel(passengerId)) {
				prog2.passengers.get(passengerId).removeBookedFlight(flight);
			}
			
		}
		finally {
			prog2.passengers_lock.get(j).unlock();
			prog2.flights_lock.get(i).unlock();
		}
	}

	/**
	 * Function to print the flights of a particular passenger
	 */
	public void My_Flights(int passengerId) {
		int j = passengerId;
		prog2.passengers_lock.get(j).lock();
		try {
			prog2.passengers.get(passengerId).getAllFlights();
		}
		finally {
			prog2.passengers_lock.get(j).unlock();
		}
	}

	/**
	 * Function to print the total number of reservations made in all the flights
	 */
	public void Total_Reservations() {

		int totalReservations = 0;
		for(Flight flight: Main.flights) {
			int i = prog2.flights.indexOf(flight);
			prog2.flights_lock.get(i).lock();
			try {
				if(flight.getNumReserved() > 0) {
					totalReservations += flight.getNumReserved();
				}
			}
			finally {
				prog2.flights_lock.get(i).unlock();
			}
		}
		System.out.println("Total Number of Reservations are: " + String.valueOf(totalReservations));
		
	}

	/**
	 * Function to Transfer the user from one flight to the other
	 */
	public void Transfer(Flight f1, Flight f2, int passengerId) {
		int i = prog2.flights.indexOf(f1);
		int k = prog2.flights.indexOf(f2);
		int j = passengerId;
		prog2.flights_lock.get(i).lock();
		prog2.flights_lock.get(k).lock();
		prog2.passengers_lock.get(j).lock();
		try {
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
		finally {
			prog2.flights_lock.get(i).unlock();
			prog2.flights_lock.get(k).unlock();
			prog2.passengers_lock.get(j).unlock();
		}
	}
}

public class prog2 {

	public static ArrayList<Flight> flights = new ArrayList<>();
	public static ArrayList<Passenger> passengers = new ArrayList<>();
	public static ReentrantLock lock = new ReentrantLock();
	public static ArrayList<ReentrantLock> flights_lock = new ArrayList<ReentrantLock>();
	public static ArrayList<ReentrantLock> passengers_lock = new ArrayList<ReentrantLock>();
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

	// public static void serialize(Main main) throws IOException {
	// 	ObjectOutputStream out = null;
	// 	try {
	// 		out = new ObjectOutputStream(new FileOutputStream("Program_1_data.txt"));
	// 		out.writeObject(main);
	// 	}
	// 	finally {
	// 		out.close();
	// 	}
	// }

	// public static Main deserialize() throws IOException, ClassNotFoundException {
	// 	ObjectInputStream in = null;
	// 	try {
	// 		in = new ObjectInputStream(new FileInputStream("Program_1_data.txt"));
	// 		Main a = (Main) in.readObject();
	// 		return a;
	// 	}
	// 	finally {
	// 		in.close();
	// 	}
	// }

	public static void main(String[] args) throws IOException,  InterruptedException {
		long start_time = System.currentTimeMillis();
		prog2 Database = new prog2();
		ArrayList<Flight> flights_local =prog2.getFlights();
		ArrayList<Passenger> passengers_local = prog2.getPassengers();

		for(int i=0; i<=10; i++) {
			flights_local.add(new Flight(i,2));
			passengers_local.add(new Passenger(i));
			flights_lock.add(new ReentrantLock());
			passengers_lock.add(new ReentrantLock());
		}
		int counter = 0;

		ExecutorService exec = Executors.newFixedThreadPool(3);

		long wait_time = 10000;
		long end_time = start_time + wait_time;


		while(System.currentTimeMillis() < end_time) {

			int randomNum = getRand(1,5);
			System.out.println("randomNum: "+randomNum);

			int randomFlight = getRand(0, flights_local.size()-1);
			Flight selectedFlight = flights_local.get(randomFlight);

			int randomPassenger = getRand(0, passengers_local.size()-1);
			Passenger selectedPassenger = passengers_local.get(randomPassenger);

			int randomFlight2 = getRand(0, flights.size() - 1);
			Flight selectedFlight2 = flights_local.get(randomFlight);
			
			Transaction transaction = new Transaction();
			transaction.putOption(randomNum);
			transaction.putFlight1(selectedFlight);
			transaction.putFlight2(selectedFlight2);
			transaction.putPassengerId(selectedPassenger.getId());

			exec.execute(transaction);


			// if(counter == 10) {
				System.out.println("Transaction: "+Transaction.transaction_count);
			// 	break;
			// }
			// counter += 1;
		}
		System.out.println("finished");
		if ( !exec.isTerminated() )
		{
			exec.shutdownNow();
			// exec.awaitTermination(5L, TimeUnit.SECONDS);
		}	
	}
}