package model;

/**
 *
 * @author Max Sainsbury
 */
public class Employee extends User{
    private String password;
    
    /**
     * Constructor for Employee class for when id is known
     * use for when getting employee from the database
     * 
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     * @param id id of the employee
     */
    public Employee(String firstName, String lastName, String password, int id) {
        super(firstName, lastName, id);
        this.password = password;
    }
    
    /**
     * Constructor for Employee class for when id is not know
     * use for when creating a new employee 
     * 
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     */
    public Employee(String firstName, String lastName, String password) {
        super(firstName, lastName);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
