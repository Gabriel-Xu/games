import java.awt.*;

public abstract class Block implements Item {
   int myX, myY;
   int myWidth, myHeight;
   
   public Block(int newX, int newY, int newHeight, int newWidth) {
      myX=newX;
      myY=newY;
      myWidth=newWidth;
      myHeight=newHeight;     
   }
   
   public int getMiddleX() {
      return myX+myWidth;
   }
   
   public int getMiddleY() {
      return myY+myHeight;
   }
   
   public void setPosition(int x, int y) {
      myX=x;
      myY=y;
   }
   
   public boolean isPlaceable() {
      return true;
   }

   public int getDamage() {
      return 1;
   }
   
   public abstract void drawItem(Graphics g);
      
   public abstract void draw(Graphics g);
}