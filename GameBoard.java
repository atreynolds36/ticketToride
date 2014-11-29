import java.awt.*;
import java.awt.event.*;
import java.awt.List.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.geom.Line2D;
/**
 * Class GameBoard - The JApplet class for TTR.
 * Handles all graphics and the turn engine.
 * 
 * @author Chan Tran, Dan Egan, Damian Crisafulli
 * @author Andrew Reynolds, Matt Brancato 
 * @version April 15, 2013
 */
public class GameBoard extends JApplet implements MouseListener
{
  // Instance variables
  private int width, height;
  private int xpos, ypos;
  //WHICH PLAYER HAS THE CURRENT TURN
  private int playerTurn;
  //A TURN IS BROKEN UP INTO 2 MOVES: 
  //EITHER PLACING DOWN A ROUTE( SELECTING TWO CITIES),
  //PICKING TWO CARDS, OR PICKING ANOTHER DESTINATION CARD
  private int turnCount;
  private int pNumi = 0;
  private int currDest, tempSize;
  // Initializing the frame and icon variables
  private Component frame;
  private Icon icon;
  // Images for the map, background, and cards (face-up and face-down)
  private Image black, blue, brown,
  green, purp, red, white, yellow, loco;
  private Image map, background, faceDownDest, 
  faceDownTrain, tempImage, gameOver;

  private boolean drawEdge = false;
  private Deck railDeck, discardDeck;
  private Card[] faceUp;
  // Creating players and the current player.
  private Player player1, player2, player3, current;
  // Creating two cities for comparison.
  private City press1, press2; //= null;
  //Parellel arrays to hold completed routes and their
  //colors
  private ArrayList<Line2D.Double> completedRoutes;
  private ArrayList<Color> colorRoutes;
  private ArrayList<Integer> p1Hand, p2Hand ;
  private ArrayList<DestinationCard> tempDests;

  private ArrayList<Integer> currentHand;

  private Map m;
  private Object[] PlayerColors = 
    {"GREEN","RED","YELLOW","BLUE","PINK","BLACK"};

  private String player1name, player2name, player3name;
  private String p1Color, p2Color, p3Color;
  private String pNum = null;

  private Deck shortDeck = new Deck();

  private boolean pickCard;
  private boolean pickCity;

  private boolean isLastTurn;
  private boolean gameo = false;
  private boolean donepaint = false;
  private int endGame = 0;
  private int turnsLeft = 99;
  /**
   * Called by the browser or applet viewer to inform this JApplet that it
   * has been loaded into the system. 
   * It is always called before the first 
   * time that the start method is called.
   */
  public void init()
  {
    JRootPane rootPane = this.getRootPane();  
    rootPane.putClientProperty("defeatSystemEventQueueCheck", 
      Boolean.TRUE);
    // Initialize the width and the height of the JavaApplet
    width = getWidth();
    height = getHeight();
    // Obtains the background image to display behind the board
    background = getImage(getDocumentBase(), "background.jpg");
    // Obtains the map for the game
    map = getImage(getDocumentBase(), "board.jpg");
    String fs = File.separator;
    // Obtains the individual cards 
    //in order to display as player cards
    black = getImage(getDocumentBase(), "Cards" +fs + "black.gif");
    blue = getImage(getDocumentBase(), "Cards" +fs + "blue.gif");
    brown = getImage(getDocumentBase(), "Cards" +fs + "brown.gif");
    green = getImage(getDocumentBase(), "Cards" +fs + "green.gif");
    purp = getImage(getDocumentBase(), "Cards" +fs + "purple.gif");
    red = getImage(getDocumentBase(), "Cards" +fs + "red.gif");
    white = getImage(getDocumentBase(), "Cards" +fs + "white.gif");
    yellow = getImage(getDocumentBase(), "Cards" +fs + "yellow.gif");
    loco = getImage(getDocumentBase(), "Cards" +fs + "loco.gif");
    // Obtains the game over background image
    gameOver = getImage(getDocumentBase(), "gameover.jpg");
    // Obtains the back of the cards to use as decks
    faceDownDest = getImage(getDocumentBase(), "Cards" + fs +
      "ticketBack.png");;
    faceDownTrain = getImage(getDocumentBase(), "Cards" + fs + 
      "trainCardBack.png");;
    // Creates a new rail card deck and shuffles it.
    railDeck = new Deck();
    railDeck.initRailDeck();
    railDeck.shuffle();
    // Initializing arrays, ArrayLists and Decks
    p1Hand = new ArrayList<Integer>();
    p2Hand = new ArrayList<Integer>();
    for (int i = 0; i < 9; i++)
    {
      p1Hand.add(0);
      p2Hand.add(0);
    }

    currentHand = p1Hand;
    currDest = 0; tempSize = 0;

    discardDeck = new Deck();
    faceUp = new RailCard[5];
    // Sets up the face up rail cards
    setFaceUp();

    completedRoutes = new ArrayList<Line2D.Double> ();
    colorRoutes = new ArrayList<Color>();
    //Player Prompts
    Object[] options = {"3 Players",
        "2 Players"};
    int n = JOptionPane.showOptionDialog(this,
        "Choose Number Of Players",
        "Choose Number Of Players",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,  
        options, 
        options[1]); 
    if(n == JOptionPane.YES_OPTION)
      pNumi = 3;
    else
      pNumi = 2;
    while(true)
    {
      player1name = JOptionPane.showInputDialog(this,
        "Enter Player One Name","Player 1");
      if(player1name == null)
        continue;
      else
        break;

    }
    while(true)
    {
      p1Color =  (String) JOptionPane.showInputDialog(this,
        "Choose Player 1 Color", "Choose Player 1 Color",
        JOptionPane.INFORMATION_MESSAGE, null,PlayerColors, 
        PlayerColors[0]);
      if(p1Color == null)
        continue;
      else
        break;
    }

    ArrayList<Object> tempColor = new ArrayList<Object>();
    for(Object o:PlayerColors)
    {
      tempColor.add(o);
    }
    tempColor.remove(p1Color);
    tempColor.trimToSize();
    PlayerColors = tempColor.toArray();
    tempColor.clear();
    while(true)
    {
      player2name = JOptionPane.showInputDialog(this,
        "Enter Player Two Name","Player 2");
      if(player2name == null)
        continue;
      else
        break;
    }
    while(true)
    {
      p2Color = (String)JOptionPane.showInputDialog(this,
        "Choose Player 2 Color", "Choose Player 2 Color",
        JOptionPane.INFORMATION_MESSAGE, null,
        PlayerColors, PlayerColors[0]);
      if(p2Color == null)
      {
        continue;
      }
      else
        break;
    }

    for(Object o:PlayerColors)
    {
      tempColor.add(o);
    }
    tempColor.remove(p2Color);
    tempColor.trimToSize();
    PlayerColors = tempColor.toArray();
    tempColor.clear();

    if(pNumi == 3)
    {
      while(true)
      {
        player3name = JOptionPane.showInputDialog(this,
          "Enter Player Three Name","Player 3");
        if(player3name == null)
          continue;
        else
          break;
      }
      while(true)
      {
        p3Color =  (String) JOptionPane.showInputDialog(this,
          "Choose Player 3 Color", "Choose Player 3 Color",
          JOptionPane.INFORMATION_MESSAGE, null,
          PlayerColors, PlayerColors[0]);
        if(p3Color == null)
          continue;
        else
          break;
      }

    }
    else
    {
      player3 = null;
    }

    shortDeck.initShortDestDeck();
    shortDeck.shuffle();

    Deck longDeck = new Deck();
    longDeck.initLongDestDeck();
    longDeck.shuffle();

    ArrayList<Card> p3Cards;
    //ASSUMING 2 PLAYERS
    player1 = new Player(player1name , p1Color );
    player2 = new Player(player2name , p2Color );
    if(pNumi == 3)
    {
      player3 = new Player(player3name , p3Color );
    }

    //starting train cards
    Card d;
    current = player1;
    startCards(current);
    for(int i = 0; i < 4; i++)
    {
      d = railDeck.draw();
      addToHand(d);
    }
    current = player2;
    startCards(current);
    for(int i=0; i<4; i++)
    {
      d = railDeck.draw();
      addToHand(d);
    }
    if(pNumi==3)
    {
      current = player3;
      startCards(current);
      for(int i = 0; i < 4; i++)
      {
        d = railDeck.draw();
        addToHand(d);
      }
    }
    current = player1;

    playerTurn = 0;
    turnCount = 0;

    press1 = null;
    press2 = null;

    m = new Map();

    addMouseListener(this);  //old

  }

