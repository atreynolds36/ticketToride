import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.*;
import java.util.*;
/**
 * Holds cities and routes for Legendary Asia TTR. This
 * class will create a map in which the player can play
 * the game on.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 15, 2013
 */
public class Map
{
  private ArrayList<Route> allRoutes;
  private ArrayList<City> cities;
  private HashMap < City , LinkedList<Route> > cityRoutes;
  /**
   * ConstructorMouseEvent object for the click.
   */
  public Map() 
  {
    allRoutes = new ArrayList<Route>();
    cities = new ArrayList<City> ();
    cityRoutes = new HashMap < City , LinkedList<Route> > ();
    initMap();
    initHash();
  }

  /**
   * Initializes cities and routes in Legendary Asia TTR
   */
  public void initMap()
  {        
    int count = 0;
    try{
      File f1 = new File("cities.txt");
      Scanner scan = new Scanner(f1);
      while( scan.hasNext() ){
        StringTokenizer t = new StringTokenizer(scan.nextLine(), " ");
        String name = t.nextToken();
        if(name.equals("Ulan"))
          name = "Ulan " + t.nextToken();
        int x = Integer.parseInt(t.nextToken());
        int y = Integer.parseInt(t.nextToken());
        City temp = new City( name, x , y );
        cities.add(temp);
      }
      scan.close();
      File f2 = new File("routes.txt");
      Scanner scan2 = new Scanner(f2);
      while( scan2.hasNext() ){
        String ln = scan2.nextLine();
        StringTokenizer tokenizer = new StringTokenizer(ln, " -,:");
        Route r;
        String c1 = tokenizer.nextToken();
        if( c1.equals("Ulan"))
          c1 = c1 + " " +tokenizer.nextToken();
        String c2 = tokenizer.nextToken();
        if( c2.equals("Ulan"))
          c2 = c2 + " " +tokenizer.nextToken();
        int three = Integer.parseInt(tokenizer.nextToken());
        String get = tokenizer.nextToken();
        Color col = setColor(get);
        City cit1 = new City();
        City cit2 = new City();
        int stop = 0;
        for( City c : cities ){
          if( c.getName().equals(c1)){
            cit1 = c;
            stop++;
          }
          if( c.getName().equals(c2)){
            cit2 = c;
            stop++;
          }
          if( stop == 2)
            break;
        }

        if( ln.contains("Mountain")){
          tokenizer.nextToken();
          int four = Integer.parseInt(tokenizer.nextToken());
          r = new MountainRoute(cit1,cit2,three,col,four);
        }
        else if( ln.contains("Ferry")){
          r = new FerryRoute(cit1, cit2, three, col, 1 );
        }
        else
          r = new Route(cit1,cit2,three,col); 
        System.out.println(c1 + "   " + c2 );
        System.out.println(allRoutes.size() );
        allRoutes.add(r);
        if(count == 0){}
        else
        {
          Route first = allRoutes.get(allRoutes.size() - 2 );
          Route second = allRoutes.get(allRoutes.size() - 1);
          if( first.getFirst().equals(second.getFirst()) &&
          first.getSecond().equals(second.getSecond()) ){
            first.setNeighbor(second);
            second.setNeighbor(first);
          }

        }
        count++;

      }
    }
    catch(FileNotFoundException fnfe){
      System.out.println("No Route File");
    }
  }

  /**
   * Returns a color object of the color str
   * 
   * @param str  name of a color
   * 
   * @return a Color object
   */
  private Color setColor(String str)
  {
    if( str.equals("Grey"))
      return Color.gray;
    if( str.equals("Red"))
      return Color.red;
    if( str.equals("Orange"))
      return Color.orange;
    if( str.equals("Blue"))
      return Color.blue;
    if( str.equals("Green"))
      return Color.green;
    if( str.equals("Yellow"))
      return Color.yellow;
    if( str.equals("Purple"))
      return Color.magenta;
    if( str.equals("White"))
      return Color.white;
    if( str.equals("Black"))
      return Color.black;
    return null;
  }

  /**
   * Initializes a hashmap that has a city as a key and
   * a linked list with routes as values
   */
  public void initHash()
  {   
    for( City c : cities )
    {
      LinkedList< Route > connected = new LinkedList< Route > ();
      for( Route r : allRoutes)
      {
        if( r.getFirst().equals(c)  || 
        r.getSecond().equals(c) )
          connected.add(r);
      }
      cityRoutes.put(c, connected);
    }
  }

  /**
   * Tests whether the coordinates lie in a city
   * 
   * @param x x-Cord
   * @param y y-Cord
   * 
   * @return city that lies on (x,y) on applet graphic
   */
  public City testCords(int x , int y)
  {
    for( City c : cities )
      if( c.contains( x , y ) )
        return c;
    return null;
  }

  /**
   * Determines if there is an unfilled route from
   * city one to city two
   * 
   * @param one first city
   * @param two second city
   * 
   * @return if theres an available route
   */
  public boolean canComplete(City one, City two)
  {
    LinkedList< Route > routes = cityRoutes.get ( one );
    for( Route r : routes){
      if( r.same(one, two) && 
      r.canTake() )
      {
        //r.markTaken();
        return true;
      }
    }
    return false;

  }


  /**
   * Finds a route that connects a and b
   * 
   * @param a  city one
   * @param b  city two
   * 
   * @return route connecting a and b
   */
  public Route findRoute(City a, City b)
  {
    for( Route r: allRoutes )
      if( r.same( a, b) )
        return r;
    return null;

  }


  /**
   * Returns a city that has name 
   * 
   * @param
   */
  public City getCity( String s )
  {
    for( City c : cities )
      if( c.getName().equalsIgnoreCase( s ) )
        return c;
    return null;
    }
}