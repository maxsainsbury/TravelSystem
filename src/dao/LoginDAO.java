package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;
import utility.DBConnection;

/**
 *
 * @author Max Sainsbury
 */
public class LoginDAO {
    
    public LoginDAO() {
        
    }
    
    public User checkUserType(String username, String password) {
        String query = "SELECT user_type FROM user WHERE UPPER(username) = UPPER(?) AND password = ?;";
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            ResultSet result = preparedStatement.executeQuery();
            if(result.isBeforeFirst()) {
                result.next();
                String userType = result.getString("user_type");
                int userId = result.getInt("user_id");
                User output = new User(userId, username, password, userType);
                return output;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        User output = new User();
        return output;
    }
    
    public String checkEmployeeType(User user) {
        String query = "SELECT role FROM employee WHERE user_id = ?";
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user.getUserId());
            
            ResultSet result = preparedStatement.executeQuery();
            if(result.isBeforeFirst()) {
                result.next();
                String role = result.getString("role");
                return role;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
    }
        return "";
}
