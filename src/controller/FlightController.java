package controller;

import dao.FlightDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Flight;
import view.AddFlightView;
import view.DeleteFlightView;
import view.EditFlightView;
import view.SearchFlightView;

/**
 *
 * @author Max Sainsbury
 */
public class FlightController {
    private AddFlightView addFlightView;
    private DeleteFlightView deleteFlightView;
    private EditFlightView editFlightView;
    private SearchFlightView searchFlightView;
    private FlightDAO flightDAO;
    
    public FlightController(AddFlightView addFlightView, FlightDAO flightDAO) {
        this.addFlightView = addFlightView;
        this.flightDAO = flightDAO;
        
        this.addFlightView.addFlightBtnListener(new AddFlightRecord());
        this.addFlightView.addClearBtnListener(new ClearAddTextFields());
    }
    
    public FlightController(DeleteFlightView deleteFlightView, FlightDAO flightDAO){
        this.deleteFlightView = deleteFlightView;
        this.flightDAO = flightDAO;
        
        this.deleteFlightView.searchBtnListener(new SearchToDelete());
        this.deleteFlightView.clearAllBtnListener(new ClearDeleteTextFields());
        this.deleteFlightView.deleteFlightBtnListener(new DeletFlightRecord());
    }
    
    public FlightController(EditFlightView editFlightView, FlightDAO flightDAO) {
        this.editFlightView = editFlightView;
        this.flightDAO = flightDAO;
        
        this.editFlightView.searchBtnListener(new SearchToEdit());
        this.editFlightView.editBtnListener(new EditFlighRecord());
        this.editFlightView.clearAllBtnListener(new ClearEditTextFields());
    }
    
    public FlightController(SearchFlightView searchFlightView, FlightDAO flightDAO) {
        this.searchFlightView = searchFlightView;
        this.flightDAO = flightDAO;
    }


    private class ClearDeleteTextFields implements ActionListener {

