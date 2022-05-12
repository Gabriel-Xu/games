import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public abstract class Ship {
   private int[] xPoints, yPoints;
   private Color color;
   private int height, width;
   private int mydx, mydy;
   private int hitpoints, speed;
   private int rocketWidth;
   private boolean doubleRocket, displayHP;
   
   public Ship(Color newColor, int newHeight, int newWidth, int newSpeed, int newX, int newY, int totalHP) {
      color=newColor;
      height=newHeight;
      width=newWidth;
      xPoints=new int[] {newX-width/2, newX, newX+width/2};
      yPoints=new int[] {newY, newY-height, newY};
      mydx=0;
      mydy=0;
      hitpoints=totalHP;
      speed=newSpeed;
      rocketWidth=2;
      doubleRocket=false;
      displayHP=true;
   }
   
   public void move() {
      if ((mydx<0&&xPoints[0]>=0)||(mydx>0&&xPoints[2]<=400)) {
         for (int i=0; i<3; i++) {
            xPoints[i]+=mydx;
         }
      }
      if ((mydy<0&&yPoints[1]>=0)||(mydy>0&&yPoints[0]<=400)) {
         for (int i=0; i<3; i++) {
            yPoints[i]+=mydy;
         }
      }
   }
   
   public boolean demoMove(int x) {
      for (int i=0; i<3; i++) {
         yPoints[i]+=speed*x;
      }
      return yPoints[1]<=100||yPoints[0]>=400;
   }
   
   public boolean hit() {
      hitpoints--;
      return hitpoints<=0;
   }
   
   public void adddx(int dx) {
      mydx+=dx*speed;
   }
   
   public void adddy(int dy) {
      mydy+=dy*speed;
   }
   
   public void speedUp(int x) {
      mydx=mydx/speed*(speed+x);
      mydy=mydy/speed*(speed+x);
      speed+=x;
   }
   
   public void sizeUp(int x) {
      height*=x;
      width*=x;
      int oldX=getX();
      int oldY=getY();
      xPoints=new int[] {oldX, oldX+width/2, oldX+width};
      yPoints=new int[] {oldY, oldY-height, oldY};
      hitpoints*=x;
      rocketUp(x);
   }
   
   public void rocketUp(int x) {
      rocketWidth*=x;
   }
   
   public void doubleRockets() {
      doubleRocket=true;
   }
   
   public void resetd() {
      mydx=0;
      mydy=0;
   }
   
   public void disableHP() {
      displayHP=false;
   }
   
   public int getRocketWidth() {
      return rocketWidth;
   }
   
   public boolean hasDoubleRocket() {
      return doubleRocket;
   }
   
   public int getX() {
      return xPoints[0];
   }
   
   public int getY() {
      return yPoints[1];
   }
   
   public int getWidth() {
      return width;
   }
   
   public int getHeight() {
      return height;
   }
   
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillPolygon(xPoints, yPoints, 3);
      g.setFont(new Font("Serif", Font.BOLD, 15));
      if (displayHP) {
         g.setColor(Color.WHITE);
         g.drawString("HP: "+hitpoints, 5, 395);
      }
   }
}