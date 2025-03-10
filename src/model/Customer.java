package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class Customer extends User{
    private int customerId;
    private static final String userType = "customer";
    
    /**
     * Constructor for Customer object when getting the information from the database
     * 
     * @param firstName first name of Client
     * @param lastName last name of Client
     * @param phoneNumber phone number of Client
     * @param email email of Client
     * @param address 
     * @param billingAddress
     * @param dob
     * @param id 
     */
    public Customer(String username, String firstName, String lastName, String email, String phone, String unitNumber, String streetAddress, String city, String country, String postalCode, String dob, int userId, int customerId) {
        super(username, firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, dob, userId);
        this.customerId = customerId;
    }
    
    
    /**
     * Constructor for Customer object for when making a new client in the GUI
     * missing bookedTrips and id because a new customer will have none
     * 
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param email
     * @param address
     * @param billingAddress
     * @param year
     * @param month
     * @param day 
     */
    public Customer(String username, String firstName, String lastName, String email, String phone, String unitNumber, String streetAddress, String city, String country, String postalCode, int year, int month, int day, String password, String userType) {
        super(username, firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, year, month, day, password, Customer.userType);
        
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUserType() {
        return userType;
    }
    
    
}
