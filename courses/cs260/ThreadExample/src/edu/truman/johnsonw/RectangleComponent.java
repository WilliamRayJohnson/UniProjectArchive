package edu.truman.johnsonw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.util.Random;

/**
 *  This component displays a rectangle that can be moved.
 *  @author Jon Beck
 */
public class RectangleComponent extends JComponent 
{
    private static final long serialVersionUID = 1L;
    private static final int BOX_WIDTH = 20;
    private static final int BOX_HEIGHT = 30;
    private static final Random rng = new Random();
    private MovingRectangle box;

    /**
     * Construct a component to hold a rectangle
     */
    public RectangleComponent() 
    {
        int x = rng.nextInt(RectangleMoverMultiple.FRAME_WIDTH);
        int y = rng.nextInt(RectangleMoverMultiple.FRAME_HEIGHT);
        box = new MovingRectangle(x, y);
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
        g2.setColor(box.getColor());
        g2.fill(box);
        g2.setColor(Color.BLACK);
        g2.draw(box);
    }

    /**
     * Called by the timer listener.  Fixes up location, detects the edge of
     * the frame, and reverses direction when the frame edge is reached.
     * @param frameWidth horizontal extent of the frame
     * @param frameHeight vertical extent of the frame
     */
    public void doTick(int frameWidth, int frameHeight) 
    {
        // see if it's hit the right or left edges
        if (box.getX() + box.getWidth() >= frameWidth)
        {
            box.setLocation(frameWidth - (int)box.getWidth(), (int)box.getY());
            box.clearMovingRight();
        }
        else if (box.getX() <= 0)
        {
            box.setMovingRight();
        }

        if (box.movingRight())
        {
            box.translate(1, 0);
        }
        else
        {
            box.translate(-1, 0);
        }
    }
        
    /**
     * Reverse the direction of the rectangle
     */
    public void reverseDirection() 
    {
        box.reverseDirection();
    }

    /**
     * Translate a random rectangle's position to the specified location
     * @param x the x coordinate of the new location
     * @param y the y coordinate of the new location
     */
    public void moveTo(int x, int y) 
    {
        box.setLocation(x - (int)box.getWidth() / 2, 
                        y - (int)box.getHeight() / 2);
    }
}
