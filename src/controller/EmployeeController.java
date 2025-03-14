package controller;

import dao.EmployeeDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
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
    
    public EmployeeController(EmployeeDAO employeeDao, AddEmployeeView addEmployeeView) {
        this.employeeDao = employeeDao;
        this.addEmployeeView = addEmployeeView;
        
        this.addEmployeeView.addEmpBtnActionListener(new AddEmployee());
        addEmployeeView.clearAllBtnActionListener(new ClearAllTextFields());
    }
    
    private class AddEmployee implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {      
            String empFName = addEmployeeView.getFnameTxt().getText();
            String empLName = addEmployeeView.getLnameTxt().getText();
            //Ask Jana and Max how to get month, year, day into variable
            String empDob = addEmployeeView.getDobTxt().getText();
            String empEmail = addEmployeeView.getEmailTxt().getText();
            // Do I need to remove spaced or dashes?
            int empSin = Integer.parseInt(addEmployeeView.getSinTxt().getText());
            String empStatus = addEmployeeView.getStatusTxt().getText();
            String empPhone = addEmployeeView.getPhoneTxt().getText();
            
            
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    private class ClearAllTextFields implements ActionListener {
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
        }
    
    }
}
