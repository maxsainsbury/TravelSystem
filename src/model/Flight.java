package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class Flight {
    
    private String airline;
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private String seatClass;
    private String status;
    private int tripId;
    private int flightId;
    
    
    /**
     * Constructor for a flight class when getting the information from the database
     * @param airLine
     * @param flightNumber
     * @param departureTime
     * @param arrivalTime
     * @param price
     * @param seatClass
     * @param status
     * @param tripId
     * @param flightId 
     */
    public Flight(String airLine, String flightNumber, String departureTime, String arrivalTime, double price, String seatClass, String status, int tripId, int flightId) {
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.price = price;
        this.seatClass = seatClass;
        this.status = status;
        this.tripId = tripId;
        this.flightId = flightId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
        //take the departure and arrival time parameters and turn them into a LocalTime value
        this.departureTime = LocalDateTime.parse(departureTime, formatter);
        this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter);
    }
    
    /**
     * Constructor for a Flight object for when a new flight is created from the new flight window
     * @param airLine
     * @param flightNumber
     * @param departureTime
     * @param arrivalTime
     * @param departureCity
     * @param destinationCity
     * @param price
     * @param seatClass
     * @param staturs
     * @param tripId 
     */
    public Flight(String airline, String flightNumber, String departureTime, String arrivalTime, double price, String seatClass, String staturs, int tripId) {
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.price = price;
        this.seatClass = seatClass;
        this.status = status;
        this.tripId = tripId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //take the departure and arrival time parameters and turn them into a LocalTime value
        this.departureTime = LocalDateTime.parse(departureTime, formatter);
        this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter);
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    
    
}
