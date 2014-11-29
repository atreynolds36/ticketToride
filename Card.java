/**
 * This class is a placeholder for the different types of cards:
 * Rail cards, short destination cards, and long destination
 * cards.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 10, 2013
 */
public abstract class Card
{
  /**
   * Empty constructor of the Card class. This constructor
   * does nothing.
   */
  public Card()
  {

  }

  /**
   * This abstract toString method is used to output a value as a String
   * This can be used from other inherited classes.
   * 
   * @return the String of the card
   */
  public abstract String toString();
}
