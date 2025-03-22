package controller;

import dao.CustomerDAO;
import dao.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import view.*;
import model.Customer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ebba de Groot
 */
public class CustomerController {
    private CustomerDAO customerDao;
    private AddCustomerView addCustomerView;
    private RegisterCustomerView registerCustomerView;
    private UserDAO userDao;
    private DeleteCustomerView deleteCustomerView;
    private SearchCustomerView searchCustomerView;
    private EditCustomerView editCustomerView;
    // This variable will hold an instance of customer to be used in employee editing.
    private Customer tempCustomer;
    
    /**
     * Controller for addCustomerView
     * @param customerDao
     * @param addCustomerView
     * @param userDao 
     */
    public CustomerController(CustomerDAO customerDao, AddCustomerView addCustomerView, UserDAO userDao) {
        this.customerDao = customerDao;
        this.addCustomerView = addCustomerView;
        this.userDao = userDao;
        
        this.addCustomerView.addCustomerBtnActionListener(new AddCustomer());
        this.addCustomerView.clearAllBtnActionListener(new ClearAllTextFields());
    }
    
    /**
     * Controller for registerCustomerView
     * @param customerDao
     * @param registerCustomerView
     * @param userDao 
     */
    public CustomerController(CustomerDAO customerDao, RegisterCustomerView registerCustomerView, UserDAO userDao) {
        this.customerDao = customerDao;
        this.registerCustomerView = registerCustomerView;
        this.userDao = userDao;
        
        this.registerCustomerView.registerBtnActionListener(new RegisterCustomer());
        this.registerCustomerView.clearAllBtnActionListener(new ClearAllTextRegCustView());
    }
    
    /**
     * Constructor for DeleteCustomerView
     * @param customerDao
     * @param deleteCustomerView
     */
    public CustomerController(CustomerDAO customerDao, DeleteCustomerView deleteCustomerView){
        this.customerDao = customerDao;
        this.deleteCustomerView = deleteCustomerView;
        
        this.deleteCustomerView.searchBtnActionListener(new SearchCustomerById());
        this.deleteCustomerView.clearAllBtnActionListener(new ClrAllTxtDelCustomerView());
        this.deleteCustomerView.deleteBtnActionListener(new DeleteCustomer());
    }
    
    /**
     * Constructor for SearchCustomerView
     * @param customerDao
     * @param searchCustomerView 
     */
    public CustomerController(CustomerDAO customerDao, SearchCustomerView searchCustomerView) {
        this.customerDao = customerDao;
        this.searchCustomerView = searchCustomerView;
        
        this.searchCustomerView.searchIdBtnActionListener(new GetCustomerById());
        this.searchCustomerView.clearAllBtnActionListener(new ClrAllTxtSearchCustomerView());
        this.searchCustomerView.searchPhoneBtnActionListener(new SearchCustomerByPhone());
        this.searchCustomerView.searchEmailBtnActionListener(new SearchCustomerByEmail());
        this.searchCustomerView.searchAllBtnActionListener(new SearchAllCustomers());
    }
    
    /**
     * Constructor for EditCustomerView
     * @param customerDao
     * @param editCustomerView 
     */
    public CustomerController(CustomerDAO customerDao, EditCustomerView editCustomerView){
        this.customerDao = customerDao;
        this.editCustomerView = editCustomerView;
        
        this.editCustomerView.searchBtnActionListener(new SearchById());
        this.editCustomerView.clearAllBtnActionListener(new ClearAllEditView());
        this.editCustomerView.editBtnActionListener(new EditCustomer());
    }

