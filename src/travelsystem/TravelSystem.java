package travelsystem;


import view.*;
import controller.*;
import dao.*;

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
        LoginController loginController = new LoginController(loginView, loginDAO);
        loginView.setVisible(true);
    }
    
}
