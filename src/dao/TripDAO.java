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
        String query = """
                       INSERT INTO trip
                       (origin, destination, departure_date, return_date, trip_status, promotion_id) 
                       VALUES(?,?,?,?,?,?);
                       """;
        
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
        String query = """
                       INSERT INTO trip
                       (origin, destination, departure_date, return_date, trip_status) 
                       VALUES(?,?,?,?,?);""";
        
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
            else {
                return new Trip();
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
    
    public boolean editTripRecord(Trip trip) {
        String query;
        if(trip.getPromotionId() > 0) {
            query = """
                    UPDATE trip 
                    SET origin = ?, 
                    destination = ?, 
                    departure_date = ?, 
                    return_date = ?, 
                    trip_status = ?, 
                    promotion_id = ? 
                    WHERE trip_id = ?
                    """;
        }
        else {
            query = """
                    UPDATE trip 
                    SET origin = ?, 
                    destination = ?, 
                    departure_date = ?, 
                    return_date = ?, 
                    trip_status = ? 
                    WHERE trip_id = ?
                    """;
        }
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if(!trip.getDepartureDate().equals("") | !trip.getReturnDate().equals("")) {
                preparedStatement.setString(1, trip.getOrigin());
                preparedStatement.setString(2, trip.getDestination());
                preparedStatement.setString(3, trip.getDepartureDate().toString());
                preparedStatement.setString(4, trip.getReturnDate().toString());
                preparedStatement.setString(5, trip.getStatus());
                if(trip.getPromotionId() > 0) {
                    preparedStatement.setInt(6, trip.getPromotionId());
                    preparedStatement.setInt(7, trip.getTripId());
                }
                else {
                    preparedStatement.setInt(6, trip.getTripId());
                }


                return preparedStatement.executeUpdate() > 0;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Trip[] searchByOrigin(String origin) {
        String query = """
                       SELECT *, 
                       COUNT(*) OVER() as count 
                       FROM trip 
                       WHERE UPPER(origin) = UPPER(?);
                       """;
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, origin);
            
            ResultSet results = preparedStatement.executeQuery();
            if (results.isBeforeFirst()) {
                results.next();
                int totalRows = results.getInt("count");
                Trip[] output = new Trip[totalRows];
                for(int i = 0; i < totalRows; i++) {
                    int tripId = results.getInt("trip_id");
                    String destination = results.getString("destination");
                    String departureDate = results.getString("departure_date");
                    String returnDate = results.getString("return_date");
                    String status = results.getString("trip_status");
                    int promotionId = results.getInt("promotion_id");
                    
                    output[i] = new Trip(origin, destination, departureDate, returnDate, promotionId, status, tripId);
                    results.next();
                }
                return output;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Trip[] output= {new Trip()};
        return output;
    }
    
    public Trip[] searchByMonth(String departureMonth) {
        String query = """
                       SELECT *, 
                       COUNT(*) OVER() as count 
                       FROM trip 
                       WHERE departure_date LIKE ?
                       """;
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%-" + departureMonth + "-%");
            
            ResultSet results = preparedStatement.executeQuery();
            if (results.isBeforeFirst()) {
                results.next();
                int totalRows = results.getInt("count");
                Trip[] output = new Trip[totalRows];
                for(int i = 0; i < totalRows; i++) {
                    int tripId = results.getInt("trip_id");
                    String origin = results.getString("origin");
                    String destination = results.getString("destination");
                    String departureDate = results.getString("departure_date");
                    String returnDate = results.getString("return_date");
                    String status = results.getString("trip_status");
                    int promotionId = results.getInt("promotion_id");
                    
                    output[i] = new Trip(origin, destination, departureDate, returnDate, promotionId, status, tripId);
                    results.next();
                }
                return output;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Trip[] output= {new Trip()};
        return output;
    }
    
    public Trip[] searchAll() {
        String query = "SELECT *, COUNT(*) OVER() AS count FROM trip;";
        
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet results = preparedStatement.executeQuery();
            if(results.isBeforeFirst()) {
                results.next();
                int totalRows = results.getInt("count");
                Trip[] output = new Trip[totalRows];
                for(int i = 0; i < totalRows; i++) {
                    int tripId = results.getInt("trip_id");
                    String origin = results.getString("origin");
                    String destination = results.getString("destination");
                    String departureDate = results.getString("departure_date");
                    String returnDate = results.getString("return_date");
                    String status = results.getString("trip_status");
                    int promotionId = results.getInt("promotion_id");
                    output[i] = new Trip(origin, destination, departureDate, returnDate, promotionId, status, tripId);
                    results.next();
                }
                return output;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Trip[] output= {new Trip()};
        return output;
    }
    
}
