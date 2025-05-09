package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Max Sainsbury
 */
public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private String phone;
    private String unitNumber;
    private String streetAddress;
    private String city;
    private String country;
    private String postalCode;
    private String userType;
    private int userId;
    
    
    
    /**
     * Constructor for the user class, use as a base constructor for both public constructors to get rid of repeated code
     * @param username
     * @param firstName
     * @param lastName
     * @param dob
     * @param email
     * @param phone
     * @param unitNumber
     * @param streetAddress
     * @param city
     * @param country
     * @param postalCode 
     */
    private User(String firstName, String lastName, String email, String phone, String unitNumber, String streetAddress, String city, String country, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.unitNumber = unitNumber;
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        
    }
    
    /**
     * Constructor for the User class when the id is known
     * use when getting from the database
     * @param username
     * @param firstName
     * @param lastName
     * @param dob
     * @param email
     * @param phone
     * @param unitNumber
     * @param streetAddress
     * @param city
     * @param country
     * @param postalCode
     * @param userId 
     */

    public User(String firstName, String lastName, String email, String phone, String unitNumber, String streetAddress, String city, String country, String postalCode, String dob, int userId) {
        this(firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode);
        //Set up a formatter to tell the program how to interpret the value given by the date column in the Promotion database
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //turn the value from the database into a LocalDate object
        this.dob = LocalDate.parse(dob, formatter);
        this.userId = userId;
    }
    
    /**
     * Constructor for User class when creating a new user.
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phone
     * @param unitNumber
     * @param streetAddress
     * @param city
     * @param country
     * @param postalCode
     */
    public User(String firstName, String lastName, String email, String phone, String unitNumber, String streetAddress, String city, String country, String postalCode, String dob, String userType, String username, String password) {
        this(firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //turn the value from the database into a LocalDate object
        this.dob = LocalDate.parse(dob, formatter);
        this.userType = userType;
        this.username = username;
        this.password = password;
        
    }
    
    /**
     * Constructor to create new user with username and password.
     * Used when adding new employee or customer. 
     * table in database. 
     * @param username
     * @param password
     * @param userType
     */
    public User(String username, String password, String userType){
        this.username = username;
        this.password = password;
        this.userType = userType;
        
    }
    
    /**
     * Constructor to create user when userId is known.
     * @param userId
     * @param username
     * @param password
     * @param userType 
     */
    public User(int userId, String username, String password, String userType){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = userType;
        
    }
    
    /**
     * Basic constructor to initialize first and last name. 
     * @param firstName
     * @param lastName 
     */
    public User(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    };
    
    public User() {
        this.userId = 0;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }
   
    
}
