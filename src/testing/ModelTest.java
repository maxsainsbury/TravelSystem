package testing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import junit.framework.Assert;
import model.Client;
import model.Employee;
import model.Flight;
import model.Promotion;
import model.Trip;
import org.junit.Test;
import org.junit.Assert.*;
import model.User;
import static org.junit.Assert.assertEquals;


/**
 *
 * @author Max Sainsbury
 */
public class ModelTest {
    
    public ModelTest() {
        
    }
    
    @Test
    public void UserTest() {
        System.out.println("Starting User Model Tests");
        User databaseUser = new User("Jim", "Frank", 34);
        User createUser = new User("Elsa", "Panda");
        
        assertEquals("Jim", databaseUser.getFirstName());
        assertEquals("Frank", databaseUser.getLastName());
        assertEquals(34, databaseUser.getId());
        assertEquals("Elsa", createUser.getFirstName());
        assertEquals("Panda", createUser.getLastName());
        
        createUser.setFirstName("Sophia");
        createUser.setLastName("Sleep");
        createUser.setId(88);
        assertEquals("Sophia", createUser.getFirstName());
        assertEquals("Sleep", createUser.getLastName());
        assertEquals(88, createUser.getId());
        System.out.println("User Model Tests completed with no errors");
    }
    
    @Test
    public void EmployeeTest() {
        System.out.println("Starting employee model tests");
        Employee databaseEmployee = new Employee("Eric", "Kevin", 23);
        Employee createEmployee = new Employee("Sage", "Tyme");
        
        assertEquals("Eric", databaseEmployee.getFirstName());
        assertEquals("Kevin", databaseEmployee.getLastName());
        assertEquals(23, databaseEmployee.getId());
        assertEquals("Sage", createEmployee.getFirstName());
        assertEquals("Tyme", createEmployee.getLastName());
        System.out.println("employee model test completed with no errors");
    }
    
    public void ClientTest() {
        System.out.println("Sterting client model tests");
        Client databaseClient = new Client("Eric", "Kevin", "250-738-3219", "erickevin@gmail.com", "123 Street st", "124 Street st", "1989-02-23", 32);
        Client createClient = new Client("Eric", "Kevin", "250-738-3219", "erickevin@gmail.com", "123 Street st", "124 Street st", 1989, 02, 23);
        
        assertEquals("Eric", databaseClient.getFirstName());
        assertEquals("Kevin", databaseClient.getLastName());
        assertEquals("250-738-3219", databaseClient.getPhoneNumber());
        assertEquals("erickevin@gmail.com", databaseClient.getEmail());
        assertEquals("123 Street st", databaseClient.getAddress());
        assertEquals("124 Street st", databaseClient.getBillingAddress());
        assertEquals(1989, databaseClient.getDob().getYear());
        assertEquals(2, databaseClient.getDob().getMonthValue());
        assertEquals(23, databaseClient.getDob().getDayOfMonth());
        assertEquals(32, databaseClient.getId());
        
        assertEquals("Eric", createClient.getFirstName());
        assertEquals("Kevin", createClient.getLastName());
        assertEquals("250-738-3219", createClient.getPhoneNumber());
        assertEquals("erickevin@gmail.com", createClient.getEmail());
        assertEquals("123 Street st", createClient.getAddress());
        assertEquals("124 Street st", createClient.getBillingAddress());
        assertEquals(1989, createClient.getDob().getYear());
        assertEquals(2, createClient.getDob().getMonthValue());
        assertEquals(23, createClient.getDob().getDayOfMonth());
        
        createClient.setFirstName("Elsa");
        createClient.setLastName("Snow");
        createClient.setPhoneNumber("111-111-1111");
        createClient.setEmail("elsasnow@gmail.com");
        createClient.setDob(LocalDate.of(1111, 03, 12));
        createClient.setAddress("321 Road ave");
        createClient.setBillingAddress("543 Avenue road");
        createClient.setId(22);
        
        assertEquals("Elsa", createClient.getFirstName());
        assertEquals("Snow", createClient.getLastName());
        assertEquals("111-111-1111", createClient.getPhoneNumber());
        assertEquals("elsasnow@gmail.com", createClient.getEmail());
        assertEquals("321 Road ave", createClient.getAddress());
        assertEquals("543 Avenue road", createClient.getBillingAddress());
        assertEquals(1111, createClient.getDob().getYear());
        assertEquals(3, createClient.getDob().getMonthValue());
        assertEquals(12, createClient.getDob().getDayOfMonth());
        System.out.println("Client model tests completed with no errors");
    }
    
