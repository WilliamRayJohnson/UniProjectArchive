package edu.truman.johnsonw;

import java.awt.Rectangle;
import java.awt.Color;
import java.util.Random;

/**
 * A rectangle that has a fill color and a moving direction
 * @author Jon Beck
 */
@SuppressWarnings("serial")
public class MovingRectangle extends Rectangle implements Runnable
{
    private Color color;
    private boolean movingRight;
    public static final int HEIGHT = 30;
    public static final int WIDTH = 20;
    private int delay;

    /**
     * Create the rectangle with specified attributes
     * @param x top-left x coordinate
     * @param y top-left y coordinate
     */
    public MovingRectangle(int x, int y)
    {
        super(x, y, WIDTH, HEIGHT);
        Random rng = new Random();
        movingRight = rng.nextInt(2) == 0 ? false : true;
        color = new Color
            (rng.nextInt(255), rng.nextInt(255), rng.nextInt(255));
        delay = rng.nextInt(50);
    }

    public void run()
    {
        try
        {
            while(true)
            {
                if(getX() + getWidth() >= RectangleMoverMultiple.FRAME_WIDTH)
                {
                    setLocation(RectangleMoverMultiple.FRAME_WIDTH - (int)getWidth(),
                                (int)getY());
                    clearMovingRight();
                }
                else if(getX() <= 0)
                {
                    setMovingRight();
                }

                if(movingRight())
                {
                    translate(1, 0);
                }
                else
                {
                    translate(-1 ,0);
                }
                Thread.sleep(delay);
            }
        }
        catch(InterruptedException exception) 
        {
            // we're done
        }
    }
            

    /**
     * Color getter
     * @return the fill color of the rectangle
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Change the direction this rectangle is moving
     */
    public void reverseDirection()
    {
        movingRight = movingRight ? false : true;
    }

    /**
     * Set the direction to move left
     */
    public void clearMovingRight()
    {
        movingRight = false;
    }

    /**
     * Set the direction to move right
     */
    public void setMovingRight()
    {
        movingRight = true;
    }

    /**
     * Determine which direction the rectangle is moving
     * @return the boolean status of moving to the right
     */
    public boolean movingRight()
    {
        return movingRight;
    }

    /**
     * Generate a string representation of this rectangle
     * @return the string representation
     */
    public String toString()
    {
        return super.toString() + "color:" + color;
    }
}
