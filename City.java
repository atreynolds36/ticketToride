import java.util.LinkedList;
/**
 * City object represents city in Ticket to Ride. It contains
 * the cities of the game and their coordinates. It also
 * will be able to determine if the city has been visited
 * or not.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 15, 2013
 */
public class City
{
  //City x and y are based on central points
  //Cities are 16 x 13
  public int getX;
  public int getY;
  private String name;
  private boolean visit;
  
  
  /**
   * Constructor
   * @param n Name of the city.
   * @param x x coordinate of city
   * @param y y coordinate of city
   */
  public City( String n , int x, int y )
  {
    name = n;
    getX = x;
    getY = y;
  }
  /**
   * Contrsuctor - Creates a black city.
   */
  public City()
  {
    name = "NO CITY";
  }
  /**
   * Returns the name of the city
   * @return name the name of the city
   */ 
  public String getName()
  {
    return name;
  }
  /**
   * Determines if two cities are equal.
   * @returns true if cities are equal.
   * @return false if not equal.
   */
  public boolean equals(City c)
  {
    if( c.getName().equals(name))
      return true;
    return false;
  }
  /**
   * returns the boolean value of visit.
   * @return boolean value for visit.
   */
  public boolean getVisit()
  {
    return visit;
  }
  /**
   * set the boolean value of visit.
   * @param x boolean value for visit.
   */
  public void setVisit(boolean x)
  {
    visit = x;
  }
  /**
   * determines if the city is contained within
   * the coordinate.
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if is contained within coordinates.
   * false if not contained within coordinates.
   */
  public boolean contains( int x , int y )
  {
    if( ( x > (getX - 8) && x < (getX + 8 )) 
    && y > (getY - 7) && y < (getY + 7 ) )
      return true;
    return false;
  }
  /**
   * returns cities x coordinate.
   * @return x the x coordinate.
   */
  public int getX()
  {
    return getX;
  }
  /**
   * returns cities y coordinate.
   * @return y the y coordinate.
   */
  public int getY()
  {
    return getY;
  }
}
