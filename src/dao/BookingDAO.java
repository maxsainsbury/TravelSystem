package dao;

import model.*;
import utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Goen Choi
 */
public class BookingDAO {
    private Booking booking;
    
    public BookingDAO() {}
    
    public boolean addBooking(Booking booking) {
        String query = """
                       INSERT INTO booking (employee_id, booking_date, customer_id, trip_id, total_price)
                       VALUES (?,?,?,?,?)
                       """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, booking.getEmployeeId());
            preparedStatement.setObject(2, booking.getBookingDate());
            preparedStatement.setInt(3, booking.getCustomerId());
            preparedStatement.setInt(4, booking.getTripId());
            preparedStatement.setDouble(5, booking.getTotalPrice());
            
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }                 
        return false;
    }
    
    /**
     * Retrieves data from booking and trip table from database where booking id matches.
     * @param bookingId
     * @return TripAndBooking object that holds the trip and booking data.
     */
    public TripAndBooking fetchBookingByBookId(int bookingId) {
        TripAndBooking tripAndbookObj  = null;
        String query = """ 
                       SELECT * 
                       FROM booking INNER JOIN trip USING (trip_id)
                       WHERE booking_id = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                Booking bookingObject = new Booking(
                resultSet.getInt("customer_id"),
                resultSet.getInt("trip_id"),
                resultSet.getDouble("total_price"),
                resultSet.getString("booking_date"),
                resultSet.getInt("booking_id"),
                resultSet.getInt("employee_id")
                );
                
                Trip tripObject = new Trip(
                resultSet.getString("origin"),
                resultSet.getString("destination"),
                resultSet.getString("departure_date"),
                resultSet.getString("return_date"),
                resultSet.getInt("promotion_id"),
                resultSet.getString("status"),
                resultSet.getInt("trip_id")
                );
                
                tripAndbookObj = new TripAndBooking(bookingObject, tripObject);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tripAndbookObj;               
    }
    
    /**
     * Retrieves data from trip and booking table from database where the 
     * customer id matches.
     * @param customerId 
     * @return TripAndBooking object that holds the trip and booking data
     */
    public ArrayList<TripAndBooking> fetchBookingByCusId(int customerId) {
        ArrayList<TripAndBooking> tripAndBookings = new ArrayList<>();
        String query = """
                       SELECT * 
                       FROM booking INNER JOIN trip USING (trip_id)
                       WHERE customer_id = ?
                       """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                Booking bookingObject = new Booking(
                resultSet.getInt("customer_id"),
                resultSet.getInt("trip_id"),
                resultSet.getDouble("total_price"),
                resultSet.getString("booking_date"),
                resultSet.getInt("booking_id"),
                resultSet.getInt("employee_id")
                );
                
                Trip tripObject = new Trip(
                resultSet.getString("origin"),
                resultSet.getString("destination"),
                resultSet.getString("departure_date"),
                resultSet.getString("return_date"),
                resultSet.getInt("promotion_id"),
                resultSet.getString("status"),
                resultSet.getInt("trip_id")
                );
                
                TripAndBooking tripAndBookObj = new TripAndBooking(bookingObject, tripObject);
                tripAndBookings.add(tripAndBookObj);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tripAndBookings;            
    }
    
    /**
     * Retrieves all booking data from database.
     * @return array list of all existing booking objects in database.
     */
    public ArrayList<TripAndBooking> fetchAllBooking() {
        ArrayList<TripAndBooking> tripAndBookings = new ArrayList<>();
        String query = """
                       SELECT * 
                       FROM booking INNER JOIN trip USING (trip_id)
                       """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                Booking bookingObject = new Booking(
                resultSet.getInt("customer_id"),
                resultSet.getInt("trip_id"),
                resultSet.getDouble("total_price"),
                resultSet.getString("booking_date"),
                resultSet.getInt("booking_id"),
                resultSet.getInt("employee_id")
                );
                
                Trip tripObject = new Trip(
                resultSet.getString("origin"),
                resultSet.getString("destination"),
                resultSet.getString("departure_date"),
                resultSet.getString("return_date"),
                resultSet.getInt("promotion_id"),
                resultSet.getString("status"),
                resultSet.getInt("trip_id")
                );
                
                TripAndBooking tripAndBookObj = new TripAndBooking(bookingObject, tripObject);
                tripAndBookings.add(tripAndBookObj);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tripAndBookings;         
    }
    
    /**
     * Method receives booking object as parameter and updates the database with 
     * this object's attributes.
     * @param booking
     * @return 
     */
    public boolean updateBooking(Booking booking) {
        String query = """
                       UPDATE booking
                       SET booking_id = ?,
                           employee_id = ?,
                           booking_date = ?,
                           customer_id = ?,
                           trip_id = ?,
                           total_price = ?
                       WHERE booking_id = ?
                       """;
    try(Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, booking.getBookingId());
        preparedStatement.setInt(2, booking.getEmployeeId());
        preparedStatement.setString(3, booking.getBookingDate().toString());
        preparedStatement.setInt(4, booking.getCustomerId());
        preparedStatement.setInt(5, booking.getTripId());
        preparedStatement.setDouble(6, booking.getTotalPrice());
        preparedStatement.setInt(7, booking.getBookingId());

        return preparedStatement.executeUpdate() > 0;
        
    } catch (Exception e) {
        e.printStackTrace();
    }
        return false;
    }
    
    /**
     * Method deletes the booking in database that matches the booking id that 
     * is passed in as parameter.
     * @param bookingId
     * @return true if successful deletion.
     */
    public boolean deleteBooking(int bookingId) {
        String query = """
                       DELETE FROM booking
                       WHERE booking_id = ?
                       """;
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, bookingId);

        return preparedStatement.executeUpdate() > 0;
        
        } catch (Exception e) {
            e.printStackTrace();
        }
            return false;
        }
   
}
