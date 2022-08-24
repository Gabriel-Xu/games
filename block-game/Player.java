import java.awt.*;

public class Player {
   private int myX, myY;
   private int myWidth, myHeight;
   private int mydx, mydy;
   private int mySpeed;
    
   public Player(int newX) {
      myWidth=10;
      myHeight=20;
      myX=newX-myWidth/2;
      myY=200;
      mySpeed=2;
      mydx=0;
      mydy=0;
   }
   
   public int checkX() {
      return myX+mydx;
   }
   
   public int checkY() {
      return myY+mydy;
   }
   
   public void moveX() {
      myX+=mydx;
   }
   
   public void moveY() {
      myY+=mydy;
   }
   
   public double distance(int x1, int y1, int x2, int y2) {
      return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
   }
   
   public boolean itemInRange(Item item, int BLOCKSIZE) {
      return distance(getMiddleX(), getMiddleY(), item.getMiddleX(), item.getMiddleY())<=1.5*BLOCKSIZE;
   }
   
   public void shiftChunk(int x, int y, int FRAME) {
      if (x==-1) myX=FRAME-myWidth-1;
      else if (x==1) myX=0;
      if (y==-1) myY=FRAME-myHeight-1;
      else if (y==1) myY=0;
   }
   
   public void draw(Graphics g) {
      g.setColor(new Color(0,0,0));
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
   
   public int getWidth() {
      return myWidth;
   }
   
   public int getHeight() {
      return myHeight;
   }
   
   public int getSpeed() {
      return mySpeed;
   }
   
   public void setBottomY(int newY) {
      myY=newY-myHeight;
   }
   
   public void adddx(int dx) {
      mydx+=mySpeed*dx;
   }
   
   public void adddy(int dy) {
      mydy+=mySpeed*dy;
   }
   
   public void setdx(int dx) {
      mydx=mySpeed*dx;
   }
   
   public void setdy(int dy) {
      mydy=mySpeed*dy;
   }
   
   public int getdx() {
      return mydx;
   }
   
   public int getdy() {
      return mydy;
   }
}