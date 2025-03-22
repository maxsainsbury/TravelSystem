package controller;

import dao.EmployeeDAO;
import dao.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
    // This variable will hold an instance of employee to be used in employee editing.
    private Employee tempEmployee;
    
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
    public EmployeeController(EmployeeDAO employeeDao, DeleteEmployeeView deleteEmployeeView){
        this.employeeDao = employeeDao;
        this.deleteEmployeeView = deleteEmployeeView;
        
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
        
        this.editEmployeeView.searchBtnActionListener(new SearchById());
        this.editEmployeeView.clearAllBtnActionListener(new ClearAllEditView());
        this.editEmployeeView.editBtnActionListener(new EditEmployee());
    }
    
    /**
     * Class to create an instance of Employee from the user input in
     * addEmployeeView and send the employee information to the database
     * using employeeDao.
     */
    private class AddEmployee implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {      
            try {
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
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please double check your input.");
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
            DefaultTableModel model = (DefaultTableModel)deleteEmployeeView.getTblEmployee().getModel();
            model.setRowCount(0);
            
            try{
                int employeeId = Integer.parseInt(deleteEmployeeView.getEmpIdTxt().getText());
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
            try{
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
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please double check your input.");
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
            try{
                int employeeId = Integer.parseInt(searchEmployeeView.getEmpIdTxt().getText());
                DefaultTableModel model = (DefaultTableModel)searchEmployeeView.getTblSearchEmployees().getModel();
                model.setRowCount(0);
            
            
                if(employeeId != 0) {                    
                    Employee employee = employeeDao.fetchEmployeeById(employeeId);
                    Object[] row = {
                        employee.getEmployeeId(),
                        employee.getFirstName() + " " + employee.getLastName(),
                        employee.getRole(),
                        employee.getPosition(),
                        employee.getEmail(),
                        employee.getCell(),
                        employee.getUnitNumber() + employee.getStreetAddress() + employee.getCity() + employee.getCountry(),
                        employee.getCountry(),
                        employee.getPostalCode(),
                        employee.getStatus(),
                        employee.getSIN(),
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

                    for (Employee employee: employees) {
                        Object[] row = {
                        employee.getEmployeeId(),
                        employee.getFirstName() + " " + employee.getLastName(),
                        employee.getRole(),
                        employee.getPosition(),
                        employee.getEmail(),
                        employee.getCell(),
                        employee.getUnitNumber() + employee.getStreetAddress() + employee.getCity() + employee.getCountry(),
                        employee.getCountry(),
                        employee.getPostalCode(),
                        employee.getStatus(),
                        employee.getSIN(),
                        employee.getCreatedBy()                      
                        };                 
                        model.addRow(row);
                    }   
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
                        employee.getCountry(),
                        employee.getPostalCode(),
                        employee.getStatus(),
                        employee.getSIN(),
                        employee.getCreatedBy()                        
                };                 
                model.addRow(row);
            }                             
        }
    }
    
    // Class to search employee from employee table in database for employee edit view
    private class SearchById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {     
            try{
                int employeeId = Integer.parseInt(editEmployeeView.getEmpIdTxt().getText());

                if(employeeId != 0) {                    
                    Employee employee = employeeDao.fetchEmployeeById(employeeId);
                    // Temporarly hold this employee to be used in employee edit.
                    tempEmployee = employee;
                    editEmployeeView.getFnameTxt().setText(employee.getFirstName());
                    editEmployeeView.getLnameTxt().setText(employee.getLastName());
                    editEmployeeView.getDobTxt().setText(employee.getDob().toString());
                    editEmployeeView.getEmailTxt().setText(employee.getEmail());
                    editEmployeeView.getSinTxt().setText(Integer.toString(employee.getSIN()));
                    editEmployeeView.getStatusTxt().setText(employee.getStatus());
                    editEmployeeView.getPhoneTxt().setText(employee.getPhone());
                    editEmployeeView.getCellTxt().setText(employee.getCell());
                    editEmployeeView.getUnitTxt().setText(employee.getUnitNumber());
                    editEmployeeView.getStreetTxt().setText(employee.getStreetAddress());
                    editEmployeeView.getCityTxt().setText(employee.getCity());
                    editEmployeeView.getPostalTxt().setText(employee.getPostalCode());
                    editEmployeeView.getCountryTxt().setText(employee.getCountry());
                    editEmployeeView.getPositionTxt().setText(employee.getPosition());
                    editEmployeeView.getSalaryTxt().setText(Double.toString(employee.getSalary()));
                    editEmployeeView.getRoleTxt().setText(employee.getRole());
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Employee Id does not exist.");
            }
        }
    }
    
    private class ClearAllEditView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editEmployeeView.getEmpIdTxt().setText("");
            editEmployeeView.getFnameTxt().setText("");
            editEmployeeView.getLnameTxt().setText("");
            editEmployeeView.getDobTxt().setText("");
            editEmployeeView.getEmailTxt().setText("");
            editEmployeeView.getSinTxt().setText("");
            editEmployeeView.getStatusTxt().setText("");
            editEmployeeView.getPhoneTxt().setText("");
            editEmployeeView.getCellTxt().setText("");
            editEmployeeView.getUnitTxt().setText("");
            editEmployeeView.getStreetTxt().setText("");
            editEmployeeView.getCityTxt().setText("");
            editEmployeeView.getPostalTxt().setText("");
            editEmployeeView.getCountryTxt().setText("");
            editEmployeeView.getPositionTxt().setText("");
            editEmployeeView.getSalaryTxt().setText("");
            editEmployeeView.getRoleTxt().setText("");

        }
    }
    
    /**
     * Class to edit employee information in database in the editEmployeeView
     */
    private class EditEmployee implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {  
            try{
                if(tempEmployee != null) {                                
                    tempEmployee.setFirstName(editEmployeeView.getFnameTxt().getText());
                    tempEmployee.setLastName(editEmployeeView.getLnameTxt().getText());
                    tempEmployee.setDob(LocalDate.parse(editEmployeeView.getDobTxt().getText()));
                    tempEmployee.setEmail(editEmployeeView.getEmailTxt().getText());
                    tempEmployee.setSIN(Integer.parseInt(editEmployeeView.getSinTxt().getText()));
                    tempEmployee.setStatus(editEmployeeView.getStatusTxt().getText());
                    tempEmployee.setPhone(editEmployeeView.getPhoneTxt().getText());
                    tempEmployee.setCell(editEmployeeView.getCellTxt().getText());
                    tempEmployee.setUnitNumber(editEmployeeView.getUnitTxt().getText());
                    tempEmployee.setStreetAddress(editEmployeeView.getStreetTxt().getText());
                    tempEmployee.setCity(editEmployeeView.getCityTxt().getText());
                    tempEmployee.setPostalCode(editEmployeeView.getPostalTxt().getText());
                    tempEmployee.setCountry(editEmployeeView.getCountryTxt().getText());
                    tempEmployee.setPosition(editEmployeeView.getPositionTxt().getText());
                    tempEmployee.setSalary(Double.parseDouble(editEmployeeView.getSalaryTxt().getText()));
                    tempEmployee.setRole(editEmployeeView.getRoleTxt().getText());

                    boolean result = employeeDao.editEmployee(tempEmployee);
                    if(result) {
                        JOptionPane.showMessageDialog(null, "Successfully edited employee Id:  " + tempEmployee.getEmployeeId() + ".");
                    } else {
                        JOptionPane.showMessageDialog(null, "Was not able to edit employee.");
                    }
                }     
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please double check your input.");
            }
        }
    }
}
