package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class Booking {
    private int bookingId;
    private int employeeId;
    private LocalDate bookingDate;
    private int customerId;
    private int tripId;
    private double totalPrice;
    
    /**
     * Constructor for Booking object for when grabbing information from the database
     * @param customerId
     * @param tripId
     * @param totalPrice
     * @param bookingDate
     * @param bookingId
     * @param employeeId 
     */
    public Booking(int customerId, int tripId, double totalPrice, String bookingDate, int bookingId, int employeeId) {
        this.customerId = customerId;
        this.tripId = tripId;
        this.totalPrice = totalPrice;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.bookingDate = LocalDate.parse(bookingDate, formatter);
        this.bookingId = bookingId;
        this.employeeId = employeeId;
    }
    
    /**
     * Constructor for when employee is creating a new Trip object
     * @param customerId
     * @param tripId
     * @param totalPrice
     * @param year
     * @param month
     * @param day
     * @param employeeId 
     */
    public Booking(int customerId, int tripId, double totalPrice, int year, int month, int day, int employeeId){
        this(customerId, tripId, totalPrice, year, month, day);
        this.employeeId = employeeId;
    }
    
    /**
     * Constructor for Booking object for when customer is creating a new object
     * @param customerId
     * @param tripId
     * @param totalPrice
     * @param year
     * @param month
     * @param day 
     */
    public Booking(int customerId, int tripId, double totalPrice, int year, int month, int day) {
        this.customerId = customerId;
        this.tripId = tripId;
        this.totalPrice = totalPrice;
        this.bookingDate = LocalDate.of(year, month, day);
        
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
