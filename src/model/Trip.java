package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class Trip {
    private int customerId;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private int promotionId;
    private int tripId;
    
    /**
     * Constructor for a Trip object for when getting information from the database
     * 
     * @param flightId
     * @param promoId
     * @param tripId 
     */
    public Trip (int customerId, String origin, String destination, String departureDate, String returnDate, int promotionId, int tripId) {
        this.customerId = customerId;
        this.origin = origin;
        this.destination = destination;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.departureDate = LocalDate.parse(departureDate, formatter);
        this.returnDate = LocalDate.parse(returnDate, formatter);
        this.promotionId = promotionId;
        this.tripId = tripId;
    }
    
    /**
     * Constructor for a Trip object for when making one in the GUI
     * 
     * @param flightId
     * @param promoId 
     */
    public Trip(int customerId, String origin, String destination, int departureYear, int departureMonth, int departureDay, int returnYear, int returnMonth, int returnDay) {
        this.customerId = customerId;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = LocalDate.of(departureYear, departureMonth, departureDay);
        this.returnDate = LocalDate.of(returnYear, returnMonth, returnDay);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    
    
    
}
