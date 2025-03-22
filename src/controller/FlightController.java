package controller;

import dao.FlightDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;
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
    private Pattern lettersOnly = Pattern.compile("[A-z]+");
    private Pattern numbersOnly = Pattern.compile("[0-9]+");
    private Pattern date = Pattern.compile("[0-9]{4}-[0-1][0-9]-");
    
    /**
     * Constructor for controlling the addFlightView
     * @param addFlightView
     * @param flightDAO 
     */
    public FlightController(AddFlightView addFlightView, FlightDAO flightDAO) {
        this.addFlightView = addFlightView;
        this.flightDAO = flightDAO;
        
        this.addFlightView.addFlightBtnListener(new AddFlightRecord());
        this.addFlightView.addClearBtnListener(new ClearAddTextFields());
    }
    /**
     * Constructor for controlling the deleteFlightView
     * @param deleteFlightView
     * @param flightDAO 
     */
    public FlightController(DeleteFlightView deleteFlightView, FlightDAO flightDAO){
        this.deleteFlightView = deleteFlightView;
        this.flightDAO = flightDAO;
        
        this.deleteFlightView.searchBtnListener(new SearchToDelete());
        this.deleteFlightView.clearAllBtnListener(new ClearDeleteTextFields());
        this.deleteFlightView.deleteFlightBtnListener(new DeletFlightRecord());
    }
    
    /**
     * Constructor for controlling the editFlightView
     * @param editFlightView
     * @param flightDAO 
     */
    public FlightController(EditFlightView editFlightView, FlightDAO flightDAO) {
        this.editFlightView = editFlightView;
        this.flightDAO = flightDAO;
        
        this.editFlightView.searchBtnListener(new SearchToEdit());
        this.editFlightView.editBtnListener(new EditFlighRecord());
        this.editFlightView.clearAllBtnListener(new ClearEditTextFields());
    }
    
    /**
     * Constructor for controlling the searchFlightView
     * @param searchFlightView
     * @param flightDAO 
     */
    public FlightController(SearchFlightView searchFlightView, FlightDAO flightDAO) {
        this.searchFlightView = searchFlightView;
        this.flightDAO = flightDAO;
        
        this.searchFlightView.searchAirlineBtnLisener(new SearchAirline());
        this.searchFlightView.searchIdBtnLisener(new SearchId());
        this.searchFlightView.searchAllBtnLisener(new SearchAll());
        this.searchFlightView.clearAllBtnLisener(new clearAllSearch());
    }

    //in the search view search by airline
    private class SearchAirline implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //formatter to change the DateTime values to the propper format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //store the table in a variable to manipulate later
            DefaultTableModel model = (DefaultTableModel)searchFlightView.getSearchTable().getModel();
            //remove all rows form the table
            model.setRowCount(0);
            //get the inputed airline vlaue
            String airline = searchFlightView.getAirlineTxt().getText();
            //get all returned flight from that airline from the database
            if(!airline.equals("")) {
                Flight[] output = flightDAO.searchFromAirline(airline);
                //if query returned any rows
                if(output[0].getFlightId() > 0) {
                    //for all rows returned
                    for(int i = 0; i < output.length; i++) {
                        //store all values in variables
                        int flightId = output[i].getFlightId();
                        int tripId = output[i].getTripId();
                        String flightNumber = output[i].getFlightNumber();
                        //format the dates to the correct format and remove the seconds position 
                        String departureTime = formatter.format(output[i].getDepartureTime()).substring(0, 16);
                        String arrivalTime = formatter.format(output[i].getArrivalTime()).substring(0, 16);
                        double price = output[i].getPrice();
                        String seatClass = output[i].getSeatClass();
                        String Status = output[i].getStatus();
                        //create a object array to place in the table
                        Object[] row = { flightId, tripId, airline, flightNumber, departureTime, arrivalTime, price, seatClass, Status };
                        //add the object to the table
                        model.addRow(row);
                    }

                }
                //if no rows returned
                else {
                    JOptionPane.showMessageDialog(null, "No flights with that airline!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No airline inputed in text box!");
            }
        }
        
    }
    //int the search view search by id
    private class SearchId implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DefaultTableModel model = (DefaultTableModel)searchFlightView.getSearchTable().getModel();
            model.setRowCount(0);
            String flightId = searchFlightView.getFlightIdTxt().getText();
            if(!flightId.equals("")) {
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
            else {
                JOptionPane.showMessageDialog(null, "No id inputed in text box!");
            }
        }
    }
    
    //in the search view search for everything
    private class SearchAll implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DefaultTableModel model = (DefaultTableModel)searchFlightView.getSearchTable().getModel();
            model.setRowCount(0);
            Flight[] output = flightDAO.searchAll();
            if(output[0].getFlightId() > 0) {
                for(int i = 0; i < output.length; i++) {
                    int flightId= output[i].getFlightId();
                    int tripId = output[i].getTripId();
                    String airline = output[i].getAirline();
                    String flightNumber = output[i].getFlightNumber();
                    String departureTime = formatter.format(output[i].getDepartureTime()).substring(0, 16);
                    String arrivalTime = formatter.format(output[i].getArrivalTime()).substring(0, 16);
                    double price = output[i].getPrice();
                    String seatClass = output[i].getSeatClass();
                    String Status = output[i].getStatus();
                    Object[] row = { flightId, tripId, airline, flightNumber, departureTime, arrivalTime, price, seatClass, Status };

                    model.addRow(row);
                }
                
            }
            else {
                JOptionPane.showMessageDialog(null, "No flight in database!");
            }
        }
    }
    
    //in the search view clear all fields
    private class clearAllSearch implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)searchFlightView.getSearchTable().getModel();
            model.setRowCount(0);
            searchFlightView.getAirlineTxt().setText("");
            searchFlightView.getFlightIdTxt().setText("");
        }
    }

    //in the delete view clear all fields
    private class ClearDeleteTextFields implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel)deleteFlightView.getDeleteTable().getModel();
            model.removeRow(0);
            model.addRow(new Object[9]);
            deleteFlightView.getIdTxt().setText("");
        }
    }

    //in the delete view delete a row
    private class DeletFlightRecord implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //get inputed vlaue
            int flightId = Integer.parseInt(deleteFlightView.getIdTxt().getText());
            //try to delete the row
            boolean result = flightDAO.deleteFlightRecort(flightId);
            //store the table in a variable
            DefaultTableModel model = (DefaultTableModel)deleteFlightView.getDeleteTable().getModel();
            //remove row from table
            model.setRowCount(0);
            //if row deleted
            if (result) {
                JOptionPane.showMessageDialog(null, "Flight record deleted successfully!");
            }
            //if row was not deleted
            else {
                JOptionPane.showMessageDialog(null, "Flight record was not deleted!");
            }
        }
    }
    
    //in delete view search the database
    private class SearchToDelete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e)  {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DefaultTableModel model = (DefaultTableModel)deleteFlightView.getDeleteTable().getModel();
            model.setRowCount(0);
            String flightId = deleteFlightView.getIdTxt().getText();
            if(!flightId.equals("")) {
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
            else {
                JOptionPane.showMessageDialog(null, "No id inputed in text box!");
            }
        }
    }

    //in the edit vie search the database
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
            if(flightId.equals("")) {
                Flight output = flightDAO.searchFlightFromId(flightId);
                System.out.println(output.getFlightId());
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
            else {
                JOptionPane.showMessageDialog(null, "No id entered in text box!");
            }
        }
    }
    
    //in the edit view edit a row in the database
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
            try {
                Flight flight = new Flight(airline, flightNumber, departureTime, arrivalTime, price, seatClass, status, tripId, flightId);

                boolean result = flightDAO.editFlightRecord(flight);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Flight record updated successfully!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Flight record was not updated!");
                }
            }
            catch(Exception err) {
                JOptionPane.showMessageDialog(null, "One or more dates not inputed correctly!");
            }
        }
    }
    
    //in edit view clear all text fields
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
    
    //in add view add a row to database
    private class AddFlightRecord implements ActionListener{
        public AddFlightRecord() {}

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String airline = addFlightView.getAirlineTxt().getText();
                String flightNumber = addFlightView.getFlightNumTxt().getText();
                String departureTime = addFlightView.getDepartureTxt().getText();
                String arrivalTime = addFlightView.getArrivalTxt().getText();
                double price = Double.parseDouble(addFlightView.getPriceTxt().getText());
                String seatClass = addFlightView.getSeatTxt().getText();
                String status = addFlightView.getStatusTxt().getText();
                int tripId = Integer.parseInt(addFlightView.getTripIdTxt().getText());
                try {
                    Flight flight = new Flight(airline, flightNumber, departureTime, arrivalTime, price, seatClass, status, tripId);
                    boolean result = flightDAO.addFlightRecord(flight);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Flight record created successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Flight record was not created!");
                    }
                }
                catch(Exception err) {
                    JOptionPane.showMessageDialog(null, "One or more dates not inputed correctly!");
                }
            }
            catch(Exception err) {
                JOptionPane.showMessageDialog(null, "One or more inputs are incorrect!");
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
