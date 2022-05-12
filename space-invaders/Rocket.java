import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Rocket
{
   private int myX;
   private int myY;
   private int myWidth, myHeight;
   private Color myColor;
   private int mydx, mydy;
   
   public Rocket(int newX, int newY, int newdx, int newdy)
   {
      myWidth = 2;
      myHeight = 10;
      myY = newY;
      myX = newX;
      myColor = new Color(200, 200, 200);
      mydx = newdx;
      mydy = newdy;
   }
   
   public Rocket(int newX, int newY, int newdx, int newdy, int newWidth)
   {
      myWidth = newWidth;
      myHeight = 10;
      myY = newY;
      myX = newX;
      myColor = new Color(200, 200, 200);
      mydx = 0;
      mydy = newdy;
   }
   
   public boolean move()
   {
      myY += mydy;
      myX += mydx;
      return myY<-myHeight||myY>400;
   }
   
   public int getX() {
      return myX;
   }
   
   public int getY() {
      return myY;
   }
   
   public int getWidth() {
      return myWidth;
   }
   
   public int getHeight() {
      return myHeight;
   }
   
   public void draw(Graphics myBuffer) 
   {
      myBuffer.setColor(myColor);
      myBuffer.fillRect(myX, myY, myWidth, myHeight);
   }
}