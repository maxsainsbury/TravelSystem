package dao;

import model.Customer;
import utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Connection;

/**
 *
 * @author Ebba de Groot
 */
public class CustomerDAO {
    private Customer customer;
    
    // Default constructor
    public CustomerDAO() { }
    
    // EmployeeDAO constructor when adding new employee
    public CustomerDAO(String firstName, String lastName, String email, String phone, String unitNumber, 
            String streetAddress, String city, String country, String postalCode, String dob, String userType, 
            String userName, String userPassword) {
        
        this.customer = new Customer(firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, dob, userName, userPassword);
    }
    
    // Sending data to employee table in database when adding new employee.
    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO customer(customer_id, first_name, last_name, dob, "
                        + "email, phone, unit_number, street_address, "
                        + "city, postal_code, country, user_id)"
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setObject(4, customer.getDob());
            preparedStatement.setString(5, customer.getEmail());
            preparedStatement.setString(6, customer.getPhone());
            preparedStatement.setString(7, customer.getUnitNumber());
            preparedStatement.setString(8, customer.getStreetAddress());
            preparedStatement.setString(9, customer.getCity());
            preparedStatement.setString(10, customer.getPostalCode());
            preparedStatement.setString(11, customer.getCountry());
            preparedStatement.setInt(12, customer.getUserId());
            
                return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
       
}
