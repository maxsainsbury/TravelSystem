package dao;

import model.*;
import utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author goenchoi
 */
public class EmployeeDAO {
    private Employee employee;
    
    // Default constructor
    public EmployeeDAO() { }
    
    // Constructor for adding new employee
    public EmployeeDAO(String firstName, String lastName, String email, int SIN, String phone, String unitNumber, 
            String streetAddress, String city, String country, String postalCode, String dob, String status, 
            String cell, String position, double salary, String role, int createdBy, int userId, int employeeID) {
        
        this.employee = new Employee(firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, dob, userId, SIN, status, cell, position, salary, role, createdBy, employeeID);
    }
    
    // Constructor for employee deletion or search.
    public EmployeeDAO(int employeeId, String firstName, String lastName, String phone, String email, String role ){
        this.employee = new Employee(employeeId, firstName, lastName, phone, email, role);
    };
    
    // Sending data to employee table in database when adding new employee.
    public boolean addEmployee(Employee employee) {
        String query = "INSERT INTO employee(employee_id, first_name, last_name, dob, "
                        + "email, SIN, status, phone, cell, unit_number, street_address, "
                        + "city, postal_code, country, position, salary, role, created_by, user_id)"
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, employee.getEmployeeId());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setObject(4, employee.getDob());
            preparedStatement.setString(5, employee.getEmail());
            preparedStatement.setInt(6, employee.getSIN());
            preparedStatement.setString(7, employee.getStatus());
            preparedStatement.setString(8, employee.getPhone());
            preparedStatement.setString(9, employee.getCell());
            preparedStatement.setString(10, employee.getUnitNumber());
            preparedStatement.setString(11, employee.getStreetAddress());
            preparedStatement.setString(12, employee.getCity());
            preparedStatement.setString(13, employee.getPostalCode());
            preparedStatement.setString(14, employee.getCountry());
            preparedStatement.setString(15, employee.getPosition());
            preparedStatement.setDouble(16, employee.getSalary());
            preparedStatement.setString(17, employee.getRole());
            preparedStatement.setInt(18, employee.getCreatedBy());
            preparedStatement.setInt(19, employee.getUserId());
     
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Method to fetch employee data from database by employee id for employee delete view
    public Employee fetchEmployeeForDelTable(int employeeId) throws Exception{
        String query = """
                       SELECT employee_id, first_name, last_name, phone, email, role
                       FROM employee
                       WHERE employee_id = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, employeeId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                employee = new Employee(
                resultSet.getInt("employee_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("phone"),
                resultSet.getString("email"),
                resultSet.getString("role")                     
                );
            } else {
                throw new Exception();
            }
        } catch (SQLException e) {
            
        }
        
        return employee;      
    }
    
    /**
     * Method to delete employee in database
     * @param employeeId
     * @return 
     */
    public boolean deleteEmployee(int employeeId) {
        String query = """
                      DELETE u
                      FROM user u INNER JOIN employee e USING (user_id)
                      WHERE employee_id = ?                     
                      """;
       try(Connection connection = DBConnection.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(query)){
           preparedStatement.setInt(1, employeeId);
                      
           return preparedStatement.executeUpdate() > 0;

       } catch (SQLException e) {
           // Exception will be caught in Controller to display message to user.
       }
       return false;
    }
     // Method to fetch employee data from database by employee id for edit employee view
    public ArrayList<Employee> fetchEmployeeByPosition(String position){
        ArrayList<Employee> employees = new ArrayList<>();
        String query = """
                       SELECT *
                       FROM employee
                       WHERE position = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, position);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                employee = new Employee(
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
                resultSet.getInt("SIN"),
                resultSet.getString("status"),
                resultSet.getString("cell"), 
                resultSet.getString("position"),
                resultSet.getDouble("salary"),      
                resultSet.getString("role"),
                resultSet.getInt("created_by"),                       
                resultSet.getInt("employee_id")
                );
                employees.add(employee);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
         // Method to fetch employee data from database by employee id for edit employee view
    public ArrayList<Employee> fetchAllEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();
        String query = """
                       SELECT *
                       FROM employee
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                employee = new Employee(
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
                resultSet.getInt("SIN"),
                resultSet.getString("status"),
                resultSet.getString("cell"), 
                resultSet.getString("position"),
                resultSet.getDouble("salary"),      
                resultSet.getString("role"),
                resultSet.getInt("created_by"),                       
                resultSet.getInt("employee_id")
                );
                employees.add(employee);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    
    // Method to fetch employee data from database by employee id for edit employee view and search employee view
    public Employee fetchEmployeeById(int employeeId) throws Exception{
        String query = """
                       SELECT *
                       FROM employee
                       WHERE employee_id = ?
                       """;
        
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, employeeId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                employee = new Employee(
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
                resultSet.getInt("SIN"),
                resultSet.getString("status"),
                resultSet.getString("cell"), 
                resultSet.getString("position"),
                resultSet.getDouble("salary"),      
                resultSet.getString("role"),
                resultSet.getInt("created_by"),                       
                resultSet.getInt("employee_id")
                );
            } else {
                throw new Exception();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    
    public boolean editEmployee(Employee employee) {
        String query = """
                      UPDATE employee
                      SET first_name = ?,
                          last_name = ?,
                          dob = ?,
                          email = ?,
                          SIN = ?,
                          status = ?,
                          phone = ?,
                          cell = ?,
                          unit_number = ?,
                          street_address = ?,
                          city = ?,
                          postal_code = ?,
                          country = ?,
                          position = ?,
                          salary = ?,
                          role = ?       
                      WHERE employee_id = ?
                      """;
       try(Connection connection = DBConnection.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(query)){
           preparedStatement.setString(1, employee.getFirstName());
           preparedStatement.setString(2, employee.getLastName());
           preparedStatement.setString(3, employee.getDob().toString());
           preparedStatement.setString(4, employee.getEmail());
           preparedStatement.setInt(5, employee.getSIN());
           preparedStatement.setString(6, employee.getStatus());
           preparedStatement.setString(7, employee.getPhone());
           preparedStatement.setString(8, employee.getCell());
           preparedStatement.setString(9, employee.getUnitNumber());
           preparedStatement.setString(10, employee.getStreetAddress());
           preparedStatement.setString(11, employee.getCity());
           preparedStatement.setString(12, employee.getPostalCode());
           preparedStatement.setString(13, employee.getCountry());
           preparedStatement.setString(14, employee.getPosition());
           preparedStatement.setDouble(15, employee.getSalary());
           preparedStatement.setString(16, employee.getRole());  
           preparedStatement.setInt(17, employee.getEmployeeId());  
                  
           return preparedStatement.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;
    }
    
    
}
