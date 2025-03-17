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
    
    public FlightDAO(String airLine, String flightNumber, String departureTime, String arrivalTime, double price, String seatClass, String status, int tripId, int flightId) {
        this.flight = new Flight(airLine, flightNumber, departureTime, arrivalTime, price, seatClass, status, tripId, flightId);
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    
    public boolean addFlightRecord(Flight flight) {
        //SQL query to insert a flight record into the database
        String query = "INSERT INTO flight(trip_id, airline, flight_number, departure_time, arrival_time, price, seat_class, status) VALUE (?,?,?,?,?,?,?,?)";
        
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
            
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public Flight searchFlightFromId(String id) {
        String query = "SELECT * FROM flight WHERE flight_id = ?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            
            ResultSet result = preparedStatement.executeQuery();
            if(result.isBeforeFirst()) {
                result.next();
                int flightId = result.getInt("flight_id");
                int tripId = result.getInt("trip_id");
                String airline = result.getString("airline");
                String flightNumber = result.getString("flight_number");
                String departureDateTime = result.getString("departure_time");
                String departureDate = departureDateTime.substring(0, 10);
                String departureTime = departureDateTime.substring(11, departureDateTime.length() - 3);
                departureDateTime = departureDate + " " + departureTime;
                String arrivalDateTime = result.getString("arrival_time");
                String arrivalDate = arrivalDateTime.substring(0, 10);
                String arrivalTime = arrivalDateTime.substring(11, arrivalDateTime.length() - 3);
                arrivalDateTime = arrivalDate + " " + arrivalTime;
                double price = result.getDouble("price");
                String seatClass = result.getString("seat_class");
                String status = result.getString("status");
                
                return new Flight(airline, flightNumber, departureDateTime, arrivalDateTime, price, seatClass, status, tripId, flightId);
            }  
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new Flight();
    }
    
    public boolean editFlightRecord(Flight flight) {
        String query = "UPDATE flight SET trip_id = ?, airline = ?, flight_number = ?, departure_time = ?, arrival_time = ?, price = ?, seat_class = ?, status = ?";
        
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
            
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
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
    
    public Flight[] searchFromAirline(String airline) {
        String query = "SELECT *, COUNT(*) OVER() as count FROM flight WHERE UPPER(airline) = UPPER(?)";
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, airline);
            
            ResultSet results = preparedStatement.executeQuery();
            results.next();
            int totalRows = results.getInt("count");
            if (totalRows > 0) {
                Flight[] output = new Flight[totalRows];
                for(int i = 0; i < totalRows; i++) {
                    int flightId = results.getInt("flight_id");
                    int tripId = results.getInt("trip_id");
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
    
    public Flight[] searchAll() {
        String query = "SELECT *, COUNT(*) OVER() as count FROM flight";
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            ResultSet results = preparedStatement.executeQuery();
            results.next();
            int totalRows = results.getInt("count");
            if (totalRows > 0) {
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
