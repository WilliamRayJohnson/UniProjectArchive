package edu.truman.johnsonw;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * A shape that can be place in a scene.
 * @author William Ray Johnson
 */
public interface ScenceShape
{
   /**
    * Sets selcted state of shape
    * @param b value of selected
    */
   void setSelected(boolean b);
   
   /**
    * Returns whether shape is selected.
    */
   boolean isSelected();
   
   /**
    * Draws the shape when not selected.
    * @param g2 the graphics context
    */
   void drawNormal(Graphics2D g2);
   
   /**
    * Draws the shape when selected.
    * @param g2 the graphics context
    */
   void drawSelected(Graphics2D g2);
   
   /**
    * Moves the shape by a given amount
    * @param dx the amount to translate in x-direction
    * @param dy the amount to translate in y-direction
    */
   void translate(int dx, int dy);
   
   /**
    * Determines if the mouse is within the shape
    * @param aPoint the location of the mouse
    */
   boolean contains(Point2D aPoint);
   
   /**
    * Returns whether shape was selected when mouse was 
    * last pressed on this shape.
    */
   boolean wasSelectedWhenMousePressed();
   
   /**
    * Set selected state when mouse was last pressed this shape.
    * @param selectedWhenMousePressed
    */
   void setSelectedWhenMousePressed(boolean selectedWhenMousePressed);
}
