package controller;

import dao.EmployeeDAO;
import dao.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import view.AddEmployeeView;
import model.Employee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.User;
import view.DeleteEmployeeView;
import view.EditEmployeeView;
import view.SearchEmployeeView;


/**
 *
 * @author goenchoi
 */
public class EmployeeController {
    private EmployeeDAO employeeDao;
    private AddEmployeeView addEmployeeView;
    private UserDAO userDao;
    private DeleteEmployeeView deleteEmployeeView;
    private SearchEmployeeView searchEmployeeView;
    private EditEmployeeView editEmployeeView;
    
    /**
     * Controller for addEmployeeView
     * @param employeeDao
     * @param addEmployeeView
     * @param userDao 
     */
    public EmployeeController(EmployeeDAO employeeDao, AddEmployeeView addEmployeeView, UserDAO userDao) {
        this.employeeDao = employeeDao;
        this.addEmployeeView = addEmployeeView;
        this.userDao = userDao;
        
        this.addEmployeeView.addEmpBtnActionListener(new AddEmployee());
        this.addEmployeeView.clearAllBtnActionListener(new ClearTxtAddEmpView());
    }
    
    /**
     * Constructor for DeleteEmployeeView
     * @param employeeDao
     * @param deleteEmployeeView
     * @param userDao 
     */
    public EmployeeController(EmployeeDAO employeeDao, DeleteEmployeeView deleteEmployeeView, UserDAO userDao){
        this.employeeDao = employeeDao;
        this.deleteEmployeeView = deleteEmployeeView;
        this.userDao = userDao;
        
        this.deleteEmployeeView.searchBtnActionListener(new SearchEmployeeById());
        this.deleteEmployeeView.clearBtnActionListener(new ClrAllTxtDelEmpView());
        this.deleteEmployeeView.deleteBtnActionListener(new DeleteEmployee());
    }
    
    /**
     * Constructor for SearchEmployeeView
     * @param employeeDao
     * @param searchEmployeeView 
     */
    public EmployeeController(EmployeeDAO employeeDao, SearchEmployeeView searchEmployeeView) {
        this.employeeDao = employeeDao;
        this.searchEmployeeView = searchEmployeeView;
        
        this.searchEmployeeView.searchIdBtnActionListener(new GetEmployeeById());
        this.searchEmployeeView.clearAllBtnActionListener(new ClrAllTxtSearchEmpView());
        this.searchEmployeeView.searchPositonBtnActionListener(new SearchEmployeeByPosition());
        this.searchEmployeeView.searchAllBtnActionListener(new SearchAllEmployees());
    }
    
