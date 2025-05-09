package controller;

import controller.*;
import view.*;
import dao.*;
import model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Booking;



/**
 *
 * @author Goen Choi
 */
public class BookingController {
    private BookingDAO bookingDao;
    private AddBookingView addBookingView;
    private SearchBookingView searchBookingView;
    private EditBookingView editBookingView;
    private Booking bookingToEdit;
    private DeleteBookingView deleteBookingView;
    private int bookingIdToDelete;
    private String numberOnlyRegEx = "^[0-9]+$";
    // Date must be yyyy-mm-dd format
    private String dateRegEx = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])";
    private String floatRegEx = "[-+]?\\d*[.,]\\d+|\\d+";
    
    /**
     * Constructor for add booking view.
     * @param bookingDao
     * @param addBookingView 
     */
    public BookingController(BookingDAO bookingDao, AddBookingView addBookingView) {
        this.bookingDao = bookingDao;
        this.addBookingView = addBookingView;
        
        addBookingView.addBookingBtnActionListener(new AddBooking());
        addBookingView.clearAllBtnActionListener(new AddBookingClrAllTxt());
//        addBookingView.payBtnActionListener();   
    }
    
    /**
     * Constructor for search booking view
     * @param bookingDao
     * @param searchBookingView 
     */
    public BookingController(BookingDAO bookingDao, SearchBookingView searchBookingView) {
        this.bookingDao = bookingDao;
        this.searchBookingView = searchBookingView;
        
        searchBookingView.searchIdBtnActionListener(new SearchBookingById());
        searchBookingView.searchCustomerBtnActionListener(new SearchBookingByCusId());
        searchBookingView.searchAllBtnActionListener(new SearchAllBooking());
        searchBookingView.clearAllBtnActionListener(new clearAllSearchBooking());
    }
    
    /**
     * Constructor for edit booking view
     * @param bookingDao
     * @param editBookingView 
     */
    public BookingController(BookingDAO bookingDao, EditBookingView editBookingView) {
        this.bookingDao = bookingDao;
        this.editBookingView = editBookingView;
        
        editBookingView.searchBtnActionListener(new SearchBookingToEdit());
        editBookingView.clearAllBtnActionListener(new ClearAllEditView());
        editBookingView.editBtnActionListener(new EditBooking());
    }
    
    /**
     * Constructor for deleting booking in database
     * @param bookingDao
     * @param deleteBookingView 
     */
    public BookingController(BookingDAO bookingDao, DeleteBookingView deleteBookingView) {
        this.bookingDao = bookingDao;
        this.deleteBookingView = deleteBookingView;
       
        deleteBookingView.searchBtnActionListener(new SearchBookingToDelete());
        deleteBookingView.deleteBtnActionListener(new DeleteBooking());
        deleteBookingView.clearAllBtnActionListener(new ClearAllDelBookingView());
    }
    /**
     * Class gets values from user and adds new booking to booking table in database.
     */
    private class AddBooking implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the input valeus and store it in variables
            String empIdString = addBookingView.getEmpIdTxt().getText().strip();
            String cusIdString = addBookingView.getCustomerIdTxt().getText().strip();
            String tripIdString = addBookingView.getTripIdTxt().getText().strip();
            if(!(empIdString.matches(numberOnlyRegEx)) || !(cusIdString.matches(numberOnlyRegEx)) 
                    || !(tripIdString.matches(numberOnlyRegEx))) {
                JOptionPane.showMessageDialog(null, "Id can consist of numbers only.");
                return;
            }           
            int employeeId = Integer.parseInt(empIdString);
            int customerId = Integer.parseInt(cusIdString);
            int tripId = Integer.parseInt(tripIdString);
            
            String priceString = addBookingView.getPriceTxt().getText().strip();
            if(!priceString.matches(floatRegEx)){
                JOptionPane.showMessageDialog(null, "Amount must be a valid dollar.cent format.");
                return;
            }
            double totalPrice = Double.parseDouble(priceString);
            
            String date = addBookingView.getDateTxt().getText();
            if(!date.matches(dateRegEx)) {
                JOptionPane.showMessageDialog(null, "Date must be in yyyy-mm-dd format.");
                return;
            }          
            //Create new instance of booking using the varibles
            Booking booking = new Booking(customerId, tripId, totalPrice, date, employeeId);
            boolean result = bookingDao.addBooking(booking);
            
            if(result) {
                JOptionPane.showMessageDialog(null, "Successfully added a new booking.");
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to add new booking");
            }           
        }   
    }
    
    /**
     * Clears all text on add booking view
     */
    private class AddBookingClrAllTxt implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addBookingView.getEmpIdTxt().setText("");
            addBookingView.getCustomerIdTxt().setText("");
            addBookingView.getTripIdTxt().setText("");
            addBookingView.getPriceTxt().setText("");
            addBookingView.getDateTxt().setText("");
        }
    }
    
    /**
     * Class that retrieves trip and booking data from database that matches the
     * booking id that was entered in by user.
     */
    private class SearchBookingById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                // Validate id with regex
                String id = searchBookingView.getBookingIdTxt().getText().strip();
                if(!id.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Booking Id can consist of numbers only.");   
                    return;
                }
                int bookingId = Integer.parseInt(id);

               DefaultTableModel model = (DefaultTableModel)searchBookingView.getBookingTbl().getModel();
               model.setRowCount(0);

               if( bookingId != 0 ) {
                   TripAndBooking tripAndBookingObj = bookingDao.fetchBookingByBookId(bookingId);
                   Object[] row = {
                       tripAndBookingObj.getBookingObject().getBookingId(),
                       tripAndBookingObj.getBookingObject().getEmployeeId(),
                       tripAndBookingObj.getBookingObject().getCustomerId(),
                       tripAndBookingObj.getBookingObject().getTripId(),
                       tripAndBookingObj.getTripObject().getOrigin(),
                       tripAndBookingObj.getTripObject().getDestination(),
                       tripAndBookingObj.getBookingObject().getBookingDate(),
                       tripAndBookingObj.getTripObject().getPromotionId(),
                       tripAndBookingObj.getBookingObject().getTotalPrice()
                   };
                   model.addRow(row);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No bookings to display");              
            }
        }
    }
    
    /**
     * Class retrieves and displays booking and trip data that matches the
     * customer id that was entered in by the user.
     */
    public class SearchBookingByCusId implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
        // Validate input with regex
        String id = searchBookingView.getCustomerIdTxt().getText().strip();
        if(!id.matches(numberOnlyRegEx)) {
            JOptionPane.showMessageDialog(null, "Customer Id can consist of numbers only.");   
            return;
        }
        int customerId = Integer.parseInt(id);
        
        DefaultTableModel model = (DefaultTableModel)searchBookingView.getBookingTbl().getModel();
        model.setRowCount(0);
        
        ArrayList<TripAndBooking> tripsAndBookings = bookingDao.fetchBookingByCusId(customerId);
            if(tripsAndBookings.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No bookings to display.");
            }

            for(TripAndBooking tripAndBookingObj: tripsAndBookings){
             Object[] row = {
             tripAndBookingObj.getBookingObject().getBookingId(),
             tripAndBookingObj.getBookingObject().getEmployeeId(),
             tripAndBookingObj.getBookingObject().getCustomerId(),
             tripAndBookingObj.getBookingObject().getTripId(),
             tripAndBookingObj.getTripObject().getOrigin(),
             tripAndBookingObj.getTripObject().getDestination(),
             tripAndBookingObj.getBookingObject().getBookingDate(),
             tripAndBookingObj.getTripObject().getPromotionId(),
             tripAndBookingObj.getBookingObject().getTotalPrice()
           };
           model.addRow(row);
            }                
        }  
    }
    
    /**
     * Class retrieves and displays all existing bookings in database on to 
     * table in search booking view.
     */
    public class SearchAllBooking implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        DefaultTableModel model = (DefaultTableModel)searchBookingView.getBookingTbl().getModel();
        model.setRowCount(0);
        
        ArrayList<TripAndBooking> tripsAndBookings = bookingDao.fetchAllBooking();
            if(tripsAndBookings.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No bookings to display.");
            }

            for(TripAndBooking tripAndBookingObj: tripsAndBookings){
             Object[] row = {
             tripAndBookingObj.getBookingObject().getBookingId(),
             tripAndBookingObj.getBookingObject().getEmployeeId(),
             tripAndBookingObj.getBookingObject().getCustomerId(),
             tripAndBookingObj.getBookingObject().getTripId(),
             tripAndBookingObj.getTripObject().getOrigin(),
             tripAndBookingObj.getTripObject().getDestination(),
             tripAndBookingObj.getBookingObject().getBookingDate(),
             tripAndBookingObj.getTripObject().getPromotionId(),
             tripAndBookingObj.getBookingObject().getTotalPrice()
           };
           model.addRow(row);
           }                
        }  
    }
    
    /**
     * Class clears all text fields and clears table results for search booking view.
     */
    private class clearAllSearchBooking implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            searchBookingView.getBookingIdTxt().setText("");
            searchBookingView.getCustomerIdTxt().setText("");
            DefaultTableModel model = (DefaultTableModel)searchBookingView.getBookingTbl().getModel();
            model.setRowCount(0);
        }
    
    }
    
    /**
     * Class searches booking in database that matches booking id that user has entered.
     * Displays the attributes of the retrieves booking on the view.
     */
    private class SearchBookingToEdit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                // Validate booking id input with regex
                String bookingIdString = editBookingView.getBookingIdTxt().getText().strip();
                if(!bookingIdString.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Booking Id can consist onf numbers only.");
                    return;
                }
                int bookingId = Integer.parseInt(bookingIdString);

                if(bookingId != 0) {
                    TripAndBooking tripAndBooking = bookingDao.fetchBookingByBookId(bookingId);
                    bookingToEdit = tripAndBooking.getBookingObject();

                    //concatenate empty string to int types to convert to string type
                    editBookingView.getEmployeeIdTxt().setText("" + bookingToEdit.getEmployeeId());
                    editBookingView.getCustomerIdTxt().setText("" + bookingToEdit.getCustomerId());
                    editBookingView.getBookingDateTxt().setText(bookingToEdit.getBookingDate().toString());
                    editBookingView.getTripIdTxt().setText("" + bookingToEdit.getTripId());
                    editBookingView.getTotalPriceTxt().setText("" + bookingToEdit.getTotalPrice());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No bookings to display.");
            } 
        }
    
    }
    
    /**
     * Class uses the bookingToEdit object that holds the booking id that was searched.
     * Stores the user input to the bookingToEdit attributes and updates the booking table
     * using this new data. 
     */
    private class EditBooking implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Validate all input with regex
            String bookingIdString = editBookingView.getBookingIdTxt().getText().strip();
            String cusIdString = editBookingView.getCustomerIdTxt().getText().strip();
            String empIdString = editBookingView.getEmployeeIdTxt().getText().strip();
            String tripIdString = editBookingView.getTripIdTxt().getText().strip();
            if(!(bookingIdString.matches(numberOnlyRegEx)) || !(empIdString.matches(numberOnlyRegEx)) 
                    || !(cusIdString.matches(numberOnlyRegEx)) || !(tripIdString.matches(numberOnlyRegEx))) {
                JOptionPane.showMessageDialog(null, "Booking Id, employee Id, customer Id, and trip  can "
                    + "consist of numbers only.");
                return;
            }   
            bookingToEdit.setBookingId(Integer.parseInt(bookingIdString));
            bookingToEdit.setCustomerId(Integer.parseInt(cusIdString));
            bookingToEdit.setEmployeeId(Integer.parseInt(empIdString));
            bookingToEdit.setTripId(Integer.parseInt(tripIdString));
            
            String price = editBookingView.getTotalPriceTxt().getText().strip();
            if(!price.matches(floatRegEx)) {
                JOptionPane.showMessageDialog(null, "Price can consist of numbers and decimal point only.");
                return;
            }
            bookingToEdit.setTotalPrice(Double.parseDouble(price));
            
            String dateString = editBookingView.getBookingDateTxt().getText().strip();
            if(!dateString.matches(dateRegEx)) {
                JOptionPane.showMessageDialog(null, "Date muse be in yyyy-mm-dd format.");
                return;
            }
            bookingToEdit.setBookingDate(LocalDate.parse(dateString));

            boolean result = bookingDao.updateBooking(bookingToEdit);          
            if (result){
                JOptionPane.showMessageDialog(null, "Successfully updated booking.");
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to update booking. ");
            } 
            
        }   
    }
    
    private class ClearAllEditView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editBookingView.getBookingIdTxt().setText("");
            editBookingView.getCustomerIdTxt().setText("");
            editBookingView.getEmployeeIdTxt().setText("");
            editBookingView.getBookingDateTxt().setText("");
            editBookingView.getTripIdTxt().setText("");
            editBookingView.getTotalPriceTxt().setText("");
            
            bookingToEdit = null;
        }  
    }
    
    private class SearchBookingToDelete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String id = deleteBookingView.getBookingIdTxt().getText().strip();
                if(!id.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Id must consist of numbers only.");
                    return;
                }
                bookingIdToDelete = Integer.parseInt(id);

               DefaultTableModel model = (DefaultTableModel)deleteBookingView.getBookingTbl().getModel();
               model.setRowCount(0);

               if( bookingIdToDelete != 0 ) {
                   TripAndBooking tripAndBookingObj = bookingDao.fetchBookingByBookId(bookingIdToDelete);
                   Object[] row = {
                       tripAndBookingObj.getBookingObject().getBookingId(),
                       tripAndBookingObj.getBookingObject().getEmployeeId(),
                       tripAndBookingObj.getBookingObject().getCustomerId(),
                       tripAndBookingObj.getBookingObject().getTripId(),
                       tripAndBookingObj.getTripObject().getOrigin(),
                       tripAndBookingObj.getTripObject().getDestination(),
                       tripAndBookingObj.getBookingObject().getBookingDate(),
                       tripAndBookingObj.getTripObject().getPromotionId(),
                       tripAndBookingObj.getBookingObject().getTotalPrice()
                   };
                   model.addRow(row);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No bookings to display");              
            }
        }   
    }
    
    /**
     * Class deletes the booking in database that matches the booking id that was searched.
     */
    private class DeleteBooking implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean result = bookingDao.deleteBooking(bookingIdToDelete);
            
            if (result){
                JOptionPane.showMessageDialog(null, "Successfully deleted booking.");
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to dellete booking. ");
            }  
            // Clear variable after deleting.
            bookingIdToDelete = 0;          
        }    
    }
    
    /**
     * Class clears all text and tables in delete booking view.
     */
    private class ClearAllDelBookingView implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteBookingView.getBookingIdTxt().setText("");
            DefaultTableModel model = (DefaultTableModel)deleteBookingView.getBookingTbl().getModel();
            model.setRowCount(0);
            bookingIdToDelete = 0;
        }    
    }
}
