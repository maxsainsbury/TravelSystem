package travelsystem;


import view.*;
import controller.*;
import dao.*;
import model.*;

/**
 *
 * @author Goen, Max, Ebba
 */
public class TravelSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Test Employee Create, Read, Update, Delete functionality. 
        LoginDAO loginDAO = new LoginDAO();
        LoginView loginView = new LoginView();
        CustomerMainFrame customerMainFrame = new CustomerMainFrame();
        EmployeeMainFrame employeeMainFrame = new EmployeeMainFrame();
        AdminMainFrame adminMainFrame = new AdminMainFrame();
        LoginController loginController = new LoginController(loginView, loginDAO, customerMainFrame, employeeMainFrame, adminMainFrame);
        loginView.setVisible(true);
        
        //adminMainFrame.setVisible(true);
        

    }
    
}
