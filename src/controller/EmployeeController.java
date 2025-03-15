package controller;

import dao.EmployeeDAO;
import dao.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.AddEmployeeView;
import model.Employee;
import javax.swing.*;


/**
 *
 * @author goenchoi
 */
public class EmployeeController {
    private EmployeeDAO employeeDao;
    private AddEmployeeView addEmployeeView;
    private UserDAO userDao;
    
    public EmployeeController(EmployeeDAO employeeDao, AddEmployeeView addEmployeeView, UserDAO userDao) {
        this.employeeDao = employeeDao;
        this.addEmployeeView = addEmployeeView;
        this.userDao = userDao;
        
        this.addEmployeeView.addEmpBtnActionListener(new AddEmployee());
        this.addEmployeeView.clearAllBtnActionListener(new ClearAllTextFields());
    }
    
    /**
     * Class to create an instance of Employee from the user input in
     * addEmployeeView and send the employee information to the database
     * using employeeDao.
     */
    private class AddEmployee implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {      
            String empFName = addEmployeeView.getFnameTxt().getText();
            String empLName = addEmployeeView.getLnameTxt().getText();
            String empDob = addEmployeeView.getDobTxt().getText();
            String empEmail = addEmployeeView.getEmailTxt().getText();
            int empSin = Integer.parseInt(addEmployeeView.getSinTxt().getText());
            String empStatus = addEmployeeView.getStatusTxt().getText();
            String empPhone = addEmployeeView.getPhoneTxt().getText();
            String empCell = addEmployeeView.getCellTxt().getText();
            String empUnit = addEmployeeView.getUnitTxt().getText();
            String empStreetAdress = addEmployeeView.getStreetTxt().getText();
            String empCity = addEmployeeView.getCityTxt().getText();
            String empPostalCode = addEmployeeView.getPostalTxt().getText();
            String empCountry = addEmployeeView.getCountryTxt().getText();
            String empPosition = addEmployeeView.getPositionTxt().getText();
            float empSalary = Float.parseFloat(addEmployeeView.getSalaryTxt().getText());
            String empRole = addEmployeeView.getRoleTxt().getText();
            String empUserName = addEmployeeView.getUserNameTxt().getText();
            String empPassword = addEmployeeView.getPasswordTxt().getText();
            int empCreatedBy = Integer.parseInt(addEmployeeView.getCreatedByTxt().getText());
            
            // Create new instance of Employee to send data to database. 
            Employee newEmployee = new Employee(empFName, empLName, empEmail, empSin, empPhone, 
                    empUnit, empStreetAdress, empCity, empCountry, empPostalCode, empDob, empStatus, 
                    empCell, empPosition, empSalary, empRole, empUserName, empPassword, empCreatedBy);   
            
            // Inserting new user in database
            // newEmployee is automatically casted with User when passed into userDao
            boolean result = userDao.addUserRecord(newEmployee);
            
            // If failed to add data to database
            if(!result) {
                JOptionPane.showMessageDialog(null, "Was not able to add a new user.");
                // Leave if statement to continue
                return;
            } 
            
            //
            JOptionPane.showMessageDialog(null, "Successfully added a new user.");
            newEmployee.setUserId(userDao.getUser().getUserId());
            result = employeeDao.addEmployee(newEmployee);
            if(result) {
                JOptionPane.showMessageDialog(null, "Successfully added a new employee");
            } else {
                JOptionPane.showMessageDialog(null, "Was no able to add a new employee.");
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
            addEmployeeView.getFnameTxt().setText("");
            addEmployeeView.getLnameTxt().setText("");
            addEmployeeView.getDobTxt().setText("");
            addEmployeeView.getEmailTxt().setText("");
            addEmployeeView.getSinTxt().setText("");
            addEmployeeView.getStatusTxt().setText("");
            addEmployeeView.getPhoneTxt().setText("");
            addEmployeeView.getCellTxt().setText("");
            addEmployeeView.getUnitTxt().setText("");
            addEmployeeView.getStreetTxt().setText("");
            addEmployeeView.getCityTxt().setText("");
            addEmployeeView.getPostalTxt().setText("");
            addEmployeeView.getCountryTxt().setText("");
            addEmployeeView.getPositionTxt().setText("");
            addEmployeeView.getSalaryTxt().setText("");
            addEmployeeView.getRoleTxt().setText("");   
            addEmployeeView.getUserNameTxt().setText("");
            addEmployeeView.getPasswordTxt().setText("");
        }   
    }
}
