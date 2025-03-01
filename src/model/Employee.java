package model;

/**
 *
 * @author Max Sainsbury
 */
public class Employee extends User{
    
    /**
     * Constructor for Employee class for when id is known
     * use for when getting employee from the database
     * 
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     * @param id id of the employee
     */
    public Employee(String firstName, String lastName, int id) {
        super(firstName, lastName, id);
    }
    
    /**
     * Constructor for Employee class for when id is not know
     * use for when creating a new employee 
     * 
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     */
    public Employee(String firstName, String lastName) {
        super(firstName, lastName);
    }
}
