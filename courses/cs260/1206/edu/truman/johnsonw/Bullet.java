package edu.truman.johnsonw;

import java.awt.Color;

/**
 * Adapted by Jon Beck from Beginning Java 5 Game Programming
 * by Jonathan S. Harbour
 */

import java.awt.Rectangle;

/**
 * Bullet class derives from BaseVectorShape
 * 
 * @author William Ray Johnson
 */
public class Bullet extends BaseVectorShape
{
   private double bulletSpeed;

   public static final int BULLET_SPEED = 6;

   /**
    * Constructs a red bullet based on ships position and speed
    * 
    * @param shipX
    *           X coordinate of ship
    * @param shipY
    *           Y coordinate of ship
    * @param shipFaceAngle
    *           The angle the ship is facing
    * @param shipSpeed
    *           Ship Speed
    */
   public Bullet(double shipX, double shipY, double shipFaceAngle,
      double shipSpeed)
   {
      setShape(new Rectangle(-1, 1, 2, 2));
      setAlive(true);
      setMoveAngle(shipFaceAngle - 90);
      setBulletSpeed(shipSpeed);
      calcVelX();
      calcVelY();
      setX(shipX);
      setY(shipY);
      setColor(Color.RED);
   }

   /**
    * Sets x to ships x then increments it so the bullet is out of the ship's
    * bounding box.
    * 
    * @param x
    *           Ship's x position
    */
   public void setX(double x)
   {
      super.setX(x);
      incX(getVelX());
   }

   /**
    * Sets y to ships y then increments it so the bullet is out of the ship's
    * bounding box.
    * 
    * @param y
    *           Ship's y position
    */
   public void setY(double y)
   {
      super.setY(y);
      incY(getVelY());
   }

   /**
    * Calculates this bullet's speed by adding the ship's speed to the speed of
    * a bullet
    * 
    * @param shipSpeed
    *           The speed of the ship.
    */
   public void setBulletSpeed(double shipSpeed)
   {
      bulletSpeed = shipSpeed + BULLET_SPEED;
   }

   /**
    * Gets the bounds of the bullet.
    * 
    * @return The bounds as a Rectangle
    */
   public Rectangle getBounds()
   {
      return new Rectangle((int) getX(), (int) getY(), 2, 2);
   }

   /**
    * Calculates X velocity by solving for x where x = bulletSpeed *
    * sin(moveAngle)/tan(moveAngle)
    */
   public void calcVelX()
   {
      setVelX(bulletSpeed * Math.sin(getMoveAngle() * Math.PI / 180)
         / Math.tan(getMoveAngle() * Math.PI / 180));
   }

   /**
    * Calculate Y velocity by solving for y where y = bulletSpeed *
    * cos(moveAngle) * tan(moveAngle)
    */
   public void calcVelY()
   {
      setVelY(bulletSpeed * Math.cos(getMoveAngle() * Math.PI / 180)
         * Math.tan(getMoveAngle() * Math.PI / 180));
   }

   /**
    * Updates the position of the bullet.
    * 
    * @param height
    *           Height of component
    * @param width
    *           Width of component
    */
   public void updateBullet(int height, int width)
   {
      if (isAlive())
      {
         incX(getVelX());
         if (getX() < 0 || getX() > width)
         {
            setAlive(false);
         }

         incY(getVelY());
         if (getY() < 0 || getY() > height)
         {
            setAlive(false);
         }
      }
   }
}
