package controller;

import dao.CustomerDAO;
import dao.LoginDAO;
import dao.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.*;
import javax.swing.JOptionPane;
import model.User;
import view.AdminMainFrame;
import view.CustomerMainFrame;
import view.EmployeeMainFrame;
import view.LoginView;
import view.MainFrame;
import view.RegisterCustomerView;

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
    private Pattern usernameRegex = Pattern.compile("^[0-9A-z\\-_]+$");
    private Pattern passwordRegex = Pattern.compile("^[0-9A-z\\-_@!#\\$%\\^&\\*\\.,]+$");
    
    public LoginController(LoginView loginView, LoginDAO loginDAO, CustomerMainFrame customerMainFrame, EmployeeMainFrame employeeMainFrame, AdminMainFrame adminMainFrame) {
        this.loginView = loginView;
        this.loginDAO = loginDAO;
        
        this.customerMainFrame = customerMainFrame;
        this.employeeMainFrame = employeeMainFrame;
        this.adminMainFrame = adminMainFrame;
        this.loginView.loginBtnListener(new Login());
        this.loginView.registerBtnListener(new RgisterCustomer());
    }

    private class RgisterCustomer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterCustomerView registerCustomerView = new RegisterCustomerView();
            CustomerDAO customerDAO = new CustomerDAO();
            UserDAO userDAO = new UserDAO();
            CustomerController customerController = new CustomerController(customerDAO, registerCustomerView, userDAO);
            registerCustomerView.setVisible(true);
        }
    }

    private class Login implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUserIdTxt().getText();
            Matcher usernameMatch = usernameRegex.matcher(username);
            if(!usernameMatch.find()) {
                JOptionPane.showMessageDialog(null, "Username can only contain letters numbers - and _");
                return;
            }
            String password = loginView.getPasswordTxt().getText();
            Matcher passwordMatch = passwordRegex.matcher(password);
            if(!passwordMatch.find()) {
                JOptionPane.showMessageDialog(null, "Password can on contain letters numbers and special characters");
                return;
            }
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
