package dao;

import model.Employee;
import utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Connection;

/**
 *
 * @author goenchoi
 */
public class EmployeeDAO {
    private Employee employee;
    
    // Default constructor
    public EmployeeDAO() { }
    
    // EmployeeDAO constructor when adding new employee
    public EmployeeDAO(String firstName, String lastName, String email, int SIN, String phone, String unitNumber, 
            String streetAddress, String city, String country, String postalCode, String dob, String status, 
            String cell, String position, double salary, String role, int createdBy, int userId, int employeeID) {
        
        this.employee = new Employee(firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, dob, userId, SIN, status, cell, position, salary, role, createdBy, employeeID);
    }
    
    // Sending data to employee table in database when adding new employee.
    public boolean addEmployee(Employee employee) {
        String query = "INSERT INTO employee(employee_id, first_name, last_name, dob, "
                        + "email, SIN, status, phone, cell, unit_number, street_address, "
                        + "city, postal_code, country, position, salary, role, created_by, user_id) )"
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
    
}
