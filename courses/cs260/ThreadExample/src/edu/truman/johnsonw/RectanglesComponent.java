package edu.truman.johnsonw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;

import javax.swing.JComponent;
/**
 *  This component holds and displays moving rectangles
 *  @author Jon Beck
 */
@SuppressWarnings("serial")
public class RectanglesComponent extends JComponent 
{
    private static final int WIDTH = 20;
    private static final int HEIGHT = 30;
    private ArrayList<MovingRectangle> rList;

    /**
     * Construct a component that can hold rectangles
     */
    public RectanglesComponent() 
    {
        rList = new ArrayList<MovingRectangle>();
    }

    public void add(MovingRectangle r)
    {
        rList.add(r);
    }

    /**
     * The standard paintComponent method.  Outlines a rectangle in black
     * and fills it with its color
     * @param g the graphics context
     */
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for(MovingRectangle rectangle : rList)
        {
            g2.setColor(rectangle.getColor());
            g2.fill(rectangle);
            g2.setColor(Color.BLACK);
            g2.draw(rectangle);
        }
    }

    /**
     * Called by the timer listener.  Fixes up location, detects the edge of
     * the frame, and reverses direction when the frame edge is reached.
     * @param frameWidth horizontal extent of the frame
     * @param frameHeight vertical extent of the frame
     */
    public void doTick(int frameWidth, int frameHeight) 
    {
        for(MovingRectangle r : rList)
        {
            // see if it's hit the right or left edges
            if (r.getX() + r.getWidth() >= frameWidth)
            {
                r.setLocation((int)(frameWidth - r.getWidth()), (int)r.getY());
                r.clearMovingRight();
            }
            else if (r.getX() <= 0)
            {
                r.setMovingRight();
            }

            if (r.movingRight())
            {
                r.translate(1, 0);
            }
            else
            {
                r.translate(-1, 0);
            }
        }
    }
}
