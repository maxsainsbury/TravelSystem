package controller;

import dao.CustomerDAO;
import dao.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.AddCustomerView;
import model.Customer;
import javax.swing.*;

/**
 *
 * @author Ebba de Groot
 */
public class CustomerController {
    private CustomerDAO customerDao;
    private AddCustomerView addCustomerView;
    private UserDAO userDao;
    
    public CustomerController(CustomerDAO customerDao, AddCustomerView addCustomerView, UserDAO userDao) {
        this.customerDao = customerDao;
        this.addCustomerView = addCustomerView;
        this.userDao = userDao;
        
        this.addCustomerView.addCustomerBtnActionListener(new AddCustomer());
        this.addCustomerView.clearAllBtnActionListener(new ClearAllTextFields());
    }
    
    /**
     * Class to create an instance of Employee from the user input in
     * addEmployeeView and send the employee information to the database
     * using employeeDao.
     */
    private class AddCustomer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {      
            String custFName = addCustomerView.getFnameTxt().getText();
            String custLName = addCustomerView.getLnameTxt().getText();
            String custDob = addCustomerView.getDobTxt().getText();
            String custEmail = addCustomerView.getEmailTxt().getText();
            String custPhone = addCustomerView.getPhoneTxt().getText();
            String custUnit = addCustomerView.getUnitTxt().getText();
            String custStreetAdress = addCustomerView.getStreetTxt().getText();
            String custCity = addCustomerView.getCityTxt().getText();
            String custPostalCode = addCustomerView.getPostalTxt().getText();
            String custCountry = addCustomerView.getCountryTxt().getText();
            String custUsername = addCustomerView.getUsernameTxt().getText();
            String custPassword = addCustomerView.getPasswordTxt().getText();
            
            // Create new instance of Employee to send data to database. 
            Customer newCustomer = new Customer(custFName, custLName, custEmail, custPhone, 
                    custUnit, custStreetAdress, custCity, custCountry, custPostalCode, custDob, 
                    custUsername, custPassword);   
            
            // Set the user attribute of the userDao to the new employee.
            userDao.setUser(newCustomer);

            // Inserting new user in database
            // newCustomer is automatically casted with User when passed into userDao
            boolean result = userDao.addUserRecord(newCustomer);
            
            // If failed to add data to database
            if(!result) {
                JOptionPane.showMessageDialog(null, "Was not able to add a new user.");
                // Leave if statement to continue
                return;
            } 
            
            JOptionPane.showMessageDialog(null, "Successfully added a new user.");
            // setting the newEmployee's userId to the newly created userId when new user was inserted.
            newCustomer.setUserId(userDao.getUser().getUserId());
            // Insert new employee in database
            result = customerDao.addCustomer(newCustomer);
            if(result) {
                JOptionPane.showMessageDialog(null, "Successfully added a new customer");
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to add a new customer.");
            }
        }
    }
    
    /**
     * Class that sets all text fields in addEmployeeView to an empty string. 
     */
    private class ClearAllTextFields implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("button is working.");
            addCustomerView.getFnameTxt().setText("");
            addCustomerView.getLnameTxt().setText("");
            addCustomerView.getDobTxt().setText("");
            addCustomerView.getEmailTxt().setText("");
            addCustomerView.getPhoneTxt().setText("");
            addCustomerView.getUnitTxt().setText("");
            addCustomerView.getStreetTxt().setText("");
            addCustomerView.getCityTxt().setText("");
            addCustomerView.getPostalTxt().setText("");
            addCustomerView.getCountryTxt().setText("");
            addCustomerView.getUsernameTxt().setText("");
            addCustomerView.getPasswordTxt().setText("");
        }   
    }
}