        public ClearDeleteTextFields() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deleteFlightView.getDeleteTable().getModel();
            model.removeRow(0);
            model.addRow(new Object[9]);
            deleteFlightView.getIdTxt().setText("");
        }
    }

    private class DeletFlightRecord implements ActionListener {

        public DeletFlightRecord() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int flightId = Integer.parseInt(deleteFlightView.getIdTxt().getText());
            boolean result = flightDAO.deleteFlightRecort(flightId);
            DefaultTableModel model = (DefaultTableModel)deleteFlightView.getDeleteTable().getModel();
            model.removeRow(0);
            model.addRow(new Object[9]);
            if (result) {
                JOptionPane.showMessageDialog(null, "Flight record deleted successfully!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Flight record was not deleted!");
            }
        }
    }

    private class SearchToDelete implements ActionListener {

        public SearchToDelete() {
        }

        @Override
        public void actionPerformed(ActionEvent e)  {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DefaultTableModel model = (DefaultTableModel)deleteFlightView.getDeleteTable().getModel();
            model.removeRow(0);
            String flightId = deleteFlightView.getIdTxt().getText();
            Flight output = flightDAO.searchFlightFromId(flightId);
            if(output.getFlightId() > 0) {
                int tripId = output.getTripId();
                String airline = output.getAirline();
                String flightNumber = output.getFlightNumber();
                String departureTime = formatter.format(output.getDepartureTime()).substring(0, 16);
                String arrivalTime = formatter.format(output.getArrivalTime()).substring(0, 16);
                double price = output.getPrice();
                String seatClass = output.getSeatClass();
                String Status = output.getStatus();
                Object[] row = { flightId, tripId, airline, flightNumber, departureTime, arrivalTime, price, seatClass, Status };
                
                model.addRow(row);
                
            }
            else {
                model.addRow(new Object[9]);
                JOptionPane.showMessageDialog(null, "No flight with that flight id!");
            }
        }
    }

    private class SearchToEdit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editFlightView.getAirlineTxt().setText("");
            editFlightView.getFlightNumTxt().setText("");
            editFlightView.getDepartureTxt().setText("");
            editFlightView.getArrivalTxt().setText("");
            editFlightView.getPriceTxt().setText("");
            editFlightView.getSeatTxt().setText("");
            editFlightView.getStatusTxt().setText("");
            editFlightView.getTripIdTxt().setText("");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String flightId = editFlightView.getFlighIdTxt().getText();
            Flight output = flightDAO.searchFlightFromId(flightId);
            if(output.getFlightId() > 0) {
                editFlightView.getAirlineTxt().setText(output.getAirline());
                editFlightView.getFlightNumTxt().setText(output.getFlightNumber());
                editFlightView.getDepartureTxt().setText(formatter.format(output.getDepartureTime()).substring(0,16));
                editFlightView.getArrivalTxt().setText(formatter.format(output.getArrivalTime()).substring(0, 16));
                editFlightView.getPriceTxt().setText(Double.toString(output.getPrice()));
                editFlightView.getSeatTxt().setText(output.getSeatClass());
                editFlightView.getStatusTxt().setText(output.getStatus());
                editFlightView.getTripIdTxt().setText(Integer.toString(output.getTripId()));
            }
            else {
                JOptionPane.showMessageDialog(null, "No flight with that flight id!");
            }
        }
    }

    private class EditFlighRecord implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int flightId = Integer.parseInt(editFlightView.getFlighIdTxt().getText());
            String airline = editFlightView.getAirlineTxt().getText();
            String flightNumber = editFlightView.getFlightNumTxt().getText();
            String departureTime = editFlightView.getDepartureTxt().getText();
            String arrivalTime = editFlightView.getArrivalTxt().getText();
            double price = Double.parseDouble(editFlightView.getPriceTxt().getText());
            String seatClass = editFlightView.getSeatTxt().getText();
            String status = editFlightView.getStatusTxt().getText();
            int tripId = Integer.parseInt(editFlightView.getTripIdTxt().getText());
            Flight flight = new Flight(airline, flightNumber, departureTime, arrivalTime, price, seatClass, status, tripId, flightId);
            
            boolean result = flightDAO.editFlightRecord(flight);
            if (result) {
                JOptionPane.showMessageDialog(null, "Flight record updated successfully!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Flight record was not updated!");
            }
        }
    }

    private class ClearEditTextFields implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            editFlightView.getAirlineTxt().setText("");
            editFlightView.getFlightNumTxt().setText("");
            editFlightView.getDepartureTxt().setText("");
            editFlightView.getArrivalTxt().setText("");
            editFlightView.getPriceTxt().setText("");
            editFlightView.getSeatTxt().setText("");
            editFlightView.getStatusTxt().setText("");
            editFlightView.getTripIdTxt().setText("");
        }
    }
    
    private class AddFlightRecord implements ActionListener{
        public AddFlightRecord() {}

        @Override
        public void actionPerformed(ActionEvent e) {
            String airline = addFlightView.getAirlineTxt().getText();
            String flightNumber = addFlightView.getFlightNumTxt().getText();
            String departureTime = addFlightView.getDepartureTxt().getText();
            String arrivalTime = addFlightView.getArrivalTxt().getText();
            double price = Double.parseDouble(addFlightView.getPriceTxt().getText());
            String seatClass = addFlightView.getSeatTxt().getText();
            String status = addFlightView.getStatusTxt().getText();
            int tripId = Integer.parseInt(addFlightView.getTripIdTxt().getText());
            
            Flight flight = new Flight(airline, flightNumber, departureTime, arrivalTime, price, seatClass, status, tripId);
            boolean result = flightDAO.addFlightRecord(flight);
            if (result) {
                JOptionPane.showMessageDialog(null, "Flight record created successfully!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Flight record was not created!");
            }
        }
    }
    
    private class ClearAddTextFields implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            addFlightView.getAirlineTxt().setText("");
            addFlightView.getFlightNumTxt().setText("");
            addFlightView.getDepartureTxt().setText("");
            addFlightView.getArrivalTxt().setText("");
            addFlightView.getPriceTxt().setText("");
            addFlightView.getSeatTxt().setText("");
            addFlightView.getStatusTxt().setText("");
            addFlightView.getTripIdTxt().setText("");
        }
    
}
}
