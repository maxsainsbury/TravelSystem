package model;

/**
 *
 * @author Max Sainsbury
 */
public class User {
    private String firstName;
    private String lastName;
    private int id;
    
    /**
     * Constructor for the User class when the id is known
     * use when getting from the database
     * 
     * @param id id of the user
     * @param firstName first name of the user
     * @param lastName last name of the user
     */
    public User(String firstName, String lastName, int id) {
        this(firstName, lastName);
        this.id = id;
    }
    
    /**
     * Constructor for the User class when the id is not known
     * use when making a new user
     * 
     * @param firstName first name of the user
     * @param lastName last name of the user
     */
    public User(String firstName, String lastName) {
        //call the 3 param constructor
        //must change id to next available when adding to the database
        this.firstName = firstName;
        this.lastName = lastName;
        
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
