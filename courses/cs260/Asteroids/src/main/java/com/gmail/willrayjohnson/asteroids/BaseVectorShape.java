package edu.truman.johnsonw;

/**
 * Adapted by Jon Beck from Beginning Java 5 Game Programming
 * by Jonathan S. Harbour
 * Base vector shape class for game entities
 */

import java.awt.Shape;
import java.awt.Color;

/**
 * The basis of all vector shapes. Includes their general fields and methods.
 * 
 * @author William Ray Johnson
 *
 */
public class BaseVectorShape
{
   private Shape shape;
   private Color color;
   private boolean alive;
   private boolean exploding;
   private int explosionCounter;
   private double x;
   private double y;
   private double velX;
   private double velY;
   private double moveAngle;
   private double faceAngle;
   private Animation explosionAnimation;

   /**
    * Constructs a BaseVecotrShape.
    */
   public BaseVectorShape()
   {
      shape = null;
      alive = false;
      exploding = false;
      explosionCounter = 0;
      x = 0.0;
      y = 0.0;
      velX = 0.0;
      velY = 0.0;
      moveAngle = 0.0;
      faceAngle = 0.0;
   }

   /**
    * Gets the shape.
    * 
    * @return shape
    */
   public Shape getShape()
   {
      return shape;
   }

   /**
    * Sets the color of the shape.
    * 
    * @param color
    *           The desired color
    */
   public void setColor(Color color)
   {
      this.color = color;
   }

   /**
    * Gets the color of the shape.
    * 
    * @return color
    */
   public Color getColor()
   {
      return color;
   }

   /**
    * Determines if shape is alive.
    * 
    * @return alive
    */
   public boolean isAlive()
   {
      return alive;
   }

   /**
    * Set the shape to explode
    * 
    * @param e
    *           Exploding or not
    */
   public void setExploding(boolean e)
   {
      exploding = e;
   }

   /**
    * Determines if the shape is exploding
    * 
    * @return exploding
    */
   public boolean isExploding()
   {
      return exploding;
   }

   /**
    * Gets how many iterations explosion has gone through.
    * 
    * @return explosion interations
    */
   public int getExplosionCounter()
   {
      return explosionCounter;
   }

   /**
    * Increments explosion counter to keep track of how many iterations
    * explosion has gone through.
    */
   public void incExplosionCounter()
   {
      explosionCounter++;
   }

   /**
    * Resets explosion counter when explosion is finished.
    */
   public void resetExplosionCounter()
   {
      explosionCounter = 0;
   }

   /**
    * Get x value.
    * 
    * @return x position
    */
   public double getX()
   {
      return x;
   }

   /**
    * Get y value.
    * 
    * @return y position
    */
   public double getY()
   {
      return y;
   }

   /**
    * Get x velocity.
    * 
    * @return x velocity
    */
   public double getVelX()
   {
      return velX;
   }

   /**
    * Get y velocity.
    * 
    * @return y velocity
    */
   public double getVelY()
   {
      return velY;
   }

   /**
    * Gets the angle the shape is to move towards.
    * 
    * @return Move angle
    */
   public double getMoveAngle()
   {
      return moveAngle;
   }

   /**
    * Gets the angle the shape is facing.
    * 
    * @return Face angle
    */
   public double getFaceAngle()
   {
      return faceAngle;
   }

   /**
    * Sets the shape the shape will take on.
    * 
    * @param shape
    *           the shape's shape
    */
   public void setShape(Shape shape)
   {
      this.shape = shape;
   }

   /**
    * Sets the value of alive.
    * 
    * @param alive
    */
   public void setAlive(boolean alive)
   {
      this.alive = alive;
   }

   /**
    * Sets the value of x.
    * 
    * @param x
    *           New position
    */
   public void setX(double x)
   {
      this.x = x;
   }

   /**
    * Increments x by inputed velocity.
    * 
    * @param i
    *           Velocity
    */
   public void incX(double i)
   {
      x += i;
   }

   /**
    * Sets the value of y.
    * 
    * @param y
    *           New position
    */
   public void setY(double y)
   {
      this.y = y;
   }

   /**
    * Increments y by inputed velocity.
    * 
    * @param i
    *           Velocity
    */
   public void incY(double i)
   {
      y += i;
   }

   /**
    * Sets the velocity of the X direction.
    * 
    * @param velX
    *           New velocity
    */
   public void setVelX(double velX)
   {
      this.velX = velX;
   }

   /**
    * Increments velocity of the X direction.
    * 
    * @param i
    *           Velocity added
    */
   public void incVelX(double i)
   {
      velX += i;
   }

   /**
    * Sets the velocity of the Y direction.
    * 
    * @param velY
    *           New velocity
    */
   public void setVelY(double velY)
   {
      this.velY = velY;
   }

   /**
    * Increments velocity of the Y direction.
    * 
    * @param i
    *           Velocity added
    */
   public void incVelY(double i)
   {
      velY += i;
   }

   /**
    * Sets the face angle of shape.
    * 
    * @param angle
    *           New face angle
    */
   public void setFaceAngle(double angle)
   {
      faceAngle = angle;
   }

   /**
    * Increments face angle by inputed degree.
    * 
    * @param i
    *           Degree
    */
   public void incFaceAngle(double i)
   {
      faceAngle += i;
   }

   /**
    * Sets the move angle of the shape.
    * 
    * @param angle
    *           New move angle
    */
   public void setMoveAngle(double angle)
   {
      moveAngle = angle;
   }

   /**
    * Increments move angle by inputed degree.
    * 
    * @param i
    *           Degree
    */
   public void incMoveAngle(double i)
   {
      moveAngle += i;
   }

   /**
    * Sets up a new explosion animation.
    * 
    * @param a
    *           New animation
    */
   public void setExplosionAnimation(Animation a)
   {
      explosionAnimation = a;
   }

   /**
    * Gets the explosion animation.
    * 
    * @return explosion animation
    */
   public Animation getExplosionAnimation()
   {
      return explosionAnimation;
   }

   /**
    * Calculates the "speed" of the shape by calcuating the distance between the
    * current location and after coordinates are incremented.
    * 
    * @return distance or "speed" of shape
    */
   public double calcSpeed()
   {
      return Math
         .sqrt(Math.pow((x - (x + velX)), 2) + Math.pow((y - (y + velY)), 2));
   }

   /**
    * Calculates the speed the shape will have after current iteration.
    * 
    * @param nextVelX
    *           The next x velocity
    * @param nextVelY
    *           The next y velocity
    * @return Next speed
    */
   public double calcNextSpeed(double nextVelX, double nextVelY)
   {
      nextVelX += velX;
      nextVelY += velY;

      double nextX = x + nextVelX;
      double nextY = y + nextVelY;

      return Math.sqrt(Math.pow((nextX - (nextX + nextVelX)), 2)
         + Math.pow((nextY - (nextY + nextVelY)), 2));
   }

   /**
    * calculate X movement value based on direction angle.
    * 
    * @param Angle
    *           shape is moving
    * @return x movement
    */
   public double calcAngleMoveX(double angle)
   {
      return (Math.cos(angle * Math.PI / 180));
   }

   /**
    * calculate Y movement value based on direction angle.
    * 
    * @angle Angle shape is moving
    * @return y movement
    */
   public double calcAngleMoveY(double angle)
   {
      return (Math.sin(angle * Math.PI / 180));
   }

}
