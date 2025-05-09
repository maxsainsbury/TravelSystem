package view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Ebba de Groot
 */
public class SearchEmployeeView extends javax.swing.JFrame {

    /**
     * Creates new form SearchEmployeeView
     */
    public SearchEmployeeView() {
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

        empIdLbl = new javax.swing.JLabel();
        empIdTxt = new javax.swing.JTextField();
        positionLbl = new javax.swing.JLabel();
        positionTxt = new javax.swing.JTextField();
        searchIdBtn = new javax.swing.JButton();
        searchPositionBtn = new javax.swing.JButton();
        searchAllBtn = new javax.swing.JButton();
        clearAllBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSearchEmployees = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Employees");

        empIdLbl.setText("Employee ID:");

        positionLbl.setText("Position:");

        searchIdBtn.setText("Search");
        searchIdBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchIdBtnActionPerformed(evt);
            }
        });

        searchPositionBtn.setText("Search");
        searchPositionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPositionBtnActionPerformed(evt);
            }
        });

        searchAllBtn.setText("Search All");
        searchAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAllBtnActionPerformed(evt);
            }
        });

        clearAllBtn.setText("Clear All");
        clearAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllBtnActionPerformed(evt);
            }
        });

        tblSearchEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee Id", "Name", "Role", "Position", "Email", "Cell", "Address", "Country", "Postal Code", "Status", "SIN", "Created by"
            }
        ));
        tblSearchEmployees.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tblSearchEmployees);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(positionLbl)
                            .addComponent(empIdLbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchAllBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearAllBtn))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(positionTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(empIdTxt))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchIdBtn)
                                    .addComponent(searchPositionBtn)))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1061, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empIdLbl)
                    .addComponent(empIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchIdBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(positionLbl)
                    .addComponent(positionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchPositionBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchAllBtn)
                    .addComponent(clearAllBtn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchIdBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchIdBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchIdBtnActionPerformed

    private void searchPositionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPositionBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchPositionBtnActionPerformed

    private void searchAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchAllBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchAllBtnActionPerformed

    private void clearAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearAllBtnActionPerformed

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
            java.util.logging.Logger.getLogger(SearchEmployeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchEmployeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchEmployeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchEmployeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchEmployeeView().setVisible(true);
            }
        });
    }
    
    
    public JButton getClearAllBtn() {
        return clearAllBtn;
    }

    public void setClearAllBtn(JButton clearAllBtn) {
        this.clearAllBtn = clearAllBtn;
    }

    public JTextField getEmpIdTxt() {
        return empIdTxt;
    }

    public void setEmpIdTxt(JTextField empIdTxt) {
        this.empIdTxt = empIdTxt;
    }

    public JTextField getPositionTxt() {
        return positionTxt;
    }

    public void setPositionTxt(JTextField positionTxt) {
        this.positionTxt = positionTxt;
    }

    public JButton getSearchAllBtn() {
        return searchAllBtn;
    }

    public void setSearchAllBtn(JButton searchAllBtn) {
        this.searchAllBtn = searchAllBtn;
    }

    public JButton getSearchIdBtn() {
        return searchIdBtn;
    }

    public void setSearchIdBtn(JButton searchIdBtn) {
        this.searchIdBtn = searchIdBtn;
    }

    public JButton getSearchPositionBtn() {
        return searchPositionBtn;
    }

    public void setSearchPositionBtn(JButton searchPositionBtn) {
        this.searchPositionBtn = searchPositionBtn;
    }

    public JTable getTblSearchEmployees() {
        return tblSearchEmployees;
    }

    public void setTblSearchEmployees(JTable tblSearchEmployees) {
        this.tblSearchEmployees = tblSearchEmployees;
    }
    
    public void clearAllBtnActionListener(ActionListener myActionListener) {
        clearAllBtn.addActionListener(myActionListener);
    }
    
    public void searchAllBtnActionListener(ActionListener myActionListener) {
        searchAllBtn.addActionListener(myActionListener);
    }
    
    public void searchPositonBtnActionListener(ActionListener myActionListener) {
        searchPositionBtn.addActionListener(myActionListener);
    }
    
    public void searchIdBtnActionListener(ActionListener myActionListener) {
        searchIdBtn.addActionListener(myActionListener);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearAllBtn;
    private javax.swing.JLabel empIdLbl;
    private javax.swing.JTextField empIdTxt;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel positionLbl;
    private javax.swing.JTextField positionTxt;
    private javax.swing.JButton searchAllBtn;
    private javax.swing.JButton searchIdBtn;
    private javax.swing.JButton searchPositionBtn;
    private javax.swing.JTable tblSearchEmployees;
    // End of variables declaration//GEN-END:variables
}
