package edu.truman.johnsonw;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;


/**
 * An icon that contains a movable shape.
 * @author William Ray Johnson
 */
public class ShapeIcon implements Icon
{
   private int width;
   private int height;
   private MoveableShape shape;
   
   /**
    * Constructs a shape icon.
    * @param shape the shape to be used
    * @param width the width of the shape
    * @param height the height of the shape
    */
   public ShapeIcon(MoveableShape shape, int width, int height)
   {
      this.shape = shape;
      this.width = width;
      this.height = height;
   }
   
   public int getIconWidth()
   {
      return width;
   }
   
   public int getIconHeight()
   {
      return height;
   }
   
   public void paintIcon(Component c, Graphics g, int x, int y)
   {
      Graphics2D g2 = (Graphics2D) g;
      shape.draw(g2);
   }
}