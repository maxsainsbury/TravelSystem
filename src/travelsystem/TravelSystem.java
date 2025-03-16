package travelsystem;

import controller.FlightController;
import dao.FlightDAO;
import view.AddFlightView;

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
        AddFlightView addFlightView = new AddFlightView();
        FlightController flightController = new FlightController(addFlightView, flightDAO);
        
        addFlightView.setVisible(true);
    }
    
}
