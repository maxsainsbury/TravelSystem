package controller;

import dao.PaymentDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Payment;
import view.AddPaymentView;
import view.SearchPaymentView;

/**
 *
 * @author Ebba de Groot
 */
public class PaymentController {
    private AddPaymentView addPaymentView;
    private SearchPaymentView searchPaymentView;
    private PaymentDAO paymentDAO;
    // Regex variables
    private String numberOnlyRegEx = "^[0-9]+$";
    private String dateRegEx = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])";
    private String floatRegEx = "[-+]?\\d*[.,]\\d+|\\d+";
    
    /**
     * Constructor for controlling the addPaymentView
     * @param addPaymentView
     * @param paymentDAO 
     */
    public PaymentController(AddPaymentView addPaymentView, PaymentDAO paymentDAO) {
        this.addPaymentView = addPaymentView;
        this.paymentDAO = paymentDAO;
        
        this.addPaymentView.payBtnActionListener(new AddPayment());
        this.addPaymentView.clearAllBtnActionListener(new ClearAddTextFields());
    }
    
    /**
     * Constructor for controlling the searchPaymentView
     * @param searchPaymentView
     * @param paymentDAO 
     */
    public PaymentController(SearchPaymentView searchPaymentView, PaymentDAO paymentDAO) {
        this.searchPaymentView = searchPaymentView;
        this.paymentDAO = paymentDAO;
        
        this.searchPaymentView.searchIdBtnActionListener(new SearchPaymentId());
        this.searchPaymentView.searchBookingBtnActionListener(new SearchBookingId());
        this.searchPaymentView.searchAllBtnBtnActionListener(new SearchAll());
        this.searchPaymentView.clearAllBtnActionListener(new ClearSearchTextFields());
    }
    
    private class ClearAddTextFields implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addPaymentView.getEmpIdTxt().setText("");
            addPaymentView.getDateTxt().setText("");
            addPaymentView.getBookingIdTxt().setText("");
            addPaymentView.getAmountTxt().setText("");
        }
    }
    
    private class ClearSearchTextFields implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchPaymentView.getSearchPaymentTbl().getModel();
            model.setRowCount(0);
            searchPaymentView.getPaymentIdTxt().setText("");
            searchPaymentView.getBookingIdTxt().setText("");
        }
    }
    
    private class AddPayment implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String bookIdString = addPaymentView.getBookingIdTxt().getText().strip();
            String empIdString = addPaymentView.getEmpIdTxt().getText().strip();
            if(!(empIdString.matches(numberOnlyRegEx)) || !(bookIdString.matches(numberOnlyRegEx))) {
                JOptionPane.showMessageDialog(null, "Id can consist of numbers only.");
                return;
            }  
            int bookId = Integer.parseInt(bookIdString);
            int empId = Integer.parseInt(empIdString);
            
            String payDate = addPaymentView.getDateTxt().getText().strip();
            if(!payDate.matches(dateRegEx)){
                JOptionPane.showMessageDialog(null, "Date must be in yyyy-mm-dd format.");
                return;
            }
            
            String payAmountString = addPaymentView.getAmountTxt().getText().strip();
            if(!payAmountString.matches(floatRegEx)){
                JOptionPane.showMessageDialog(null, "Amount must be a valid dollar.cent format.");
                return;
            }
            double payAmount = Double.parseDouble(payAmountString);
            
            String payMethod = (String) addPaymentView.getMethodCmbo().getSelectedItem();

            
            boolean result;
            
            try {
                Payment payment = new Payment(bookId, empId, payAmount, payMethod, payDate);
                result = paymentDAO.addPaymentRecord(payment);

                if(result) {
                    JOptionPane.showMessageDialog(null, "Successfully added a new payment record");
                } else {
                    JOptionPane.showMessageDialog(null, "Was not able to add new payment record.");
                } 
            }
            catch (Exception err) {
                JOptionPane.showMessageDialog(null, "Date not entered correctly!");
            }
        }
    }
    
    private class SearchAll implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchPaymentView.getSearchPaymentTbl().getModel();
            model.setRowCount(0);          
                  
            ArrayList<Payment> payments = paymentDAO.fetchAllPayments();
            
            if(payments.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No payments to display.");
            }

            for (Payment payment: payments) {
                Object[] row = {
                    payment.getPaymentId(),
                    payment.getBookingId(),
                    payment.getEmployeeId(),
                    payment.getPaymentDate(),
                    payment.getAmount(),
                    payment.getPaymentMethod(),
                    payment.getStatus()
                };                 
                model.addRow(row);
            }  
        }
    }
    
    private class SearchPaymentId implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchPaymentView.getSearchPaymentTbl().getModel();
            model.setRowCount(0);
            
            // Validate id with regex
            String id = searchPaymentView.getPaymentIdTxt().getText().strip();
            if(!id.matches(numberOnlyRegEx)) {
                JOptionPane.showMessageDialog(null, "Payment Id can consist of numbers only.");   
                return;
            }
            int paymentId = Integer.parseInt(id);
            
            try{
                if(paymentId != 0) {                    
                    Payment payment = paymentDAO.fetchPaymentById(paymentId);
                    Object[] row = {
                        payment.getPaymentId(),
                        payment.getBookingId(),
                        payment.getEmployeeId(),
                        payment.getPaymentDate(),
                        payment.getAmount(),
                        payment.getPaymentMethod(),
                        payment.getStatus()
                    };
                model.addRow(row);                    
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Payment Id does not exist.");
            }   
        }
    }
    
    private class SearchBookingId implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchPaymentView.getSearchPaymentTbl().getModel();
            model.setRowCount(0);
            
            // Validate id with regex
            String id = searchPaymentView.getBookingIdTxt().getText().strip();
            if(!id.matches(numberOnlyRegEx)) {
                JOptionPane.showMessageDialog(null, "Booking Id can consist of numbers only.");   
                return;
            }
            int bookingId = Integer.parseInt(id);
            
            try{
                if(bookingId != 0) {                    
                    Payment payment = paymentDAO.fetchPaymentByBookingId(bookingId);
                    Object[] row = {
                        payment.getPaymentId(),
                        payment.getBookingId(),
                        payment.getEmployeeId(),
                        payment.getPaymentDate(),
                        payment.getAmount(),
                        payment.getPaymentMethod(),
                        payment.getStatus()
                    };
                model.addRow(row);                    
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Booking Id does not exist.");
            }   
        }
    }
    
}
