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
    private SearchTripView searchTripView;
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
        
        this.editTripView.searchBtnListener(new SearchForEdit());
        this.editTripView.clearAllBtnListener(new ClearAllEdit());
        this.editTripView.editTripBtnListener(new EditTripRecord());
    }
    
    public TripController(SearchTripView searchTripView, TripDAO tripDAO) {
        this.searchTripView = searchTripView;
        this.tripDAO = tripDAO;
        
        this.searchTripView.clearAllBtnListener(new ClearAllSearch());
        this.searchTripView.searchAllBtnListener(new SerchAll());
        this.searchTripView.searchIdBtnListener(new SearchById());
        this.searchTripView.searchMonthBtnListener(new SearchByMonth());
        this.searchTripView.searchOriginBtnListener(new SearchByOrigin());
    }

    private class ClearAllSearch implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchTripView.getSearchTable().getModel();
            searchTripView.getTripIdTxt().setText("");
            searchTripView.getOriginTxt().setText("");
            searchTripView.getMonthTxt().setText("");
            model.setRowCount(0);
        }
    }

    private class SerchAll implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Trip[] output = tripDAO.searchAll();
            DefaultTableModel model = (DefaultTableModel)searchTripView.getSearchTable().getModel();
            model.setRowCount(0);
            if(output[0].getTripId() > 0) {
                for(int i = 0; i < output.length; i++) {
                    int tripId = output[i].getTripId();
                    String origin = output[i].getOrigin();
                    String destination = output[i].getDestination();
                    String departureDate = output[i].getDepartureDate().toString();
                    String returnDate = output[i].getReturnDate().toString();
                    String status = output[i].getStatus();
                    int promotionId = output[i].getPromotionId();
                    
                    Object[] row = { tripId, origin, destination, departureDate, returnDate, status, promotionId };
                    model.addRow(row);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No trips in the trip database!");
            }
        }
    }

    private class SearchById implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String tripIdString = searchTripView.getTripIdTxt().getText();
            DefaultTableModel model = (DefaultTableModel)searchTripView.getSearchTable().getModel();
            model.setRowCount(0);
            if(!tripIdString.equals("")) {
                int tripId = Integer.parseInt(tripIdString);
                Trip output = tripDAO.searchTripFromId(tripId);
                if(output.getTripId() > 0) {
                    String origin = output.getOrigin();
                    String destination = output.getDestination();
                    String departureDate = output.getDepartureDate().toString();
                    String returnDate = output.getReturnDate().toString();
                    String status = output.getStatus();
                    int promotionId = output.getPromotionId();

                    Object[] row = { tripId, origin, destination, departureDate, returnDate, status, promotionId };
                    model.addRow(row);
                }
                else {
                    JOptionPane.showMessageDialog(null, "No trip related to that id!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No id inputed in text field!");
            }
        }
    }

    private class SearchByMonth implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchTripView.getSearchTable().getModel();
            model.setRowCount(0);
            String departureMonth = searchTripView.getMonthTxt().getText();
            if (!departureMonth.equals("")) {
                switch(departureMonth) {
                    case "January":
                    case "january":
                    case "Jan":
                    case "jan":
                        departureMonth = "01";
                        break;
                    case "Febuary":
                    case "febuary":
                    case "Feb":
                    case "feb":
                        departureMonth = "02";
                        break;
                    case "March":
                    case "Mar":
                    case "march":
                    case "mar":
                        departureMonth = "03";
                        break;
                    case "April":
                    case "april":
                    case "Apr":
                    case "apr":
                        departureMonth = "04";
                        break;
                    case "May":
                    case "may":
                        departureMonth = "05";
                        break;
                    case "June":
                    case "june":
                        departureMonth = "06";
                        break;
                    case "July":
                    case "july":
                        departureMonth = "07";
                        break;
                    case "August":
                    case "august":
                    case "Aug":
                    case "aug":
                        departureMonth = "08";
                        break;
                    case "September":
                    case "september":
                    case "Sep":
                    case "sep":
                        departureMonth = "09";
                        break;
                    case "October":
                    case "october":
                    case "Oct":
                    case "oct":
                        departureMonth = "10";
                        break;
                    case "November":
                    case "november":
                    case "Nov":
                    case "nov":
                        departureMonth = "11";
                        break;
                    case "December":
                    case "december":
                    case "Dec":
                    case "dec":
                        departureMonth = "12";
                        break;
                    default:
                        if (departureMonth.length() == 1) {
                            departureMonth = "0" + departureMonth;
                        }
                }


                Trip[] output = tripDAO.searchByMonth(departureMonth);
                if(output[0].getTripId() > 0) {
                    for(int i = 0; i < output.length; i++) {
                        int tripId = output[i].getTripId();
                        String origin = output[i].getOrigin();
                        String destination = output[i].getDestination();
                        String departureDate = output[i].getDepartureDate().toString();
                        String returnDate = output[i].getReturnDate().toString();
                        String status = output[i].getStatus();
                        int promotionId = output[i].getPromotionId();

                        Object[] row = { tripId, origin, destination, departureDate, returnDate, status, promotionId };
                        model.addRow(row);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No trip starting in that month!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No departure month inputed in text field!");
            }
        }
    }

    private class SearchByOrigin implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchTripView.getSearchTable().getModel();
            model.setRowCount(0);
            String origin = searchTripView.getOriginTxt().getText();
            if(!origin.equals("")) {
                Trip[] output = tripDAO.searchByOrigin(origin);
                if(output[0].getTripId() > 0) {
                    for(int i = 0; i < output.length; i++) {
                        int tripId = output[i].getTripId();
                        String destination = output[i].getDestination();
                        String departureDate = output[i].getDepartureDate().toString();
                        String returnDate = output[i].getReturnDate().toString();
                        String status = output[i].getStatus();
                        int promotionId = output[i].getPromotionId();

                        Object[] row = { tripId, origin, destination, departureDate, returnDate, status, promotionId };
                        model.addRow(row);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No trip from that origin city!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No origin location inputed in text field!");
            }
        }
    }

    private class SearchForEdit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editTripView.getOriginTxt().setText("");
            editTripView.getDestinationTxt().setText("");
            editTripView.getDepartureTxt().setText("");
            editTripView.getReturnTxt().setText("");
            editTripView.getStatusTxt().setText("");
            editTripView.getPromoIdTxt().setText("");
            String tripIdString = editTripView.getTripIdTxt().getText();
            if(!tripIdString.equals("")) {
                int tripId = Integer.parseInt(tripIdString);
                Trip output = tripDAO.searchTripFromId(tripId);
                if(output.getTripId() > 0) {
                    editTripView.getOriginTxt().setText(output.getOrigin());
                    editTripView.getDestinationTxt().setText(output.getDestination());
                    editTripView.getDepartureTxt().setText(output.getDepartureDate().toString());
                    editTripView.getReturnTxt().setText(output.getReturnDate().toString());
                    editTripView.getStatusTxt().setText(output.getStatus());
                    if(output.getPromotionId() > 0) {
                    editTripView.getPromoIdTxt().setText(Integer.toString(output.getPromotionId()));
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No Trip related to that id!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No id inputed in text field!");
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
            String tripIdString = editTripView.getTripIdTxt().getText();
            if(!tripIdString.equals("")) {
                System.out.println(tripIdString);
                int tripId = Integer.parseInt(tripIdString);
                String origin = editTripView.getOriginTxt().getText();
                String destination = editTripView.getDestinationTxt().getText();
                String departureDate = editTripView.getDepartureTxt().getText();
                String returnDate = editTripView.getReturnTxt().getText();
                String status = editTripView.getStatusTxt().getText();
                String promotionIdString = editTripView.getPromoIdTxt().getText();
                int promotionId;
                if(!promotionIdString.equals("")) {
                    promotionId = Integer.parseInt(editTripView.getPromoIdTxt().getText());
                }
                else {
                    promotionId = 0;
                }
                try {
                    Trip trip = new Trip(origin, destination, departureDate, returnDate, promotionId, status, tripId);
                    boolean result = tripDAO.editTripRecord(trip);

                    if(result) {
                        JOptionPane.showMessageDialog(null, "Trip record was updated scuccessfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Trip record was not updated!");
                    }
                }
                catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "One or more dates not inputed correctly!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No id inputed in the text field!");
            }
        }
    }

    private class SearchForDelete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DefaultTableModel model = (DefaultTableModel)deleteTripView.getDeleteTable().getModel();
            model.setRowCount(0);
            String tripIdString = deleteTripView.getIdTxt().getText();
            if(!tripIdString.equals("")) {
                int tripId = Integer.parseInt(tripIdString);
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
            else {
                JOptionPane.showMessageDialog(null, "No id inputed in text field!");
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
            try {
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
            catch (Exception err) {
                JOptionPane.showMessageDialog(null, "One or more dates not entered correctly!");
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
