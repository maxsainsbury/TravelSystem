package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class Flight {
    
    private String airLine;
    private int duration;
    private String departureCity;
    private String destinationCity;
    private double price;
    private int flightId;
    private LocalDateTime departureDate;
    
    
    /**
     * Constructor for a Flight object for when a flight is grabbed from the database
     * 
     * @param airLine the air lines name
     * @param duration total time of flight in minutes
     * @param departureCity city the flight is leaving from
     * @param destinationCity city the flight will land in
     * @param price price of the flight
     * @param flightId id of the flight in the database
     * @param departureDate the departure date of the flight (formatted in YYYY-MM-dd HH-mm-ss format)
     */
    public Flight(String airLine, int duration, String departureCity, String destinationCity, double price, int flightId, String departureDate) {
        this.airLine = airLine;
        this.duration = duration;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.price = price;
        this.flightId = flightId;
        //set up a DateTimeFormatter to tell the program how to parse the value passed by the datetime column in the database
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //parse the value in the departure_date column and store it as a LocalDateTime
        this.departureDate = LocalDateTime.parse(departureDate , formatter);
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
    public Flight(String airLine, int duration, String departureCity, String destinationCity, double price, int year, int month, int day, int hour, int minute) {
        this.airLine = airLine;
        this.duration = duration;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        //take the year, month, day, hour, and minute parameters and turn them into a LocalDateTime
        this.departureDate = LocalDateTime.of(year, month, day, hour, minute);
        //random flight id, must be change to first not used id when adding to the database
        this.flightId = 0;
    }

    public String getAirLine() {
        return airLine;
    }

    public void setAirLine(String airLine) {
        this.airLine = airLine;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }
    
    
    
    
    
}
