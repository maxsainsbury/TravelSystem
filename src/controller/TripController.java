/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.TripDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
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
