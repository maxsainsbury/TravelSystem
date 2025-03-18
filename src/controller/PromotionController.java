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
            String name = addPromotionView.getPromoNameTxt().getText();
            double discount = Double.parseDouble(addPromotionView.getPercentTxt().getText());
            String startDate = addPromotionView.getStartTxt().getText();
            String endDate = addPromotionView.getEndTxt().getText();
            String description = addPromotionView.getDescTxt().getText();
            String status = addPromotionView.getStatusTxt().getText();

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
            String promoName = searchPromotionView.getPromoNameTxt().getText();
            
            try {
                if(!promoName.equals("")) {          
                    ArrayList<Promotion> promotions = promotionDao.fetchPromotionByName(promoName);
                     
                    if(promotions.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No promotions with that name.");
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
                } else {
                    throw new Exception(); 
                }
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Promotion name does not exist.");
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
            String promoMonth = searchPromotionView.getMonthTxt().getText();
            
            try {
                if(!promoMonth.equals("")) {          
                    ArrayList<Promotion> promotions = promotionDao.fetchPromotionByMonth(promoMonth);
                     
                    if(promotions.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No promotion during this month.");
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
                } else {
                    throw new Exception(); 
                }
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid month (format: MMM).");
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
    
    private class SearchPromotionById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deletePromotionView.getDeletePromoTbl().getModel();
            model.setRowCount(0);
            // Store the promotion id that was searched to be deleted in class level variavble.
            promotionIdToDelete = Integer.parseInt(deletePromotionView.getIdTxt().getText());
            if(promotionIdToDelete != 0) {
                try{
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
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, "Promotion Id does not exist.");
                }
            }
        }            
    }
    
    private class DeletePromotion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deletePromotionView.getDeletePromoTbl().getModel();
            model.setRowCount(0);
            boolean result = promotionDao.deletePromotion(promotionIdToDelete);

            if(result) {
                JOptionPane.showMessageDialog(null, "Successfully deleted promotion");
                promotionIdToDelete = 0;
            } else {
                JOptionPane.showMessageDialog(null, "Was not able to delete promotion.");
            }     
        }
    }
    
    private class EditPromotion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {  
            
            if(promoToEdit != null) {                                
                promoToEdit.setPromoName(editPromotionView.getPromoNameTxt().getText());
                promoToEdit.setPromoId(Integer.parseInt(editPromotionView.getPromoIdTxt().getText()));
                promoToEdit.setStartDate(LocalDate.parse(editPromotionView.getStartTxt().getText()));
                promoToEdit.setEndDate(LocalDate.parse(editPromotionView.getEndTxt().getText()));
                promoToEdit.setStatus(editPromotionView.getStatusTxt().getText());
                promoToEdit.setDescription(editPromotionView.getDescTxt().getText());
                promoToEdit.setDiscountPercent(Double.parseDouble(editPromotionView.getPercentTxt().getText()));

                boolean result = promotionDao.editPromotion(promoToEdit);
                if(result) {
                    JOptionPane.showMessageDialog(null, "Successfully updated promotion");
                } else {
                    JOptionPane.showMessageDialog(null, "Was not able to update promotion.");
                }
                // Celar the variable.
                promoToEdit = null;
            }            
        }  
    }
    
        // Class to search promotion from promotion table in database for promotion search view
    private class SearchByIdForEdit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int promotionId = Integer.parseInt(editPromotionView.getPromoIdTxt().getText());       
            try{
                if(promotionId != 0) {                    
                    promoToEdit = promotionDao.fetchPromotionById(promotionId);

                    editPromotionView.getPromoNameTxt().setText(promoToEdit.getPromoName());
                    editPromotionView.getStatusTxt().setText(promoToEdit.getStatus());
                    editPromotionView.getStartTxt().setText(promoToEdit.getStartDate().toString());
                    editPromotionView.getEndTxt().setText(promoToEdit.getEndDate().toString());
                    editPromotionView.getDescTxt().setText(promoToEdit.getDescription());
                    editPromotionView.getPromoIdTxt().setText(Integer.toString(promoToEdit.getPromoId()));
                    editPromotionView.getPercentTxt().setText(String.valueOf(promoToEdit.getDiscountPercent()));
                } else {
                    throw new Exception();

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
