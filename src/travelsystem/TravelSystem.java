package travelsystem;

import controller.FlightController;
import dao.FlightDAO;
import view.AddFlightView;
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
        EditFlightView editFlightView = new EditFlightView();
        FlightController flightController = new FlightController(editFlightView, flightDAO);
        
        editFlightView.setVisible(true);
    }
    
}
