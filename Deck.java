// Necessary Imports
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
/**
 * This deck class will create many different decks that are used 
 * throughout the game. It can initialize the decks as well as 
 * shuffle and draw from the decks.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 15, 2013
 */
public class Deck
{
  // Instance variables for the Deck class.
  private ArrayList<Card> deck;
  private Scanner scanIn;

  /**
   * Constructor Deck class. It will initialize the ArrayList of Cards.
   */
  public Deck()
  {
    // initialize instance variables
    deck = new ArrayList<Card>();
  }

  /**
   * This method will initialize the rail deck. This means
   * that it will set up all of the rail cards for the game.
   */
  public void initRailDeck()
  {
    // Creates 12 Box rail cards.
    for (int i = 0; i < 12; i++)
    {
      deck.add(new RailCard("Box"));
    }
    // Creates 12 Passenger rail cards.
    for (int i = 0; i < 12; i++)
    {
      deck.add(new RailCard("Passenger"));
    }
    // Creates 12 Tanker rail cards.
    for (int i = 0; i < 12; i++)
    {
      deck.add(new RailCard("Tanker"));
    }
    // Creates 12 Reefer rail cards.
    for (int i = 0; i < 12; i++)
    {
      deck.add(new RailCard("Reefer"));
    }
    // Creates 12 Freight rail cards.
    for (int i = 0; i < 12; i++)
    {
      deck.add(new RailCard("Freight"));
    }
    // Creates 12 Hopper rail cards.
    for (int i = 0; i < 12; i++)
    {
      deck.add(new RailCard("Hopper"));
    }
    // Creates 12 Coal rail cards.
    for (int i = 0; i < 12; i++)
    {
      deck.add(new RailCard("Coal"));
    }
    // Creates 12 Caboose rail cards.
    for (int i = 0; i < 12; i++)
    {
      deck.add(new RailCard("Caboose"));
    }
    // Creates 14 Locomotive rail cards.
    for (int i = 0; i < 14; i++)
    {
      deck.add(new RailCard());
    }
  }

  /**
   * This method will initialize the short destination deck.
   * This means that it will set up the destination cards that
   * are between shorter cities.
   */
  public void initShortDestDeck()
  {
    // Tries to open the text file.
    try
    {
      scanIn = new Scanner(new File("DestinationShort.txt"));
    }
    catch (java.io.FileNotFoundException e)
    {
      System.out.println(e.getMessage());
    }

    // Scans each line of the text file and adds it to the deck.
    while (scanIn.hasNext())
    {
      deck.add(new DestinationCard(scanIn.nextLine()));
    }
  }

  /**
   * This method will initialize the long destination deck.
   * This means that it will set up the destination cards that
   * are between longer cities.
   */
  public void initLongDestDeck()
  {
    // Tries to open the text file.
    try
    {
      scanIn = new Scanner(new File("DestinationLong.txt"));
    }
    catch (java.io.FileNotFoundException e)
    {
      System.out.println(e.getMessage());
    }

    // Scans each line of the text file and adds it to the deck.
    while (scanIn.hasNext())
    {
      deck.add(new DestinationCard(scanIn.nextLine()));
    }
  }

  /**
   * This method will return the size of the deck.
   * 
   * @return the size of the deck
   */
  public int size()
  {
    return deck.size();  
  }

  /**
   * This method will draw a card from the deck and return it.
   * 
   * @return top card of the ArrayList
   */
  public Card draw()
  {
    return deck.remove(0);
  }

  /**
   * This method will draw a card from a certain position.
   * 
   * @param i index for removing a card
   * @return i-th card from the deck
   */
  public Card draw(int i)
  {
    return deck.remove(i);
  }

  /**
   * This method will add a Card to the deck taken from the parameter.
   * 
   * @param c the Card that will be added to the deck
   */
  public void add(Card c)
  {
    deck.add(c);
  }

  /**
   * This method will check to see whether or not the deck
   * is empty.
   * 
   * @return true if the size of the deck is zero
   */
  public boolean isEmpty()
  {
    return (deck.size() == 0);  
  }

  /**
   * This method will randomly shuffle the deck.
   */
  public void shuffle()
  {
    // Sets the number of cards to the deck size and the position to 0.
    int cards = deck.size();
    int pos = 0;

    // Creates a temporary ArrayList of Strings for a deck.
    ArrayList<Card> randDeck = new ArrayList<Card>();
    Random rand = new Random();
    // This loop randomizes the deck by choose one card at random and
    // placing it in a new temporary ArrayList.
    while (cards > 0)
    {
      pos = rand.nextInt(cards);
      randDeck.add(deck.get(pos));
      deck.remove(pos);
      cards--;
    }
    // Sets the original deck to the randomized deck.
    deck = randDeck;
  }
  /**
   * Adds cards to the deck.
   * @param cards an array list of cards to be
   * added to the deck.
   */
  public void add(ArrayList<RailCard> cards)
  {
    deck.addAll(cards);
  }
}
