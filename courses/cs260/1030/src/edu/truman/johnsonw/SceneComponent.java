package edu.truman.johnsonw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Stores shapes and handles associated mouse events.
 * @author William Ray Johnson
 */
public class SceneComponent extends JComponent
{
   private ArrayList<ScenceShape> shapes;
   private Point mousePoint;
   
   /**
    * Constructs a scene component.
    */
   public SceneComponent()
   {
      shapes = new ArrayList<ScenceShape>();
      
      addMouseListener(new
         MouseAdapter()
         {
            public void mousePressed(MouseEvent event)
            {
               mousePoint = event.getPoint();
               for (ScenceShape s : shapes)
               {
                  if (s.contains(mousePoint))
                  {
                     s.setSelectedWhenMousePressed(s.isSelected());
                     s.setSelected(true);
                  }
               }
               repaint();
            }
            
            public void mouseClicked(MouseEvent event)
            {
               mousePoint = event.getPoint();
               for (ScenceShape s : shapes)
               {
                  if (s.contains(mousePoint) 
                     && s.wasSelectedWhenMousePressed())
                  {
                     s.setSelected(false);
                  }
               }
               repaint();
            }
         });
      
      addMouseMotionListener(new
         MouseMotionAdapter()
         {
            public void mouseDragged(MouseEvent event)
            {
               Point lastMousePoint = mousePoint;
               mousePoint = event.getPoint();
               for (ScenceShape s : shapes)
               {
                  if (s.isSelected())
                  {
                     double dx
                        = mousePoint.getX() - lastMousePoint.getX();
                     double dy 
                        = mousePoint.getY() - lastMousePoint.getY();
                     s.translate((int) dx, (int) dy);   
                  }
               }
               repaint();
            }
         });
   }
   
   /**
    * Adds a shape to the scene
    * @param s the shape to add
    */
   public void add(ScenceShape s)
   {
      shapes.add(s);
      repaint();
   }
   
   /**
    * Removes all selected shapes from the scene.
    */
   public void removeSelected()
   {
      for (int i = shapes.size() - 1; i >= 0; i--)
      {
         ScenceShape s = shapes.get(i);
         if (s.isSelected()) shapes.remove(i);
      }
      repaint();
   }
   
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      for (ScenceShape s : shapes)
      {
         s.drawNormal(g2);
         if (s.isSelected())
            s.drawSelected(g2);
      }
   }
}
