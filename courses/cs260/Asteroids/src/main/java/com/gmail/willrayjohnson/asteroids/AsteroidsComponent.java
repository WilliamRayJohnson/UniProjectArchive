package com.gmail.willrayjohnson.asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * Contains all the logic to run the Asteroids game.
 * 
 * @author William Ray Johnson
 *
 */
public class AsteroidsComponent extends JComponent implements KeyListener {
   private Graphics2D g2d;
   private ArrayList<Integer> keysPressed;
   private boolean showBounds;
   private ArrayList<Asteroid> ast;
   private ArrayList<Bullet> bulletsAL;
   private int numberOfAsteroids;
   private int aliveAsteroids;
   private Ship ship;
   private boolean winner;
   private int lives;
   private boolean gameOver;
   private Timer bulletFireRateTimer;
   private boolean canFire;
   private Image shipExplosionImage;
   private Image asteroidExplosionImage;
   private AffineTransform identity;
   private Random rand;

   private static final int EXPLOSION_DELAY = 2;
   private static final int FIRE_RATE = 150;
   private static final String SHIP_EXPLOSION_IMAGE = "/META-INF/shipExplosion.png";
   private static final String ASTEROID_EXPLOSION_IMAGE = "/META-INF/asteroidExplosion.png";

   /**
    * Constructs the asteroid game component.
    */
   AsteroidsComponent() {
      g2d = null;
      keysPressed = new ArrayList<Integer>();
      showBounds = false;
      ast = new ArrayList<Asteroid>();
      bulletsAL = new ArrayList<Bullet>();
      ship = new Ship();
      gameOver = false;
      winner = false;
      bulletFireRateTimer = new Timer(FIRE_RATE, new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            if (!canFire)
               canFire = true;
         }
      });
      bulletFireRateTimer.start();
      Toolkit tk = Toolkit.getDefaultToolkit();
      shipExplosionImage = tk.createImage(getClass().getResource(SHIP_EXPLOSION_IMAGE));
      asteroidExplosionImage = tk
            .createImage(getClass().getResource(ASTEROID_EXPLOSION_IMAGE));
      identity = new AffineTransform();
      rand = new Random();
   }

   /**
    * Initialized the game with various parameters.
    * 
    * @param numberOfAsteroids Number of asteroids in game
    * @param topShipSpeed Top speed of ship
    * @param lives Number of lives
    */
   public void initializeGame(int numberOfAsteroids, int topShipSpeed, int lives) {
      this.numberOfAsteroids = numberOfAsteroids;
      this.lives = lives;
      ship.setTopShipSpeed(topShipSpeed);

      ship.setX(Asteroids.FRAMEWIDTH / 2);
      ship.setY(Asteroids.FRAMEHEIGHT / 2);
      ship.setExplosionAnimation(
            new Animation(shipExplosionImage, 23, 5, 64, 64, EXPLOSION_DELAY, this));

      for (int n = 0; n < this.numberOfAsteroids; n++) {
         Asteroid a = new Asteroid();
         a.setX((double) rand.nextInt(Asteroids.FRAMEWIDTH) + 20);
         a.setY((double) rand.nextInt(Asteroids.FRAMEHEIGHT) + 20);
         a.setMoveAngle(rand.nextInt(360));
         double ang = a.getMoveAngle() - 90;
         a.setVelX(calcAngleMoveX(ang));
         a.setVelY(calcAngleMoveY(ang));
         a.setExplosionAnimation(new Animation(asteroidExplosionImage, 17, 5, 96, 96,
               EXPLOSION_DELAY, this));
         ast.add(a);
      }

      aliveAsteroids = calcAliveAsteroids();

      addKeyListener(this);
      requestFocusInWindow();
   }

   /**
    * Resets game to inital values with new parameters.
    * 
    * @param numberOfAsteroids Number of asteroids in game
    * @param topShipSpeed Top speed of ship
    * @param lives Number of lives
    */
   public void restartGame(int numberOfAsteroids, int topShipSpeed, int lives) {
      this.numberOfAsteroids = numberOfAsteroids;
      this.lives = lives;
      ship.setTopShipSpeed(topShipSpeed);

      gameOver = false;
      winner = false;

      ship.setAlive(true);
      ship.setX(Asteroids.FRAMEWIDTH / 2);
      ship.setY(Asteroids.FRAMEHEIGHT / 2);
      ship.setVelX(0);
      ship.setVelY(0);

      ast.clear();
      keysPressed.clear();
      bulletsAL.clear();

      for (int n = 0; n < this.numberOfAsteroids; n++) {
         Asteroid a = new Asteroid();
         a.setX((double) rand.nextInt(Asteroids.FRAMEWIDTH) + 20);
         a.setY((double) rand.nextInt(Asteroids.FRAMEHEIGHT) + 20);
         a.setMoveAngle(rand.nextInt(360));
         double ang = a.getMoveAngle() - 90;
         a.setVelX(calcAngleMoveX(ang));
         a.setVelY(calcAngleMoveY(ang));
         a.setExplosionAnimation(new Animation(asteroidExplosionImage, 17, 5, 96, 96,
               EXPLOSION_DELAY, this));
         ast.add(a);
      }

      aliveAsteroids = calcAliveAsteroids();
      requestFocusInWindow();
   }

   public void paintComponent(Graphics g) {
      g2d = (Graphics2D) g;
      g2d.setTransform(identity);
      g2d.setPaint(Color.BLACK);
      g2d.fillRect(0, 0, getSize().width, getSize().height);
      g2d.setColor(Color.WHITE);
      g2d.drawString("Asteroids: " + aliveAsteroids, 5, 10);
      if (lives > 0)
         g2d.drawString("Lives: " + lives, 5, 20);
      else
         g2d.drawString("Lives: Game Over", 5, 20);
      drawShip();
      drawBullets();
      drawAsteroids();
   }

   /**
    * Draw the ship based on it's fields and set flags.
    */
   public void drawShip() {
      if (ship.isAlive()) {
         // draw the ship
         g2d.setTransform(identity);
         g2d.translate(ship.getX(), ship.getY());
         g2d.rotate(Math.toRadians(ship.getFaceAngle()));
         g2d.setColor(ship.getColor());
         g2d.fill(ship.getShape());
         if (showBounds) {
            g2d.setTransform(identity);
            g2d.setColor(Color.BLUE);
            g2d.draw(ship.getBounds());
         }
      } else if (ship.isExploding()) {
         ship.getExplosionAnimation().drawFrame(g2d, (int) ship.getX() - 32,
               (int) ship.getY() - 32);
         ship.incExplosionCounter();
         if (ship.getExplosionCounter() >= EXPLOSION_DELAY * 22) {
            ship.setExploding(false);
            ship.resetExplosionCounter();
            ship.setX(Asteroids.FRAMEWIDTH / 2);
            ship.setY(Asteroids.FRAMEHEIGHT / 2);
            ship.setFaceAngle(0);
            if (lives > 0) {
               ship.setAlive(true);
            } else {
               gameOver = true;
            }

         }
      }
   }

   /**
    * Draw the bullets.
    */
   public void drawBullets() {
      for (Bullet bullet : bulletsAL) {
         if (bullet.isAlive()) {
            g2d.setTransform(identity);
            g2d.translate(bullet.getX(), bullet.getY());
            g2d.setColor(bullet.getColor());
            g2d.draw(bullet.getShape());
         }
      }
   }

   /**
    * Draw the asteroids based on its fields and set flags.
    */
   public void drawAsteroids() {
      for (Asteroid a : ast) {
         if (a.isAlive()) {
            g2d.setTransform(identity);
            g2d.translate(a.getX(), a.getY());
            g2d.rotate(Math.toRadians(a.getMoveAngle()));
            g2d.setColor(a.getColor());
            g2d.fill(a.getShape());

            if (showBounds) {
               g2d.setTransform(identity);
               g2d.setColor(Color.BLUE);
               g2d.draw(a.getBounds());
            }
         } else if (a.isExploding()) {
            a.getExplosionAnimation().drawFrame(g2d, (int) a.getX() - 32,
                  (int) a.getY() - 32);
            a.incExplosionCounter();
            if (a.getExplosionCounter() >= EXPLOSION_DELAY * 16) {
               a.setExploding(false);
               a.resetExplosionCounter();
               if (aliveAsteroids == 0) {
                  winner = true;
               }
            }
         }
      }
   }

   /**
    * Updates game based on keys pressed and shapes individual update methods.
    */
   public void gameUpdate() {
      for (Integer key : keysPressed) {
         if (ship.isAlive()) {
            switch (key) {
               case KeyEvent.VK_LEFT:
               case KeyEvent.VK_RIGHT:
               case KeyEvent.VK_UP:
                  ship.moveShip(key);
                  break;

               case KeyEvent.VK_CONTROL:
               case KeyEvent.VK_ENTER:
               case KeyEvent.VK_SPACE:
                  if (canFire) {
                     bulletsAL.add(new Bullet(ship.getX(), ship.getY(),
                           ship.getFaceAngle(), ship.calcSpeed()));
                     canFire = false;
                     bulletFireRateTimer.restart();
                  }
                  break;
            }
         }
      }

      ship.updateShip(getSize().height, getSize().width);
      updateBullets();
      updateAsteroids();
      checkCollisions();
   }

   /**
    * Update the bullets based on velocity
    */
   public void updateBullets() {
      for (Bullet bullet : bulletsAL) {
         bullet.updateBullet(getSize().height, getSize().width);
      }
   }

   /**
    * Update the asteroids based on velocity
    */
   public void updateAsteroids() {
      for (Asteroid a : ast) {
         a.updateAsteroid(getSize().height, getSize().width);
      }
   }

   /**
    * Test asteroids for collisions with ship or bullets
    */
   public void checkCollisions() {
      for (Asteroid a : ast) {
         if (a.isAlive()) {
            for (Bullet bullet : bulletsAL) {
               if (bullet.isAlive()) {
                  if (a.getBounds().contains(bullet.getX(), bullet.getY())) {
                     bullet.setAlive(false);
                     a.setAlive(false);
                     a.setExploding(true);
                     aliveAsteroids = calcAliveAsteroids();
                     continue;
                  }
               }
            }

            if (a.getBounds().intersects(ship.getBounds()) && ship.isAlive()) {
               ship.setAlive(false);
               ship.setExploding(true);
               ship.setVelX(0);
               ship.setVelY(0);
               lives -= 1;
               continue;
            }
         }
      }

      for (Bullet bullet : bulletsAL) {
         if (bullet.isAlive()) {
            if (bullet.getBounds().intersects(ship.getBounds()) && ship.isAlive()) {
               bullet.setAlive(false);
               ship.setAlive(false);
               ship.setExploding(true);
               ship.setVelX(0);
               ship.setVelY(0);
               lives -= 1;
               continue;
            }
         }
      }
   }

   /**
    * calculate X movement value based on direction angle
    */
   public double calcAngleMoveX(double angle) {
      return (Math.cos(angle * Math.PI / 180));
   }

   /**
    * calculate Y movement value based on direction angle
    */
   public double calcAngleMoveY(double angle) {
      return (Math.sin(angle * Math.PI / 180));
   }

   /**
    * Calculates the asteroids that are currently alive.
    * 
    * @return Currently alive asteroids
    */
   public int calcAliveAsteroids() {
      int aliveAsteroids = 0;

      for (Asteroid a : ast) {
         if (a.isAlive())
            aliveAsteroids++;
      }
      return aliveAsteroids;
   }

   /**
    * Checks to see if the player has lost the game.
    * 
    * @return gameOver
    */
   public boolean isGameOver() {
      return gameOver;
   }

   /**
    * Checks to see if the player has won.
    * 
    * @return winner
    */
   public boolean isWinner() {
      return winner;
   }

   public void keyReleased(KeyEvent k) {
      keysPressed.remove(new Integer(k.getKeyCode()));
   }

   public void keyTyped(KeyEvent k) {
   }

   public void keyPressed(KeyEvent k) {
      if (!keysPressed.contains(new Integer(k.getKeyCode())))
         keysPressed.add(k.getKeyCode());
      if (k.getKeyCode() == KeyEvent.VK_B)
         showBounds = !showBounds;
   }
}
