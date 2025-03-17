package travelsystem;

import controller.FlightController;
import dao.FlightDAO;
import view.AddFlightView;
import view.DeleteFlightView;
import view.EditFlightView;

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
        
        deleteFlightView.setVisible(true);
        addFlightView2.setVisible(true);
    }
    
}