  /**
   * Called by the browser or applet viewer to inform
   * this JApplet that it should start its execution. 
   * It is called after the init method and 
   * each time the JApplet is revisited in a Web page. 
   */
  public void start()
  {
    // provide any code requred to run each time 
    // web page is visited
  }

  /** 
   * Called by the browser or applet viewer to inform this JApplet that
   * it should stop its execution. It is called when the Web page that
   * contains this JApplet has been replaced by another page, and also
   * just before the JApplet is to be destroyed. 
   */
  public void stop()
  {
    // provide any code that needs to be run when page
    // is replaced by another page or before JApplet is destroyed 
  }

  /**
   * Paint method for applet.
   * Draws background and the graphics
   * for turn operations.
   * 
   * @param  g   the Graphics object for this applet
   */
  public void paint(Graphics g)
  {  
    setCurrent();
    if( isLastTurn ){
      if( turnsLeft == 0 )
      {
        if( endGame == 0 )
        {
          gameOver(g);
          endGame = 1;
          return;
        }
        return;
      }
    }

    g.setColor(Color.white);
    printHand(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(8.0f));
    g.setColor(Color.blue);

    //g.drawString("(" + xpos + "," + ypos + ")", xpos, ypos);
    paintRoutes(g);

    if(railDeck.isEmpty())
    {
      replaceDeck();
    }

    if( turnCount < 2 )
    {
      if( pickCard ){
        showStatus( " Card Picked " );
        turnCount++;
      }
      else if( turnCount == 0 ){
        //Returns null if not pressed on a city
        press1 = m.testCords( xpos , ypos );
        showStatus("C1 " + press1.getName() );
        if( press1 != null)
          turnCount++;
        pickCity = true;
      }
      else if( turnCount == 1 ){
        press2 = m.testCords( xpos, ypos );
        if( press2 != null)
          turnCount++;
      }

      //IF TWO CITIES ARE PRESSED, 
      //CHECKS TO SEE IF THEY ARE A ROUTE AND IF IT IS FREE
      if( press1 != null && press2 != null)
      {
        boolean can = m.canComplete( press1, press2 );

        if( can )
        {   
          boolean go = true;
          int keep = 0;
          while( go && keep < 2 )
          {
            keep++;
            Route completedRoute = m.findRoute(press1, press2);
            if( keep == 2 ){
              if( completedRoute.neighbor != null )
                completedRoute = completedRoute.neighbor;
              else
                break;
            }

            int n = completedRoute.getLength();
            int trns = n;
            int mtnx = 0;
            ArrayList<RailCard> tempCards = new ArrayList<RailCard>();
            if(completedRoute instanceof MountainRoute)
            {
              MountainRoute mnt = (MountainRoute)completedRoute;
              mtnx = mnt.getXs();
              trns += mnt.getXs();
            }
            Color col = completedRoute.getColor();
            if(completedRoute instanceof FerryRoute)
            {
              if(current.getTrains() >= trns)
              {
                tempCards=current.hasFerry(n, col);
                if(tempCards!=null){
                  discardDeck.add(tempCards);
                  completedRoute.markTaken();
                  current.removeTrains(trns);
                  if( completedRoute.neighbor != null)
                    completedRoute.neighbor.markTaken();

                  JOptionPane.showMessageDialog(this, 
                    "Connecting " + press1.getName() 
                    + " and " + press2.getName() );
                  g.setColor(Color.green);
                  completedRoutes.add( new Line2D.Double( press1.getX(), 
                      press1.getY(),press2.getX(), press2.getY()));
                  colorRoutes.add(current.getColor());
                  current.addRoute (completedRoute);
                  setScore(completedRoute);
                  drawScoreboard(g);
                  go = false;
                }
                else{
                  JOptionPane.showMessageDialog(this, "Cannot connect " + 
                    press1.getName() + 
                    " and " + press2.getName());
                  press1 = null;
                  press2 = null;
                  turnCount = 0;

                }
              }
            }
            else if(col.equals(Color.gray))
            {
              if( grayPath( completedRoute , current ) )
              {
                current.incrementScore(mtnx * 2);
                completedRoute.markTaken();
                current.removeTrains(trns);
                if( completedRoute.neighbor != null)
                  completedRoute.neighbor.markTaken();

                JOptionPane.showMessageDialog(this, "Connecting " + 
                  press1.getName() +
                  " and " + press2.getName() );
                g.setColor(Color.green);
                completedRoutes.add( new Line2D.Double( press1.getX(), 
                    press1.getY(), 
                    press2.getX(), press2.getY() ) );
                colorRoutes.add( current.getColor() );
                current.addRoute (  completedRoute );
                setScore( completedRoute );
                drawScoreboard(g);
                go = false;
              }
              else{
                JOptionPane.showMessageDialog(this, "Cannot connect " + 
                  press1.getName() + 
                  " and " + press2.getName());
                press1 = null;
                press2 = null;
                turnCount = 0;
              }
            }
            else{
              if(current.getTrains() >= trns){
                tempCards=current.hasColor(n,col);
                if (tempCards!=null){
                  current.incrementScore(mtnx * 2);
                  discardDeck.add(tempCards);
                  completedRoute.markTaken();
                  current.removeTrains(trns);
                  if( completedRoute.neighbor != null)
                    completedRoute.neighbor.markTaken();

                  JOptionPane.showMessageDialog(this, "Connecting " + 
                    press1.getName() + " and " 
                    + press2.getName() );
                  g.setColor(Color.green);
                  completedRoutes.add( new Line2D.Double( 
                      press1.getX(), press1.getY(), 
                      press2.getX(), press2.getY() ) );
                  colorRoutes.add( current.getColor() );
                  current.addRoute (  completedRoute );
                  setScore( completedRoute );
                  drawScoreboard(g);
                  go = false;
                }
              }
              else{
                JOptionPane.showMessageDialog(this, "Cannot connect " +
                  press1.getName() + " and " 
                  + press2.getName());
                press1 = null;
                press2 = null;
                turnCount = 0;
              }
            }
          }
        }
        else
        {
          JOptionPane.showMessageDialog(this, 
            " Impossible move. Try again. " + "C1  " + 
            press1.getName() +  " C2  " + press2.getName());
          press1 = null;
          press2 = null;
          turnCount = 0;
        }
        showStatus("C1  " + press1.getName() +  
          "C2   " + press2.getName() );
      }
    }
    //TURN IS OVER...SWITCH PLAYERS AND RESET turnCount
    if( turnCount == 2 )
    {
      showStatus("End of turn for : " + current.getName());
      playerTurn++;
      press1 = null;
      press2 = null;
      turnCount = 0;
      pickCity = false;
      pickCard = false;
      if(isLastTurn)
        turnsLeft--;
    }
    if( press1 != null )
      showStatus("CITY ONE " + press1.getName() );
    if( press2 != null )
      showStatus("CITY TWO " + press2.getName() );

    if (turnCount == 0)
    {
      drawBackground(g);
      showFaceUp(g);

      if(player3 != null)
      {
        if (current == player1)
        {
          printHand(g, player2);
        }
        else if (current == player2)
        {
          printHand(g, player3);
        }
        else
        {
          printHand(g, player1);
        }
      }
      else
      {
        if (current == player1)
        {
          printHand(g, player2);
        }
        else
        {
          printHand(g, player1);
        }
      }
    }

    pickCard = false;
    paintRoutes(g);
    if(isLastTurn){}
    else
      isLastTurn = lastTurn();
  }
  /**
   * Creates the face up rail cards.
   */
  void setFaceUp()
  {
    for (int i = 0; i < 5; i ++)
    {
      if (faceUp[i] != null)
      {
        discardDeck.add(faceUp[i]);
      }
      faceUp[i] = railDeck.draw();
    }
  }

