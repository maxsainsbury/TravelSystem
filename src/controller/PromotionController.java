package controller;

import dao.PromotionDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Promotion;
import view.AddPromoView;
import view.SearchPromoView;
import view.DeletePromoView;
import view.EditPromoView;


/**
 *
 * @author Goen Choi
 */

public class PromotionController {
    private PromotionDAO promotionDao;
    private AddPromoView addPromotionView;
    private SearchPromoView searchPromotionView;
    private DeletePromoView deletePromotionView;
    private EditPromoView editPromotionView;
    // Class variables that will hold values to be used in multiple classes.
    private int promotionIdToDelete;
    private Promotion promoToEdit;    
    private String lettersRegEx = "^[a-zA-Z]+[ ]*[a-zA-Z]*$";
    private String numberOnlyRegEx = "^[0-9]+$";
    // Date must be yyyy-mm-dd format
    private String dateRegEx = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])";
    private String floatRegEx = "[-+]?\\d*[.,]\\d+|\\d+";
    private String monthRegEx = "^[a-zA-Z]{3}$";
    private String percentRegEx = "^(100(\\.0+)?|(\\d{1,2})(\\.\\d+)?)$";
    
    // For adding promotion
    public PromotionController(PromotionDAO promotionDao, AddPromoView addPromotionView){
        this.promotionDao = promotionDao;
        this.addPromotionView = addPromotionView;
        addPromotionView.addPromoBtnActionListener(new AddPromotion());
        addPromotionView.clearAllBtnActionListener(new ClearAllAddPromotion());
    }
    
    // for searching promotion 
    public PromotionController(PromotionDAO promotionDao, SearchPromoView searchPromotionView) {
        this.promotionDao = promotionDao;
        this.searchPromotionView = searchPromotionView;
        searchPromotionView.searchAllBtnActionListener(new SearchAllPromotion());
        searchPromotionView.clearAllBtnActionListener(new ClearAllSearchPromotion());
        searchPromotionView.searchNameActionListener(new SearchPromoByName());
        searchPromotionView.searchMonthBtnActionListener(new SearchPromoByMonth());
    }
    
    // For deleting promotion
    public PromotionController(PromotionDAO promotionDao, DeletePromoView deletePromotionView) {
        this.promotionDao = promotionDao;
        this.deletePromotionView = deletePromotionView;
        deletePromotionView.clearAllBtnActionListener(new ClearAllDeletePromotion());
        deletePromotionView.searchBtnActionListener(new SearchPromotionById());
        deletePromotionView.deleteBtnActionListener(new DeletePromotion());
        
    }
    
    // For editing promotion
    public PromotionController(PromotionDAO promotionDao, EditPromoView editPromotionView) {
        this.promotionDao = promotionDao;
        this.editPromotionView = editPromotionView;

        editPromotionView.clearAllBtnActionListener(new ClearAllEditPromoView());
        editPromotionView.searchBtnActionListener(new SearchByIdForEdit());
        editPromotionView.editBtnActionListener(new EditPromotion());
        
    }
    /**
     * Inserts the newly created promotion into the database with it's attributes.
     */
    private class AddPromotion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Validate all input values with regex
            String name = addPromotionView.getPromoNameTxt().getText().strip();
            if(!name.matches(lettersRegEx)) {
                JOptionPane.showMessageDialog(null, "Name should only have letters and spaces.");
                return;
            }
            
            String discountString = addPromotionView.getPercentTxt().getText().strip();
            if(!discountString.matches(percentRegEx)) {
                JOptionPane.showMessageDialog(null, "Discount should only have numbers and decimal point.");
                return;
            }          
            double discount = Double.parseDouble(discountString);
                       
            String startDate = addPromotionView.getStartTxt().getText().strip();
            String endDate = addPromotionView.getEndTxt().getText();
            if(!startDate.matches(dateRegEx) || !(endDate.matches(dateRegEx))) {
                JOptionPane.showMessageDialog(null, "State date and end date must be yyyy-mm-dd format.");
                return;
            }
                        
            String description = addPromotionView.getDescTxt().getText();
            String status = addPromotionView.getStatusTxt().getText().strip();
            if(!description.matches(lettersRegEx) || !(status.matches(lettersRegEx))){
                JOptionPane.showMessageDialog(null, "Description and status can only have letters and spcaes.");
                return;
            }            
            // If all values are validated, create a new promotion with those valuse and add to database. 
            Promotion newPromotion = new Promotion(name, discount, description, status, startDate, endDate);
            boolean result = promotionDao.addPromotion(newPromotion);

            if(result) {
                JOptionPane.showMessageDialog(null, "Successfully added a new promotion");
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to add new promotion.");
            }   
        }
    }
    
    /**
     * Clears all text in add promotion view.
     */
    private class ClearAllAddPromotion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addPromotionView.getPromoNameTxt().setText("");
            addPromotionView.getPercentTxt().setText("");
            addPromotionView.getStartTxt().setText("");
            addPromotionView.getEndTxt().setText("");
            addPromotionView.getDescTxt().setText("");
            addPromotionView.getStatusTxt().setText("");
        }     
    }
    
    /**
     * Display all promotions in database.
     */
    private class SearchAllPromotion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchPromotionView.getSearchPromoTbl().getModel();
            model.setRowCount(0);          
                  
            ArrayList<Promotion> promotions = promotionDao.fetchAllPromotions();
            if(promotions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No promotions to display.");
            }

            for (Promotion promotion: promotions) {
                Object[] row = {
                    promotion.getPromoId(),
                    promotion.getPromoName(),
                    promotion.getDiscountPercent(),
                    promotion.getStartDate(),
                    promotion.getEndDate(),
                    promotion.getStatus()                      
                };                 
                model.addRow(row);
            }  
        }
    }

    
    /**
     * Clear all text in search promotion view.
     */
    private class ClearAllSearchPromotion implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchPromotionView.getSearchPromoTbl().getModel();
            model.setRowCount(0);
            searchPromotionView.getPromoNameTxt().setText("");
            searchPromotionView.getMonthTxt().setText("");
        }
    }
    
    /**
     * Search promotion by name.
     * Displays of all promotions with matching name.
     */
    private class SearchPromoByName implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchPromotionView.getSearchPromoTbl().getModel();
            model.setRowCount(0);
            // Validate input with regex
            String promoName = searchPromotionView.getPromoNameTxt().getText().strip();
            if(!promoName.matches(lettersRegEx)) {
                JOptionPane.showMessageDialog(null, "Promotion name can only have letters and spaces.");
            }  
            // Get an arraylist of the promotion instances that were retrieved from the database.
            ArrayList<Promotion> promotions = promotionDao.fetchPromotionByName(promoName);
            if(promotions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Promotion name does not exist.");
                return;
            }
            // Display the attributes of each instances in each row of table.
            for (Promotion promotion: promotions) {
                Object[] row = {
                    promotion.getPromoId(),
                    promotion.getPromoName(),
                    promotion.getDiscountPercent(),
                    promotion.getStartDate(),
                    promotion.getEndDate(),
                    promotion.getStatus()                      
                };                 
                model.addRow(row);
            }  
        }
    }
    
    /**
     * Displays all promotions with matching month.
     */
    private class SearchPromoByMonth implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchPromotionView.getSearchPromoTbl().getModel();
            model.setRowCount(0);
            // Validate input with regex
            String promoMonth = searchPromotionView.getMonthTxt().getText().strip();
            if(!promoMonth.matches(monthRegEx)) {
                JOptionPane.showMessageDialog(null, "Please enter a month in MMM format(e.g.JAN, FEB).");
                return;
            }
            // Get an arraylist of promotion instances retrieved from database.          
            ArrayList<Promotion> promotions = promotionDao.fetchPromotionByMonth(promoMonth);                    
            // If no promotions exsist for that month in database display meessage to user.
            if(promotions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No promotion during this month.");
                return;
            }
            for (Promotion promotion: promotions) {
                Object[] row = {
                    promotion.getPromoId(),
                    promotion.getPromoName(),
                    promotion.getDiscountPercent(),
                    promotion.getStartDate(),
                    promotion.getEndDate(),
                    promotion.getStatus()                      
                };                 
                model.addRow(row);
            }   
        }
    }
    
    /**
     * Clears all text in delete promotion view.
     */
    private class ClearAllDeletePromotion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deletePromotionView.getDeletePromoTbl().getModel();
            model.setRowCount(0);
            deletePromotionView.getIdTxt().setText("");           
        }     
    }
    
    /**
     * Class to search promotion by id in database.
     * Retrieve the promotion and display it in search promotion view.
     */
    private class SearchPromotionById implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deletePromotionView.getDeletePromoTbl().getModel();
            model.setRowCount(0);
            // Store the promotion id that was searched to be deleted in class level variavble.
            String promotionIdString = deletePromotionView.getIdTxt().getText().strip();
            if(!promotionIdString.matches(numberOnlyRegEx)) {
                JOptionPane.showMessageDialog(null, "Promotion id can only have numbers.");
                return;
            }
            promotionIdToDelete = Integer.parseInt(promotionIdString);

            if(promotionIdToDelete != 0) {                    
                Promotion promotion = promotionDao.fetchPromotionById(promotionIdToDelete);
                Object[] row = {
                    promotion.getPromoId(),
                    promotion.getPromoName(),
                    promotion.getDiscountPercent(),
                    promotion.getStartDate(),
                    promotion.getEndDate(),
                    promotion.getStatus()

                };
                model.addRow(row);                    
            }
        }                              
    }
    
    /**
     * Deletes the promotion that was searched in SearchPromotionById.
     */
    private class DeletePromotion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deletePromotionView.getDeletePromoTbl().getModel();
            model.setRowCount(0);
            boolean result = promotionDao.deletePromotion(promotionIdToDelete);

            if(result) {
                JOptionPane.showMessageDialog(null, "Successfully deleted promotion");
                // Reset the promotion id to 0
                promotionIdToDelete = 0;
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to delete promotion.");
            }     
        }
    }
    
    /**
     * Edits the promotion that was searched by SearchByIdForEdit.
     */
    private class EditPromotion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {  
            // check if the instance of promotion that was instantiated by SearchByIDForEdit is not null
            if(promoToEdit != null) {
                // Validate all input values with regex
                String name = editPromotionView.getPromoNameTxt().getText().strip();
                String description = editPromotionView.getDescTxt().getText().strip();
                String status = editPromotionView.getStatusTxt().getText().strip();
                if(!(name.matches(lettersRegEx)) || !(description.matches(lettersRegEx)) || !(status.matches(lettersRegEx))) {
                    JOptionPane.showMessageDialog(null, "Promotion name, description, status can only contain letters and spaces.");
                    return;
                }
                promoToEdit.setPromoName(name);
                promoToEdit.setStatus(description);
                promoToEdit.setDescription(status);
                
                String id = editPromotionView.getPromoIdTxt().getText().strip();
                if(!id.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Promotion id can consist of numbers only.");
                    return;
                }
                promoToEdit.setPromoId(Integer.parseInt(id));      
                
                String startDate = editPromotionView.getStartTxt().getText().strip();
                String endDate = editPromotionView.getEndTxt().getText().strip();
                if(!(startDate.matches(dateRegEx)) || !(endDate.matches(dateRegEx))) {
                    JOptionPane.showMessageDialog(null, "Date must be in yyyy-mm-dd formate.");
                    return;
                }     
                promoToEdit.setStartDate(LocalDate.parse(startDate));
                promoToEdit.setEndDate(LocalDate.parse(endDate));
                
                String percentage = editPromotionView.getPercentTxt().getText().strip();
                if(!percentage.matches(percentRegEx)) {
                    JOptionPane.showMessageDialog(null, "Discount percentage must be in decimal format.");
                    return;
                }
                promoToEdit.setDiscountPercent(Double.parseDouble(percentage));

                boolean result = promotionDao.editPromotion(promoToEdit);
                if(result) {
                    JOptionPane.showMessageDialog(null, "Successfully updated promotion");
                } else {
                    JOptionPane.showMessageDialog(null, "Was not able to update promotion.");
                }
            }     
        }  
    }
    
    /**
     * Class to search promotion from promotion table in database for promotion search view.
     */
    private class SearchByIdForEdit implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {      
            try{
                // Validate id with regex
                String id = editPromotionView.getPromoIdTxt().getText().strip();
                if(!id.matches(numberOnlyRegEx)) {
                    JOptionPane.showMessageDialog(null, "Promotion Id can only have numbers");
                    return;
                }
                int promotionId = Integer.parseInt(id);
                
                if(promotionId != 0) {                    
                    promoToEdit = promotionDao.fetchPromotionById(promotionId);

                    editPromotionView.getPromoNameTxt().setText(promoToEdit.getPromoName());
                    editPromotionView.getStatusTxt().setText(promoToEdit.getStatus());
                    editPromotionView.getStartTxt().setText(promoToEdit.getStartDate().toString());
                    editPromotionView.getEndTxt().setText(promoToEdit.getEndDate().toString());
                    editPromotionView.getDescTxt().setText(promoToEdit.getDescription());
                    editPromotionView.getPromoIdTxt().setText(Integer.toString(promoToEdit.getPromoId()));
                    editPromotionView.getPercentTxt().setText(String.valueOf(promoToEdit.getDiscountPercent()));
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "promotion Id does not exist.");
            }
        }
    }
        
    /**
     * Clear all text in search promotion view.
     */
    private class ClearAllEditPromoView implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            editPromotionView.getPromoIdTxt().setText("");
            editPromotionView.getPromoNameTxt().setText("");
            editPromotionView.getStartTxt().setText("");
            editPromotionView.getEndTxt().setText("");
            editPromotionView.getStatusTxt().setText("");
            editPromotionView.getDescTxt().setText("");
            editPromotionView.getPercentTxt().setText("");

        }
    }
}
