package dao;

import model.Customer;
import utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Ebba de Groot
 */
public class CustomerDAO {
    private Customer customer;
    
    // Default constructor
    public CustomerDAO() { }
    
    // EmployeeDAO constructor when adding new customer
    public CustomerDAO(String firstName, String lastName, String email, String phone, String unitNumber, 
            String streetAddress, String city, String country, String postalCode, String dob, String userType, 
            String userName, String userPassword) {
        
        this.customer = new Customer(firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, dob, userName, userPassword);
    }
    
    // Constructor for customer deletion or search.
    public CustomerDAO(int customerId, String firstName, String lastName, String phone, String email){
        this.customer = new Customer(customerId, firstName, lastName, phone, email);
    };
    
    // Sending data to customer table in database when adding new customer.
    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO customer(customer_id, first_name, last_name, dob, "
                        + "email, phone_num, unit_number, street_address, "
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
    
    // Method to fetch customer data from database by customer id for customer delete view
    public Customer fetchCustomerForDelTable(int customerId) throws Exception{
        String query = """
                       SELECT customer_id, first_name, last_name, phone, email
                       FROM customer
                       WHERE customer_id = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, customerId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                customer = new Customer(
                resultSet.getInt("customer_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("phone"),
                resultSet.getString("email")
                );
            } else {
                throw new Exception();
            }
        } catch (SQLException e) {
            
        }
        
        return customer;      
    }
    
    /**
     * Method to delete customer in database
     * @param customerId
     * @return 
     */
    public boolean deleteCustomer(int customerId) {
        String query = """
                      DELETE FROM user
                      WHERE user_id = ( SELECT user_id
                                        FROM (SELECT customer_id
                                              FROM user INNER JOIN customer USING (user_id)) as temp
                                        WHERE customer_id = ?)                      
                      """;
       try(Connection connection = DBConnection.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(query)){
           preparedStatement.setInt(1, customerId);
                      
           return preparedStatement.executeUpdate() > 0;

       } catch (SQLException e) {
           // Exception will be caught in Controller to display message to user.
       }
       return false;
    }
    
    // Method to fetch customer data from database by customer phone for edit customer view
    public ArrayList<Customer> fetchCustomerByPhone(String phone){
        ArrayList<Customer> customers = new ArrayList<>();
        String query = """
                       SELECT *
                       FROM customer
                       WHERE phone_num = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, phone);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                customer = new Customer(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone_num"),
                resultSet.getString("unit_number"), 
                resultSet.getString("street_address"),   
                resultSet.getString("city"),
                resultSet.getString("country"), 
                resultSet.getString("postal_code"), 
                resultSet.getString("dob"),
                resultSet.getInt("user_id"),                       
                resultSet.getInt("customer_id")
                );
                customers.add(customer);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    // Method to fetch customer data from database by customer email for edit customer view
    public ArrayList<Customer> fetchCustomerByEmail(String email){
        ArrayList<Customer> customers = new ArrayList<>();
        String query = """
                       SELECT *
                       FROM customer
                       WHERE email = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, email);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                customer = new Customer(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone_num"),
                resultSet.getString("unit_number"), 
                resultSet.getString("street_address"),   
                resultSet.getString("city"),
                resultSet.getString("country"), 
                resultSet.getString("postal_code"), 
                resultSet.getString("dob"),
                resultSet.getInt("user_id"),                       
                resultSet.getInt("customer_id")
                );
                customers.add(customer);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    // Method to fetch customer data from database by customer id for edit customer view and search customer view
    public Customer fetchCustomerById(int customerId) throws Exception{
        String query = """
                       SELECT *
                       FROM customer
                       WHERE customer_id = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, customerId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                customer = new Customer(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getString("unit_number"), 
                resultSet.getString("street_address"),   
                resultSet.getString("city"),
                resultSet.getString("country"), 
                resultSet.getString("postal_code"), 
                resultSet.getString("dob"),
                resultSet.getInt("user_id"),                       
                resultSet.getInt("customer_id")
                );
            } else {
                throw new Exception();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    
    // Method to fetch all customer data from database for search customer view
    public ArrayList<Customer> fetchAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        String query = """
                       SELECT *
                       FROM customer
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                customer = new Customer(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone_num"),
                resultSet.getString("unit_number"), 
                resultSet.getString("street_address"),   
                resultSet.getString("city"),
                resultSet.getString("country"), 
                resultSet.getString("postal_code"), 
                resultSet.getString("dob"),
                resultSet.getInt("user_id"),                       
                resultSet.getInt("customer_id")
                );
                customers.add(customer);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    public boolean editCustomer(Customer customer) {
        String query = """
                      UPDATE customer
                      SET first_name = ?,
                          last_name = ?,
                          dob = ?,
                          email = ?,
                          phone = ?,
                          unit_number = ?,
                          street_address = ?,
                          city = ?,
                          postal_code = ?,
                          country = ?,      
                      WHERE customer_id = ?
                      """;
       try(Connection connection = DBConnection.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(query)){
           preparedStatement.setString(1, customer.getFirstName());
           preparedStatement.setString(2, customer.getLastName());
           preparedStatement.setString(3, customer.getDob().toString());
           preparedStatement.setString(4, customer.getEmail());
           preparedStatement.setString(5, customer.getPhone());
           preparedStatement.setString(6, customer.getUnitNumber());
           preparedStatement.setString(7, customer.getStreetAddress());
           preparedStatement.setString(8, customer.getCity());
           preparedStatement.setString(9, customer.getPostalCode());
           preparedStatement.setString(10, customer.getCountry());  
           preparedStatement.setInt(11, customer.getCustomerId());  
                  
           return preparedStatement.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;
    }
    
}