    private class ClearAllRegister implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            registerCustomerView.getUsernameTxt().setText("");
            registerCustomerView.getPasswordTxt().setText("");
            registerCustomerView.getFnameTxt().setText("");
            registerCustomerView.getLnameTxt().setText("");
            registerCustomerView.getEmailTxt().setText("");
            registerCustomerView.getPhoneTxt().setText("");
            registerCustomerView.getPhoneTxt().setText("");
            registerCustomerView.getDobTxt().setText("");
            registerCustomerView.getUnitTxt().setText("");
            registerCustomerView.getStreetTxt().setText("");
            registerCustomerView.getCityTxt().setText("");
            registerCustomerView.getPostalTxt().setText("");
            registerCustomerView.getCountryTxt().setText("");
        }

    
    }
    
    /**
     * Class to create an instance of Customer from the user input in
     * addCustomerView and send the customer information to the database
     * using customerDao.
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
            
            // Create new instance of Customer to send data to database. 
            Customer newCustomer = new Customer(custFName, custLName, custEmail, custPhone, 
                    custUnit, custStreetAdress, custCity, custCountry, custPostalCode, custDob, 
                    custUsername, custPassword);   
            
            // Set the user attribute of the userDao to the new customer.
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
            // setting the newCustomer's userId to the newly created userId when new user was inserted.
            newCustomer.setUserId(userDao.getUser().getUserId());
            // Insert new customer in database
            result = customerDao.addCustomer(newCustomer);
            if(result) {
                JOptionPane.showMessageDialog(null, "Successfully added a new customer");
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to add a new customer.");
            }
        }
    }
    
    /**
     * Class to create an instance of Customer from the user input in
     * addCustomerView and send the customer information to the database
     * using customerDao.
     */
    private class RegisterCustomer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {      
            String custFName = registerCustomerView.getFnameTxt().getText();
            String custLName = registerCustomerView.getLnameTxt().getText();
            String custDob = registerCustomerView.getDobTxt().getText();
            String custEmail = registerCustomerView.getEmailTxt().getText();
            String custPhone = registerCustomerView.getPhoneTxt().getText();
            String custUnit = registerCustomerView.getUnitTxt().getText();
            String custStreetAdress = registerCustomerView.getStreetTxt().getText();
            String custCity = registerCustomerView.getCityTxt().getText();
            String custPostalCode = registerCustomerView.getPostalTxt().getText();
            String custCountry = registerCustomerView.getCountryTxt().getText();
            String custUsername = registerCustomerView.getUsernameTxt().getText();
            String custPassword = registerCustomerView.getPasswordTxt().getText();
            
            // Create new instance of Customer to send data to database. 
            Customer newCustomer = new Customer(custFName, custLName, custEmail, custPhone, 
                    custUnit, custStreetAdress, custCity, custCountry, custPostalCode, custDob, 
                    custUsername, custPassword);   
            
            // Set the user attribute of the userDao to the new customer.
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
            // setting the newCustomer's userId to the newly created userId when new user was inserted.
            newCustomer.setUserId(userDao.getUser().getUserId());
            // Insert new customer in database
            result = customerDao.addCustomer(newCustomer);
            if(result) {
                JOptionPane.showMessageDialog(null, "Successfully added a new customer");
                registerCustomerView.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to add a new customer.");
            }
            return ;
        }
    }
    
    /**
     * Class that sets all text fields in addCustomerView to an empty string. 
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
    
    /**
     * Class that sets all text fields in addCustomerView to an empty string. 
     */
    private class ClearAllTextRegCustView implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("button is working.");
            registerCustomerView.getFnameTxt().setText("");
            registerCustomerView.getLnameTxt().setText("");
            registerCustomerView.getDobTxt().setText("");
            registerCustomerView.getEmailTxt().setText("");
            registerCustomerView.getPhoneTxt().setText("");
            registerCustomerView.getUnitTxt().setText("");
            registerCustomerView.getStreetTxt().setText("");
            registerCustomerView.getCityTxt().setText("");
            registerCustomerView.getPostalTxt().setText("");
            registerCustomerView.getCountryTxt().setText("");
            registerCustomerView.getUsernameTxt().setText("");
            registerCustomerView.getPasswordTxt().setText("");
        }   
    }
    
    // Class to search customer from customer table in database for customer delete view.
    private class SearchCustomerById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int customerId = Integer.parseInt(deleteCustomerView.getIdTxt().getText());
            DefaultTableModel model = (DefaultTableModel)deleteCustomerView.getDelCustomerTbl().getModel();
            model.setRowCount(0);
            
            try{
                if(customerId != 0) {                    
                    Customer customer = customerDao.fetchCustomerForDelTable(customerId);
                    Object[] row = {
                        customer.getCustomerId(),
                        customer.getFirstName() + " " + customer.getLastName(),
                        customer.getPhone(),
                        customer.getEmail()
                    };
                model.addRow(row);                    
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Customer Id does not exist.");
            }
        }
    }
    
    // Class to delete customer in delete deleteCustomerView
    private class DeleteCustomer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int customerId = Integer.parseInt(deleteCustomerView.getIdTxt().getText());
            DefaultTableModel model = (DefaultTableModel)deleteCustomerView.getDelCustomerTbl().getModel();

            if(customerId != 0) {
                boolean result = customerDao.deleteCustomer(customerId);
                
                if(result) {
                JOptionPane.showMessageDialog(null, "Successfully deleted the customer");
                model.setRowCount(0);
                } else {
                JOptionPane.showMessageDialog(null, "Was not able to delete customer.");
                }
            }
        }
    }
    
    private class ClrAllTxtDelCustomerView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deleteCustomerView.getDelCustomerTbl().getModel();
            model.setRowCount(0);
            deleteCustomerView.getIdTxt().setText("");
        }
    }
    
    // Class to search customer from customer table in database for customer search view
    private class GetCustomerById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int customerId = Integer.parseInt(searchCustomerView.getCustomerIdTxt().getText());
            DefaultTableModel model = (DefaultTableModel)searchCustomerView.getSearchCustomerTbl().getModel();
            model.setRowCount(0);
            
            try{
                if(customerId != 0) {                    
                    Customer customer = customerDao.fetchCustomerById(customerId);
                    Object[] row = {
                        customer.getCustomerId(),
                        customer.getFirstName() + " " + customer.getLastName(),
                        customer.getDob(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getUnitNumber() + " " + customer.getStreetAddress() + " " + customer.getCity() + " " + customer.getCountry(),
                        customer.getPostalCode()
                    };
                    model.addRow(row);                    
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Customer Id does not exist.");
            }
        }    
    }
    
    /**
     * Class clears all text in search customer view.
     */
    private class ClrAllTxtSearchCustomerView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchCustomerView.getSearchCustomerTbl().getModel();
            model.setRowCount(0);
            searchCustomerView.getCustomerIdTxt().setText("");
            searchCustomerView.getEmailTxt().setText("");
            searchCustomerView.getPhoneTxt().setText("");
        }
    }
    
    // Class to search customer from customer table in database for customer search view
    private class SearchCustomerByEmail implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String customerEmail = searchCustomerView.getEmailTxt().getText();
            DefaultTableModel model = (DefaultTableModel)searchCustomerView.getSearchCustomerTbl().getModel();
            model.setRowCount(0);          

            if(!customerEmail.equals("")) {       
                ArrayList<Customer> customers = customerDao.fetchCustomerByEmail(customerEmail);
                if(customers.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No customer with that email.");
                }

                for (Customer customer: customers) {
                    Object[] row = {
                        customer.getCustomerId(),
                        customer.getFirstName() + " " + customer.getLastName(),
                        customer.getDob(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getUnitNumber() + " " + customer.getStreetAddress() + " " + customer.getCity() + " " + customer.getCountry(),
                        customer.getPostalCode()                        
                    };                 
                    model.addRow(row);
                }       
            }            
        }
    }
    
    // Class to search customer from customer table in database for customer search view
    private class SearchCustomerByPhone implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String customerPhone = searchCustomerView.getPhoneTxt().getText();
            DefaultTableModel model = (DefaultTableModel)searchCustomerView.getSearchCustomerTbl().getModel();
            model.setRowCount(0);          

            if(!customerPhone.equals("")) {       
                ArrayList<Customer> customers = customerDao.fetchCustomerByPhone(customerPhone);
                if(customers.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No customer with that Phone Number.");
                }

                for (Customer customer: customers) {
                    Object[] row = {
                        customer.getCustomerId(),
                        customer.getFirstName() + " " + customer.getLastName(),
                        customer.getDob(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getUnitNumber() + " " + customer.getStreetAddress() + " " + customer.getCity() + " " + customer.getCountry(),
                        customer.getPostalCode()                        
                    };                 
                    model.addRow(row);
                }       
            }            
        }
    }
    
    // Class to search customer from customer table in database for customer search view
    private class SearchAllCustomers implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {            
            DefaultTableModel model = (DefaultTableModel)searchCustomerView.getSearchCustomerTbl().getModel();
            model.setRowCount(0);          
                  
            ArrayList<Customer> customers = customerDao.fetchAllCustomers();
            if(customers.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No customers to display.");
            }

            for (Customer customer: customers) {
                Object[] row = {
                    customer.getCustomerId(),
                        customer.getFirstName() + " " + customer.getLastName(),
                        customer.getDob(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getUnitNumber() + " " + customer.getStreetAddress() + " " + customer.getCity() + " " + customer.getCountry(),
                        customer.getPostalCode()                        
                };                 
                model.addRow(row);
            }                             
        }
    }
    
    // Class to search customer from customer table in database for customer edit view
    private class SearchById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int customerId = Integer.parseInt(editCustomerView.getCustomerIdTxt().getText());       
            try{
                if(customerId != 0) {                    
                    Customer customer = customerDao.fetchCustomerById(customerId);
                    // Temporarly hold this customer to be used in customer edit.
                    tempCustomer = customer;
                    System.out.println(tempCustomer.getFirstName());

                        editCustomerView.getFnameTxt().setText(customer.getFirstName());
                        editCustomerView.getLnameTxt().setText(customer.getLastName());
                        editCustomerView.getDobTxt().setText(customer.getDob().toString());
                        editCustomerView.getEmailTxt().setText(customer.getEmail());
                        editCustomerView.getPhoneTxt().setText(customer.getPhone());
                        editCustomerView.getUnitTxt().setText(customer.getUnitNumber());
                        editCustomerView.getStreetTxt().setText(customer.getStreetAddress());
                        editCustomerView.getCityTxt().setText(customer.getCity());
                        editCustomerView.getPostalTxt().setText(customer.getPostalCode());
                        editCustomerView.getCountryTxt().setText(customer.getCountry());
                } else {
                    throw new Exception();

                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Customer Id does not exist.");
            }
        }
    }
    
    private class ClearAllEditView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editCustomerView.getCustomerIdTxt().setText("");
            editCustomerView.getFnameTxt().setText("");
            editCustomerView.getLnameTxt().setText("");
            editCustomerView.getDobTxt().setText("");
            editCustomerView.getEmailTxt().setText("");
            editCustomerView.getPhoneTxt().setText("");
            editCustomerView.getUnitTxt().setText("");
            editCustomerView.getStreetTxt().setText("");
            editCustomerView.getCityTxt().setText("");
            editCustomerView.getPostalTxt().setText("");
            editCustomerView.getCountryTxt().setText("");
            

        }
    }
    
    /**
     * Class to edit customer information in database in the editCustomerView
     */
    private class EditCustomer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {  
            System.out.println(tempCustomer.getFirstName());

            if(tempCustomer != null) {                                
                tempCustomer.setFirstName(editCustomerView.getFnameTxt().getText());
                tempCustomer.setLastName(editCustomerView.getLnameTxt().getText());
                tempCustomer.setDob(LocalDate.parse(editCustomerView.getDobTxt().getText()));
                tempCustomer.setEmail(editCustomerView.getEmailTxt().getText());
                tempCustomer.setPhone(editCustomerView.getPhoneTxt().getText());
                tempCustomer.setUnitNumber(editCustomerView.getUnitTxt().getText());
                tempCustomer.setStreetAddress(editCustomerView.getStreetTxt().getText());
                tempCustomer.setCity(editCustomerView.getCityTxt().getText());
                tempCustomer.setPostalCode(editCustomerView.getPostalTxt().getText());
                tempCustomer.setCountry(editCustomerView.getCountryTxt().getText());
                
                boolean result = customerDao.editCustomer(tempCustomer);
                if(result) {
                    JOptionPane.showMessageDialog(null, "Successfully edited customer Id:  " + tempCustomer.getCustomerId() + ".");
                } else {
                    JOptionPane.showMessageDialog(null, "Was not able to edit customer.");
                }
            }            
        }
    }
}
