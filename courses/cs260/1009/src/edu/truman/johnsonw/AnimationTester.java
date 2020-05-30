package edu.truman.johnsonw;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This program implements an animation that moves a car shape.
 * @author William Ray Johnson
 */
public class AnimationTester
{   
   private JFrame frame;
   private JPanel outerPanel;
   private JPanel innerPanel;
   private JPanel innerSouthPanel;
   private MoveableShape shape;
   private ShapeIcon icon;
   private JLabel label;
   private JLabel sliderLabel;
   private JSlider sizeSlider;
   private JButton pauseButton;
   
   
   private static final int ICON_WIDTH = 1000;
   private static final int ICON_HEIGHT = 400;
   private static final int CAR_WIDTH = 150;
   private static final int Y_VELOCITY = 0;
   private static final int X_VELOCITY = 1;
   private static final int INITIAL_X_POSITION = 0;
   private static final int INITIAL_Y_POSITION = 10;
   private static final int MINIMUM_SLIDER_VALUE = 50;
   private static final int MAXIMUM_SLIDER_VALUE = 600;
   private static final int MAJOR_TICK_SPACING = 50;
   private static final int MINOR_TICK_SPACING = 10;
   
   /**
    * Consturcts an AnimationTester.
    */
   public AnimationTester()
   {
      this.frame = new JFrame();
      this.outerPanel = new JPanel(new BorderLayout());
      this.innerPanel = new JPanel(new BorderLayout());
      this.innerSouthPanel = new JPanel();
      this.shape = new CarShape(INITIAL_X_POSITION, INITIAL_Y_POSITION
         , CAR_WIDTH);
      this.icon = new ShapeIcon(shape, ICON_WIDTH, ICON_HEIGHT);
      this.label = new JLabel(icon);
      this.sliderLabel = new JLabel("Size");
      this.sizeSlider = new JSlider(JSlider.HORIZONTAL, MINIMUM_SLIDER_VALUE
         , MAXIMUM_SLIDER_VALUE, CAR_WIDTH);
      this.pauseButton = new JButton("Start");
   }
   
   /**
    * Builds the GUI and runs the animation.
    */
   public void run()
   {
      sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      innerSouthPanel.setLayout(new BoxLayout(innerSouthPanel
         , BoxLayout.Y_AXIS));
      
      sizeSlider.setMajorTickSpacing(MAJOR_TICK_SPACING);
      sizeSlider.setMinorTickSpacing(MINOR_TICK_SPACING);
      sizeSlider.setPaintTicks(true);
      sizeSlider.setSnapToTicks(true);
      sizeSlider.setPaintLabels(true);
      
      frame.add(outerPanel);
      outerPanel.add(label, BorderLayout.NORTH);
      outerPanel.add(sizeSlider, BorderLayout.SOUTH);
      outerPanel.add(innerPanel, BorderLayout.CENTER);
      innerPanel.add(innerSouthPanel, BorderLayout.SOUTH);
      innerSouthPanel.add(pauseButton);
      innerSouthPanel.add(sliderLabel);
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   
      final int DELAY = 100;
      // milliseconds between timer ticks
      final Timer t = new Timer(DELAY, 
            new ActionListener()
            {
               public void actionPerformed(ActionEvent event)
               {
                  shape.translate(X_VELOCITY, Y_VELOCITY);
                  label.repaint();
               }
            });
      
      pauseButton.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent evt)
               {
                  if (pauseButton.getText().equals("Start"))
                  {
                     t.start();
                     pauseButton.setText("Pause");
                  }
                  else if (pauseButton.getText().equals("Pause"))
                  {
                     t.stop();
                     pauseButton.setText("Resume");
                  }
                  else if (pauseButton.getText().equals("Resume"))
                  {
                     t.start();
                     pauseButton.setText("Pause");
                  }
               }
            });
      
      sizeSlider.addChangeListener(
            new ChangeListener()
             {
                 public void stateChanged(ChangeEvent e) 
                 {
                     JSlider source = (JSlider)e.getSource();
                     int size = (int)source.getValue();
                     CarShape car = (CarShape)shape;
                     car.setSize(size);
                     label.repaint();
                 }
             });
   }
   
   public static void main(String[] args)
   {
      new AnimationTester().run();
   }
}