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
        //Test Employee create, read, update, delete functionality. 
//        AdminMainFrame adminMainFrame = new AdminMainFrame();
//        adminMainFrame.setVisible(true);
        
        //Test Promotion create, read, update, delete functionality.
//        EmployeeMainFrame employeeMainFrame = new EmployeeMainFrame();
//        employeeMainFrame.setVisible(true);
        
        //Test Booking
        AddBookingView addBookingView = new AddBookingView();
        BookingDAO bookingDao = new BookingDAO();
        BookingController bookingController = new BookingController(bookingDao, addBookingView);
        addBookingView.setVisible(true);

        SearchBookingView searchBookingView = new SearchBookingView();
        BookingController searchBookingController = new BookingController(bookingDao, searchBookingView);
        searchBookingView.setVisible(true);

        EditBookingView editBookingView = new EditBookingView();
        BookingController editBookingController = new BookingController(bookingDao, editBookingView);
        editBookingView.setVisible(true);
          
        DeleteBookingView deleteBookingView = new DeleteBookingView();
        BookingController deleteookingController = new BookingController(bookingDao, deleteBookingView);
        deleteBookingView.setVisible(true);
    }
    
}
