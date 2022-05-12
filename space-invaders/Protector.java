import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Protector {
   int myX, myY;
   int myWidth, myHeight;
   int hitpoints;
   Color myColor;
      
   public Protector(int x, int y, int hp) {
      myX=x;
      myY=y;
      myWidth=50;
      myHeight=10;
      hitpoints=hp;
      myColor=Color.BLUE;
   }
   
   public boolean hit(Rocket rocket) {
      if (rocket!=null && !(rocket.getX()>=myX+myWidth
      ||rocket.getX()+rocket.getWidth()<=myX
      ||rocket.getY()>=myY+myHeight
      ||rocket.getY()+rocket.getHeight()<=myY)) {
         getHit();
         return true;
      }
      return false;
   }
   
   public boolean isDead() {
      return hitpoints<=0;
   }
   
   private int colorDistance(int x1, int y1, int z1, int x2, int y2, int z2) {
      return (int)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2));
   }
   
   public void getHit() {
      Color tmp=new Color((int)(myColor.getRed()*4/5), (int)(myColor.getGreen()*4/5), (int)(myColor.getBlue()*4/5));
      if (colorDistance(0,0,0,tmp.getRed(),tmp.getGreen(),tmp.getBlue())>30) myColor=tmp;
      hitpoints--;
   }
   
   public void draw(Graphics myBuffer)
   {
      myBuffer.setColor(myColor);
      myBuffer.fillRect(myX, myY, myWidth, myHeight);
   }
}