import java.util.ArrayList;
import java.util.Scanner;

interface Bookable {
    void bookTicket();
    void cancelTicket();
}

class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private int availableSeats;
    private double ticketPrice;

    Flight(String flightNumber, String source, String destination, int availableSeats, double ticketPrice) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public boolean reserveSeats(int count) {
        if (count <= 0 || count > availableSeats) {
            return false;
        }
        availableSeats -= count;
        return true;
    }

    public void releaseSeats(int count) {
        availableSeats += count;
    }

    public void displayDetails() {
        System.out.printf("%-10s %-15s %-15s %-8d Rs.%.2f%n",
                flightNumber, source, destination, availableSeats, ticketPrice);
    }
}

class DomesticFlight extends Flight {
    private String classType;

    DomesticFlight(String flightNumber, String source, String destination,
                   int availableSeats, double ticketPrice, String classType) {
        super(flightNumber, source, destination, availableSeats, ticketPrice);
        this.classType = classType;
    }

    @Override
    public void displayDetails() {
        System.out.printf("%-10s %-15s %-15s %-8d Rs.%.2f  %s%n",
                getFlightNumber(), getSource(), getDestination(),
                getAvailableSeats(), getTicketPrice(), classType);
    }
}

class Booking implements Bookable {
    private static int nextBookingId = 1001;
    private int bookingId;
    private String passengerName;
    private Flight flight;
    private int seats;
    private boolean active;

    Booking(String passengerName, Flight flight, int seats) {
        this.bookingId = nextBookingId++;
        this.passengerName = passengerName;
        this.flight = flight;
        this.seats = seats;
        this.active = true;
    }

    public int getBookingId() {
        return bookingId;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void bookTicket() {
        System.out.println("Booking confirmed.");
    }

    @Override
    public void cancelTicket() {
        if (active) {
            flight.releaseSeats(seats);
            active = false;
            System.out.println("Booking " + bookingId + " cancelled successfully.");
        } else {
            System.out.println("This booking is already cancelled.");
        }
    }

    public void displayBooking() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Passenger: " + passengerName);
        System.out.println("Flight: " + flight.getFlightNumber());
        System.out.println("Route: " + flight.getSource() + " to " + flight.getDestination());
        System.out.println("Seats: " + seats);
        System.out.printf("Total amount: Rs.%.2f%n", seats * flight.getTicketPrice());
        System.out.println("Status: " + (active ? "Confirmed" : "Cancelled"));
        System.out.println("------------------------------");
    }
}

class SeatBookingException extends Exception {
    SeatBookingException(String message) {
        super(message);
    }
}

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Flight> flights = new ArrayList<>();
    private static final ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        addSampleFlights();
        int choice;

        do {
            System.out.println("\n===== FLIGHT RESERVATION SYSTEM =====");
            System.out.println("1. View flights");
            System.out.println("2. Search flights");
            System.out.println("3. Book ticket");
            System.out.println("4. View bookings");
            System.out.println("5. Cancel booking");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            while (!input.hasNextInt()) {
                System.out.print("Enter a number from 1 to 6: ");
                input.next();
            }
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    showFlights();
                    break;
                case 2:
                    searchFlights();
                    break;
                case 3:
                    bookTicket();
                    break;
                case 4:
                    showBookings();
                    break;
                case 5:
                    cancelBooking();
                    break;
                case 6:
                    System.out.println("Thank you for using Flight Reservation System.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);

        input.close();
    }

    private static void addSampleFlights() {
        flights.add(new DomesticFlight("AI101", "Chennai", "Delhi", 30, 4500, "Economy"));
        flights.add(new DomesticFlight("AI202", "Coimbatore", "Mumbai", 25, 3800, "Economy"));
        flights.add(new DomesticFlight("AI303", "Bengaluru", "Hyderabad", 20, 2500, "Business"));
    }

    private static void showFlights() {
        System.out.println("\nFlight No  Source          Destination     Seats    Price       Class");
        System.out.println("---------------------------------------------------------------------");
        for (Flight flight : flights) {
            flight.displayDetails();
        }
    }

    private static void searchFlights() {
        System.out.print("Enter source city: ");
        String source = input.nextLine().trim();
        System.out.print("Enter destination city: ");
        String destination = input.nextLine().trim();

        boolean found = false;
        for (Flight flight : flights) {
            if (flight.getSource().equalsIgnoreCase(source)
                    && flight.getDestination().equalsIgnoreCase(destination)) {
                if (!found) {
                    System.out.println("\nMatching flights:");
                    System.out.println("Flight No  Source          Destination     Seats    Price       Class");
                }
                flight.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching flight found.");
        }
    }

    private static void bookTicket() {
        showFlights();
        System.out.print("Enter flight number: ");
        String flightNumber = input.nextLine().trim();
        Flight selectedFlight = findFlight(flightNumber);

        if (selectedFlight == null) {
            System.out.println("Flight number not found.");
            return;
        }

        System.out.print("Enter passenger name: ");
        String passengerName = input.nextLine().trim();
        if (passengerName.isEmpty()) {
            System.out.println("Passenger name cannot be empty.");
            return;
        }

        System.out.print("Enter number of seats: ");
        if (!input.hasNextInt()) {
            System.out.println("Please enter a valid whole number.");
            input.nextLine();
            return;
        }
        int seats = input.nextInt();
        input.nextLine();

        try {
            if (seats <= 0 || !selectedFlight.reserveSeats(seats)) {
                throw new SeatBookingException("Requested seats are not available.");
            }

            Booking booking = new Booking(passengerName, selectedFlight, seats);
            bookings.add(booking);
            booking.bookTicket();
            booking.displayBooking();
        } catch (SeatBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    private static Flight findFlight(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    private static void showBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Booking booking : bookings) {
            booking.displayBooking();
        }
    }

    private static void cancelBooking() {
        System.out.print("Enter booking ID: ");
        if (!input.hasNextInt()) {
            System.out.println("Please enter a valid booking ID.");
            input.nextLine();
            return;
        }
        int bookingId = input.nextInt();
        input.nextLine();

        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                booking.cancelTicket();
                return;
            }
        }
        System.out.println("Booking ID not found.");
    }
}
