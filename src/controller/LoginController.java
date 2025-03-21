package controller;

import dao.LoginDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.User;
import view.AdminMainFrame;
import view.CustomerMainFrame;
import view.EmployeeMainFrame;
import view.LoginView;
import view.MainFrame;

/**
 *
 * @author Max Sainsbury
 */
public class LoginController {
    private LoginView loginView;
    private LoginDAO loginDAO;
    private CustomerMainFrame customerMainFrame;
    private EmployeeMainFrame employeeMainFrame;
    private AdminMainFrame adminMainFrame;
    
    public LoginController(LoginView loginView, LoginDAO loginDAO, CustomerMainFrame customerMainFrame, EmployeeMainFrame employeeMainFrame, AdminMainFrame adminMainFrame) {
        this.loginView = loginView;
        this.loginDAO = loginDAO;
        
        this.customerMainFrame = customerMainFrame;
        this.employeeMainFrame = employeeMainFrame;
        this.adminMainFrame = adminMainFrame;
        this.loginView.loginBtnListener(new Login());
    }

    private class Login implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUserIdTxt().getText();
            String password = loginView.getPasswordTxt().getText();
            
            User user = loginDAO.checkUserType(username, password);
            if(user.getUserId() > 0) {
                if(user.getUserType().toUpperCase().equals("customer".toUpperCase())) {
                    customerMainFrame.setVisible(true);
                    loginView.dispose();
                }
                else if(user.getUserType().toUpperCase().equals("employee".toUpperCase())) {
                    String role = loginDAO.checkEmployeeType(user);
                    if(role.toUpperCase().equals("employee".toUpperCase())) {
                        employeeMainFrame.setVisible(true);
                        loginView.dispose();
                    }
                    else if(role.toUpperCase().equals("admin".toUpperCase())) {
                        System.out.println(role);
                        adminMainFrame.setVisible(true);
                        loginView.dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Employee found in user table but not in employee table");
                    }
                }
            } 
            else {
                JOptionPane.showMessageDialog(null, "No user with that username or password");
            }
        }
    }
}
