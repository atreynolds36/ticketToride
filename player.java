import java.util.ArrayList;
import java.awt.Color; 
/**
 * Ticket To Ride - Player Class
 * @author Damian Crisafulli
 * 
 */
public class Player {
    private static byte numberOfPlayers = 0;
    private byte playerNumber;
    /**
     *ArrayList representing the players hand of Destination cards.
     */
    public ArrayList<DestinationCard> destCards;
    private String playerName;
    private byte blue, yellow, white, black, orange, green, red, purple, loco;
    private int score;
    private byte trains;
    private Color playerColor;
    
    /**
     * Constructor
     * @param name The players name.
     * @param color Color that will represent the player - 
     * (Chosen from PlayerColors enum class)
     * 
     */
    public Player(String name,PlayerColors color)
    {
        playerName = name;
        destCards = new ArrayList<DestinationCard>();
        blue = 0;
        yellow = 0;
        white = 0;
        black = 0;
        orange = 0;
        green = 0;
        red = 0;
        purple = 0;
        loco = 0;
        score = 0;
        trains = 45;
        playerNumber = ++numberOfPlayers;
        switch(color){
            case GREEN:
                playerColor = Color.GREEN;
                break;
                
            case RED:
                playerColor = Color.RED;
                break;
            
            case YELLOW:
                playerColor = Color.YELLOW;
                break;
                
            case BLUE:
                playerColor = Color.BLUE;
                break;
                
            case PINK:
                playerColor = Color.PINK;
                break;
            
            case BLACK:
                playerColor = Color.BLACK;
                break;
                       
        }
    }
    
    /**
     *
     * @param d DestinationCard to be added to the player hand
     */
    public void addDestCard(DestinationCard d)
    {
        destCards.add(d);
    }
    
    /**
     * Returns an ArrayList representing the player's current Destination Cards.
     * @return The players hand.
     */
    public ArrayList<DestinationCard> getDestCards()
    {
        return destCards;
    }
    
    /**
     * Returns the color representing the player.
     * @return Color object for the player's representing color.
     */
    public Color getColor()
    {
        return playerColor;
    }
    
    /**
     * Sets players number of trains remaining
     * @param t Number of trains to be set.
     */
    public void setTrains (byte t)
    {
        trains = t;
    }

    /**
     * Remove one of the player's trains.
     */
    public void decrementTrains ()
    {
        trains--;
    }

    /**
     * Returns the number of trains the player has remaining.
     * @return Number of trains the player has remaining.
     */
    public byte getTrains()
    {
        return trains;
    }
    
    /**
     * Sets the player's score.
     * @param s new score to be set.
     */
    public void setScore(int s)
    {
        score = s;
    }

    /**
     * Returns the player's current score.
     * @return player's current score.
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * Returns the player's player number.
     * @return player's player number.
     */
    public int getPlayerNumber()
    {
        return playerNumber; 
    }
    
    /**
     * Returns the player's name
     * @return player's name.
     */
    public String getName()
    {
        return playerName;
    }
    
    /**
     * Returns number of blue cards the player currently has.
     * @return number of current blue cards.
     */
    public byte getBlue()
    {
        return blue;
    }

    /**
     * Returns number of black cards the player currently has.
     * @return number of current black cards.
     */
    public byte getBlack()
    {
        return black;
    }

    /**
     * Returns number of green cards the player currently has.
     * @return number of current green cards.
     */
    public byte getGreen()
    {
        return green;
    }

    /**
     * Returns number of orange cards the player currently has.
     * @return number of current orange cards.
     */
    public byte getOrange()
    {
        return orange;
    }

    /**
     * Returns number of purple cards the player currently has.
     * @return number of current purple cards.
     */
    public byte getPurple()
    {
        return purple;
    }

    /**
     * Returns number of red cards the player currently has.
     * @return number of current red cards.
     */
    public byte getRed()
    {
        return red;
    }

    /**
     * Returns number of white cards the player currently has.
     * @return number of current white cards.
     */
    public byte getWhite()
    {
        return white;
    }

    /**
     * Returns number of yellow cards the player currently has.
     * @return number of current yellow cards.
     */
    public byte getYellow()
    {
        return yellow;
    }

    /**
     * Returns number of locomotive cards the player currently has.
     * @return number of current locomotive cards.
     */
    public byte getLoco()
    {
        return loco;
    }

    /**
     * Sets number of blue cards the player currently has.
     * @param n New value of blue cards.
     */
    public void setBlue(byte n)
    {
        blue = n;
    }
    
    /**
     * Adds one to player's blue card count.
     */
    public void incrementBlue()
    {
        blue++;
    }

    /**
     * Sets number of black cards the player currently has.
     * @param new value of black cards.
     */
    public void setBlack(byte n)
    {
        black = n;
    }
    
    /**
     * Adds one to player's black card count.
     */
    public void incrementBlack()
    {
        black++;
    }

    /**
     * Sets number of green cards the player currently has.
     * @param n new value of green cards.
     */
    public void setGreen(byte n)
    {
        green = n;
    }
    
    /**
     * Adds one to player's green card count.
     */
    public void incrementGreen()
    {
        green++;
    }

    /**
     * Sets number of orange cards the player currently has.
     * @param n new value of orange cards.
     */
    public void setOrange(byte n)
    {
        orange = n;
    }
    
    /**
     * Adds one to player's orange card count.
     */
    public void incrementOrange()
    {
        orange++;
    }

    /**
     * Sets number of purple cards the player currently has.
     * @param n new value of purple cards.
     */
    public void setPurple(byte n)
    {
        purple = n;
    }
    
    /**
     * Adds one to player's purple card count.
     */
    public void incrementPurple()
    {
        purple++;
    }

    /**
     * Sets number of red cards the player currently has.
     * @param n new value of red cards.
     */
    public void setRed(byte n)
    {
        red = n;
    }
    
    /**
     * adds one to player's red card count.
     */
    public void incrementRed()
    {
        red++;
    }

    /**
     * Sets number of white cards the player currently has.
     * @param n new value of white cards.
     */
    public void setWhite(byte n)
    {
        white = n;
    }
    
    /**
     * adds one to player's white card count.
     */
    public void incrementWhite()
    {
        white++;
    }

    /**
     * Sets number of yellow cards the player currently has.
     * @param n new value of yellow cards.
     */
    public void setYellow(byte n)
    {
        yellow = n;
    }
    
    /**
     * adds one to player's yellow card count.
     */
    public void incrementYellow()
    {
        yellow++;
    }

    /**
     * Sets number of locomotive cards the player currently has.
     * @param n new value of locomotive cards.
     */
    public void setLoco(byte n)
    {
        loco = n;
    }
    
    /**
     * adds one to player's locomotive card count.
     */
    public void incrementLoco()
    {
        loco++;
    }

    
    
}
