package edu.truman.johnsonw;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * A car that can be moved around.
 * 
 * @author William Ray Johnson
 */
public class CarShape extends AbstractSceneShape
{
   private int width;

   private static final int WHEEL_DIAMETER_FACTOR = 6;
   private static final int BODY_AND_BOTTOM_OF_WINDSHIELD_Y_POSITION = 6;
   private static final int FRONTTIRE_BOTTOM_FRONT_WINDSHIELD_X_POSITION = 6;
   private static final int REARTIRE_TOP_REAR_WINDSHIELD_X_POSITION = 3;
   private static final int TIRE_Y_POSITION = 3;
   private static final int TOP_FRONT_WINDSHIELD_X_POSITION = 3;
   private static final int BODY_HEIGHT_FACTOR = 6;
   private static final int REARTIRE_REAR_WINDSHIELD_FACTOR = 2;
   private static final int BOTTOM_REAR_WINDSHILED_FACTOR = 5;
   private static final int BOTTOM_REAR_WINDSHILED_X_POSITION = 6;

   /**
    * Constructs a car shape.
    * 
    * @param x
    *           the left of the bounding rectangle
    * @param y
    *           the top of the bounding rectangle
    * @param width
    *           the width of the bounding rectangle
    */
   public CarShape(int x, int y, int width)
   {
      super(x, y);
      this.width = width;
   }

   public void drawNormal(Graphics2D g2)
   {
      Rectangle2D.Double body = new Rectangle2D.Double(getX(),
         getY() + width / BODY_AND_BOTTOM_OF_WINDSHIELD_Y_POSITION, width,
         width / BODY_HEIGHT_FACTOR);
      Ellipse2D.Double frontTire = new Ellipse2D.Double(
         getX() + width / FRONTTIRE_BOTTOM_FRONT_WINDSHIELD_X_POSITION,
         getY() + width / TIRE_Y_POSITION, width / WHEEL_DIAMETER_FACTOR,
         width / WHEEL_DIAMETER_FACTOR);
      Ellipse2D.Double rearTire = new Ellipse2D.Double(
         getX() + width * REARTIRE_REAR_WINDSHIELD_FACTOR
            / REARTIRE_TOP_REAR_WINDSHIELD_X_POSITION,
         getY() + width / TIRE_Y_POSITION, width / WHEEL_DIAMETER_FACTOR,
         width / WHEEL_DIAMETER_FACTOR);

      // The bottom of the front windshield
      Point2D.Double r1 = new Point2D.Double(
         getX() + width / FRONTTIRE_BOTTOM_FRONT_WINDSHIELD_X_POSITION,
         getY() + width / BODY_AND_BOTTOM_OF_WINDSHIELD_Y_POSITION);
      // The front of the roof
      Point2D.Double r2 = new Point2D.Double(
         getX() + width / TOP_FRONT_WINDSHIELD_X_POSITION, getY());
      // The rear of the roof
      Point2D.Double r3 = new Point2D.Double(
         getX() + width * REARTIRE_REAR_WINDSHIELD_FACTOR
            / REARTIRE_TOP_REAR_WINDSHIELD_X_POSITION,
         getY());
      // The bottom of the rear windshield
      Point2D.Double r4 = new Point2D.Double(
         getX() + width * BOTTOM_REAR_WINDSHILED_FACTOR
            / BOTTOM_REAR_WINDSHILED_X_POSITION,
         getY() + width / BODY_AND_BOTTOM_OF_WINDSHIELD_Y_POSITION);

      Line2D.Double frontWindshield = new Line2D.Double(r1, r2);
      Line2D.Double roofTop = new Line2D.Double(r2, r3);
      Line2D.Double rearWindshield = new Line2D.Double(r3, r4);

      g2.fill(body);
      g2.fill(frontTire);
      g2.fill(rearTire);
      g2.draw(frontWindshield);
      g2.draw(roofTop);
      g2.draw(rearWindshield);
   }

   public boolean contains(Point2D aPoint)
   {
      return getX() <= aPoint.getX() && aPoint.getX() <= getX()
         + width && getY() <= aPoint.getY() && aPoint.getY() <= getY()
            + width / BODY_HEIGHT_FACTOR
            + width / BODY_AND_BOTTOM_OF_WINDSHIELD_Y_POSITION
            + width / WHEEL_DIAMETER_FACTOR;
   }
}
