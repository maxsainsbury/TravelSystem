package controller;

import dao.FlightDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
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
        
    }
    
    public FlightController(EditFlightView editFlightView, FlightDAO flightDAO) {
        this.editFlightView = editFlightView;
        this.flightDAO = flightDAO;
    }
    
    public FlightController(SearchFlightView searchFlightView, FlightDAO flightDAO) {
        this.searchFlightView = searchFlightView;
        this.flightDAO = flightDAO;
    }
    
    private class AddFlightRecord implements ActionListener{
        public AddFlightRecord() {}

        @Override
        public void actionPerformed(ActionEvent e) {
            String airLine = addFlightView.getAirlineTxt().getText();
            String flightNumber = addFlightView.getFlightNumTxt().getText();
            String departureTime = addFlightView.getDepartureTxt().getText();
            String arrivalTime = addFlightView.getArrivalTxt().getText();
            double price = Double.parseDouble(addFlightView.getPriceTxt().getText());
            String seatClass = addFlightView.getSeatTxt().getText();
            String status = addFlightView.getSeatTxt().getText();
            int tripId = Integer.parseInt(addFlightView.getTripIdTxt().getText());
            
            System.out.println(departureTime);
            System.out.println(arrivalTime);
            Flight flight = new Flight(airLine, flightNumber, departureTime, arrivalTime, price, seatClass, status, tripId);
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
