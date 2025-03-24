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
import java.sql.SQLException;


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
    private String lettersRegEx = "^[a-zA-Z]+[ ]*[a-zA-Z]*$";
    private String sinNumRegEx =  "^\\d{9}$";
    private String postalCodeRegEx = "^[ABCEGHJ-NPRSTVXY][0-9][ABCEGHJ-NPRSTV-Z][ ]?[0-9][ABCEGHJ-NPRSTV-Z][0-9]$";
    private String phoneNumRegEx = "^(1|)[2-9]\\d{2}[2-9]\\d{6}$";
    private String ageRegEx = "^([1-9][0-9]?|100)$";
    private String emailRegEx = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private String numberOnlyRegEx = "^[0-9]+$";
    // Date must be yyyy-mm-dd format
    private String dateRegEx = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])";
    private String floatRegEx = "[-+]?\\d*[.,]\\d+|\\d+";
    
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
                // Validate all values with regex
                String empFName = addEmployeeView.getFnameTxt().getText().strip();
                String empLName = addEmployeeView.getLnameTxt().getText().strip();
                if(!empFName.matches(lettersRegEx) || (!empLName.matches(lettersRegEx))) {
                    JOptionPane.showMessageDialog(null, "Name can only have letters.");
                    return;
                }
                
                String empDob = addEmployeeView.getDobTxt().getText().strip();
                if(!empDob.matches(dateRegEx)){
                    JOptionPane.showMessageDialog(null, "Date must be in yyyy-mm-dd format.");
                    return;
                }
                
                String empEmail = addEmployeeView.getEmailTxt().getText().strip();
                if(!empEmail.matches(emailRegEx)){
                    JOptionPane.showMessageDialog(null, "Inavalid email format.");
                    return;
                }
                
                int empSin;
                String empSinString = addEmployeeView.getSinTxt().getText().strip();
                if (!empSinString.matches(sinNumRegEx)){
                    JOptionPane.showMessageDialog(null, "SIN must consist of 9 digits.");
                    return;
                }                
                empSin = Integer.parseInt(empSinString);
                
                String empStatus = addEmployeeView.getStatusTxt().getText().strip();
                if(!empStatus.matches(lettersRegEx)) {
                    JOptionPane.showMessageDialog(null, "Status can only have letters.");
                    return;
                }
                
                String empPhone = addEmployeeView.getPhoneTxt().getText().strip();
                String empCell = addEmployeeView.getCellTxt().getText().strip();
                if(!empPhone.matches(phoneNumRegEx) || (!empCell.matches(phoneNumRegEx))) {
                    JOptionPane.showMessageDialog(null, "Phone number can only have numbers. ");
                    return;
                }

                String empUnit = addEmployeeView.getUnitTxt().getText().strip();
                if(!empUnit.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Invalid unit number. ");
                    return;
                }
                
                String empStreetAddress = addEmployeeView.getStreetTxt().getText().strip();
                String empCity = addEmployeeView.getCityTxt().getText();
                if(!empStreetAddress.matches(lettersRegEx)|| (!empCity.matches(lettersRegEx))){
                    JOptionPane.showMessageDialog(null, "Street address and city can only have letters. ");
                    return;
                }

                String empPostalCode = addEmployeeView.getPostalTxt().getText().strip();
                if(!empPostalCode.matches(postalCodeRegEx)) {
                    JOptionPane.showMessageDialog(null, "Invalid Canadian postal code. ");
                    return;                   
                }
                
                String empCountry = addEmployeeView.getCountryTxt().getText().strip();                
                if(!empCountry.matches(lettersRegEx)) {
                    JOptionPane.showMessageDialog(null, "Country can only have letters. ");
                }
                
                String empPosition = addEmployeeView.getPositionTxt().getText().strip();
                if(!empPosition.matches(lettersRegEx)) {
                    JOptionPane.showMessageDialog(null, "Position can only have letters and spaces. ");
                }

                String empSalaryString = addEmployeeView.getSalaryTxt().getText().strip();
                if(!empSalaryString.matches(floatRegEx)) {
                    JOptionPane.showMessageDialog(null, "Salary can only have numbers and a decimal point. ");
                    return;
                }                              
                float empSalary = Float.parseFloat(empSalaryString);
                               
                String empRole = addEmployeeView.getRoleTxt().getText().strip();
                if(!empRole.matches(lettersRegEx)) {
                    JOptionPane.showMessageDialog(null, "Role can only have letters. ");
                    return;
                }
                
                String empUserName = addEmployeeView.getUserNameTxt().getText().strip();
                String empPassword = addEmployeeView.getPasswordTxt().getText().strip();
                
                String empCreatedByString = addEmployeeView.getCreatedByTxt().getText().strip();
                if (!empCreatedByString.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Salary can only have numbers and a decimal point. ");
                    return;
                }
                int empCreatedBy = Integer.parseInt(empCreatedByString);

                // Create new instance of Employee to send data to database. 
                Employee newEmployee = new Employee(empFName, empLName, empEmail, empSin, empPhone, 
                        empUnit, empStreetAddress, empCity, empCountry, empPostalCode, empDob, empStatus, 
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
            addEmployeeView.getCreatedByTxt().setText("");
        }   
    }
    
    // Class to search employee from employee table in database for employee delete view.
    private class SearchEmployeeById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deleteEmployeeView.getTblEmployee().getModel();
            model.setRowCount(0);
            
            try{
                // Validate employee id with regex
                String employeeIdString = deleteEmployeeView.getEmpIdTxt().getText().strip();
                if(!employeeIdString.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Employee Id can only have numbers");
                    return;
                }
                int employeeId = Integer.parseInt(employeeIdString);
                
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
                // Validate employee id with regex
                String employeeIdString = deleteEmployeeView.getEmpIdTxt().getText().strip();
                if(!employeeIdString.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Employee Id can only have numbers");
                    return;
                }                
                int employeeId = Integer.parseInt(employeeIdString);
                
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
                // Validate employee id with regex
                String employeeIdString = searchEmployeeView.getEmpIdTxt().getText().strip();
                if(!employeeIdString.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Employee can only have numnbers.");
                    return;
                }
                int employeeId = Integer.parseInt(employeeIdString);
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
                        employee.getUnitNumber() +", " + employee.getStreetAddress() + ", " + employee.getCity(),
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
        public void actionPerformed(ActionEvent e){
            DefaultTableModel model = (DefaultTableModel)searchEmployeeView.getTblSearchEmployees().getModel();
            model.setRowCount(0);
            String employeePosition = searchEmployeeView.getPositionTxt().getText().strip();
            // Validate position with regex
            if(!employeePosition.matches(lettersRegEx)) {
                JOptionPane.showMessageDialog(null, "Position can only have letters and spaces.");
                return;
            }
                                 

            if(!employeePosition.equals("")) {  
                System.out.println(employeePosition);
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
                        employee.getUnitNumber() +", " + employee.getStreetAddress() + ", " + employee.getCity(),                     
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
                        employee.getUnitNumber() +", " + employee.getStreetAddress() + ", " + employee.getCity(),
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
                // Validate employee id with regex
                String employeeIdString = editEmployeeView.getEmpIdTxt().getText().strip();
                if(!employeeIdString.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Employee Id can only have numbers.");
                    return;
                }               
                int employeeId = Integer.parseInt(employeeIdString);

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
            // check if tempEmployee object is not null
            if(tempEmployee != null) {
                // Validate all inputs with regex before setting the employee's attribues
                String fName = editEmployeeView.getFnameTxt().getText().strip();
                String lName = editEmployeeView.getLnameTxt().getText().strip();
                if(!fName.matches(lettersRegEx) || !(lName.matches(lettersRegEx))) {
                    JOptionPane.showMessageDialog(null, "Name can only have letters and spaces.");
                    return;
                }                        
                tempEmployee.setFirstName(fName);
                tempEmployee.setLastName(lName);

                String dobString = editEmployeeView.getDobTxt().getText().strip();
                if (!dobString.matches(dateRegEx)) {
                    JOptionPane.showMessageDialog(null, "Date must be in yyyy-mm-dd format.");
                    return;
                }            
                tempEmployee.setDob(LocalDate.parse(dobString));

                String email = editEmployeeView.getEmailTxt().getText().strip();
                if(!email.matches(emailRegEx)) {
                    JOptionPane.showMessageDialog(null, "Invalid email format.");
                    return;
                }
                tempEmployee.setEmail(email);

                String sin = editEmployeeView.getSinTxt().getText().strip();
                if(!sin.matches(sinNumRegEx)){
                    JOptionPane.showMessageDialog(null, "Sin must be 9 digits long");
                    return;
                }                                       
                tempEmployee.setSIN(Integer.parseInt(sin));

                String status = editEmployeeView.getStatusTxt().getText().strip();
                if(!status.matches(lettersRegEx)) {
                    JOptionPane.showMessageDialog(null, "Status can only have letters and spaces.");
                    return;
                }
                tempEmployee.setStatus(status);

                String phone = editEmployeeView.getPhoneTxt().getText().strip();
                String cell = editEmployeeView.getCellTxt().getText().strip();
                if(!phone.matches(phoneNumRegEx) || !(cell.matches(phoneNumRegEx))) {
                    JOptionPane.showMessageDialog(null, "Phone number can only have numbers. ");
                    return;
                }
                tempEmployee.setPhone(phone);
                tempEmployee.setCell(cell);

                String unit = editEmployeeView.getUnitTxt().getText().strip();
                if(!unit.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Unit number can only have numbers. ");
                    return;
                }
                tempEmployee.setUnitNumber(unit);

                String street = editEmployeeView.getStreetTxt().getText().strip();
                String city = editEmployeeView.getCityTxt().getText().strip();
                if(!street.matches(lettersRegEx) || !(city.matches(lettersRegEx))) {
                    JOptionPane.showMessageDialog(null, "Street and city can only have letters and spaces. ");
                    return;
                }
                tempEmployee.setStreetAddress(street);    
                tempEmployee.setCity(city);

                String postal = editEmployeeView.getPostalTxt().getText().strip();
                if(!postal.matches(postalCodeRegEx)) {
                    JOptionPane.showMessageDialog(null, "Invalid Canadian postal code.");
                    return;
                }
                tempEmployee.setPostalCode(postal);

                String country = editEmployeeView.getCountryTxt().getText().strip();
                String position = editEmployeeView.getPositionTxt().getText().strip();
                if(!country.matches(lettersRegEx) || !(position.matches(lettersRegEx))) {
                    JOptionPane.showMessageDialog(null, "Invalid Canadian postal code.");
                    return;
                }         
                tempEmployee.setCountry(country);
                tempEmployee.setPosition(position);

                String salary = editEmployeeView.getSalaryTxt().getText().strip();
                if(!salary.matches(floatRegEx)) {
                    JOptionPane.showMessageDialog(null, "Salary must be a whole or decimal point number.");
                    return;
                }                   
                tempEmployee.setSalary(Double.parseDouble(salary));

                String role = editEmployeeView.getRoleTxt().getText().strip();
                if(!role.matches(lettersRegEx)) {
                    JOptionPane.showMessageDialog(null, "Role can only have letters and spaces.");
                    return;
                }
                tempEmployee.setRole(role);

                boolean result = employeeDao.editEmployee(tempEmployee);
                if(result) {
                    JOptionPane.showMessageDialog(null, "Successfully edited employee Id:  " + tempEmployee.getEmployeeId() + ".");
                } else {
                    JOptionPane.showMessageDialog(null, "Was not able to edit employee.");
                }
            }     
        }
    }
}
