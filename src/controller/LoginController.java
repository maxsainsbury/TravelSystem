package controller;

import dao.LoginDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;
import view.CustomerMainFrame;
import view.LoginView;
import view.MainFrame;

/**
 *
 * @author Max Sainsbury
 */
public class LoginController {
    private LoginView loginView;
    private LoginDAO loginDAO;
    
    public LoginController(LoginView loginView, LoginDAO loginDAO) {
        this.loginView = loginView;
        this.loginDAO = loginDAO;
        
        this.loginView.loginBtnListener(new Login());
    }

    private class Login implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUserIdTxt().getText();
            String password = loginView.getPasswordTxt().getText();
            
            User user = loginDAO.checkUserType(username, password);
            if(user.getUserId() > 0) {
                if(user.getUserType().toUpperCase() == "customer".toUpperCase()) {
                    CustomerMainFrame customerMainFrame = new CustomerMainFrame();
                }
                else if(user.getUserType().toUpperCase() == "employee".toUpperCase()) {
                    
                }
            } 
        }
    }
}
