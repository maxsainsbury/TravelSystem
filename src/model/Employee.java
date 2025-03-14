package model;

/**
 *
 * @author Max Sainsbury
 */
public class Employee extends User{
    private int SIN;
    private String status;
    private String cell;
    private String role;
    private String position;
    private double salary;
    private int createdBy;
    private int employeeId;
    private static final String userType = "employee";
    
    /**
     * Constructor for Employee class for when id is known
     * use for when getting employee from the database
     * 
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     * @param id id of the employee
     */
    public Employee(String username, String firstName, String lastName, String email, String phone, String unitNumber, String streetAddress, String city, String country, String postalCode, String dob, int userId, int SIN, String status, String cell, String position, double salary, String role, int createdBy, int employeeId) {
        super(username, firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, dob, userId);
        this.SIN = SIN;
        this.status = status;
        this.cell = cell;
        this.role = role;
        this.position = position;
        this.salary = salary;
        this.createdBy = createdBy;
        this.employeeId = employeeId;
    }
    
    /**
     * Constructor for Employee class for when id is not know
     * use for when creating a new employee 
     * 
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     */
    public Employee(String username, String firstName, String lastName, String email, String phone, String unitNumber, String streetAddress, String city, String country, String postalCode, String dob, String password, String status, String cell, String position, double salary, String role, int createdBy) {
        super(username, firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, dob, password, Employee.userType);
        this.SIN = SIN;
        this.status = status;
        this.cell = cell;
        this.role = role;
        this.position = position;
        this.salary = salary;
        this.createdBy = createdBy;
    }

    public int getSIN() {
        return SIN;
    }

    public void setSIN(int SIN) {
        this.SIN = SIN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    
    
    
}
