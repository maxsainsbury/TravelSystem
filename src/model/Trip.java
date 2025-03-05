package model;

/**
 *
 * @author Max Sainsbury
 */
public class Trip {
    private int totalTime;
    private int layoverDuration;
    private double totalPrice;
    private int flightId;
    private int promoId;
    private int tripId;
    
    /**
     * Constructor for a Trip object for when getting information from the database
     * 
     * @param flightId
     * @param promoId
     * @param tripId 
     */
    public Trip (int totalTime, int layoverDuration, double totalPrice, int flightId, int promoId, int tripId) {
        this(totalTime, layoverDuration, totalPrice, flightId, promoId);
        this.tripId = tripId;
    }
    
    /**
     * Constructor for a Trip object for when making one in the GUI
     * 
     * @param flightId
     * @param promoId 
     */
    public Trip(int totalTime, int layoverDuration, double totalPrice, int flightId, int promoId) {
        this.totalTime = totalTime;
        this.layoverDuration = layoverDuration;
        this.totalPrice = totalPrice;
        this.flightId = flightId;
        this.promoId = promoId;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getLayoverDuration() {
        return layoverDuration;
    }

    public void setLayoverDuration(int layoverDuration) {
        this.layoverDuration = layoverDuration;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
    
    
}
