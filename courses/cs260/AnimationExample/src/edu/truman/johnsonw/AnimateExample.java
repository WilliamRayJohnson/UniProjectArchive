package edu.truman.johnsonw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

/*
 * Driver program to show how to use the Animation class
 */
public class AnimateExample extends JComponent 
{
    private static int FWIDTH = 1000;
    private static int FHEIGHT = 800;
    private JFrame frame;
    private Image background;
    private Image ballImage;
    private Animation ball1;
    private Animation ball2;
    private long ticker;
    
    /**
     * Run the program
     * @param args command line arguments (none expected)
     */
    public static void main(String[] args) throws InterruptedException
    {
        new AnimateExample().go();
    }

    /**
     *	Set things up, make a couple of animations and put them at
     *	various locations
     */
    public AnimateExample()
    {
        frame = new JFrame("Animation Example");
        frame.setSize(FWIDTH, FHEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit tk = Toolkit.getDefaultToolkit();
        background = tk.createImage("bluespacecurved.jpg");
        ballImage = tk.createImage("xball32.png");
        ball1 = new Animation(ballImage, 32, 8, 64, 64, 4, this);
        ball2 = new Animation(ballImage, 32, 8, 64, 64, 1, this);
        frame.add(this);
        frame.setVisible(true);
        ticker = 0L;
    }

    /**
     *	Start the animations running
     */
    public void go() throws InterruptedException
    {
        while(true)
        {
            repaint();
            Thread.sleep(1);
        }
    }
 
    /**
     * This is what to do when we're told to repaint ourselves
     * @param g the graphics context
     */
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background, 0, 0, FWIDTH-1, FHEIGHT-1, this);

        ball1.drawFrame(g2, 300, 300);
        ball2.drawFrame(g2, 600, 300);
    }
}
