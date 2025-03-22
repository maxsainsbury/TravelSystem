package model;

/**
 * Class holds represents data for trip and booking
 * @author Goen Choi
 */
public class TripAndBooking {
    private Booking bookingObject;
    private Trip tripObject;
    
    public TripAndBooking(Booking bookingObject, Trip tripObject) {
        this.bookingObject = bookingObject;
        this.tripObject = tripObject;
    }

    public Booking getBookingObject() {
        return bookingObject;
    }

    public void setBookingObject(Booking bookingObject) {
        this.bookingObject = bookingObject;
    }

    public Trip getTripObject() {
        return tripObject;
    }

    public void setTripObject(Trip tripObject) {
        this.tripObject = tripObject;
    }
    
    
}
