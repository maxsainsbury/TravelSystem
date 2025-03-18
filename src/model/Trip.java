package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class Trip {
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String status;
    private int promotionId;
    private int tripId;
    
    /**
     * Constructor for a Trip object for when getting information from the database
     * @param origin
     * @param destination
     * @param departureDate
     * @param returnDate
     * @param promotionId
     * @param tripId 
     */
    public Trip (String origin, String destination, String departureDate, String returnDate, int promotionId, String status, int tripId) {
        this.origin = origin;
        this.destination = destination;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.departureDate = LocalDate.parse(departureDate, formatter);
        this.returnDate = LocalDate.parse(returnDate, formatter);
        this.promotionId = promotionId;
        this.status = status;
        this.tripId = tripId;
    }
    
    /**
     * Constructor for a Trip object for when making one in the GUI
     * @param origin
     * @param destination
     * @param departureYear
     * @param departureMonth
     * @param departureDay
     * @param returnYear
     * @param returnMonth
     * @param returnDay 
     */
    public Trip(String origin, String destination, String departureDate, String returnDate, int promotionId, String status) {
        this.origin = origin;
        this.destination = destination;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.departureDate = LocalDate.parse(departureDate, formatter);
        this.returnDate = LocalDate.parse(returnDate, formatter);
        this.promotionId = promotionId;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    
    
    
}
