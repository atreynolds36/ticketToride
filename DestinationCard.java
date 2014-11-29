/**
 * This DestinationCard class inherits from the Card class. 
 * This class will store the cities and values on a destination card.
 * It will be able to separate a large String and split it into two
 * cities and the value on that card.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 10, 2013
 */
public class DestinationCard extends Card
{
  // Instance variables for the DestinationCard class.
  private String city1;
  private String city2;
  private int value;

  /**
   * Constructor for DestinationCard class. It will initialize
   * city1 and city2 to the empty String and value to zero.
   */
  public DestinationCard()
  {
    city1 = "";
    city2 = "";
    value = 0;
  }

  /**
   * This constructor for the DestinationCard class will take
   * in a String parameter that will contain two city names,
   * delimited by a dash (-) followed by a colon and the point
   * value.
   * 
   * @param s the String of a destination card
   */
  public DestinationCard(String s)
  {
    // Splits the String s to take in only the city portion
    // which is before the colon. It will save the first
    // city before the dash and the second city after the dash.
    String phrase = s;
    int colon = phrase.indexOf(":");
    phrase = phrase.substring(0, colon);
    city1 = phrase.substring(0, phrase.indexOf("-"));
    city2 = phrase.substring(phrase.indexOf("-") + 1, colon - 1);

    // Saves the point value of the card between the cities
    // by parsing the number after the colon.
    phrase = s;
    colon = phrase.lastIndexOf(":");
    String num = phrase.substring(colon + 2, phrase.length());
    value = Integer.parseInt(num);
  }

  /**
   * This method will return the String value for the first city.
   * 
   * @return city1 the String value of the first city
   */
  public String getCityOne()
  {
    return city1;
  }

  /**   
   * This method will return the String value for the second city.
   * 
   * @return city2 the String value of the second city
   */
  public String getCityTwo()
  {
    return city2; 
  }

  /**
   * This method will return the point value between the two cities.
   * 
   * @return value the point value of the two cities
   */
  public int getValue()
  {
    return value;
  }

  /**
   * This method will return the string of city1 followed be a dash
   * and the string of city2. A colon will delimit the point value
   * between the two cities.
   * 
   * @return the String of two cities and their value
   */
  public String toString()
  {
    return city1 + "-" + city2 + " : " + value;
  }

  /**
   * This method will return a string that will remove the empty
   * spaces with blanks (nothing). This will help read in the file.
   * 
   * @return the String of two cities without empty spaces
   */
  public String fileName()
  {
    String s = city1 + "-" + city2;
    return s.replaceAll(" ","");
  }
}
