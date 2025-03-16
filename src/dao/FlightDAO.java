package dao;

import java.sql.Connection;
import model.Flight;
import utility.DBConnection;
import java.sql.PreparedStatement;

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
}
