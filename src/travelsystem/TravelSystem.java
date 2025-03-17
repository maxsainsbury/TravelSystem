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
        AddCustomerView addCustomerView = new AddCustomerView();
        CustomerDAO custDao = new CustomerDAO();
        UserDAO userDao = new UserDAO();
        CustomerController custController = new CustomerController(custDao, addCustomerView, userDao);
        addCustomerView.setVisible(true);
        //Test Employee Create, Read, Update, Delete functionality. 
        AdminMainFrame adminMainFrame = new AdminMainFrame();
        adminMainFrame.setVisible(true);
    }
    
}
