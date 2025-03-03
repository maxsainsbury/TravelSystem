package model;

/**
 *
 * @author Max Sainsbury
 */
public class Trip {
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
    public Trip (int flightId, int promoId, int tripId) {
        this(flightId, promoId);
        this.tripId = tripId;
    }
    
    /**
     * Constructor for a Trip object for when making one in the GUI
     * 
     * @param flightId
     * @param promoId 
     */
    public Trip(int flightId, int promoId) {
        this.flightId = flightId;
        this.promoId = promoId;
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
