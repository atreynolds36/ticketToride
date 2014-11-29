import java.util.*;
import java.awt.*;
/**
 * Methods needed to run Ticket to Ride Legendary Asia.
 * These methods will help with the gameplay as well as
 * calculate the points system throughout the game.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds
 * @version April 15, 2013
 */
public class GamePlay
{
  /**
   * Returns the total of all of player p's additional
   * points recieved/subtracted from their destination
   * cards
   * 
   * @param  a player
   * 
   * @return number to be added to score at games end
   */
  public static int getEndCardScore( Player p, Map m )
  {
    ArrayList<DestinationCard> pDestCard = p.getDestCards();
    int score = 0;
    for( DestinationCard d : pDestCard ){
      if( complete ( p , d , m)  )
        score = score + d.getValue();
      else
        score = score - d.getValue();
    }
    return score;
  }

  /**
   * Sets cities to unvisited
   * 
   * @param p current player
   */
  private static void initCities( Player p )
  {
    ArrayList<Route> routes = p.connectedRoutes();
    for( Route r : routes){
      r.getFirst().setVisit( false );
      r.getSecond().setVisit( false );
    }
  }

  /**
   * Completes a destination card for player p on map m
   * 
   * @param p current player
   * @param d current destination card
   * @param m current map
   * 
   * @return whether the card route has been completed
   */
  private static boolean complete( Player p , DestinationCard d , Map m)
  {
    City start = m.getCity( d.getCityOne() );
    City end   = m.getCity( d.getCityTwo() );
    ArrayList<Route> routes = p.connectedRoutes();
    initCities( p );
    ArrayList<Route> r = p.connectedRoutes() ;
    mdfs( r , start , end );
    return end.getVisit();
  }

  /**
   * Modified depth first search on routes from start to end
   * 
   * @param routes arraylist of routes connected by player p
   * @param start start city
   * @param end desired end city
   * 
   * @return whether the end city has been reached
   */
  private static boolean mdfs(ArrayList<Route> routes,City start,City end)
  {
    start.setVisit( true );
    for( Route r : routes ){
      City one = r.getFirst();
      City two = r.getSecond();
      if( one.equals( start ) ){
        if( two.equals( end ) ){
          end.setVisit( true );
          return true;
        }
        else if ( ! ( two.getVisit() ) )
          mdfs( routes, two , end );
      }

      if( two.equals( start ) ){ 
        if( one.equals( end ) ){
          end.setVisit( true );
          return true;
        }
        else if ( ! ( one.getVisit() ) )
          mdfs( routes, one , end );                
      }
    }
    return false;
  }

  /**
   * Calculates longest path for player p
   * 
   * @param p current player
   * 
   * @return longest number of connected cities
   */
  public static int explorerBonus( Player p )
  {
    initCities( p );

    ArrayList<Route> routes = p.connectedRoutes();
    int high = 0;
    for( int i = 0 ; i < routes.size(); i++ )
    {
      int num = 0;
      City one = routes.get(i).getFirst();
      if( one.getVisit() == false ){
        dfs( routes, one );
        for( int s = 0 ; s < routes.size(); s++ )
        {
          if( routes.get(s).getFirst().getVisit() )
            num++; 
        }
        if( num > high )
          high = num;
      }
    }
    return high;
  }

  /**
   * Depth first search on routes starting at City city
   * 
   * @param routes arraylist of connected routes
   * @param city start city
   */
  public static void dfs( ArrayList<Route> routes , City city )
  {
    city.setVisit( true );
    for( Route r : routes ){
      City one = r.getFirst();
      City two = r.getSecond();
      if( one.equals( city ) ){
        if ( ! ( two.getVisit() ) )
          dfs( routes, two );
      }

      if( two.equals( city ) ){ 
        if ( ! ( one.getVisit() ) )
          dfs( routes , one );                
      }
    }

    }

}
