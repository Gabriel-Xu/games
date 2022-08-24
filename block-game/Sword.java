import java.awt.*;

public class Sword implements Item {
   private int myX;
   private int myY;
   private int myWidth;
   private int myHeight;
   private int myDamage;
   
   public Sword(int x, int y) {
      myX = x;
      myY = y;
      myWidth = 12;
      myHeight = 12;
      myDamage=5;
   }
   
   public void drawItem(Graphics g) {
      g.setColor(new Color(0, 255, 0));
      g.fillRect(myX+myWidth/4, myY+myWidth/4, myWidth/2, myHeight/2);
   }
   
   public void draw(Graphics g) {
      g.setColor(new Color(0, 255, 0));
      g.fillRect(myX, myY, myWidth, myHeight);
   }
   
   public int getMiddleX() {
      return myX +(myWidth/2);
   }
   
   public int getMiddleY() {
      return myY +(myHeight/2);
   }
   
   public void setPosition(int x, int y) {
      myX = x;
      myY = y;
   }
   
   public String getItem() {
      return "Sword";   
   }
   
   public boolean isPlaceable() {
      return false;
   }
   
   public int getDamage() {
      return myDamage;
   }
}