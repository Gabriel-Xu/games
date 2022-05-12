import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public abstract class Enemy
{
   protected int myX, myY, myWidth, myHeight;
   private Color myColor;
   private int mydx, mydy;
   protected Rocket myRocket;
   private int hitpoints;
   
   public Enemy(int newX, int newY, int newdy, boolean hasRocket, Color newColor, int totalHP)
   {
      myWidth = 30;
      myHeight = 20;
      myY = newY;
      myX = newX;
      myColor = newColor;
      mydx=-3;
      mydy=newdy;
      hitpoints=totalHP;
      if (hasRocket) makeRocket();
   }
   
   public void makeRocket() {
      myRocket=new Rocket(myX+myWidth/2-1, myY+myHeight, 0, 4);
   }
   
   public void removeRocket() {
      if (myRocket!=null) myRocket=null;
   }
   
   public boolean move()
   {
      myX+=mydx;
      return myX<10||myX+myWidth>390;
   }
   
   public void reverse() {
      mydx=-1*mydx;
      myY+=mydy;
   }
   
   public abstract void attack();
   
   public boolean hit(Rocket rocket) {
      if (!(rocket.getX()>=myX+myWidth
      ||rocket.getX()+rocket.getWidth()<=myX
      ||rocket.getY()>=myY+myHeight
      ||rocket.getY()+rocket.getHeight()<=myY)) {
         getHit();
         if (rocket.getX()<myX+myWidth*3/4&&rocket.getX()+rocket.getWidth()>myX+myWidth/4) getHit();
         return true;
      }
      return false;
   }
   
   private int colorDistance(int x1, int y1, int z1, int x2, int y2, int z2) {
      return (int)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2));
   }
   
   public void getHit() {
      Color tmp=new Color((int)(myColor.getRed()*4/5), (int)(myColor.getGreen()*4/5), (int)(myColor.getBlue()*4/5));
      if (colorDistance(0,0,0,tmp.getRed(),tmp.getGreen(),tmp.getBlue())>20) myColor=tmp;
      hitpoints--;
   }
   
   public boolean isDead() {
      return hitpoints<=0;
   }
   
   public boolean offScreen() {
      return myY>400;
   }
   
   public abstract boolean hitShip(Ship ship);
   
   public Rocket getRocket() {
      return myRocket;
   }
   
   public void draw(Graphics myBuffer)
   {
      myBuffer.setColor(myColor);
      myBuffer.fillRect(myX, myY, myWidth, myHeight);
      if (myRocket!=null) myRocket.draw(myBuffer);
   }
}