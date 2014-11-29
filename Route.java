import java.awt.*;
/**
 * A Route Object that contains the routes between cities. It
 * will know the name of the cities, their length, and the color
 * of that route. It will also know if the route has been taken
 * by a player or not.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 15, 2013
 */
public class Route
{
  protected City one;
  protected City two;
  protected int length;
  protected Color color;
  protected boolean take;
  public Route neighbor;
  /**
   * Constructor that creates a route from two cities,
   * a length, and a color
   */
  public Route( City c1 , City c2 , int lng, Color c)
  {
    one = c1;
    two = c2;
    length = lng;
    color = c;
    take = true;
    neighbor = null;

  }

  /**
   * Returns distance of route
   * 
   * @return distance of route
   */
  public int getLength()
  {
    return length;
  }

  /**
   * Returns first city on route
   * 
   * @param first city
   */
  public City getFirst()
  {
    return one;
  }

  /**
   * Returns second city on route
   * 
   * @param second city
   */
  public City getSecond()
  {
    return two;
  }

  /**
   * Returns color of route
   * 
   * @param color of the route
   */
  public Color getColor()
  {
    return color;
  }

  /**
   * Marks take to false
   * 
   */
  public void markTaken()
  {
    take = false;
  }

  /**
   * Returns whether this route can be taken
   * 
   * @boolean can route be taken
   */
  public boolean canTake()
  {
    return take;
  }

  /**
   * Neighboring route set to r
   */
  public void setNeighbor(Route r)
  {
    neighbor = r;
  }

  /**
   * Returns true if double route
   * 
   * @boolean true if double route
   */
  public boolean isNeighbor(Route r)
  {
    if( r == neighbor )
      return true;
    return false;
  }    

  /**
   * Returns if same route from city o to city s
   * 
   * @param o first city on route
   * @param s second city on route
   * 
   * @return if same route
   */
  public boolean same( City o, City s )
  {
    if( o.equals(one) && s.equals(two) )
      return true;
    if( o.equals(two) && s.equals(one) )
      return true;
    return false;
    }
}