  /**
   * Actions taken once game has ended. Score's are displayed
   * and a winner is chosen. Also deals with end game bonuses 
   * 
   * @param g applet graphic
   */
  void gameOver( Graphics g )
  {
    g.clearRect(0, 0, 1200, 700);
    g.drawImage(background, 0 , 0, width, height, this);

    g.setColor( Color.WHITE);
    Font font = new Font("Arial", Font.ITALIC, 24);
    g.setFont( font );

    //ADDITIONAL POINTS FROM ROUTES
    int a = GamePlay.getEndCardScore(player1, m);
    int b = GamePlay.getEndCardScore(player2, m);
    int c = 0;

    String p1Add = player1.getName() 
      +"'s Additional points from completed routes : " + a;
    String p2Add = player2.getName() 
      +"'s Additional points from completed routes : " + b;
    String p3Add = "";
    if(player3 != null){
      c = GamePlay.getEndCardScore(player1, m);
      p3Add = player3.getName() 
      + "'s Additional points from completed routes : " + c;

    }
    g.drawString( p1Add, 150, 100 );
    g.drawString( p2Add, 150, 120 );
    if( player3 != null)
      g.drawString( p3Add, 100, 140 );
    if( endGame == 1 )
      return;
    player1.setScore( player1.getScore() + a );
    player2.setScore( player2.getScore() + b );
    if( player3 != null )
      player3.setScore( player3.getScore() + c );
    int iter = 0;
    int max = GamePlay.explorerBonus( player1 );
    if( max < GamePlay.explorerBonus ( player2) ){
      iter = 1;
      max = GamePlay.explorerBonus( player2 ) ;
    }
    if( player3 != null )
    {
      if( max < GamePlay.explorerBonus( player3 ) ){
        iter = 2;
        max  = GamePlay.explorerBonus( player3);
      }

    }
    String bonus = "";
    switch( iter )
    {
      case 0 : bonus = player1.getName() + " got explorer bonus!! ";
      player1.setScore( player1.getScore() + 10 );
      break;
      case 1 : bonus = player2.getName() + " got explorer bonus!! ";
      player2.setScore( player2.getScore() + 10 );
      break;
      case 2 : bonus = player3.getName() + " got explorer bonus!! ";
      player3.setScore( player3.getScore() + 10 );
      break;
    }
    g.drawString( bonus, 150, 160 );
    if( player3 != null )
      player3.setScore( player3.getScore() /2 );

    String aa = player1.getName() + " Score : " + player1.getScore();
    g.drawString( aa , 150, 400 );
    String bb = player2.getName() + " Score : " + player2.getScore();
    g.drawString( bb, 150, 420 );
    if( player3 != null )
    {
      String cc = player3.getName() + " Score : " + player3.getScore();
      g.drawString( cc, 150, 440 );
    }
    iter = 0;
    max = player1.getScore();
    if( max < player2.getScore() ){
      iter = 1;
      max = player2.getScore() ;
    }
    if( player3 != null )
    {
      if( max < player3.getScore() ){
        iter = 2;
        max  = player3.getScore();
      }

    }
    String winner = "";
    switch( iter )
    {
      case 0 : winner = player1.getName() + " WON!!! ";
      break;
      case 1 : winner = player2.getName() + " WON!!! ";
      break;
      case 2 : winner = player3.getName() + " WON!!! ";
      break;
    }
    Font f = new Font( "Arial" , Font.BOLD, 40 );
    g.setFont(f);

    g.drawString(winner, 400, 600 );
    return;
  }

  /**
   * Draws board display
   * 
   * @param g  applet graphic
   */
  void drawBackground(Graphics g)
  {
    int pos = 90;

    // Draws the background image
    g.drawImage(background, 0, 0, width, height, this);
    // Draws the map.
    g.drawImage(map, 0, 0, 800, 600, this);
    // Draws the cards that the player owns below the map.
    g.drawImage(black, 0, 600, pos, 70, this);
    g.drawImage(blue, pos, 600, pos, 70, this);
    g.drawImage(brown, pos*2, 600, pos, 70, this);
    g.drawImage(green, pos*3, 600, pos, 70, this);
    g.drawImage(purp, pos*4, 600, pos, 70, this);
    g.drawImage(red, pos*5, 600, pos, 70, this);
    g.drawImage(white, pos*6, 600, pos, 70, this);
    g.drawImage(yellow, pos*7, 600, pos, 70, this);
    g.drawImage(loco, pos*8, 600, pos, 70, this);
    // Draws the back of the destination card and train card
    // as to simulate the back of a deck.
    g.drawImage(faceDownDest, 800, 400, 150, 100, this);
    g.drawImage(faceDownTrain, 800, 500, 150, 100, this);
  }

