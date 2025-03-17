package travelsystem;

import controller.CustomerController;
import view.AddEmployeeView;
import controller.EmployeeController;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import dao.UserDAO;
import view.AddCustomerView;

/**
 *
 * @author C0457438
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
    }
    
}
