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
import java.text.SimpleDateFormat;
import java.util.Date;


class Transaction implements Runnable {

	private Flight f1, f2;
	private int passengerId;
	public static volatile int transaction_count = 0;
	private int option;
	private ReentrantLock counterLock = new ReentrantLock();	

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

	private static void sleep() {
		try {
			Thread.sleep(5);
		} 
		catch (InterruptedException e) {
		}
	}

	@Override
	public void run() {
		try {
			Main.lock.tryLock(100L, TimeUnit.MILLISECONDS);
            // System.out.println("DB Locked for Transaction: " + transaction_count);
			try {
				counterLock.tryLock(100L, TimeUnit.MILLISECONDS);
				transaction_count++;
			} catch (InterruptedException e) {

			} finally {
				counterLock.unlock();
			}
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
			sleep();

		}
		catch (InterruptedException e) {

		}
		finally {
			Main.lock.unlock();
			// System.out.println("Lock Released for Transaction: " + String.valueOf(transaction_count-1));
		}
	}

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
	public static ReentrantLock lock = new ReentrantLock();

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

	public static void main(String[] args) throws IOException,  InterruptedException {
			

		Main Database = new Main();
		ArrayList<Flight> flights_local = Main.getFlights();
		ArrayList<Passenger> passengers_local = Main.getPassengers();

		for(int i=0; i<=10; i++) {
			flights_local.add(new Flight(i,2));
			passengers_local.add(new Passenger(i));

		}
		int counter = 0;

		ExecutorService exec = Executors.newFixedThreadPool(1);
		long start_time = System.currentTimeMillis();
		long wait_time = 10000;
		long end_time = start_time + wait_time;

		while(System.currentTimeMillis() < end_time) {

			int randomNum = getRand(1,5);
			// System.out.println("randomNum: "+randomNum);

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
		}
		System.out.println("Transaction Count: " + Transaction.transaction_count);
		if (!exec.isTerminated()) {
			exec.shutdownNow();
		}	
	}
}