  /**
   * Shows the face up cards on the board
   * 
   * @param g applet graphic
   */
  void showFaceUp(Graphics g)
  {
    int pos = 80;
    int locCount = 0;

    for (int i = 0; i < 5; i++)
    {
      Card temp = faceUp[i];
      if (temp.toString().equals("Box"))
      {
        tempImage = purp;
      }
      else if (temp.toString().equals("Passenger"))
      {
        tempImage = white;
      }
      else if (temp.toString().equals("Tanker"))
      {
        tempImage = blue;
      }
      else if (temp.toString().equals("Reefer"))
      {
        tempImage = yellow;
      }
      else if (temp.toString().equals("Freight"))
      {
        tempImage = brown;
      }
      else if (temp.toString().equals("Hopper"))
      {
        tempImage = black;
      }
      else if (temp.toString().equals("Coal"))
      {
        tempImage = red;
      }
      else if (temp.toString().equals("Caboose"))
      {
        tempImage = green;
      }
      else if (temp.toString().equals("Locomotive"))
      {
        tempImage = loco;
        locCount++;
      }

      g.drawImage(tempImage, 800, pos*i, 120, 80, this);
    }

    if(locCount >= 3)
    {
      JOptionPane.showMessageDialog(this, 
        "3 Locomotives face up, reshuffling face up cards.");
      for(int i = 0; i < 5; i++)
      {
        discardDeck.add(faceUp[i]);
        if(railDeck.isEmpty())
        {
          replaceDeck();
        }
        faceUp[i] = railDeck.draw();
      }
      showFaceUp(g);
    }
  }

