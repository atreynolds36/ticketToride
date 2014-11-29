/**
 * This RailCard class inherits from Card class and it is used
 * to differentiate between different kinds of cards. 
 * These are "rail" or train cards that
 * the user can pick up in order to create routes.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 10, 2013
 */
public class RailCard extends Card
{
  // Instance variables for the RailCard class.
  private boolean isLoco;
  private String card;

  /**
   * Default constructor for the RailCard class. This class will
   * make the boolean isLoco value true and make the String card
   * become Locomotive.
   */
  public RailCard()
  {
    isLoco = true;
    card = "Locomotive";
  }

  /**
   * This second constructor will initialize the card to the input
   * parameter and set isLoco to false.
   * 
   * @param s String input to initialize card
   */
  public RailCard(String s)
  {
    isLoco = false;
    card = s;
  }

  /**
   * This method will return the card that is being used.
   * 
   * @return the String of the card
   */
  public String getCard()
  {
    return card;
  }

  /**
   * Sets the value of the field card to the parameter of the String.
   * 
   * @param s the String of the card
   */
  public void setCard(String s)
  {
    card = s;
  }

  /**
   * Sets the card to be a locomotive card or not based on
   * the parameter.
   * 
   * @param loco the boolean value if the it is a locomotive card
   */
  public void setLoco(boolean loco)
  {
    isLoco = loco;
  }

  /**
   * Determines whether a card is a locomotive card or not.
   * 
   * @return true if the card is a locomotive card
   */
  public boolean isLoco()
  {
    return isLoco;
  }

  /**
   * Determines whether a card is a locomotive card or not.
   * 
   * @param s String input in order to determine if the card is locomotive
   * @return true if the card is a locomotive card
   */
  public boolean isLoco(String s)
  {
    if (s.equals("Locomotive"))
      return true;
    else
      return false;
  }
  /**
   * Returns a string representation of
   * the color of the card.
   * @return the string representation of 
   * the card's color.
   */
  public String toColorString()
  {
    if(card.equals("Hopper"))
      return "Black";
    else if (card.equals("Box"))
      return "Purple";
    else if (card.equals("Passenger"))
      return "White";
    else if (card.equals("Tanker"))
      return "Blue";
    else if (card.equals("Reefer"))
      return "Yellow";
    else if (card.equals("Freight"))
      return "Brown";
    else if (card.equals("Coal"))
      return "Red";
    else if (card.equals("Caboose"))
      return "Green";
    else
      return "Rainbow";
  }

  /**
   * The method will return the card as a String.
   * 
   * @return card in a String form
   */
  public String toString()
  {
    return card;
  }
}
