import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Pointer {
   private int[] xPoints, yPoints;
   private Color color;
   private int height, width;
   
   public Pointer(int newHeight, int newWidth) {
      height=newHeight;
      width=newWidth;
      color=Color.BLACK;
      xPoints=new int[] {200-width/2, 200, 200+width/2};
      yPoints=new int[] {40, 40+height, 40};
   }
   
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillPolygon(xPoints, yPoints, 3);
   }
   
   public void shift() {
      int x=10;
      if (yPoints[0]==50) x=-10;
      for (int i=0; i<3; i++) {
         yPoints[i]+=x;
      }
   }
   
   public void move(int x) {
      if (xPoints[2]+x*110<400 && xPoints[0]+x*110>0)
      for (int i=0; i<3; i++) {
         xPoints[i]+=x*110;
      }
   }
   
   public int getX() {
      return xPoints[1];
   }
}