  /**
   * Draws the scoreboard onto the applet
   * 
   * @param g applet graphic
   */
  void drawScoreboard(Graphics g)
  {
    g.setColor(Color.white);
    Font font = new Font("Arial Black", Font.ITALIC, 24);
    g.setFont( font );
    g.drawString("SCOREBOARD", 940, 20);

    font = new Font("Calibri", Font.PLAIN, 12);
    g.setFont( font );
    g.drawString("Player Score", 975, 40);
    g.drawString("Trains", 1100, 40);

    g.drawString(player1name, 970, 60);      
    g.drawString("" + player1.getScore(), 1040, 60);
    g.drawString("" + player1.getTrains(), 1110, 60);

    g.drawString(player2name, 970, 80);
    g.drawString("" + player2.getScore(), 1040, 80);
    g.drawString("" + player2.getTrains(), 1110, 80);

    if(player3 != null)
    {
      g.drawString(player3name, 970, 100 );
      g.drawString("" + player3.getScore(), 1040, 100);
      g.drawString("" + player3.getTrains(), 1110, 100);
    }

    g.setColor(current.getColor());
    font = new Font("Arial Black", Font.ITALIC, 18);
    g.setFont( font );
    String pTurn = current.getName() + "'s turn";
    g.drawString(pTurn, 960, 130);

    font = new Font("Calibri", Font.PLAIN, 12);
    g.setFont( font );
    g.setColor(Color.white);

    String str = current.getName() + "'s Destination Cards.";
    g.drawString( str, 1000, 375 );
    str = "Click to cyle through";
    g.drawString( str, 1020, 390 );
  }
  /**
   * Draws scoreboard info for a specific player.
   * @param g graphics object for this applet.
   * @param player player whose scoreboard info will be drawn.
   */
  void drawScoreboard(Graphics g, Player player)
  {
    g.setColor(Color.white);
    Font font = new Font("Arial Black", Font.ITALIC, 24);
    g.setFont( font );
    g.drawString("SCOREBOARD", 940, 20);

    font = new Font("Calibri", Font.PLAIN, 12);
    g.setFont( font );
    g.drawString("Player Score", 975, 40);
    g.drawString("Trains", 1100, 40);

    g.drawString(player1name, 970, 60);      
    g.drawString("" + player1.getScore(), 1040, 60);
    g.drawString("" + player1.getTrains(), 1110, 60);

    g.drawString(player2name, 970, 80);
    g.drawString("" + player2.getScore(), 1040, 80);
    g.drawString("" + player2.getTrains(), 1110, 80);

    if(player3 != null)
    {
      g.drawString(player3name, 970, 100);
      g.drawString("" + player3.getScore(), 1040, 100);
      g.drawString("" + player3.getTrains(), 1110, 100);
    }

    g.setColor(player.getColor());
    font = new Font("Arial Black", Font.ITALIC, 18);
    g.setFont( font );
    String pTurn = player.getName() + "'s turn";
    g.drawString(pTurn, 960, 130);

    font = new Font("Calibri", Font.PLAIN, 12);
    g.setFont( font );
    g.setColor(Color.white);

    String str = player.getName() + "'s Destination Cards.";
    g.drawString( str, 1000, 375 );
    str = "Click to cyle through";
    g.drawString( str, 1020, 390 );
  }
  /**
   * Prints the current players hand.
   * @param g graphics object for this applet.
   */
  void printHand(Graphics g)
  {
    drawBackground(g);
    showFaceUp(g);
    drawScoreboard(g);
    ArrayList<Integer> p = current.getHand();
    int pos = 90;
    for (int i = 0; i < p.size(); i++)
    {
      switch (i) {
        case 0: g.drawString(Integer.toString(p.get(i)), 40, 685);
        break;
        case 1: g.drawString(Integer.toString(p.get(i)), 40+pos, 685);
        break;
        case 2: g.drawString(Integer.toString(p.get(i)), 40+pos*2, 685);
        break;
        case 3: g.drawString(Integer.toString(p.get(i)), 40+pos*3, 685);
        break;
        case 4: g.drawString(Integer.toString(p.get(i)), 40+pos*4, 685);
        break;
        case 5: g.drawString(Integer.toString(p.get(i)), 40+pos*5, 685);
        break;
        case 6: g.drawString(Integer.toString(p.get(i)), 40+pos*6, 685);
        break;
        case 7: g.drawString(Integer.toString(p.get(i)), 40+pos*7, 685);
        break;
        case 8: g.drawString(Integer.toString(p.get(i)), 40+pos*8, 685);
        break;
      }
    }

    tempDests = current.getDestCards();

    //paint top dest card***
    String fs = File.separator;
    Image i = getImage(getDocumentBase(), "Cards"
        + fs + tempDests.get(currDest).fileName().toLowerCase()+".png");
    g.drawImage(i,1000,400,150,100,this);
    g.drawString(tempDests.get(currDest).toString(),1025,525);    
  }
  /**
   * Prints a specific players hand.
   * @param g the graphics object for this applet.
   * @param player the player whose hand will be drawn.
   */
  void printHand(Graphics g, Player player)
  {
    drawBackground(g);
    showFaceUp(g);
    drawScoreboard(g, player);
    ArrayList<Integer> p = player.getHand();
    int pos = 90;
    for (int i = 0; i < p.size(); i++)
    {
      switch (i) {
        case 0: g.drawString(Integer.toString(p.get(i)), 40, 685);
        break;
        case 1: g.drawString(Integer.toString(p.get(i)), 40+pos, 685);
        break;
        case 2: g.drawString(Integer.toString(p.get(i)), 40+pos*2, 685);
        break;
        case 3: g.drawString(Integer.toString(p.get(i)), 40+pos*3, 685);
        break;
        case 4: g.drawString(Integer.toString(p.get(i)), 40+pos*4, 685);
        break;
        case 5: g.drawString(Integer.toString(p.get(i)), 40+pos*5, 685);
        break;
        case 6: g.drawString(Integer.toString(p.get(i)), 40+pos*6, 685);
        break;
        case 7: g.drawString(Integer.toString(p.get(i)), 40+pos*7, 685);
        break;
        case 8: g.drawString(Integer.toString(p.get(i)), 40+pos*8, 685);
        break;
      }
    }

    tempDests = player.getDestCards();

    if (currDest > tempDests.size() - 1)
      currDest = 0;

    //paint top dest card***
    String fs = File.separator;
    Image i = getImage(getDocumentBase(), "Cards" +fs +
        tempDests.get(currDest).fileName().toLowerCase()+".png");
    g.drawImage(i,1000,400,150,100,this);
    g.drawString(tempDests.get(currDest).toString(), 1025, 525);
  }
  /**
   * Adds a card to the current players hand list.
   * @param c the card to be added.
   */
  void addToHand(Card c)
  {
    if (c.toString().equals("Hopper"))
      current.addToHand(0);
    else if (c.toString().equals("Tanker"))
      current.addToHand(1);
    else if (c.toString().equals("Freight"))
      current.addToHand(2);
    else if (c.toString().equals("Caboose"))
      current.addToHand(3);
    else if (c.toString().equals("Box"))
      current.addToHand(4);
    else if (c.toString().equals("Coal"))
      current.addToHand(5);
    else if (c.toString().equals("Passenger"))
      current.addToHand(6);
    else if (c.toString().equals("Reefer"))
      current.addToHand(7);
    else if (c.toString().equals("Locomotive"))
      current.addToHand(8);
  }
  /**
   * Operations done on a mouse click.
   * @param e MouseEvent object.
   */
  public void mouseClicked(MouseEvent e)
  {
    // Save the coordinates of the click. 
    xpos = e.getX();
    ypos = e.getY();

    if( endGame == 1 )
      return;

    if (xpos > 44 && xpos < 56 && ypos > 17 && ypos < 36)
      JOptionPane.showMessageDialog(this, "Choosing Moscow");
    else if (xpos > 160 && xpos < 175 && ypos > 12 && ypos < 30)
      JOptionPane.showMessageDialog(this, "Choosing Perm");
    else if (xpos > 275 && xpos < 291 && ypos > 13 && ypos < 34)
      JOptionPane.showMessageDialog(this, "Choosing Omsk");
    else if (xpos > 392 && xpos < 408 && ypos > 19 && ypos < 37)
      JOptionPane.showMessageDialog(this, "Choosing Krasnoyarsk");
    else if (xpos > 504 && xpos < 521 && ypos > 23 && ypos < 41)
      JOptionPane.showMessageDialog(this, "Choosing Irkutsk");
    else if (xpos > 583 && xpos < 601 && ypos > 37 && ypos < 57)
      JOptionPane.showMessageDialog(this, "Choosing Chita");
    else if (xpos > 749 && xpos < 766 && ypos > 55 && ypos < 73)
      JOptionPane.showMessageDialog(this, "Choosing Khabarovsk");
    else if (xpos > 100 && xpos < 120 && ypos > 125 && ypos < 140)
      JOptionPane.showMessageDialog(this, "Choosing Astrakhan");
    else if (xpos > 247 && xpos < 264 && ypos > 186 && ypos < 205)
      JOptionPane.showMessageDialog(this, "Choosing Samarkand");
    else if (xpos > 400 && xpos < 418 && ypos > 140 && ypos < 162)
      JOptionPane.showMessageDialog(this, "Choosing Dihua");
    else if (xpos > 606 && xpos < 626 && ypos > 186 && ypos < 207)
      JOptionPane.showMessageDialog(this, "Choosing Peking");
    else if (xpos > 527 && xpos < 542 && ypos > 99 && ypos < 119)
      JOptionPane.showMessageDialog(this, "Choosing Ulan Bator");
    else if (xpos > 727 && xpos < 747 && ypos > 148 && ypos < 164)
      JOptionPane.showMessageDialog(this, "Choosing Vladivostok");
    else if (xpos > 80 && xpos < 95 && ypos > 170 && ypos < 187)
      JOptionPane.showMessageDialog(this, "Choosing Tbilisi");
    else if (xpos > 7 && xpos < 24 && ypos > 190 && ypos < 210)
      JOptionPane.showMessageDialog(this, "Choosing Ankara");
    else if (xpos > 135 && xpos < 151 && ypos > 235 && ypos < 255)
      JOptionPane.showMessageDialog(this, "Choosing Tehran");
    else if (xpos > 80 && xpos < 107 && ypos > 270 && ypos < 291)
      JOptionPane.showMessageDialog(this, "Choosing Baghdad");
    else if (xpos > 50 && xpos < 69 && ypos > 389 && ypos < 408)
      JOptionPane.showMessageDialog(this, "Choosing Mecca");
    else if (xpos > 170 && xpos < 190 && ypos > 313 && ypos < 334)
      JOptionPane.showMessageDialog(this, "Choosing Shiraz");
    else if (xpos > 290 && xpos < 310 && ypos > 265 && ypos < 285)
      JOptionPane.showMessageDialog(this, "Choosing Rawalpindi");
    else if (xpos > 246 && xpos < 264 && ypos > 342 && ypos < 363)
      JOptionPane.showMessageDialog(this, "Choosing Karachi");
    else if (xpos > 323 && xpos < 340 && ypos > 327 && ypos < 347)
      JOptionPane.showMessageDialog(this, "Choosing Agra");
    else if (xpos > 298 && xpos < 317 && ypos > 410 && ypos < 430)
      JOptionPane.showMessageDialog(this, "Choosing Bombay");
    else if (xpos > 356 && xpos < 373 && ypos > 542 && ypos < 566)
      JOptionPane.showMessageDialog(this, "Choosing Colombo");
    else if (xpos > 385 && xpos < 403 && ypos > 312 && ypos < 334)
      JOptionPane.showMessageDialog(this, "Choosing Kathmandu");
    else if (xpos > 427 && xpos < 445 && ypos > 278 && ypos < 300)
      JOptionPane.showMessageDialog(this, "Choosing Lhasa");
    else if (xpos > 402 && xpos < 422 && ypos > 356 && ypos < 376)
      JOptionPane.showMessageDialog(this, "Choosing Calcutta");
    else if (xpos > 542 && xpos < 562 && ypos > 244 && ypos < 264)
      JOptionPane.showMessageDialog(this, "Choosing Xian");
    else if (xpos > 694 && xpos < 714 && ypos > 235 && ypos < 255)
      JOptionPane.showMessageDialog(this, "Choosing Seoul");
    else if (xpos > 776 && xpos < 796 && ypos > 236 && ypos < 256)
      JOptionPane.showMessageDialog(this, "Choosing Kobe");
    else if (xpos > 644 && xpos < 664 && ypos > 271 && ypos < 291)
      JOptionPane.showMessageDialog(this, "Choosing Shanghai");
    else if (xpos > 457 && xpos < 477 && ypos > 367 && ypos < 387)
      JOptionPane.showMessageDialog(this, "Choosing Mandalay");
    else if (xpos > 537 && xpos < 557 && ypos > 362 && ypos < 382)
      JOptionPane.showMessageDialog(this, "Choosing Hanoi");
    else if (xpos > 654 && xpos < 674 && ypos > 346 && ypos < 366)
      JOptionPane.showMessageDialog(this, "Choosing Taipei");
    else if (xpos > 584 && xpos < 604 && ypos > 372 && ypos < 392)
      JOptionPane.showMessageDialog(this, "Choosing Macau");
    else if (xpos > 457 && xpos < 477 && ypos > 426 && ypos < 446)
      JOptionPane.showMessageDialog(this, "Choosing Rangoon");
    else if (xpos > 494 && xpos < 514 && ypos > 458 && ypos < 478)
      JOptionPane.showMessageDialog(this, "Choosing Bangkok");
    else if (xpos > 535 && xpos < 555 && ypos > 488 && ypos < 508)
      JOptionPane.showMessageDialog(this, "Choosing Saigon");
    else if (xpos > 515 && xpos < 535 && ypos > 575 && ypos < 595)
      JOptionPane.showMessageDialog(this, "Choosing Singapore");

    setCurrent();
    if( ( press1 != null || press2 != null) 
    && xpos > 800 && xpos < 950 && ypos > 0 && ypos < 600)
    {
      JOptionPane.showMessageDialog(this, "Must pick another city");
    }
    else if (xpos > 800 && xpos < 950 && ypos > 500 && ypos < 600)
    {
      if (railDeck.size() + discardDeck.size() > 20)
      {
        RailCard c = (RailCard)(railDeck.draw());
        if (c.toString().equals("Hopper"))
          JOptionPane.showMessageDialog(this, "Picked up " 
            + c.toString() + " (black) card.");
        else if (c.toString().equals("Tanker"))
          JOptionPane.showMessageDialog(this, "Picked up " 
            + c.toString() + " (blue) card.");
        else if (c.toString().equals("Freight"))
          JOptionPane.showMessageDialog(this, "Picked up "
            + c.toString() + " (brown) card.");
        else if (c.toString().equals("Caboose"))
          JOptionPane.showMessageDialog(this, "Picked up " 
            + c.toString() + " (green) card.");
        else if (c.toString().equals("Box"))
          JOptionPane.showMessageDialog(this, "Picked up " 
            + c.toString() + " (purple) card.");
        else if (c.toString().equals("Coal"))
          JOptionPane.showMessageDialog(this, "Picked up " 
            + c.toString() + " (red) card.");
        else if (c.toString().equals("Passenger"))
          JOptionPane.showMessageDialog(this, "Picked up " 
            + c.toString() + " (white) card.");
        else if (c.toString().equals("Reefer"))
          JOptionPane.showMessageDialog(this, "Picked up " 
            + c.toString() + " (yellow) card.");
        else if (c.toString().equals("Locomotive"))
          JOptionPane.showMessageDialog(this, "Picked up " 
            + c.toString() + " (rainbow) card.");
        addToHand(c);
        pickCard = true;
      }
      else
      {
        JOptionPane.showMessageDialog(this, 
          "Deck is low, please stop stacking cards.");
      }
    }
    else if (xpos > 800 && xpos < 950 && ypos > 400 && ypos < 500 )
    {
      if (turnCount != 0)
      {
        JOptionPane.showMessageDialog(this, 
          "Illegal move. Please make a different seletion.");
      }
      else
      {
        if (shortDeck.size() < 3)
        {
          JOptionPane.showMessageDialog(this, 
            "There are not more destination tickets." + 
            "Please make a different seletion.");
        }
        else
        {
          selectShortDestCard(current);
          turnCount = 2;
        }
      }
    }
    else if (xpos > 800 && xpos < 920 && ypos > 0 && ypos < 80)
    {
      if (railDeck.size() + discardDeck.size() > 20)
      {
        if (turnCount == 0)
        {
          JOptionPane.showMessageDialog(this, "Taking " 
            + faceUp[0].toString() +  " Card.");
          RailCard c = (RailCard)(faceUp[0]);
          if(c.isLoco())
          {
            turnCount++;
          }
          faceUp[0] = railDeck.draw();
          addToHand(c);   
          pickCard = true;
        }
        else if (turnCount == 1)
        {
          JOptionPane.showMessageDialog(this, "Taking " 
            + faceUp[0].toString() + " Card.");
          RailCard c = (RailCard)(faceUp[0]);
          if(c.isLoco())
          {
            JOptionPane.showMessageDialog(this, 
              "Cannot pick up Locomotive now.");
          }
          else
          {
            faceUp[0] = railDeck.draw();
            addToHand(c);   
            pickCard = true;
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(this, 
          "Deck is low, please stop stacking cards.");
      }
    }
    else if (xpos > 800 && xpos < 920 && ypos > 80 && ypos < 160)
    {
      if (railDeck.size() + discardDeck.size() > 20)
      {
        if(turnCount == 0)
        {
          JOptionPane.showMessageDialog(this, 
            "Taking " + faceUp[1].toString() + " Card.");
          RailCard c = (RailCard)(faceUp[1]);
          if (c.isLoco())
          {
            turnCount++;
          }
          faceUp[1] = railDeck.draw();
          addToHand(c);
          pickCard = true;
        }
        else if(turnCount == 1)
        {
          JOptionPane.showMessageDialog(this, 
            "Taking " + faceUp[1].toString() + " Card.");
          RailCard c = (RailCard)(faceUp[1]);
          if(c.isLoco())
          {
            JOptionPane.showMessageDialog(this, 
              "Cannot pick up Locomotive now.");
          }
          else{
            faceUp[1] = railDeck.draw();
            addToHand(c);
            pickCard = true;
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(this,
          "Deck is low, please stop stacking cards.");
      }
    }
    else if (xpos > 800 && xpos < 920 && ypos > 160 && ypos < 240)
    {
      if (railDeck.size() + discardDeck.size() > 20)
      {
        if(turnCount == 0)
        {
          JOptionPane.showMessageDialog(this, 
            "Taking " + faceUp[2].toString() + " Card.");
          RailCard c = (RailCard)(faceUp[2]);
          if(c.isLoco())
          {
            turnCount++;
          }
          faceUp[2] = railDeck.draw();
          addToHand(c);
          pickCard = true;
        }
        else if(turnCount == 1)
        {
          JOptionPane.showMessageDialog(this, "Taking "
            + faceUp[2].toString() + " Card.");
          RailCard c = (RailCard)(faceUp[2]);
          if(c.isLoco())
          {
            JOptionPane.showMessageDialog(this, 
              "Cannot pick up Locomotive now.");
          }
          else
          {
            faceUp[2] = railDeck.draw();
            addToHand(c);   
            pickCard = true;
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(this,
          "Deck is low, please stop stacking cards.");
      }
    }
    else if (xpos > 800 && xpos < 920 && ypos > 240 && ypos < 320)
    {
      if (railDeck.size() + discardDeck.size() > 20)
      {
        if(turnCount==0)
        {
          JOptionPane.showMessageDialog(this, "Taking " 
            + faceUp[3].toString() + " Card.");
          RailCard c = (RailCard)(faceUp[3]);
          if(c.isLoco())
          {
            turnCount++;
          }
          faceUp[3] = railDeck.draw();
          addToHand(c);   
          pickCard = true;
        }
        else if(turnCount == 1){
          JOptionPane.showMessageDialog(this, "Taking " 
            + faceUp[3].toString() + " Card.");
          RailCard c = (RailCard)(faceUp[3]);
          if(c.isLoco())
          {
            JOptionPane.showMessageDialog(this, 
              "Cannot pick up Locomotive now.");
          }
          else
          {
            faceUp[3] = railDeck.draw();
            addToHand(c);
            pickCard = true;
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(this,
          "Deck is low, please stop stacking cards.");
      }
    }
    else if (xpos > 800 && xpos < 920 && ypos > 320 && ypos < 400)
    {
      if (railDeck.size() + discardDeck.size() > 20)
      {
        if(turnCount==0)
        {
          JOptionPane.showMessageDialog(this, "Taking " 
            + faceUp[4].toString() + " Card.");
          RailCard c = (RailCard)(faceUp[4]);
          if(c.isLoco())
          {
            turnCount++;
          }
          faceUp[4] = railDeck.draw();
          addToHand(c);   
          pickCard = true;
        }
        else if (turnCount == 1)
        {
          JOptionPane.showMessageDialog(this, "Taking " 
            + faceUp[4].toString() + " Card.");
          RailCard c = (RailCard)(faceUp[4]);
          if(c.isLoco())
          {
            JOptionPane.showMessageDialog(this, 
              "Cannot pick up Locomotive now.");
          }
          else
          {
            faceUp[4] = railDeck.draw();
            addToHand(c);   
            pickCard = true;
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(this,
          "Deck is low, please stop stacking cards.");
      }
    }
    else
    {
      //         showFaceUp(g);
      //         printHand(g);
    }

    tempSize = tempDests.size();

    if (xpos > 1000 && xpos < 1150 && ypos > 400 && ypos < 500)
    {
      if (SwingUtilities.isRightMouseButton(e))
      {
        if (currDest == 0)
        {
          currDest = tempDests.size() - 1;
        }
        else
          currDest--;
      }
      else
      {
        if (currDest == tempDests.size() - 1)
        {
          currDest = 0;
        }
        else
          currDest++;
      }
    }

    repaint();
  }
  /**
   * Empty method for MouseListerner Interface.
   * @param e MouseEvent Object.
   */
  public void mouseEntered(MouseEvent e){}
  /**
   * Empty method for MouseListerner Interface.
   * @param e MouseEvent Object.
   */
  public void mouseExited(MouseEvent e){}
  /**
   * Empty method for MouseListerner Interface.
   * @param e MouseEvent Object.
   */
  public void mousePressed(MouseEvent e){}
  /**
   * Empty method for MouseListerner Interface.
   * @param e MouseEvent Object.
   */
  public void mouseReleased(MouseEvent e){}
  /**
   * Prompts for and initilizes the players start cards.
   * @param p The player to be prompted.
   */
  public void startCards(Player p)
  {
    ArrayList<Card> temp = new ArrayList<Card> ();
    Random r = new Random();
    Deck longDeck = new Deck();
    longDeck.initLongDestDeck();
    longDeck.shuffle();
    temp.add(longDeck.draw( r.nextInt( longDeck.size() ) ));
    int first = r.nextInt( shortDeck.size() );
    int second = r.nextInt( shortDeck.size() );
    while( second == first )
      second = r.nextInt( shortDeck.size() );
    int third = r.nextInt( shortDeck.size() );
    while( third == first || third == second)
      third = r.nextInt( shortDeck.size() );
    temp.add(shortDeck.draw( first ));
    temp.add(shortDeck.draw( second));
    temp.add(shortDeck.draw( third ));
    JOptionPane.showMessageDialog(null,
      p.getName() + " select between 2-4 cards");
    boolean stop = true;
    int count = 0;

    while( stop || count < 2 )
    {
      Card [] array = new Card [ temp.size() ];
      for( int i = 0; i < array.length ; i++  )
        array [ i ] = temp.get(i); 
      int hold = count;
      count++;
      Card c = (Card) JOptionPane.showInputDialog(this,
          "Pick a card", "Pick a card",
          JOptionPane.INFORMATION_MESSAGE, 
          null, array, array[0] );
      p.addDestCard((DestinationCard)c);
      temp.remove(c);
      if( c == null ){
        stop = false;
        count = hold;
      }
      if( count == 4 )
        break;
    }     
    //     return mine;
  }

  /**
   * Method called when destination deck is pressed
   * within gameplay
   * 
   * @param p  the current player that pressed the deck
   */
  public void selectShortDestCard ( Player p )
  {
    ArrayList<Card> temp = new ArrayList<Card> ();
    Random r = new Random();
    int first = r.nextInt( shortDeck.size() );
    int second = r.nextInt( shortDeck.size() );
    while( second == first )
      second = r.nextInt( shortDeck.size() );
    int third = r.nextInt( shortDeck.size() );
    while( third == first || third == second)
      third = r.nextInt( shortDeck.size() );
    temp.add(shortDeck.draw( first ));
    temp.add(shortDeck.draw( second));
    temp.add(shortDeck.draw( third ));
    boolean stop = true;
    int count = 0;

    while( stop || count < 1 )
    {
      Card [] array = new Card [ temp.size() ];
      for( int i = 0; i < array.length ; i++  )
        array [ i ] = temp.get(i); 
      int hold = count;
      count++;
      Card c = (Card) JOptionPane.showInputDialog(this, "Pick a card", 
          "Pick a card",JOptionPane.INFORMATION_MESSAGE, 
          null, array, array[0]);
      p.addDestCard((DestinationCard)c);
      temp.remove(c);
      if( c == null ){
        stop = false;
        count = hold;
      }
      if( count == 3 )
        break;
    }
  }

  /**
   * Sets whose turn it currently is in the game
   */
  public void setCurrent()
  {
    int num = playerTurn % pNumi;
    if( pNumi == 2 )
    {
      switch(num){
        case 0 : current = player1;
        break;
        default: current = player2;
      }
    }
    else
    {
      switch(num){
        case 0 : current = player1;
        break;
        case 1 : current = player2;
        break;
        default: current = player3;
        break;
      }
    }
  }

  /**
   * Paints the routes on the applet
   * 
   * @param g  the applet graphic
   */
  public void paintRoutes(Graphics g)
  {
    int a = 0;
    for ( Line2D.Double ln : completedRoutes )
    {
      g.setColor( colorRoutes.get(a));
      a++;
      g.drawLine( (int)ln.getX1() , 
        (int)ln.getY1(), (int)ln.getX2() , 
        (int)ln.getY2() );
    }   

  }
  /**
   * Increments a players score when a route is captured.
   * @param r the Route captured.
   */
  public void setScore(Route r)
  {
    int length = r.getLength();
    switch(length)
    {
      case 1 : current.incrementScore( 1 );
      break;
      case 2 : current.incrementScore( 2 );
      break;
      case 3 : current.incrementScore( 4 );
      break;
      case 4 : current.incrementScore( 7 );
      break;
      case 5 : current.incrementScore( 10 );
      break;
      case 6 : current.incrementScore( 15 );
      break;
    }

  }


  /**
   * Replaces the deck with the discard deck
   */
  public void replaceDeck()
  {
    railDeck = discardDeck;
    discardDeck = new Deck();
    railDeck.shuffle();
    JOptionPane.showMessageDialog(this,
      "Deck out of rail cards, reshuffling discard pile.");
  }


  /**
   * Adds card of color c to discard pile
   * @param c the color of the card to be discarded.
   */
  public void discardRail(Color c)
  {
    if(c == Color.BLACK)
      discardDeck.add(new RailCard("Hopper"));
    else if(c == Color.WHITE)
      discardDeck.add(new RailCard("Reefer"));
    else if(c == Color.RED)
      discardDeck.add(new RailCard("Coal"));
    else if(c == Color.GREEN)
      discardDeck.add(new RailCard("Caboose"));
    else if(c == Color.BLUE)
      discardDeck.add(new RailCard("Passenger"));
    else if(c == Color.YELLOW)
      discardDeck.add(new RailCard("Box"));
    else if(c == Color.MAGENTA)
      discardDeck.add(new RailCard("Freight"));
    else if(c == Color.ORANGE)
      discardDeck.add(new RailCard("Tanker"));
    else if(c == null)
      discardDeck.add(new RailCard());
  }
  /**
   * Adds a railcard to the discard pile.
   * @param the name of the card to be discarded.
   */
  public void discardRail(String s)
  {
    discardDeck.add(new RailCard(s));
  }

  /**
   * For a gray path, allows the user to pick
   * which color to use and subtracts trains 
   * 
   * @param r a route
   * @param p current player
   * 
   * @return whether the route has been filled
   */
  public boolean grayPath(Route r, Player p)
  {
    boolean connect = true;
    Object [] options = new Object [9];
    ArrayList<Integer> hand = p.getHand();
    for( int i = 0; i < hand.size() ; i++ )
    {
      String s;
      switch(i)
      {
        case 0 : s = "Black : " + hand.get(i);
        break;
        case 1: s = "Blue : " + hand.get(i);
        break;
        case 2: s = "Orange : " + hand.get(i);
        break;
        case 3: s = "Green : " + hand.get(i);
        break;
        case 4: s = "Purple : " + hand.get(i);
        break;
        case 5: s = "Red : " + hand.get(i);
        break;
        case 6: s = "White : " + hand.get(i);
        break;
        case 7: s = "Yellow : " + hand.get(i);
        break;
        default: s = "Loco : " + hand.get(i);
        break;
      }
      options[i] = s;
    }
    int num = JOptionPane.showOptionDialog(null,
        "Color of trains",
        "Trains",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]);
    int i = r.getLength();

    if(current.getBlue() >= i && num == 1){
      current.changeBlue( -i );
      replace( Color.blue , i );
    }
    else if(current.getYellow() >=i && num == 7){
      current.changeYellow( -i );
      replace( Color.yellow, i);
    }
    else if(current.getBlack() >=i && num == 0){
      current.changeBlack( -i );
      replace( Color.black , i );
    }
    else if(current.getOrange()>= i && num == 2){
      current.changeOrange( -i );
      replace( Color.orange, i );
    }
    else if(current.getGreen()>= i && num == 3){
      current.changeGreen( -i );
      replace( Color.green, i );
    }
    else if(current.getPurple()>= i && num == 4){
      current.changePurple( -i );
      replace( Color.magenta , i);
    }
    else if(current.getRed()>= i && num == 5){
      current.changeRed( -i );
      replace( Color.red, i );
    }
    else if(current.getWhite()>= i && num == 6){
      current.changeWhite( -i );
      replace( Color.white, i );
    }
    else if(current.getLoco()>= i && num == 8){
      current.changeLoco( -i );
      replace( null , i );
    }
    else if( (current.getBlue() + current.getLoco() )
    >= i && num == 1)
    {
      current.changeLoco( current.getBlue() - i ); 
      current.setBlue( 0 );
      replace( null , current.getBlue() - i );
      replace( Color.blue , current.getBlue() );
    }
    else if( (current.getYellow() + current.getLoco() )
    >=i && num == 7)
    {
      current.changeLoco( current.getYellow() - i );
      current.setYellow( 0 ); 
      replace( null , current.getYellow() - i );
      replace( Color.yellow , current.getYellow() );
    }
    else if( ( current.getBlack() + current.getLoco() )
    >=i && num == 0)
    {
      current.changeLoco( current.getBlack() - i ); 
      current.setBlack( 0 );
      replace( null , current.getBlack() - i );
      replace( Color.black , current.getBlack() );
    }
    else if( ( current.getOrange() + current.getLoco() )
    >= i && num == 2)
    {
      current.changeLoco( current.getOrange() - i );
      current.setOrange( 0 );
      replace( null , current.getOrange() - i );
      replace( Color.orange , current.getOrange() );
    }
    else if( ( current.getGreen() + current.getLoco() )
    >=  i && num == 3)
    {
      current.changeLoco( current.getGreen() - i ); 
      current.setGreen( 0 );
      replace( null , current.getGreen() - i );
      replace( Color.green , current.getGreen() );
    }
    else if( ( current.getPurple() + current.getLoco() )
    >= i && num == 4)
    {
      current.changeLoco( current.getPurple() - i ); 
      current.setPurple( 0 ); 
      replace( null , current.getPurple() - i );
      replace( Color.magenta , current.getPurple() );
    }
    else if( ( current.getRed() + current.getLoco() )
    >= i && num == 5)
    {      
      current.changeLoco( current.getRed() - i );
      current.setRed( 0 );
      replace( null , current.getRed() - i );
      replace( Color.red , current.getRed() );
    }
    else if( ( current.getWhite() + current.getLoco() )
    >= i && num == 6)
    {
      current.changeLoco( current.getWhite() - i );
      current.setWhite( 0 );
      replace( null , current.getWhite() - i );
      replace( Color.white , current.getWhite() );
    }

    else{
      //       JOptionPane.showMessageDialog(null, "Cannot Complete");
      connect = false;
    }
    return connect;

  }

  /**
   * Places i cards with color col in the discard deck
   * 
   * @param col a color
   * @param i an integer
   */
  public void replace(Color col , int i )
  {
    int num = 0;
    while ( num < i )
    {
      discardRail(col);
      num++;
    }

  }


  /**
   * Determines if this is the last turn
   * 
   * @boolean if it is the last turn
   */
  public boolean lastTurn()
  {
    if (pNumi == 3)
    {
      if(player1.getTrains() <= 2 || 
      player2.getTrains() <= 2 || 
      player3.getTrains() <= 2)
      {
        turnsLeft = 2;
        return true;
      }
      else 
        return false;
    }
    else
    {
      if(player1.getTrains() <= 2 || player2.getTrains() <= 2)
      {
        turnsLeft = 1;
        return true;
      }
      else 
        return false;
    }
  }
}