    /**
     * Constructor for EditEmployeeView
     * @param employeeDao
     * @param editEmployeeView 
     */
    public EmployeeController(EmployeeDAO employeeDao, EditEmployeeView editEmployeeView){
        this.employeeDao = employeeDao;
        this.editEmployeeView = editEmployeeView;
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
            
            // Set the user attribute of the userDao to the new employee.
            userDao.setUser(newEmployee);

            // Inserting new user in database
            // newEmployee is automatically casted with User when passed into userDao
            boolean result = userDao.addUserRecord(newEmployee);
            
            // If failed to add data to database
            if(!result) {
                JOptionPane.showMessageDialog(null, "Was not able to add a new user.");
                // Leave if statement to continue
                return;
            } 
            
            JOptionPane.showMessageDialog(null, "Successfully added a new user.");
            // setting the newEmployee's userId to the newly created userId when new user was inserted.
            newEmployee.setUserId(userDao.getUser().getUserId());
            // Insert new employee in database
            result = employeeDao.addEmployee(newEmployee);
            if(result) {
                JOptionPane.showMessageDialog(null, "Successfully added a new employee");
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to add a new employee.");
            }
        }
    }
    
    /**
     * Class that sets all text fields in addEmployeeView to an empty string. 
     */
    private class ClearTxtAddEmpView implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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
    
    // Class to search employee from employee table in database for employee delete view.
    private class SearchEmployeeById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int employeeId = Integer.parseInt(deleteEmployeeView.getEmpIdTxt().getText());
            DefaultTableModel model = (DefaultTableModel)deleteEmployeeView.getTblEmployee().getModel();
            model.setRowCount(0);
            
            try{
                if(employeeId != 0) {                    
                    Employee employee = employeeDao.fetchEmployeeForDelTable(employeeId);
                    Object[] row = {
                        employee.getEmployeeId(),
                        employee.getFirstName() + " " + employee.getLastName(),
                        employee.getRole()
                    };
                model.addRow(row);                    
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Employee Id does not exist.");
            }
        }
    }
    
    // Class to delete employee in delete deleteEmployeeView
    private class DeleteEmployee implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int employeeId = Integer.parseInt(deleteEmployeeView.getEmpIdTxt().getText());
            DefaultTableModel model = (DefaultTableModel)deleteEmployeeView.getTblEmployee().getModel();

            if(employeeId != 0) {
                boolean result = employeeDao.deleteEmployee(employeeId);
                
                if(result) {
                JOptionPane.showMessageDialog(null, "Successfully deleted the employee");
                model.setRowCount(0);
                } else {
                JOptionPane.showMessageDialog(null, "Was not able to delete employee.");
                }
            }
        }
    }
    
    private class ClrAllTxtDelEmpView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deleteEmployeeView.getTblEmployee().getModel();
            model.setRowCount(0);
            deleteEmployeeView.getEmpIdTxt().setText("");
        }
    }
    
    // Class to search employee from employee table in database for employee search view
    private class GetEmployeeById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int employeeId = Integer.parseInt(searchEmployeeView.getEmpIdTxt().getText());
            DefaultTableModel model = (DefaultTableModel)searchEmployeeView.getTblSearchEmployees().getModel();
            model.setRowCount(0);
            
            try{
                if(employeeId != 0) {                    
                    Employee employee = employeeDao.fetchEmployeeForEditTable(employeeId);
                    Object[] row = {
                        employee.getEmployeeId(),
                        employee.getFirstName() + " " + employee.getLastName(),
                        employee.getRole(),
                        employee.getPosition(),
                        employee.getEmail(),
                        employee.getCell(),
                        employee.getUnitNumber() + employee.getStreetAddress() + employee.getCity() + employee.getCountry(),
                        employee.getPostalCode(),
                        employee.getCreatedBy()
                        
                    };
                    model.addRow(row);                    
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Employee Id does not exist.");
            }
        }
    }
    
    /**
     * Class clears all text in search employee view.
     */
    private class ClrAllTxtSearchEmpView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchEmployeeView.getTblSearchEmployees().getModel();
            model.setRowCount(0);
            searchEmployeeView.getEmpIdTxt().setText("");
            searchEmployeeView.getPositionTxt().setText("");
        }
    }
    
    // Class to search employee from employee table in database for employee search view
    private class SearchEmployeeByPosition implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String employeePosition = searchEmployeeView.getPositionTxt().getText();
            DefaultTableModel model = (DefaultTableModel)searchEmployeeView.getTblSearchEmployees().getModel();
            model.setRowCount(0);          

            if(!employeePosition.equals("")) {       
                ArrayList<Employee> employees = employeeDao.fetchEmployeeByPosition(employeePosition);
                if(employees.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No employees with that position.");
                }

                for (Employee employee: employees) {
                    Object[] row = {
                        employee.getEmployeeId(),
                        employee.getFirstName() + " " + employee.getLastName(),
                        employee.getRole(),
                        employee.getPosition(),
                        employee.getEmail(),
                        employee.getCell(),
                        employee.getUnitNumber() + employee.getStreetAddress() + employee.getCity() + employee.getCountry(),
                        employee.getPostalCode(),
                        employee.getCreatedBy()                        
                    };                 
                    model.addRow(row);
                }       
            }            
        }
    }
    
    // Class to search employee from employee table in database for employee search view
    private class SearchAllEmployees implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {            
            DefaultTableModel model = (DefaultTableModel)searchEmployeeView.getTblSearchEmployees().getModel();
            model.setRowCount(0);          
                  
            ArrayList<Employee> employees = employeeDao.fetchAllEmployees();
            if(employees.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No employees to display.");
            }

            for (Employee employee: employees) {
                Object[] row = {
                    employee.getEmployeeId(),
                    employee.getFirstName() + " " + employee.getLastName(),
                    employee.getRole(),
                    employee.getPosition(),
                    employee.getEmail(),
                    employee.getCell(),
                    employee.getUnitNumber() + employee.getStreetAddress() + employee.getCity() + employee.getCountry(),
                    employee.getPostalCode(),
                    employee.getCreatedBy()                        
                };                 
                model.addRow(row);
            }                             
        }
    }
}
