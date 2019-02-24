package com.gmail.willrayjohnson.asteroids;

import java.awt.Color;
/**
 * Adapted by Jon Beck from Beginning Java 5 Game Programming
 * by Jonathan S. Harbour
 * Ship class derives from BaseVectorShape
 */
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * Ship class derives from BaseVectorShape
 * 
 * @author William Ray Johnson
 */
public class Ship extends BaseVectorShape {
   private int topShipSpeed;
   private int[] newShipX = { -6, -5, -4, -3, 3, 4, 5, 6, 6, 4, 3, 3, 0, -3, -3, -4, -6 };
   private int[] newShipY = { 6, 7, 7, 6, 6, 7, 7, 6, -1, -3, -1, -5, -8, -5, -1, -3,
         -1 };

   /**
    * Constucts a yellow ship.
    */
   public Ship() {
      setShape(new Polygon(newShipX, newShipY, newShipX.length));
      setAlive(true);
      setColor(Color.YELLOW);
   }

   /**
    * Gets the bounds of the ship.
    * 
    * @return The bounds as a Rectangle
    */
   public Rectangle getBounds() {
      return new Rectangle((int) getX() - 6, (int) getY() - 6, 12, 12);
   }

   /**
    * Sets top speed of the ship.
    * 
    * @param speed The top speed
    */
   public void setTopShipSpeed(int speed) {
      topShipSpeed = speed;
   }

   /**
    * Gets the top speed of ship.
    * 
    * @return Top speed of ship
    */
   public int getTopShipSpeed() {
      return topShipSpeed;
   }

   /**
    * Moves ship based on what key is passed to ship.
    * 
    * @param keyPressed A key that is currently pressed
    */
   public void moveShip(int keyPressed) {
      switch (keyPressed) {

         case KeyEvent.VK_LEFT:
            incFaceAngle(-5);
            if (getFaceAngle() < 0)
               setFaceAngle(360 - 5);
            break;

         case KeyEvent.VK_RIGHT:
            incFaceAngle(5);
            if (getFaceAngle() > 360)
               setFaceAngle(5);
            break;

         case KeyEvent.VK_UP:
            setMoveAngle(getFaceAngle() - 90);
            if (calcSpeed() < topShipSpeed
                  && calcNextSpeed(calcAngleMoveX(getMoveAngle()) * 0.1,
                        calcAngleMoveY(getMoveAngle()) * 0.1) < topShipSpeed) {
               incVelX(calcAngleMoveX(getMoveAngle()) * 0.1);
               incVelY(calcAngleMoveY(getMoveAngle()) * 0.1);
            }
            break;
      }
   }

   /**
    * Updates the position of the ship.
    * 
    * @param height Height of component
    * @param width Width of component
    */
   public void updateShip(int height, int width) {
      incX(getVelX());
      if (getX() < -10) {
         setX(width + 10);
      } else if (getX() > width + 10) {
         setX(-10);
      }

      incY(getVelY());
      if (getY() < -10) {
         setY(height + 10);
      } else if (getY() > height + 10) {
         setY(-10);
      }
   }
}