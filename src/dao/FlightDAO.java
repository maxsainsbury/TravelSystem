package dao;

import java.sql.Connection;
import model.Flight;
import utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Max Sainsbury
 */
public class FlightDAO {
    private Flight flight;
    
    public FlightDAO() {}
    
    /**
     * Constructor for adding to the Flight database
     * @param airLine
     * @param flightNumber
     * @param departureTime
     * @param arrivalTime
     * @param price
     * @param seatClass
     * @param status
     * @param tripId 
     */
    public FlightDAO(String airLine, String flightNumber, String departureTime, String arrivalTime, double price, String seatClass, String status, int tripId) {
        this.flight = new Flight(airLine, flightNumber, departureTime, arrivalTime, price, seatClass, status, tripId);
    }
    
    /**
     * Constructor for getting a flight from the database
     * @param airLine
     * @param flightNumber
     * @param departureTime
     * @param arrivalTime
     * @param price
     * @param seatClass
     * @param status
     * @param tripId
     * @param flightId 
     */
    public FlightDAO(String airLine, String flightNumber, String departureTime, String arrivalTime, double price, String seatClass, String status, int tripId, int flightId) {
        this.flight = new Flight(airLine, flightNumber, departureTime, arrivalTime, price, seatClass, status, tripId, flightId);
    }
    
    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    
    /**
     * add a Flight object to the database
     * @param flight
     * @return true if added false if not
     */
    public boolean addFlightRecord(Flight flight) {
        //SQL query to insert a flight record into the database
        String query = """
                       INSERT INTO flight
                       (trip_id, airline, flight_number, departure_time, arrival_time, price, seat_class, status) 
                       VALUE (?,?,?,?,?,?,?,?)
                       """;
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, flight.getTripId());
            preparedStatement.setString(2, flight.getAirline());
            preparedStatement.setString(3, flight.getFlightNumber());
            preparedStatement.setString(4, flight.getDepartureTime().toString());
            preparedStatement.setString(5, flight.getArrivalTime().toString());
            preparedStatement.setDouble(6, flight.getPrice());
            preparedStatement.setString(7, flight.getSeatClass());
            preparedStatement.setString(8, flight.getStatus());
            
