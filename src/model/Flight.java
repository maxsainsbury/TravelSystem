package model;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        //set up a DateTimeFormatter to tell the program how to parse the value passed by the datetime column in the database
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //parse the value in the departure_date column and store it as a LocalDateTime
        this.departureTime = LocalDateTime.parse(departureTime , formatter);
        this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter);
    }
    
    /**
     * Constructor for a Flight object for when a new flight is created from the new flight window
     * 
     * @param airLine the air line name
     * @param duration total time of flight in minutes
     * @param departureCity city the flight is leaving from
     * @param destinationCity city the flight will land in
     * @param price price of the flight
     * @param year year the flight departs
     * @param month month the flight departs
     * @param day day the flight departs
     * @param hour hour the flight departs
     * @param minute minute the flight departs
     */
    public Flight(String airLine, int duration, String departureCity, String destinationCity, double price, int tripId, int departureYear, int departureMonth, int departureDay, int departureHour, int departureMinute, int arrivalYear, int arrivalMonth, int arrivalDay, int arrivalHour, int arrivalMinute) {
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.price = price;
        this.seatClass = seatClass;
        this.status = status;
        this.tripId = tripId;
        //take the year, month, day, hour, and minute parameters and turn them into a LocalDateTime
        this.departureTime = LocalDateTime.of(departureYear, departureMonth, departureDay, departureHour, departureMinute);
        this.arrivalTime = LocalDateTime.of(arrivalYear, arrivalMonth, arrivalDay, arrivalHour, arrivalMinute);
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
