package edu.truman.johnsonw;

import java.awt.BorderLayout;
import java.awt.CardLayout;

/**
 * Adapted by Jon Beck from Beginning Java 5 Game Programming
 * by Jonathan S. Harbour
 * 
 * @author William Ray Johnson
 */

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.BoxLayout;
import javax.swing.JButton;

/**
 * Primary class for the game
 * 
 * @author William Ray Johnson
 */
public class Asteroids implements Runnable
{
   private Thread gameloop;
   private JPanel gameSetupPanel;
   private JLabel asteroidLabel;
   private JSlider numberOfAsteroidsSlider;
   private JLabel shipSpeedLabel;
   private JSlider topShipSpeed;
   private JLabel livesLabel;
   private JSlider numberOfLivesSlider;
   private JButton startGameButton;
   private JPanel gamePanel;
   private AsteroidsComponent game;
   private JPanel winLosePanel;
   private JLabel winLoseText;
   private JButton newGameButton;
   private JPanel mainPanel;
   private JFrame frame;
   private boolean gameInitialized;
   private boolean gameRunning;

   public static final int FRAMEWIDTH = 800;
   public static final int FRAMEHEIGHT = 700;
   public static final int MAX_ASTEROIDS = 30;
   public static final int MAX_TOP_SHIP_SPEED = 20;
   public static final int MAX_LIVES = 10;
   private static final int MAJOR_TICK_DIVISOR = 5;
   private static final String GAME_PANEL = "Game Panel";
   private static final String GAME_SETUP_PANEL = "Game Setup Panel";
   private static final String WIN_LOSE_PANEL = "Win/Lose Panel";

   /**
    * Constructs the Asteroids game
    */
   public Asteroids()
   {
      game = new AsteroidsComponent();
      gameSetupPanel = new JPanel();
      asteroidLabel = new JLabel("Number of Asteroids");
      numberOfAsteroidsSlider = new JSlider(JSlider.HORIZONTAL, 1,
         MAX_ASTEROIDS, MAX_ASTEROIDS / 2);
      shipSpeedLabel = new JLabel("Top Speed of Ship");
      topShipSpeed = new JSlider(JSlider.HORIZONTAL, 1, MAX_TOP_SHIP_SPEED,
         MAX_TOP_SHIP_SPEED / 2);
      livesLabel = new JLabel("Number of Lives");
      numberOfLivesSlider = new JSlider(JSlider.HORIZONTAL, 1, MAX_LIVES,
         MAX_LIVES / 2);
      startGameButton = new JButton("Start Game");
      gamePanel = new JPanel(new BorderLayout());
      winLosePanel = new JPanel();
      winLoseText = new JLabel();
      newGameButton = new JButton("New Game");
      mainPanel = new JPanel(new CardLayout());
      frame = new JFrame("Asteroids");
      gameInitialized = false;
      gameRunning = false;
   }

   public static void main(String[] args)
   {
      Asteroids a = new Asteroids();
      a.go();
   }

   /**
    * Creates the GUI and sets up the system for setting up a game and cycling
    * through the three panels.
    */
   public void go()
   {
      gameSetupPanel.setLayout(new BoxLayout(gameSetupPanel, BoxLayout.Y_AXIS));
      startGameButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent event)
         {
            if (!gameInitialized)
            {
               game.initializeGame(numberOfAsteroidsSlider.getValue(),
                  topShipSpeed.getValue(), numberOfLivesSlider.getValue());
               gameInitialized = true;
               gameRunning = true;
            } else
            {
               game.restartGame(numberOfAsteroidsSlider.getValue(),
                  topShipSpeed.getValue(), numberOfLivesSlider.getValue());
               gameRunning = true;
            }
            NextPanel();
            game.requestFocusInWindow();
         }
      });
      asteroidLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      numberOfAsteroidsSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
      numberOfAsteroidsSlider
         .setMajorTickSpacing(MAX_ASTEROIDS / MAJOR_TICK_DIVISOR);
      numberOfAsteroidsSlider.setMinorTickSpacing(1);
      numberOfAsteroidsSlider.setPaintTicks(true);
      numberOfAsteroidsSlider.setPaintLabels(true);
      shipSpeedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      topShipSpeed.setAlignmentX(Component.CENTER_ALIGNMENT);
      topShipSpeed.setMajorTickSpacing(MAX_TOP_SHIP_SPEED / MAJOR_TICK_DIVISOR);
      topShipSpeed.setMinorTickSpacing(1);
      topShipSpeed.setPaintTicks(true);
      topShipSpeed.setPaintLabels(true);
      livesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      numberOfLivesSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
      numberOfLivesSlider.setMajorTickSpacing(MAX_LIVES / MAJOR_TICK_DIVISOR);
      numberOfLivesSlider.setMinorTickSpacing(1);
      numberOfLivesSlider.setPaintTicks(true);
      numberOfLivesSlider.setPaintLabels(true);
      startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      gameSetupPanel.add(asteroidLabel);
      gameSetupPanel.add(numberOfAsteroidsSlider);
      gameSetupPanel.add(shipSpeedLabel);
      gameSetupPanel.add(topShipSpeed);
      gameSetupPanel.add(livesLabel);
      gameSetupPanel.add(numberOfLivesSlider);
      gameSetupPanel.add(startGameButton);

      gamePanel.add(game, BorderLayout.CENTER);

      winLosePanel.setLayout(new BoxLayout(winLosePanel, BoxLayout.Y_AXIS));
      newGameButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent event)
         {
            NextPanel();
         }
      });
      winLoseText.setAlignmentX(Component.CENTER_ALIGNMENT);
      newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      winLosePanel.add(winLoseText);
      winLosePanel.add(newGameButton);

      mainPanel.add(gameSetupPanel, GAME_SETUP_PANEL);
      mainPanel.add(gamePanel, GAME_PANEL);
      mainPanel.add(winLosePanel, WIN_LOSE_PANEL);

      frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(mainPanel);
      frame.setVisible(true);

      game.requestFocusInWindow();
      gameloop = new Thread(this);
      gameloop.start();
   }

   /**
    * thread run event (game loop)
    */
   @Override
   public void run()
   {
      Thread t = Thread.currentThread();
      while (t == gameloop)
      {

         try
         {
            if (game.isGameOver() && gameRunning)
            {
               winLoseText.setText("Game Over");
               NextPanel();
               gameRunning = false;
            } else if (game.isWinner() && gameRunning)
            {
               winLoseText.setText("You Win!");
               NextPanel();
               gameRunning = false;
            } else if (gameRunning)
               game.gameUpdate();

            Thread.sleep(20);
         } catch (InterruptedException e)
         {
            e.printStackTrace();
         }
         game.repaint();
      }
   }

   /**
    * Cycles the main panel to the next panel to be displayed.
    */
   public void NextPanel()
   {
      CardLayout cl = (CardLayout) (mainPanel.getLayout());
      cl.next(mainPanel);
   }
}
