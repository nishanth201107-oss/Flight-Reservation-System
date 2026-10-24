# Flight Reservation System

A simple Java console application for managing flight reservations. This project is created for an OOPS lab/project submission.

## Features

- Display available flights
- Search flights using source and destination
- Book tickets for a passenger
- Calculate the total ticket amount
- View all booking details
- Cancel a booking and return seats to availability
- Validate seat count and handle booking errors

## OOPS concepts used

- **Class and object:** `Flight`, `Booking`, and `DomesticFlight`
- **Encapsulation:** Flight and booking data is stored in private fields and accessed through methods
- **Constructor:** Objects are initialized using constructors
- **Inheritance:** `DomesticFlight` extends `Flight`
- **Method overriding:** `DomesticFlight` overrides `displayDetails()`
- **Interface:** `Booking` implements `Bookable`
- **Exception handling:** `SeatBookingException` handles invalid seat bookings
- **Static members:** Booking IDs are generated using a static counter
- **Collections:** `ArrayList` stores flights and bookings
- **Loops and conditions:** Used for menus, searching, booking, and cancellation

## Requirements

- Java JDK 8 or later
- Command Prompt, terminal, VS Code, or another Java IDE

## How to run

1. Download or clone this repository.
2. Open the project folder in a terminal.
3. Compile the program:

   ```bash
   javac Main.java
   ```

4. Run the program:

   ```bash
   java Main
   ```

## Sample flight data

| Flight No. | Source | Destination | Starting seats | Price |
|---|---|---|---:|---:|
| AI101 | Chennai | Delhi | 30 | Rs. 4500 |
| AI202 | Coimbatore | Mumbai | 25 | Rs. 3800 |
| AI303 | Bengaluru | Hyderabad | 20 | Rs. 2500 |

## Project note

This is a learning project with sample flight data stored in the program. It does not connect to a real airline or payment gateway.
