package dao;

import model.Trip;
import utility.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Max Sainsbury
 */
public class TripDAO {
    
    public TripDAO() {};
    
    public boolean addTripRecordPromo(Trip trip) {
        String query = "INSERT INTO trip(origin, destination, departure_date, return_date, trip_status, promotion_id) VALUES(?,?,?,?,?,?);";
        
        try(Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, trip.getOrigin());
            preparedStatement.setString(2, trip.getDestination());
            preparedStatement.setString(3, trip.getDepartureDate().toString());
            preparedStatement.setString(4, trip.getReturnDate().toString());
            preparedStatement.setString(5, trip.getStatus());
            preparedStatement.setInt(6, trip.getPromotionId());
            
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean addTripRecordNoPromo(Trip trip) {
        String query = "INSERT INTO trip(origin, destination, departure_date, return_date, trip_status) VALUES(?,?,?,?,?);";
        
        try(Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, trip.getOrigin());
            preparedStatement.setString(2, trip.getDestination());
            preparedStatement.setString(3, trip.getDepartureDate().toString());
            preparedStatement.setString(4, trip.getReturnDate().toString());
            preparedStatement.setString(5, trip.getStatus());
            
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Trip searchTripFromId(int id) {
        String query = "SELECT * FROM trip WHERE trip_id = ?;";
        
        try(Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            
            ResultSet result = preparedStatement.executeQuery();
            
            if(result.isBeforeFirst()) {
                result.next();
                int tripId = result.getInt("trip_id");
                String origin = result.getString("origin");
                String destination = result.getString("destination");
                String departureDate = result.getString("departure_date");
                String returnDate = result.getString("return_date");
                String status = result.getString("trip_status");
                int promotionId = result.getInt("promotion_id");
                
                return new Trip(origin, destination, departureDate, returnDate, promotionId, status, tripId);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return new Trip();
    }
    
    public boolean deleteTripRecord(int tripId) {
        String query = "DELETE FROM trip WHERE trip_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tripId);
            
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
