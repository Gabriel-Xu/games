import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public abstract class Powerup {
   protected int myX, myY;
   protected Color myColor;
   protected int mySize;
   private char icon;
   
   public Powerup(int newX, int newY, Color newColor, int newSize, char newIcon) {
      myX=newX;
      myY=newY;
      myColor=newColor;
      mySize=newSize;
      icon=newIcon;
   }
   
   public boolean hit(Rocket rocket) {
      return (!(rocket.getX()>=myX+mySize
      ||rocket.getX()+rocket.getWidth()<=myX
      ||rocket.getY()>=myY+mySize
      ||rocket.getY()+rocket.getHeight()<=myY));
   }
   
   public abstract void powerup(Ship ship);
   
   public void draw(Graphics g) {
      g.setColor(myColor);
      g.fillRect(myX, myY, mySize, mySize);
      g.setColor(Color.WHITE);
      g.setFont(new Font("Monospace", Font.BOLD, 20));
      g.drawString(""+icon, myX+mySize/5, myY+mySize-mySize/10);
   }
}