package com.gmail.willrayjohnson.asteroids;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * A class for a continuous-loop animation at a settable location
 * 
 * @author Jon Beck
 */
public class Animation {
   private Image image;
   private int columns;
   private int imgWidth;
   private int imgHeight;
   private int frameCounter;
   private int frameTicker;
   private JComponent component;
   private int frames;
   private int delay;
   private AffineTransform identity;

   /**
    * Create a new animation. In this javadoc, "frame" means a frame of the image, not the
    * java window frame.
    * 
    * @param image the image from which this animation's bitmaps are drawn
    * @param frames how many frames in the image
    * @param columns how many columns of frames in the image
    * @param imgWidth the width in pixels of one frame
    * @param imgHeight the height in pixels of one frame
    * @param delay how many cycles before we move to the next frame
    * @param component where we're drawing the frame
    */
   public Animation(Image image, int frames, int columns, int imgWidth, int imgHeight,
         int delay, JComponent component) {
      this.image = image;
      this.frames = frames;
      this.columns = columns;
      this.imgWidth = imgWidth;
      this.imgHeight = imgHeight;
      this.delay = delay;
      this.component = component;
      frameCounter = 0;
      frameTicker = 0;
      identity = new AffineTransform();
   }

   /**
    * Draw one frame in the image at location x, y
    * 
    * @param g2 the graphics context
    * @param x the x coordinate of the upper-left location
    * @param y the y coordinate of the upper-left location
    */
   public void drawFrame(Graphics2D g2, int x, int y) {
      g2.setTransform(identity);
      frameTicker += 1;
      if (frameTicker % delay == 0) {
         frameCounter += 1;
         frameTicker = 0;
      }
      if (frameCounter >= frames)
         frameCounter = 0;
      int framex = (frameCounter % columns) * imgWidth;
      int framey = (frameCounter / columns) * imgHeight;
      g2.drawImage(image, x, y, x + imgWidth, y + imgHeight, framex, framey,
            framex + imgWidth, framey + imgHeight, component);
   }

}
