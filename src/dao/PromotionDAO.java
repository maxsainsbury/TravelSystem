package dao;

import model.Promotion;
import utility.DBConnection;
import view.AddPromoView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Goen Choi
 */
public class PromotionDAO {
    private Promotion promotion;
    
    public PromotionDAO(){}
    
    /**
     * Promotion data access object to add a new promotion to database. 
     * @param promoName
     * @param discountPercent
     * @param description
     * @param status 
     * @param startDate 
     * @param endDate 
     */
    public PromotionDAO(String promoName, double discountPercent, String description, String status, 
                        String startDate, String endDate){
       
        this.promotion = new Promotion(promoName, discountPercent, description, status, startDate, endDate);
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
    
    /**
     * Use the promotion instance that was passed in and insert it's attributes values
     * into new row in database. 
     * @param promotion
     * @return true if successful addition.
     */
    public boolean addPromotion(Promotion promotion){
        String query = """
                       INSERT INTO promotion (name, description, discount_percentage, 
                                    start_date, end_date, status)
                       VALUES(?,?,?,?,?,?)
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, promotion.getPromoName());
            preparedStatement.setString(2, promotion.getDescription());
            preparedStatement.setDouble(3, promotion.getDiscountPercent());
            preparedStatement.setObject(4, promotion.getStartDate());
            preparedStatement.setObject(5, promotion.getEndDate());
            preparedStatement.setString(6, promotion.getStatus());

            
            return preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;        
    }
    
    /**
     * Retrieves all promotions in database.
     * @return ArrayList of retrieved promotions from database.
     */
    public ArrayList<Promotion> fetchAllPromotions(){
        ArrayList<Promotion> promotions = new ArrayList<>();
        String query = """
                       SELECT *
                       FROM promotion
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {                               
                Promotion promotion = new Promotion(
                    resultSet.getString("name"),
                    resultSet.getDouble("discount_percentage"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getString("start_date"),
                    resultSet.getString("end_date"),
                    resultSet.getInt("promotion_id")                 
                );
                promotions.add(promotion);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }
    
    /**
     * Takes in a promotion name, and returns an arraylist of all promotions with matching
     * name from database.
     * @param promoName
     * @return ArrayList of promotion instances(row from table in database).
     */
    public ArrayList<Promotion> fetchPromotionByName(String promoName) {
        ArrayList<Promotion> promotions = new ArrayList<>();
        String query = """
                       SELECT * 
                       FROM travelsystemdb.promotion
                       WHERE UPPER(name) LIKE UPPER(CONCAT('%', ? ,'%'))
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, promoName);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {                               
                Promotion promotion = new Promotion(
                    resultSet.getString("name"),
                    resultSet.getDouble("discount_percentage"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getString("start_date"),
                    resultSet.getString("end_date"),
                    resultSet.getInt("promotion_id")                 
                );
                promotions.add(promotion);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }
    
    /**
     * Takes in a promotion month string as a parameter and fetches all promotion that has
     * matching month in start_date column.
     * @param promoMonth
     * @return Arraylist of promotion instances retrieved from database.
     *       Each instance of promotion contain attributes of one row from table.
     */
    public ArrayList<Promotion> fetchPromotionByMonth(String promoMonth) {
        ArrayList<Promotion> promotions = new ArrayList<>();
        String query = """
                       SELECT * 
                       FROM travelsystemdb.promotion
                       WHERE UPPER(DATE_FORMAT(start_date, '%b')) = UPPER(?)
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, promoMonth);
            
            ResultSet resultSet = preparedStatement.executeQuery();
       
            // Loop until no more rows in the resultset
            while(resultSet.next()) {                               
                Promotion promotion = new Promotion(
                    resultSet.getString("name"),
                    resultSet.getDouble("discount_percentage"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getString("start_date"),
                    resultSet.getString("end_date"),
                    resultSet.getInt("promotion_id")                 
                );
                promotions.add(promotion);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }
    
    /** 
     * Takes in promotion id as parameter and deletes the promotion with matching
     * id in database.
     * @param promoId
     * @return true if successful deletion
     */    
    public boolean deletePromotion(int promoId){
        String query = """
                       DELETE FROM travelsystemdb.promotion
                       WHERE promotion_id = ?;
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, promoId);         
            
            return preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;        
    }      
    
    /**
     * Fetches promotion row with matching promotion id. 
     * @param promoId
     * @return Promotion instance with attributes retrieved from database.
     */
    public Promotion fetchPromotionById(int promoId){
        String query = """
                       SELECT * 
                       FROM travelsystemdb.promotion
                       WHERE promotion_id = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, promoId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                promotion = new Promotion(
                resultSet.getString("name"),
                resultSet.getDouble("discount_percentage"),
                resultSet.getString("description"),
                resultSet.getString("status"),
                resultSet.getString("start_date"), 
                resultSet.getString("end_date"), 
                resultSet.getInt("promotion_id")
                );
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotion;
    }
    
    /**
     * Method to edit promotion in edit promotion view.
     * @param promotion object that holds edited attributes and to be sent to the database.
     * @return true if successful edit in database.
     */
    public boolean editPromotion(Promotion promotion) {
        String query = """
                       UPDATE travelsystemdb.promotion
                       SET name = ?,
                           description = ?,
                           discount_percentage = ?,
                           start_date = ?,
                       	   end_date = ?,
                       	   status = ?                          
                       WHERE promotion_id = ?;
                       """;
       try(Connection connection = DBConnection.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(query)){
           preparedStatement.setString(1, promotion.getPromoName());
           preparedStatement.setString(2, promotion.getDescription());
           preparedStatement.setDouble(3, promotion.getDiscountPercent());
           preparedStatement.setString(4, promotion.getStartDate().toString());
           preparedStatement.setString(5, promotion.getEndDate().toString());
           preparedStatement.setString(6, promotion.getStatus());
           preparedStatement.setInt(7, promotion.getPromoId()); 
                  
           return preparedStatement.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;
    }
    
}
