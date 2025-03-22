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
        //Test Employee create, read, update, delete functionality. 
//        AdminMainFrame adminMainFrame = new AdminMainFrame();
//        adminMainFrame.setVisible(true);
        
        //Test Promotion create, read, update, delete functionality.
//        EmployeeMainFrame employeeMainFrame = new EmployeeMainFrame();
//        employeeMainFrame.setVisible(true);
        
        //Test Booking
//        AddBookingView addBookingView = new AddBookingView();
//        BookingDAO bookingDao = new BookingDAO();
//        BookingController bookingController = new BookingController(bookingDao, addBookingView);
//        addBookingView.setVisible(true);
//
//        SearchBookingView searchBookingView = new SearchBookingView();
//        BookingController searchBookingController = new BookingController(bookingDao, searchBookingView);
//        searchBookingView.setVisible(true);
//
//        EditBookingView editBookingView = new EditBookingView();
//        BookingController editBookingController = new BookingController(bookingDao, editBookingView);
//        editBookingView.setVisible(true);
//          
//        DeleteBookingView deleteBookingView = new DeleteBookingView();
//        BookingController deleteookingController = new BookingController(bookingDao, deleteBookingView);
//        deleteBookingView.setVisible(true);
        /*
        AddBookingView addBookingView = new AddBookingView();
        BookingDAO bookingDao = new BookingDAO();
        BookingController bookingController = new BookingController(bookingDao, addBookingView);
        addBookingView.setVisible(true);

          //Test Employee
          SearchEmployeeView searchEmpView = new SearchEmployeeView();
          EmployeeDAO empDao = new EmployeeDAO();
          EmployeeController searchEmpController = new EmployeeController(empDao, searchEmpView);
          searchEmpView.setVisible(true);
          
        DeleteBookingView deleteBookingView = new DeleteBookingView();
        BookingController deleteookingController = new BookingController(bookingDao, deleteBookingView);
        deleteBookingView.setVisible(true);
        */
        //Payment testing
        AddPaymentView addPaymentView = new AddPaymentView();
        PaymentDAO paymentDao = new PaymentDAO();
        PaymentController paymentController = new PaymentController(addPaymentView, paymentDao);
        addPaymentView.setVisible(true);
        
        SearchPaymentView searchPaymentView = new SearchPaymentView();
        PaymentController paymentSearchController = new PaymentController(searchPaymentView, paymentDao);
        searchPaymentView.setVisible(true);
        
        //register testing
        RegisterCustomerView registerCustomerView = new RegisterCustomerView();
        CustomerDAO customerDAO = new CustomerDAO();
        UserDAO userDAO = new UserDAO();
        CustomerController customerController = new CustomerController(customerDAO, registerCustomerView, userDAO);
        registerCustomerView.setVisible(true);
//        AddEmployeeView addEmpView = new AddEmployeeView();
//        EmployeeDAO empDao = new EmployeeDAO();
//        UserDAO userDao = new UserDAO();
//        EmployeeController addEmpController = new EmployeeController(empDao, addEmpView, userDao);
//        addEmpView.setVisible(true);

    }
    
}
