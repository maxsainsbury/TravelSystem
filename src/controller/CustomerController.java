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
    // This variable will hold an instance of customer to be used in customer editing.
    private Customer tempCustomer;
    // Regex variables
    private String lettersRegEx = "^[a-zA-Z]+[ ]*[a-zA-Z]*$";
    private String sinNumRegEx =  "^\\d{9}$";
    private String postalCodeRegEx = "^[ABCEGHJ-NPRSTVXY][0-9][ABCEGHJ-NPRSTV-Z][ ]?[0-9][ABCEGHJ-NPRSTV-Z][0-9]$";
    private String phoneNumRegEx = "^(\\(\\+[0-9]{2}\\))?([0-9]{3}-?)?([0-9]{3})\\-?([0-9]{4})(\\/[0-9]{4})?$";
    private String emailRegEx = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private String numberOnlyRegEx = "^[0-9]+$";
    private String dateRegEx = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])";
    
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
        this.registerCustomerView.clearAllBtnActionListener(new ClearAllRegister());
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
            
            // Validate all values with regex
            String custFName = addCustomerView.getFnameTxt().getText().strip();
            String custLName = addCustomerView.getLnameTxt().getText().strip();
            if(!custFName.matches(lettersRegEx) || (!custLName.matches(lettersRegEx))) {
                JOptionPane.showMessageDialog(null, "Name can only have letters.");
                return;
            }

            String custDob = addCustomerView.getDobTxt().getText().strip();
            if(!custDob.matches(dateRegEx)){
                JOptionPane.showMessageDialog(null, "Date must be in yyyy-mm-dd format.");
                return;
            }

            String custEmail = addCustomerView.getEmailTxt().getText().strip();
            if(!custEmail.matches(emailRegEx)){
                JOptionPane.showMessageDialog(null, "Inavalid email format.");
                return;
            }

            String custPhone = addCustomerView.getPhoneTxt().getText().strip();
            if(!custPhone.matches(phoneNumRegEx)) {
                JOptionPane.showMessageDialog(null, "Phone number can only have numbers. ");
                return;
            }

            String custUnit = addCustomerView.getUnitTxt().getText().strip();
            if(!custUnit.matches(numberOnlyRegEx)) {
                JOptionPane.showMessageDialog(null, "Invalid unit number. ");
                return;
            }

            String custStreetAdress = addCustomerView.getStreetTxt().getText().strip();
            String custCity = addCustomerView.getCityTxt().getText();
            if(!custStreetAdress.matches(lettersRegEx)|| (!custCity.matches(lettersRegEx))){
                JOptionPane.showMessageDialog(null, "Street address and city can only have letters. ");
                return;
            }

            String custPostalCode = addCustomerView.getPostalTxt().getText().strip();
            if(!custPostalCode.matches(postalCodeRegEx)) {
                JOptionPane.showMessageDialog(null, "Invalid Canadian postal code. ");
                return;                   
            }
            
            String custCountry = addCustomerView.getCountryTxt().getText().strip();                
            if(!custCountry.matches(lettersRegEx)) {
                JOptionPane.showMessageDialog(null, "Country can only have letters. ");
                return;
            }
            
            String custUsername = addCustomerView.getUsernameTxt().getText().strip();
            String custPassword = addCustomerView.getPasswordTxt().getText().strip();
            
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
            // Validate all values with regex
            String custFName = registerCustomerView.getFnameTxt().getText().strip();
            String custLName = registerCustomerView.getLnameTxt().getText().strip();
            if(!custFName.matches(lettersRegEx) || (!custLName.matches(lettersRegEx))) {
                JOptionPane.showMessageDialog(null, "Name can only have letters.");
                return;
            }

            String custDob = registerCustomerView.getDobTxt().getText().strip();
            if(!custDob.matches(dateRegEx)){
                JOptionPane.showMessageDialog(null, "Date must be in yyyy-mm-dd format.");
                return;
            }

            String custEmail = registerCustomerView.getEmailTxt().getText().strip();
            if(!custEmail.matches(emailRegEx)){
                JOptionPane.showMessageDialog(null, "Inavalid email format.");
                return;
            }

            String custPhone = registerCustomerView.getPhoneTxt().getText().strip();
            if(!custPhone.matches(phoneNumRegEx)) {
                JOptionPane.showMessageDialog(null, "Phone number can only have numbers. ");
                return;
            }

            String custUnit = registerCustomerView.getUnitTxt().getText().strip();
            if(!custUnit.matches(numberOnlyRegEx)) {
                JOptionPane.showMessageDialog(null, "Invalid unit number. ");
                return;
            }

            String custStreetAdress = registerCustomerView.getStreetTxt().getText().strip();
            String custCity = registerCustomerView.getCityTxt().getText();
            if(!custStreetAdress.matches(lettersRegEx)|| (!custCity.matches(lettersRegEx))){
                JOptionPane.showMessageDialog(null, "Street address and city can only have letters. ");
                return;
            }

            String custPostalCode = registerCustomerView.getPostalTxt().getText().strip();
            if(!custPostalCode.matches(postalCodeRegEx)) {
                JOptionPane.showMessageDialog(null, "Invalid Canadian postal code. ");
                return;                   
            }
            
            String custCountry = registerCustomerView.getCountryTxt().getText().strip();                
            if(!custCountry.matches(lettersRegEx)) {
                JOptionPane.showMessageDialog(null, "Country can only have letters. ");
                return;
            }
            
            String custUsername = registerCustomerView.getUsernameTxt().getText().strip();
            String custPassword = registerCustomerView.getPasswordTxt().getText().strip();
            
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
    
    // Class to search customer from customer table in database for customer delete view.
    private class SearchCustomerById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deleteCustomerView.getDelCustomerTbl().getModel();
            model.setRowCount(0);
             // Validate customer id with regex
            String customerIdString = deleteCustomerView.getIdTxt().getText().strip();
            if(!customerIdString.matches(numberOnlyRegEx)) {
                JOptionPane.showMessageDialog(null, "Customer can only have numnbers.");
                return;
            }
            int customerId = Integer.parseInt(customerIdString);
            
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
            DefaultTableModel model = (DefaultTableModel)deleteCustomerView.getDelCustomerTbl().getModel();
            // Validate customer id with regex
            String customerIdString = deleteCustomerView.getIdTxt().getText().strip();
            if(!customerIdString.matches(numberOnlyRegEx)) {
                JOptionPane.showMessageDialog(null, "Customer can only have numnbers.");
                return;
            }
            int customerId = Integer.parseInt(customerIdString);
            
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
            DefaultTableModel model = (DefaultTableModel)searchCustomerView.getSearchCustomerTbl().getModel();
            model.setRowCount(0);
            // Validate customer id with regex
            String customerIdString = searchCustomerView.getCustomerIdTxt().getText().strip();
            if(!customerIdString.matches(numberOnlyRegEx)) {
                JOptionPane.showMessageDialog(null, "Customer can only have numnbers.");
                return;
            }
            int customerId = Integer.parseInt(customerIdString);
            
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
            DefaultTableModel model = (DefaultTableModel)searchCustomerView.getSearchCustomerTbl().getModel();
            model.setRowCount(0);
            String customerEmail = searchCustomerView.getEmailTxt().getText().strip();
            // Validate email with regex
            if(!customerEmail.matches(emailRegEx)){
                JOptionPane.showMessageDialog(null, "Inavalid email format.");
                return;
            }

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
            DefaultTableModel model = (DefaultTableModel)searchCustomerView.getSearchCustomerTbl().getModel();
            model.setRowCount(0);
            String customerPhone = searchCustomerView.getPhoneTxt().getText().strip();
            // Validate phone with regex
            if(!customerPhone.matches(phoneNumRegEx)) {
                JOptionPane.showMessageDialog(null, "Phone number can only have numbers.");
                return;
            }

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
            try{
                // Validate customer id with regex
                String customerIdString = editCustomerView.getCustomerIdTxt().getText().strip();
                if(!customerIdString.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Customer Id can only have numbers.");
                    return;
                }               
                int customerId = Integer.parseInt(customerIdString);
                
                if(customerId != 0) {                    
                    Customer customer = customerDao.fetchCustomerById(customerId);
                    // Temporarly hold this customer to be used in customer edit.
                    tempCustomer = customer;

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
                // Validate all inputs with regex before setting the customer's attribues
                String fName = editCustomerView.getFnameTxt().getText().strip();
                String lName = editCustomerView.getLnameTxt().getText().strip();
                if(!fName.matches(lettersRegEx) || !(lName.matches(lettersRegEx))) {
                    JOptionPane.showMessageDialog(null, "Name can only have letters and spaces.");
                    return;
                }                        
                tempCustomer.setFirstName(fName);
                tempCustomer.setLastName(lName);
                
                String dobString = editCustomerView.getDobTxt().getText().strip();
                if (!dobString.matches(dateRegEx)) {
                    JOptionPane.showMessageDialog(null, "Date must be in yyyy-mm-dd format.");
                    return;
                }            
                tempCustomer.setDob(LocalDate.parse(dobString));
                
                String email = editCustomerView.getEmailTxt().getText().strip();
                if(!email.matches(emailRegEx)) {
                    JOptionPane.showMessageDialog(null, "Invalid email format.");
                    return;
                }
                tempCustomer.setEmail(email);
                
                String phone = editCustomerView.getPhoneTxt().getText().strip();
                if(!phone.matches(phoneNumRegEx)) {
                    JOptionPane.showMessageDialog(null, "Phone number can only have numbers. ");
                    return;
                }
                tempCustomer.setPhone(phone);
                
                String unit = editCustomerView.getUnitTxt().getText().strip();
                if(!unit.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Unit number can only have numbers. ");
                    return;
                }
                tempCustomer.setUnitNumber(unit);
                
                String street = editCustomerView.getStreetTxt().getText().strip();
                String city = editCustomerView.getCityTxt().getText().strip();
                if(!street.matches(lettersRegEx) || !(city.matches(lettersRegEx))) {
                    JOptionPane.showMessageDialog(null, "Street and city can only have letters and spaces. ");
                    return;
                }
                tempCustomer.setStreetAddress(street);    
                tempCustomer.setCity(city);
                
                String postal = editCustomerView.getPostalTxt().getText().strip();
                if(!postal.matches(postalCodeRegEx)) {
                    JOptionPane.showMessageDialog(null, "Invalid Canadian postal code.");
                    return;
                }
                tempCustomer.setPostalCode(postal);
                
                String country = editCustomerView.getCountryTxt().getText().strip();
                if(!country.matches(lettersRegEx)) {
                    JOptionPane.showMessageDialog(null, "Country can only have letters and spaces.");
                    return;
                }         
                tempCustomer.setCountry(country);
                
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