    public void promotionTest() {
        System.out.println("Starting promotion model tests");
        Promotion databasePromotion = new Promotion("Canada Promo", 40, "1989-02-01", 123);
        Promotion createPromotion = new Promotion("Canada Promo", 40, 1989, 02);
        
        assertEquals("Canada Promo", databasePromotion.getPromoName());
        assertEquals(40, databasePromotion.getDiscountPercent());
        assertEquals(1989, databasePromotion.getPromoMonth().getYear());
        assertEquals(2, databasePromotion.getPromoMonth().getMonthValue());
        assertEquals(123, databasePromotion.getPromoId());
        
        assertEquals("Canada Promo", createPromotion.getPromoName());
        assertEquals(40, createPromotion.getDiscountPercent());
        assertEquals(1989, createPromotion.getPromoMonth().getYear());
        assertEquals(2, createPromotion.getPromoMonth().getMonthValue());
        
        createPromotion.setPromoName("France Promo");
        createPromotion.setDiscountPercent(10);
        createPromotion.setPromoMonth(LocalDate.of(1111, 05, 01));
        createPromotion.setPromoId(987);
        
        assertEquals("France Promo", createPromotion.getPromoName());
        assertEquals(10, createPromotion.getDiscountPercent());
        assertEquals(1111, createPromotion.getPromoMonth().getYear());
        assertEquals(5, createPromotion.getPromoMonth().getMonthValue());
        assertEquals(987, createPromotion.getPromoId());
        System.out.println("Promotion model tests completed with no errors");
    }
    
    public void flightTest() {
        System.out.println("Starting flight model tests");
        Flight databaseFlight = new Flight("AirCanada", 120, "Victoria", "Vancouver", 200.21, 123, "1989-02-21 12:32:00");
        Flight createFlight = new Flight("AirCanada", 120, "Victoria", "Vancouver", 200.21, 1989, 02, 21, 12, 32);
        
        assertEquals("AirCanada", databaseFlight.getAirLine());
        assertEquals(120, databaseFlight.getDuration());
        assertEquals("Victoria", databaseFlight.getDepartureCity());
        assertEquals("Vancouver", databaseFlight.getDestinationCity());
        assertEquals(200.21, databaseFlight.getPrice(), 0.0);
        assertEquals(1989, databaseFlight.getDepartureDate().getYear());
        assertEquals(02, databaseFlight.getDepartureDate().getMonthValue());
        assertEquals(21, databaseFlight.getDepartureDate().getDayOfMonth());
        assertEquals(12, databaseFlight.getDepartureDate().getHour());
        assertEquals(32, databaseFlight.getDepartureDate().getMinute());
        assertEquals(123, databaseFlight.getFlightId());
        
        assertEquals("AirCanada", createFlight.getAirLine());
        assertEquals(120, createFlight.getDuration());
        assertEquals("Victoria", createFlight.getDepartureCity());
        assertEquals("Vancouver", createFlight.getDestinationCity());
        assertEquals(200.21, createFlight.getPrice(), 0.0);
        assertEquals(1989, createFlight.getDepartureDate().getYear());
        assertEquals(02, createFlight.getDepartureDate().getMonthValue());
        assertEquals(21, createFlight.getDepartureDate().getDayOfMonth());
        assertEquals(12, createFlight.getDepartureDate().getHour());
        assertEquals(32, createFlight.getDepartureDate().getMinute());
        
        createFlight.setAirLine("West Jet");
        createFlight.setDuration(200);
        createFlight.setDepartureCity("Calgary");
        createFlight.setDestinationCity("Victoria");
        createFlight.setPrice(432.12);
        createFlight.setDepartureDate(LocalDateTime.of(2001, 03, 24, 10, 29));
        createFlight.setFlightId(123);
        
        assertEquals("West Jet", createFlight.getAirLine());
        assertEquals(200, createFlight.getDuration());
        assertEquals("Calgary", createFlight.getDepartureCity());
        assertEquals("Victoria", createFlight.getDestinationCity());
        assertEquals(432.12, createFlight.getPrice(), 0.0);
        assertEquals(2001, createFlight.getDepartureDate().getYear());
        assertEquals(03, createFlight.getDepartureDate().getMonthValue());
        assertEquals(24, createFlight.getDepartureDate().getDayOfMonth());
        assertEquals(10, createFlight.getDepartureDate().getHour());
        assertEquals(29, createFlight.getDepartureDate().getMinute());
        assertEquals(123, createFlight.getFlightId());
        System.out.println("Flight model tests completed with no errors");
    }
    
    public void tripTest() {
        System.out.println("Starting trip model tests");
        Trip databaseTrip = new Trip(123, 321, 1);
        Trip createTrip = new Trip(123, 321);
        
        assertEquals(123, databaseTrip.getFlightId());
        assertEquals(321, databaseTrip.getPromoId());
        assertEquals(1, databaseTrip.getTripId());
        
        assertEquals(123, createTrip.getFlightId());
        assertEquals(321, createTrip.getPromoId());
        
        createTrip.setFlightId(198);
        createTrip.setPromoId(789);
        createTrip.setTripId(123);
        
        assertEquals(198, createTrip.getFlightId());
        assertEquals(789, createTrip.getPromoId());
        assertEquals(123, createTrip.getTripId());
        System.out.println("Trip model tests completed with no errors");
    }
}
