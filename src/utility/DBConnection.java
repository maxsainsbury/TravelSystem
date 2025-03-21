package utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * @author goenchoi
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/travelsystemdb";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
