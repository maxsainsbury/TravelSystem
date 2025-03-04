package travelsystem;

import testing.ModelTest;

/**
 *
 * @author C0457438
 */
public class TravelSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ModelTest test = new ModelTest();
        
        test.UserTest();
        test.EmployeeTest();
        test.ClientTest();
        test.promotionTest();
        test.flightTest();
        test.tripTest();
    }
    
}
