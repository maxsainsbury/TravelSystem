package controller;

import dao.PaymentDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    /**
     * Constructor for controlling the addPaymentView
     * @param addPaymentView
     * @param paymentDAO 
     */
    public PaymentController(AddPaymentView addPaymentView, PaymentDAO paymentDAO) {
        this.addPaymentView = addPaymentView;
        this.paymentDAO = paymentDAO;
        
        this.addPaymentView.payBtnActionListener(new AddPayment());
        this.addPaymentView.searchIdBtnActionListener(new SearchPaymentId());
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
            addPaymentView.getIdTxt().setText("");
            addPaymentView.getEmpIdTxt().setText("");
            addPaymentView.getDateTxt().setText("");
            addPaymentView.getBookingIdTxt().setText("");
            addPaymentView.getAmountTxt.setText("");
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
            String payId = addPaymentView.getIdTxt().getText();
            String empId = addPaymentView.getEmpIdTxt().getText();
            String payDate = addPaymentView.getDateTxt().getText();
            String bookId = addPaymentView.getBookingIdTxt().getText();
            String payAmount = addPaymentView.getAmountTxt.getText();
            boolean result;
            try {
                if(!promotionIdString.equals("")) {
                    promotionId = Integer.parseInt(promotionIdString);
                    Trip trip = new Trip(origin, destination, departureDate, returnDate, promotionId, status);
                    result = tripDAO.addTripRecordPromo(trip);
                }
                else {
                    Payment payment = new Payment(bookId, empId, payAmount, bookId, 0, status);
                    result = tripDAO.addTripRecordNoPromo(trip);
                }
                if (result) {
                    JOptionPane.showMessageDialog(null, "Payment record created successfully!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Payment record was not created!");
                }
            }
            catch (Exception err) {
                JOptionPane.showMessageDialog(null, "One or more dates not entered correctly!");
            }
        }
    }
    
}
