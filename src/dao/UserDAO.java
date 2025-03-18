package dao;

import model.User;
import utility.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;


/**
 *
 * @author goenchoi
 */
public class UserDAO {
    private User user;
    
    public UserDAO() {}
    
    /**
     * Constructor to get existing user from database.
     * @param userId
     * @param userName
     * @param password
     * @param userType 
     */
    public UserDAO(int userId, String userName, String password, String userType) {
        this.user = new User(userId, userName, password, userType);
    }
    
    /**
     * Constructor to create new user in database.
     * @param userName
     * @param password
     * @param userType 
     */
    public UserDAO(String userName, String password, String userType) {
        this.user = new User(userName, password, userType);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public boolean addUserRecord(User user) {
        String query ="INSERT INTO user (username, password, user_type) VALUES (?, ?, ?)";
        
        // Create a new user in the database and return the newly created user_id
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getUserType());
            int status = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                // Set the new user_id to this user. 
                int last_inserted_id = resultSet.getInt(1);
                user.setUserId(last_inserted_id);
            }
            
            // Successfull insertion will return value greater than 0.
            return status > 0; 
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    
}
