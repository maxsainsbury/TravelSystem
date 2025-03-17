package travelsystem;

import controller.FlightController;
import dao.FlightDAO;
import view.AddFlightView;
import view.DeleteFlightView;
import view.EditFlightView;
import view.SearchFlightView;

/**
 *
 * @author C0457438
 */
public class TravelSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlightDAO flightDAO = new FlightDAO();
        DeleteFlightView deleteFlightView = new DeleteFlightView();
        FlightController flightController = new FlightController(deleteFlightView, flightDAO);
        FlightDAO flightDAO2 = new FlightDAO();
        AddFlightView addFlightView2 = new AddFlightView();
        FlightController flightController2 = new FlightController(addFlightView2, flightDAO2);
        FlightDAO flightDAO3 = new FlightDAO();
        SearchFlightView searchFlightView2 = new SearchFlightView();
        FlightController flightController3 = new FlightController(searchFlightView2, flightDAO3);
        FlightDAO flightDAO4 = new FlightDAO();
        EditFlightView editFlightView2 = new EditFlightView();
        FlightController flightController4 = new FlightController(editFlightView2, flightDAO3);
        
        
        deleteFlightView.setVisible(true);
        addFlightView2.setVisible(true);
        searchFlightView2.setVisible(true);
        editFlightView2.setVisible(true);
    }
    
}
