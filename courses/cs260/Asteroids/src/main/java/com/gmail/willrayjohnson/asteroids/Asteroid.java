package com.gmail.willrayjohnson.asteroids;

import java.awt.Color;

/**
 * Adapted by Jon Beck from Beginning Java 5 Game Programming
 * by Jonathan S. Harbour
 */

import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * Asteroid class derives from BaseVectorShape.
 * 
 * @author William Ray Johnson
 */
public class Asteroid extends BaseVectorShape {
   // define the asteroid polygon shape
   private int[] astx = { -20, -13, 0, 20, 22, 20, 12, 2, -10, -22, -16 };
   private int[] asty = { 20, 23, 17, 20, 16, -20, -22, -14, -17, -20, -5 };

   /**
    * Constructs a brown asteroid.
    */
   public Asteroid() {
      setShape(new Polygon(astx, asty, astx.length));
      setAlive(true);
      setColor(new Color(160, 82, 45));
   }

   /**
    * Gets the bounds of the asteroid.
    * 
    * @return The bounds as a Rectangle
    */
   public Rectangle getBounds() {
      return new Rectangle((int) getX() - 20, (int) getY() - 20, 40, 40);
   }

   /**
    * Updates the position of the asteroid
    * 
    * @param height Height of the component
    * @param width Width of the component
    */
   public void updateAsteroid(int height, int width) {
      if (isAlive()) {
         incX(getVelX());
         if (getX() < -20) {
            setX(width + 20);
         } else if (getX() > width + 20) {
            setX(-20);
         }

         incY(getVelY());
         if (getY() < -20) {
            setY(height + 20);
         } else if (getY() > height + 20) {
            setY(-20);
         }
      }
   }
}
