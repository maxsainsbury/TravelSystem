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
    private Pattern lettersOnly = Pattern.compile("^[A-z]+$");
    private Pattern lettersAndSpaces = Pattern.compile("^[A-z ]$");
    private Pattern numbersOnly = Pattern.compile("^[0-9]+$");
    private Pattern pricePattern = Pattern.compile("^[0-9]+\\.?[0-9]{0,2}$");
    private Pattern lettersAndNumbers = Pattern.compile("^[0-9A-z]+$");
    private Pattern date = Pattern.compile(
                        "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$" 
                        + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                        + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$" 
                        + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
    private Pattern time = Pattern.compile("^ (2[0-3]|[01]?[0-9]):([0-5]?[0-9])$");
    
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
            Matcher airlineMatch = lettersOnly.matcher(airline);
            //get all returned flight from that airline from the database
            if(airlineMatch.find()) {
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
                JOptionPane.showMessageDialog(null, "Airline must only contain letters");
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
            Matcher flightIdMatch = numbersOnly.matcher(flightId);
            if(flightIdMatch.find()) {
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
                JOptionPane.showMessageDialog(null, "Id must only contain numbers!");
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
            String flightIdString = deleteFlightView.getIdTxt().getText();
            Matcher flightIdMatcher = numbersOnly.matcher(flightIdString);
            if(flightIdMatcher.find()){
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
            else {
                JOptionPane.showMessageDialog(null, "Flight id can only contain numbers");
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
            Matcher flightIdMatch = numbersOnly.matcher(flightId);
            if(flightIdMatch.find()) {
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
                JOptionPane.showMessageDialog(null, "Flight id can only contain numbers");
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
            Matcher flightIdMatch = numbersOnly.matcher(flightId);
            if(flightIdMatch.find()) {
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
                JOptionPane.showMessageDialog(null, "Flight id can only contain numbers");
            }
        }
    }
    
    //in the edit view edit a row in the database
    private class EditFlighRecord implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String flightIdString = editFlightView.getFlighIdTxt().getText();
            Matcher flightIdMatch = numbersOnly.matcher(flightIdString);
            if(!flightIdMatch.find()) {
                JOptionPane.showMessageDialog(null, "Flight id can only contain numbers");
                return;
            }
            int flightId = Integer.parseInt(editFlightView.getFlighIdTxt().getText());
            String airline = editFlightView.getAirlineTxt().getText();
            Matcher airlineMatch = lettersOnly.matcher(airline);
            if(!airlineMatch.find()) {
                JOptionPane.showMessageDialog(null, "Airline can only contain letters!");
                return;
            }
            String flightNumber = editFlightView.getFlightNumTxt().getText();
            Matcher flightNumMatch = lettersAndNumbers.matcher(flightNumber);
            if(!flightNumMatch.find()) {
                JOptionPane.showMessageDialog(null, "Flight number can only contain letters and numbers!");
                return;
            }
            String departureTime = editFlightView.getDepartureTxt().getText();
            Matcher dateMatcher = date.matcher(departureTime.substring(0, 10));
            Matcher timeMatcher= time.matcher(departureTime.substring(10));
            if(!dateMatcher.find() | !timeMatcher.find()) {
                JOptionPane.showMessageDialog(null, "Departure date formated incorrectly!");
                return;
            }
            String arrivalTime = editFlightView.getArrivalTxt().getText();
            dateMatcher = date.matcher(arrivalTime.substring(0, 10));
            timeMatcher = time.matcher(arrivalTime.substring(10));
            if(!dateMatcher.find() | !timeMatcher.find()) {
                JOptionPane.showMessageDialog(null, "Arrival dat formated incorrectly!");
                return;
            }
            String priceString = editFlightView.getPriceTxt().getText();
            Matcher priceMatch = pricePattern.matcher(priceString);
            if(!priceMatch.find()) {
                JOptionPane.showMessageDialog(null, "pirce is formated incorrectly!");
                return;
            }
            double price = Double.parseDouble(priceString);
            String seatClass = editFlightView.getSeatTxt().getText();
            Matcher seatMatch = lettersOnly.matcher(seatClass);
            if(!seatMatch.find()) {
                JOptionPane.showMessageDialog(null, "Seat class can only contain letters!");
                return;
            }
            String status = editFlightView.getStatusTxt().getText();
            Matcher statusMatch = lettersAndSpaces.matcher(status);
            if(!statusMatch.find()) {
                JOptionPane.showMessageDialog(null, "Status can only contain letters and spaces!");
                return;
            }
            String tripIdString = editFlightView.getTripIdTxt().getText();
            Matcher tripIdMatch = numbersOnly.matcher(tripIdString);
            if(!tripIdMatch.find()) {
                JOptionPane.showMessageDialog(null, "Trip id can only contain numbers!");
                return;
            }
            int tripId = Integer.parseInt(tripIdString);
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
                Matcher airlineMatch = lettersOnly.matcher(airline);
                if(!airlineMatch.find()) {
                    JOptionPane.showMessageDialog(null, "Airline can only contain letters!");
                    return;
                }
                String flightNumber = addFlightView.getFlightNumTxt().getText();
                Matcher flightNumMatch = lettersAndNumbers.matcher(flightNumber);
                if(!flightNumMatch.find()) {
                    JOptionPane.showMessageDialog(null, "Flight number can only contain letters and numbers!");
                    return;
                }
                String departureTime = addFlightView.getDepartureTxt().getText();
                System.out.println(departureTime.substring(0, 10));
                Matcher dateMatcher = date.matcher(departureTime.substring(0, 10));
                Matcher timeMatcher= time.matcher(departureTime.substring(10));
                if(!dateMatcher.find() | !timeMatcher.find()) {
                    JOptionPane.showMessageDialog(null, "Departure date formated incorrectly!");
                    return;
                }
                String arrivalTime = addFlightView.getArrivalTxt().getText();
                dateMatcher = date.matcher(arrivalTime.substring(0, 10));
                timeMatcher = time.matcher(arrivalTime.substring(10));
                if(!dateMatcher.find() | !timeMatcher.find()) {
                    JOptionPane.showMessageDialog(null, "Arrival date formated incorrectly!");
                    return;
                }
                String priceString = addFlightView.getPriceTxt().getText();
                Matcher priceMatch = pricePattern.matcher(priceString); 
                if(!priceMatch.find()) {
                    JOptionPane.showMessageDialog(null, "pirce is formated incorrectly!");
                    return;
                }
                double price = Double.parseDouble(priceString);
                String seatClass = addFlightView.getSeatTxt().getText();
                Matcher seatMatch = lettersOnly.matcher(seatClass);
                if(!seatMatch.find()) {
                    JOptionPane.showMessageDialog(null, "Seat class can only contain letters!");
                    return;
                }
                String status = addFlightView.getStatusTxt().getText();
                Matcher statusMatch = lettersAndSpaces.matcher(status);
                if(!statusMatch.find()) {
                    JOptionPane.showMessageDialog(null, "Status can only contain letters and Spaces!");
                    return;
                }
                String tripIdString = addFlightView.getTripIdTxt().getText();
                Matcher tripIdMatch = numbersOnly.matcher(tripIdString);
                if(!tripIdMatch.find()) {
                    JOptionPane.showMessageDialog(null, "Trip id can only contain numbers!");
                    return;
                }
                int tripId = Integer.parseInt(tripIdString);
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
