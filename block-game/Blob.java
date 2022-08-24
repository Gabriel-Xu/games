import java.awt.*;

public class Blob {
   private int myX, myY;
   private int myWidth, myHeight;
   private int mydx, mydy;
   private int mySpeed;
   private int myHealth;
   private int chunkX, chunkY;
   
   public Blob(int newX, int newY) {
      myWidth=10;
      myHeight=10;
      myX=newX;
      myY=newY-myHeight;
      mySpeed=1;
      mydx=0;
      mydy=0;
      myHealth=10;
      chunkX=5;
      chunkY=5;
   }
   
   public int checkX() {
      return myX+mydx;
   }
   
   public int checkY() {
      return myY+mydy;
   }
   
   public int getWidth() {
      return myWidth;
   }
   
   public int getHeight() {
      return myHeight;
   }
   
   public int getSpeed() {
      return mySpeed;
   }
   
   public void moveX() {
      myX+=mydx;
   }
   
   public void moveY() {
      myY+=mydy;
   }
   
   public void draw(Graphics g) {
      g.setColor(new Color(255,0,0));
      g.fillRect(myX, myY, myWidth, myHeight);
   }
   
   public int getX() {
      return myX;
   }
   
   public int getMiddleX() {
      return myX+myWidth/2;
   }
   
   public int getRightX() {
      return myX+myWidth;
   }
   
   public int getY() {
      return myY;
   }
   
   public int getMiddleY() {
      return myY+myHeight/2;
   }
   
   public int getBottomY() {
      return myY+myHeight;
   }
   
   public int getChunkX() {
      return chunkX;
   }

   public int getChunkY() {
      return chunkY;
   }

   public void adddx(int dx) {
      mydx+=mySpeed*dx;
   }
   
   public void adddy(int dx) {
      mydy+=mySpeed*dx;
   }
   
   public void setY(int newY) {
      myY=newY;
   }
   
   public void setdx(int dx) {
      mydx=mySpeed*dx;
   }
   
   public void setdy(int dy) {
      mydy=mySpeed*dy;
   }
   
   public boolean hitPlayer(Player p) {
      if ((p.getRightX()<getX() || p.getX()>getRightX() || (getBottomY()<p.getY() || getY()>p.getBottomY()))) {
         return false;
      }
      return true;
   }
   
   public void trackPlayer(Player p) {
      if (p.getMiddleX()>getMiddleX()) {
         setdx(mySpeed);
      }
      else {
         setdx(-mySpeed);
      }
   }

   public void getHit(int x) {
      myHealth-=x;
   }

   public boolean isAlive() {
      return myHealth>0;
   }
}