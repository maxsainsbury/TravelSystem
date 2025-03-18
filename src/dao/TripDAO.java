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
    
}
