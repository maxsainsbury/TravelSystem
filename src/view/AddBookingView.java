package view;

import controller.PaymentController;
import dao.PaymentDAO;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Ebba de Groot
 */
public class AddBookingView extends javax.swing.JFrame {

    /**
     * Creates new form BookTrip
     */
    public AddBookingView() {
        initComponents();
        setDefaultCloseOperation(SearchEmployeeView.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customerIdLbl = new javax.swing.JLabel();
        customerIdTxt = new javax.swing.JTextField();
        tripIdLbl = new javax.swing.JLabel();
        tripIdTxt = new javax.swing.JTextField();
        addBookingBtn = new javax.swing.JButton();
        clearAllBtn = new javax.swing.JButton();
        priceLbl = new javax.swing.JLabel();
        priceTxt = new javax.swing.JTextField();
        dateLbl = new javax.swing.JLabel();
        dateTxt = new javax.swing.JTextField();
        payBtn = new javax.swing.JButton();
        empIdLbl = new javax.swing.JLabel();
        empIdTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add Booking");

        customerIdLbl.setText("Customer ID:");

        tripIdLbl.setText("Trip ID:");

        addBookingBtn.setText("Add Booking");
        addBookingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookingBtnActionPerformed(evt);
            }
        });

        clearAllBtn.setText("Clear All");
        clearAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllBtnActionPerformed(evt);
            }
        });

        priceLbl.setText("Total Price:");

        dateLbl.setText("Date Booked (YYYY-MM-DD):");

        payBtn.setText("Add Payment");
        payBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payBtnActionPerformed(evt);
            }
        });

        empIdLbl.setText("Employee ID:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(empIdLbl)
                    .addComponent(dateLbl)
                    .addComponent(priceLbl)
                    .addComponent(customerIdLbl)
                    .addComponent(tripIdLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(payBtn)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addBookingBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearAllBtn))
                    .addComponent(customerIdTxt)
                    .addComponent(tripIdTxt)
                    .addComponent(priceTxt)
                    .addComponent(dateTxt)
                    .addComponent(empIdTxt))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empIdLbl)
                    .addComponent(empIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerIdLbl)
                    .addComponent(customerIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tripIdLbl)
                    .addComponent(tripIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceLbl)
                    .addComponent(priceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLbl)
                    .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBookingBtn)
                    .addComponent(clearAllBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(payBtn)
                .addGap(65, 65, 65))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBookingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookingBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addBookingBtnActionPerformed

    private void clearAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearAllBtnActionPerformed

    private void payBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payBtnActionPerformed
        AddPaymentView addPaymentView = new AddPaymentView();
        PaymentDAO paymentDAO = new PaymentDAO();
        PaymentController paymentController = new PaymentController(addPaymentView, paymentDAO);
        addPaymentView.setVisible(true);
    }//GEN-LAST:event_payBtnActionPerformed

    public JButton getAddBookingBtn() {
        return addBookingBtn;
    }

    public void setAddBookingBtn(JButton addBookingBtn) {
        this.addBookingBtn = addBookingBtn;
    }

    public JButton getClearAllBtn() {
        return clearAllBtn;
    }

    public void setClearAllBtn(JButton clearAllBtn) {
        this.clearAllBtn = clearAllBtn;
    }

    public JTextField getCustomerIdTxt() {
        return customerIdTxt;
    }

    public void setCustomerIdTxt(JTextField customerIdTxt) {
        this.customerIdTxt = customerIdTxt;
    }

    public JLabel getDateLbl() {
        return dateLbl;
    }

    public void setDateLbl(JLabel dateLbl) {
        this.dateLbl = dateLbl;
    }

    public JTextField getDateTxt() {
        return dateTxt;
    }

    public void setDateTxt(JTextField dateTxt) {
        this.dateTxt = dateTxt;
    }

    public JLabel getEmpIdLbl() {
        return empIdLbl;
    }

    public void setEmpIdLbl(JLabel empIdLbl) {
        this.empIdLbl = empIdLbl;
    }

    public JTextField getEmpIdTxt() {
        return empIdTxt;
    }

    public void setEmpIdTxt(JTextField empIdTxt) {
        this.empIdTxt = empIdTxt;
    }

    public JButton getPayBtn() {
        return payBtn;
    }

    public void setPayBtn(JButton payBtn) {
        this.payBtn = payBtn;
    }

    public JTextField getPriceTxt() {
        return priceTxt;
    }

    public void setPriceTxt(JTextField priceTxt) {
        this.priceTxt = priceTxt;
    }

    public JTextField getTripIdTxt() {
        return tripIdTxt;
    }

    public void setTripIdTxt(JTextField tripIdTxt) {
        this.tripIdTxt = tripIdTxt;
    }

    public void addBookingBtnActionListener(ActionListener myActionListener) {
        addBookingBtn.addActionListener(myActionListener);
    }
    
    public void clearAllBtnActionListener(ActionListener myActionListener) {
        clearAllBtn.addActionListener(myActionListener);
    }
    
    public void payBtnActionListener(ActionListener myActionListener) {
        payBtn.addActionListener(myActionListener);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddBookingView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBookingView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBookingView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBookingView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddBookingView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBookingBtn;
    private javax.swing.JButton clearAllBtn;
    private javax.swing.JLabel customerIdLbl;
    private javax.swing.JTextField customerIdTxt;
    private javax.swing.JLabel dateLbl;
    private javax.swing.JTextField dateTxt;
    private javax.swing.JLabel empIdLbl;
    private javax.swing.JTextField empIdTxt;
    private javax.swing.JButton payBtn;
    private javax.swing.JLabel priceLbl;
    private javax.swing.JTextField priceTxt;
    private javax.swing.JLabel tripIdLbl;
    private javax.swing.JTextField tripIdTxt;
    // End of variables declaration//GEN-END:variables
}
