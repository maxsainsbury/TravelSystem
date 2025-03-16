package travelsystem;

import view.AddEmployeeView;
import controller.EmployeeController;
import dao.EmployeeDAO;
import dao.UserDAO;
import view.DeleteEmployeeView;
import view.SearchEmployeeView;

/**
 *
 * @author C0457438
 */
public class TravelSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AddEmployeeView addEmployeeView = new AddEmployeeView();
        EmployeeDAO empDao = new EmployeeDAO();
        UserDAO userDao = new UserDAO();
        EmployeeController empController = new EmployeeController(empDao, addEmployeeView, userDao);
        addEmployeeView.setVisible(true);
        
        DeleteEmployeeView deleteEmployeeView = new DeleteEmployeeView();
        EmployeeController empDeleteController = new EmployeeController(empDao, deleteEmployeeView, userDao);
        deleteEmployeeView.setVisible(true);
    }
    
}
