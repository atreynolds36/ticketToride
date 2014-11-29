// Necessary imports
import java.awt.Color;
/**
 * This class inherits from the Route class and implement ferry routes. 
 * It will keep track of how many locomotives 
 * that are on the route as well as the length of the route,
 * the color of the route, and the two cities 
 * that are connected by the route.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 15, 2013
 */
public class FerryRoute extends Route
{
  // Instance variables
  private int locomotives;

  /**
   * This constructor will set up a ferry route. 
   * It uses the arguments from the parameters in order 
   * to set up the ferry route.
   * 
   * @param c1 the name of first city
   * @param c2 the name of second city
   * @param lng the length of the route
   * @param c the Color on the route
   * @param loco the number of locomotives on the route
   */
  public FerryRoute(City c1 , City c2 , int lng, Color c, int loco)
  {
    super(c1, c2, lng, c);
    locomotives = loco;
  }

  /**
   * This method will return the number of locomotive on the route.
   * 
   * @return the number of locomotives on a route
   */
  public int getLocomotives()
  {
    return locomotives;
  }
}
