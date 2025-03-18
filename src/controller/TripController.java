/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.TripDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Trip;
import view.AddTripView;
import view.DeleteTripView;
import view.EditTripView;
import view.SearchTripView;

/**
 *
 * @author Maxwe
 */
public class TripController {
    private AddTripView addTripView;
    private DeleteTripView deleteTripView;
    private EditTripView editTripView;
    private SearchTripView searchtripView;
    private TripDAO tripDAO;
    
    public TripController(AddTripView addTripView, TripDAO tripDAO) {
        this.addTripView = addTripView;
        this.tripDAO = tripDAO;
        
        this.addTripView.addTripBtnListener(new AddTripRecord());
        this.addTripView.addClearBtnListener(new ClearAddTextFields());
    }
    
    public TripController(DeleteTripView deleteTripView, TripDAO tripDAO) {
        this.deleteTripView = deleteTripView;
        this.tripDAO = tripDAO;
        
        this.deleteTripView.searchTripBtnListener(new SearchForDelete());
        this.deleteTripView.clearAllBtnListener(new ClearAllDelete());
        this.deleteTripView.deleteTripBtnListener(new DeleteTripRecord());
    }
    
    public TripController(EditTripView editTripView, TripDAO tripDAO) {
        this.editTripView = editTripView;
        this.tripDAO = tripDAO;
        
        this.editTripView.searchBtnListener(new SerchForEdit());
        this.editTripView.clearAllBtnListener(new ClearAllEdit());
        this.editTripView.editTripBtnListener(new EditTripRecord());
    }

    private class SerchForEdit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int tripId = Integer.parseInt(editTripView.getTripIdTxt().getText());
            Trip output = tripDAO.searchTripFromId(tripId);
            if(output.getTripId() > 0) {
                editTripView.getOriginTxt().setText(output.getOrigin());
                editTripView.getDestinationTxt().setText(output.getDestination());
                editTripView.getDepartureTxt().setText(output.getDepartureDate().toString());
                editTripView.getReturnTxt().setText(output.getReturnDate().toString());
                editTripView.getStatusTxt().setText(output.getStatus());
                editTripView.getPromoIdTxt().setText(Integer.toString(output.getPromotionId()));
            }
        }
    }

    private class ClearAllEdit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editTripView.getTripIdTxt().setText("");
            editTripView.getOriginTxt().setText("");
            editTripView.getDestinationTxt().setText("");
            editTripView.getDepartureTxt().setText("");
            editTripView.getReturnTxt().setText("");
            editTripView.getStatusTxt().setText("");
            editTripView.getPromoIdTxt().setText("");
        }
    }

    private class EditTripRecord implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int tripId = Integer.parseInt(editTripView.getTripIdTxt().getText());
            String origin = editTripView.getOriginTxt().getText();
            String destination = editTripView.getDestinationTxt().getText();
            String departureDate = editTripView.getDepartureTxt().getText();
            String returnDate = editTripView.getReturnTxt().getText();
            String status = editTripView.getStatusTxt().getText();
            int promotionId = Integer.parseInt(editTripView.getPromoIdTxt().getText());
            
            Trip trip = new Trip(origin, destination, departureDate, returnDate, promotionId, status, tripId);
            boolean result = tripDAO.editTripRecord(trip);
            
            if(result) {
                JOptionPane.showMessageDialog(null, "Trip record was updated scuccessfully!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Trip record was not updated!");
            }
        }
    }

    private class SearchForDelete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DefaultTableModel model = (DefaultTableModel)deleteTripView.getDeleteTable().getModel();
            model.setRowCount(0);
            int tripId = Integer.parseInt(deleteTripView.getIdTxt().getText());
            Trip output = tripDAO.searchTripFromId(tripId);
            if(output.getTripId() > 0) {
                String origin = output.getOrigin();
                String destination = output.getDestination();
                String departureDate = output.getDepartureDate().toString();
                String returnDate = output.getReturnDate().toString();
                String status = output.getStatus();
                int promotionId = output.getPromotionId();
                
                Object[] row = {tripId, origin, destination, departureDate, returnDate, status, promotionId };
                model.addRow(row);
                
            }
            else {
                JOptionPane.showMessageDialog(null, "No trips with that trip id!");
            }
        }
    }

    private class ClearAllDelete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deleteTripView.getDeleteTable().getModel();
            model.setRowCount(0);
            deleteTripView.getIdTxt().setText("");
        }
    }

    private class DeleteTripRecord implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int flightId = Integer.parseInt(deleteTripView.getIdTxt().getText());
            DefaultTableModel model = (DefaultTableModel)deleteTripView.getDeleteTable().getModel();
            boolean result = tripDAO.deleteTripRecord(flightId);
            if(result) {
                model.setRowCount(0);
                JOptionPane.showMessageDialog(null, "Trip record deleted successfully!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Trip record was not deleted!");
            }
            
        }
    }

    private class AddTripRecord implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String origin = addTripView.getOriginTxt().getText();
            String destination = addTripView.getDestinationTxt().getText();
            String departureDate = addTripView.getDepartureTxt().getText();
            String returnDate = addTripView.getReturnTxt().getText();
            String status = addTripView.getStatusTxt().getText();
            String promotionIdString = addTripView.getPromoIdTxt().getText();
            int promotionId;
            boolean result;
            if(!promotionIdString.equals("")) {
                promotionId = Integer.parseInt(promotionIdString);
                Trip trip = new Trip(origin, destination, departureDate, returnDate, promotionId, status);
                result = tripDAO.addTripRecordPromo(trip);
            }
            else {
                Trip trip = new Trip(origin, destination, departureDate, returnDate, 0, status);
                result = tripDAO.addTripRecordNoPromo(trip);
            }
            if (result) {
                JOptionPane.showMessageDialog(null, "Trip record created successfully!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Trip record was not created!");
            }
        }
    }

    private class ClearAddTextFields implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addTripView.getOriginTxt().setText("");
            addTripView.getDestinationTxt().setText("");
            addTripView.getDepartureTxt().setText("");
            addTripView.getReturnTxt().setText("");
            addTripView.getStatusTxt().setText("");
            addTripView.getPromoIdTxt().setText("");
        }
    }
    
}
