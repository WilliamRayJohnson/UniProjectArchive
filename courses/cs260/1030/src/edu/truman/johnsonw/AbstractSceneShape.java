package edu.truman.johnsonw;

import java.awt.Graphics2D;

/**
 * The methods and fields of all SceneShapes.
 * @author William Ray Johnson
 */
public abstract class AbstractSceneShape implements ScenceShape
{
   private boolean selectedWhenMousePressed;
   private boolean selected;
   private int x;
   private int y;
   
   /**
    * Constructs an AbstarctSceneShape
    * @param x the left of the bounding rectangle
    * @param y the top of the bounding rectangle
    */
   public AbstractSceneShape(int x, int y)
   {
      this.setX(x);
      this.setY(y);
   }
   
   public void setSelected(boolean b)
   {
      selected = b;
   }
   
   public boolean isSelected()
   {
      return selected;
   }
   
   public void drawSelected(Graphics2D g2)
   {
      translate(1, 1);
      drawNormal(g2);
      translate(1, 1);
      drawNormal(g2);
      translate(-2, -2);
   }
   
   public void translate(int dx, int dy)
   {
      x = x + dx;
      y = y + dy;
   }
   
   /**
    * Gets value of x.
    */
   public int getX()
   {
      return x;
   }

   /**
    * Sets value of x.
    * @param x new x value
    */
   public void setX(int x)
   {
      this.x = x;
   }

   /**
    * Gets value of y.
    */
   public int getY()
   {
      return y;
   }

   /**
    * Sets value of y.
    * @param y new y value
    */
   public void setY(int y)
   {
      this.y = y;
   }

   public boolean wasSelectedWhenMousePressed()
   {
      return selectedWhenMousePressed;
   }

   public void setSelectedWhenMousePressed(boolean selectedWhenMousePressed)
   {
      this.selectedWhenMousePressed = selectedWhenMousePressed;
   }
}