            //return true if added false if not
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * search the flight database with the flightId as a search term
     * @param id
     * @return 
     */
    public Flight searchFlightFromId(String id) {
        String query = "SELECT * FROM flight WHERE flight_id = ?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            
            //execute the search query and store the returned table
            ResultSet result = preparedStatement.executeQuery();
            //if there where any results
            if(result.isBeforeFirst()) {
                //got to next row in the returned table
                result.next();
                //get the values from the table and store them in variables
                int flightId = result.getInt("flight_id");
                int tripId = result.getInt("trip_id");
                String airline = result.getString("airline");
                String flightNumber = result.getString("flight_number");
                //get the departure_time value
                String departureDateTime = result.getString("departure_time");
                //get the date part of the departureTime string
                String departureDate = departureDateTime.substring(0, 10);
                //get the time part of the departureTime string and remove the seconds place
                String departureTime = departureDateTime.substring(11, departureDateTime.length() - 3);
                //add the two parts back together
                departureDateTime = departureDate + " " + departureTime;
                //do the same with the arrival_time value
                String arrivalDateTime = result.getString("arrival_time");
                String arrivalDate = arrivalDateTime.substring(0, 10);
                String arrivalTime = arrivalDateTime.substring(11, arrivalDateTime.length() - 3);
                arrivalDateTime = arrivalDate + " " + arrivalTime;
                double price = result.getDouble("price");
                String seatClass = result.getString("seat_class");
                String status = result.getString("status");
                //return a flight object from the values
                return new Flight(airline, flightNumber, departureDateTime, arrivalDateTime, price, seatClass, status, tripId, flightId);
            }  
            else {
                return new Flight();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //return a flight object with a id of 0 if select empty
        return new Flight();
    }
    
    /**
     * edit a row in the flight database
     * @param flight
     * @return true if edited false if not
     */
    public boolean editFlightRecord(Flight flight) {
        String query = """
                       UPDATE flight 
                       SET trip_id = ?, 
                       airline = ?, 
                       flight_number = ?, 
                       departure_time = ?, 
                       arrival_time = ?, 
                       price = ?, 
                       seat_class = ?, 
                       status = ?
                       WHERE flight_id = ?
                       """;
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, flight.getTripId());
            preparedStatement.setString(2, flight.getAirline());
            preparedStatement.setString(3, flight.getFlightNumber());
            preparedStatement.setString(4, flight.getDepartureTime().toString());
            preparedStatement.setString(5, flight.getArrivalTime().toString());
            preparedStatement.setDouble(6, flight.getPrice());
            preparedStatement.setString(7, flight.getSeatClass());
            preparedStatement.setString(8, flight.getStatus());
            preparedStatement.setInt(9, flight.getFlightId());
            
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * delete row from flight database
     * @param flightId
     * @return true if row deleted false if not
     */
    public boolean deleteFlightRecort(int flightId) {
        String query = "DELETE FROM flight WHERE flight_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, flightId);
            
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * search the flight table using the airline value as a search term;
     * @param airline
     * @return array of Flight Objects created from all rows found in table
     */
    public Flight[] searchFromAirline(String airline) {
        //select everything in the table and the count of all rows returned
        String query = """
                       SELECT *, 
                       COUNT(*) OVER() as count 
                       FROM flight 
                       WHERE UPPER(airline) = UPPER(?)
                       """;
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, airline);
            
            //execute the select statement and store the returned table
            ResultSet results = preparedStatement.executeQuery();
            //if at least 1 row is returned
            if (results.isBeforeFirst()) {
                //go to first row in table
                results.next();
                //store total rows returned by select in variable
                int totalRows = results.getInt("count");
                //sel output array to the length of the total rows returned
                Flight[] output = new Flight[totalRows];
                //for all rows in returned table
                for(int i = 0; i < totalRows; i++) {
                    int flightId = results.getInt("flight_id");
                    int tripId = results.getInt("trip_id");
                    String flightNumber = results.getString("flight_number");
                    //get the departure_time value
                    String departureDateTime = results.getString("departure_time");
                    //store the date part in a variable
                    String departureDate = departureDateTime.substring(0, 10);
                    //store the time part in a variable minuse seconds place
                    String departureTime = departureDateTime.substring(11, departureDateTime.length() - 3);
                    //put the 2 parts back together
                    departureDateTime = departureDate + " " + departureTime;
                    //repeat for arrival_time
                    String arrivalDateTime = results.getString("arrival_time");
                    String arrivalDate = arrivalDateTime.substring(0, 10);
                    String arrivalTime = arrivalDateTime.substring(11, arrivalDateTime.length() - 3);
                    arrivalDateTime = arrivalDate + " " + arrivalTime;
                    double price = results.getDouble("price");
                    String seatClass = results.getString("seat_class");
                    String status = results.getString("status");
                    //append new flight object made from current row into flight array
                    output[i] = new Flight(airline, flightNumber, departureDateTime, arrivalDateTime, price, seatClass, status, tripId, flightId);
                    //go to next row in table
                    results.next();
                }
                
                return output;
            }
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //if no rows returned return a 1 row array with a flight object with a flightId of 0
        Flight[] output = {new Flight()};
        return output;
    }
    
    /**
     * search for all rows in the flight database
     * @return 
     */
    public Flight[] searchAll() {
        String query = "SELECT *, COUNT(*) OVER() as count FROM flight";
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            ResultSet results = preparedStatement.executeQuery();
            if (results.isBeforeFirst()) {
                results.next();
                int totalRows = results.getInt("count");
                Flight[] output = new Flight[totalRows];
                for(int i = 0; i < totalRows; i++) {
                    int flightId = results.getInt("flight_id");
                    int tripId = results.getInt("trip_id");
                    String airline = results.getString("airline");
                    String flightNumber = results.getString("flight_number");
                    String departureDateTime = results.getString("departure_time");
                    String departureDate = departureDateTime.substring(0, 10);
                    String departureTime = departureDateTime.substring(11, departureDateTime.length() - 3);
                    departureDateTime = departureDate + " " + departureTime;
                    String arrivalDateTime = results.getString("arrival_time");
                    String arrivalDate = arrivalDateTime.substring(0, 10);
                    String arrivalTime = arrivalDateTime.substring(11, arrivalDateTime.length() - 3);
                    arrivalDateTime = arrivalDate + " " + arrivalTime;
                    double price = results.getDouble("price");
                    String seatClass = results.getString("seat_class");
                    String status = results.getString("status");
                    output[i] = new Flight(airline, flightNumber, departureDateTime, arrivalDateTime, price, seatClass, status, tripId, flightId);
                    results.next();
                }
                
                return output;
            }
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Flight[] output = {new Flight()};
        return output;
    }
}
