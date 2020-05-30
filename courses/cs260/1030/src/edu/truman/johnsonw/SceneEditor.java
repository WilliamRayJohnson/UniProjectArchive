package edu.truman.johnsonw;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This program creates a scene where cars and houses can be added, selected,
 * and removed.
 * 
 * @author William Ray Johnson
 */
public class SceneEditor
{
   private JFrame frame;
   private SceneComponent scene;
   private JButton houseButton;
   private JButton carButton;
   private JButton removeButton;
   private JPanel buttons;

   private static final int INITIAL_X_LOCATION = 20;
   private static final int INITIAL_Y_LOCATION = 20;
   private static final int SHAPE_WIDTH = 50;
   private static final int FRAME_WIDTH = 300;
   private static final int FRAME_HEIGHT = 300;

   /**
    * Constructs a SceneEditor.
    */
   public SceneEditor()
   {
      frame = new JFrame();
      scene = new SceneComponent();
      houseButton = new JButton("House");
      carButton = new JButton("Car");
      removeButton = new JButton("Remove");
      buttons = new JPanel();
   }

   /**
    * Builds the GUI and runs the scene.
    */
   public void run()
   {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      houseButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent event)
         {
            scene.add(new HouseShape(INITIAL_X_LOCATION, INITIAL_Y_LOCATION,
               SHAPE_WIDTH));
         }
      });

      carButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent event)
         {
            scene.add(new CarShape(INITIAL_X_LOCATION, INITIAL_Y_LOCATION,
               SHAPE_WIDTH));
         }
      });

      removeButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent event)
         {
            scene.removeSelected();
         }
      });

      buttons.add(houseButton);
      buttons.add(carButton);
      buttons.add(removeButton);

      frame.add(scene, BorderLayout.CENTER);
      frame.add(buttons, BorderLayout.NORTH);
      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      //frame.pack();
      frame.setVisible(true);
   }

   public static void main(String[] args)
   {
      new SceneEditor().run();
   }
}
