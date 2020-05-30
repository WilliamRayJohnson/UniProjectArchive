package edu.truman.johnsonw;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * A house that can be moved around.
 * @author William Ray Johnson
 */
public class HouseShape extends AbstractSceneShape
{
   private int width;
   
   /**
    * Constructs a house shape.
    * @param x the left of the bounding rectangle
    * @param y the top of the bounding rectangle
    * @param width the width of the bounding rectangle
    */
   public HouseShape(int x, int y, int width)
   {
      super(x, y);
      this.width = width;
   }
   
   public void drawNormal(Graphics2D g2)
   {
      Rectangle2D.Double base = new Rectangle2D.Double(getX(), getY() 
         + width, width, width);
      
      Point2D.Double r1 = new Point2D.Double(getX(), getY() + width);
      
      Point2D.Double r2 = new Point2D.Double(getX() + width / 2, getY());
      
      Point2D.Double r3 = new Point2D.Double(getX() + width, getY() + width);
      
      Line2D.Double roofLeft = new Line2D.Double(r1, r2);
      Line2D.Double roofRight = new Line2D.Double(r2, r3);
      
      g2.draw(base);
      g2.draw(roofLeft);
      g2.draw(roofRight);
   }
   
   public boolean contains(Point2D p)
   {
      return getX() <= p.getX() && p.getX() <= getX() 
         + width && getY() <= p.getY() && p.getY() <= getY() + width + width;
   }
}

