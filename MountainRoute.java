// Necessary imports
import java.awt.Color;
/**
 * This class inherits from the Route class and implement mountain routes.
 * It will keep track of how many X's there are on the route. 
 * The X's indicate how many mountains there are on that route. 
 * It will also keep track of the length of the route, the color of 
 * the route,and the two cities that are connected by the route.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 15, 2013
 */
public class MountainRoute extends Route
{
  // Instance variables
  private int xSymbols;

  /**
   * This constructor will set up a mountain route. 
   * It uses the arguments from the parameters 
   * in order to set up the mountain route.
   * 
   * @param c1 the name of first city
   * @param c2 the name of second city
   * @param lng the length of the route
   * @param c the Color on the route
   * @param x the number of X's on the mountain of that route
   */
  public MountainRoute (City c1 , City c2 , int lng, Color c, int x)
  {
    super(c1 , c2 , lng, c);
    xSymbols = x;
  }

  /**
   * This method will return the number of X's on the route, 
   * where the X is the number of mountains on that specific route.
   * 
   * @return the number of mountains on a route
   */
  public int getXs()
  {
    return xSymbols;
  }
}
