package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class Customer extends User{
    private String phoneNumber;
    private String email;
    private String address;
    private String billingAddress;
    private LocalDate dob;
    private Trip[] bookedTrips;
    
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
    public Customer(String firstName, String lastName, String phoneNumber, String email, String address, String billingAddress, String dob, Trip[] bookedTrips, int id) {
        super(firstName, lastName, id);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.billingAddress = billingAddress;
        this.bookedTrips = bookedTrips;
        //parse the string from the database into a LocalDate variable type
        this.dob = LocalDate.parse(dob);
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
    public Customer(String firstName, String lastName, String phoneNumber, String email, String address, String billingAddress, int year, int month, int day) {
        super(firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.billingAddress = billingAddress;
        //take three inputed values and turn them into a LocalDate variable type
        this.dob =LocalDate.of(year, month, day);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    
    
}
