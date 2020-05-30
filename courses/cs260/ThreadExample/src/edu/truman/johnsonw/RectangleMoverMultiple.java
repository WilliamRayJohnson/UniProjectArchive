package edu.truman.johnsonw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

/**
 * Creates a frame with a specified number of rectangles moving back and forth.
 * When a mouse press is detected it moves a randomly chosen rectangle to the
 * coordinates of the press and reverses its direction.
 * @author Jon Beck
 */
public class RectangleMoverMultiple
{
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;
    private JFrame frame;
    private RectanglesComponent rComp;
    private JPanel panel;
    private static final Random rng = new Random();

    /**
     * Instantiate a gui with a certain number of rectangles
     * @param args
     */
    public static void main(String[] args)
    { 
        if(args.length == 0)
        {
            System.out.println("Usage: java RectangleMoverMultiple n");
            System.exit(1);
        }
        else
        {
            int numberOfRectangles = Integer.parseInt(args[0]);
            new RectangleMoverMultiple(numberOfRectangles).go();
        }
    }

    /**
     * Make a frame, a panel, and a component holding the specified
     * number of rectangles.
     * @param numberOfRectangles the number of rectangles to place on
     * the panel
     */
    public RectangleMoverMultiple(int numberOfRectangles)
    {
        frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Traveling Rectangles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        rComp = new RectanglesComponent();
        for(int i = 0; i < numberOfRectangles; i++)
        {
            MovingRectangle r = new MovingRectangle(rng.nextInt(FRAME_WIDTH),
                                                    rng.nextInt(FRAME_HEIGHT));
            rComp.add(r);
            new Thread(r).start();
        }

        panel.add(rComp);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    /**
     * Run the gui with a timer and a mouse listener
     */
    public void go()
    {
        rComp.addMouseListener(new MouseAdapter() 
        {
            /**
             * Get the coordinates of a mousePressed event and
             * tell the component about it
             *
             * @param event the mouse event
             */
            public void mousePressed(MouseEvent event)
            {
                MovingRectangle r = new MovingRectangle
                    (event.getX() - MovingRectangle.WIDTH / 2, 
                     event.getY() - MovingRectangle.HEIGHT / 2);
                rComp.add(r);
                new Thread(r).start();
            }
        });

        // The timer listener can be an anonymous class
        ActionListener listener = new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) {
                rComp.repaint();
            }
        };

        final int DELAY = 5; // Milliseconds between timer ticks
        new Timer(DELAY, listener).start();
    }
}
