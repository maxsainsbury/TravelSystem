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
     * @param firstName String
     * @param lastName String
     * @param email String
     * @param phone String
     * @param unitNumber String
     * @param streetAddress String
     * @param city String
     * @param country String
     * @param postalCode String
     * @param dob String
     * @param userId int
     * @param SIN int
     * @param status String
     * @param cell String
     * @param position String
     * @param salary double
     * @param role String
     * @param createdBy int
     * @param employeeId int
     * 
     * 
     */
    public Employee(String firstName, String lastName, String email, String phone, String unitNumber, String streetAddress, String city, String country, String postalCode, String dob, int userId, int SIN, String status, String cell, String position, double salary, String role, int createdBy, int employeeId) {
        super(firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, dob, userId);
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
     * Constructor for Employee class for when id is not known
     * use for when creating a new employee 
     * 
     * @param firstName
     * @param lastName 
     * @param email 
     * @param SIN 
     * @param phone 
     * @param unitNumber 
     * @param streetAddress  
     * @param city 
     * @param country 
     * @param postalCode 
     * @param dob 
     * @param status 
     * @param cell 
     * @param position 
     * @param salary 
     * @param role 
     * @param username 
     * @param password 
     * @param createdBy 
     */
    public Employee(String firstName, String lastName, String email, int SIN, String phone, String unitNumber, String streetAddress, 
            String city, String country, String postalCode, String dob, String status, String cell, String position, 
            double salary, String role, String username, String password, int createdBy) {
        super(firstName, lastName, email, phone, unitNumber, streetAddress, city, country, postalCode, dob, userType, username, password);
        this.SIN = SIN;
        this.status = status;
        this.cell = cell;
        this.role = role;
        this.position = position;
        this.salary = salary;
        this.createdBy = createdBy;
    }
    
    /**
     * Constructor to create employee object deletion view. 
     * @param employeeId
     * @param firstName
     * @param lastName
     * @param phone
     * @param email
     * @param role 
     */
    public Employee(int employeeId, String firstName, String lastName, String phone, String email, String role) {
        super(firstName, lastName, phone, email);
        this.role = role;
        this.employeeId = employeeId;
        
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
