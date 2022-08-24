import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class GamePanel extends JPanel {
   public static final int FRAME=400;
   private int BLOCKSIZE=20;
   private int WORLDSIZE=FRAME/BLOCKSIZE;
   private Color BACKGROUND;
   
   private BufferedImage myImage;
   private Graphics myBuffer;
   
   private Block[][][][] world;
   private Item[] dropped;
   private Player myPlayer;
   private Blob myBlob;
   private Inventory myInventory;
   private HealthBar myHealthBar;
   private Timer t;
   private boolean left, right, up, down, updown, inv;
   private int gravitypower, jumppower, upcount;
   private boolean downblob, blobinchunk;
   private int knockbackleft;
   private int knockbackright;
   private int chunkX, chunkY;
   
   public GamePanel() {
      myImage= new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer=myImage.getGraphics();
      
      addKeyListener(new Key());
      setFocusable(true);
      
      restart();
      
      addMouseListener(new ClickListener());
      t=new Timer(5, new AnimationListener());
      t.start();
   }
   
   public void restart() {
      BACKGROUND=new Color(135,206,235);
      world=new Block[11][11][WORLDSIZE][WORLDSIZE];
      generate(5, 5);
      dropped=new Item[100];
      chunkX=5;
      chunkY=5;
      respawn();
   }
   
   public void generate(int x, int y) {
      for (int i=0; i<WORLDSIZE; i++) {
         for (int j=WORLDSIZE*3/4; j<WORLDSIZE; j++) {
            if (Math.random()>=0.5) {
               world[x][y][i][j]=new Dirt(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE);
            }
            else {
               world[x][y][i][j]=new Stone(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE);
            }
         }
      }
      for (int i=1; i<WORLDSIZE-1; i++) {
         if (Math.random()>=0.9) {
            world[x][y][i][WORLDSIZE*3/4-1]=new Wood(i*BLOCKSIZE, (WORLDSIZE*3/4-1)*BLOCKSIZE, BLOCKSIZE);
            world[x][y][i][WORLDSIZE*3/4-2]=new Wood(i*BLOCKSIZE, (WORLDSIZE*3/4-2)*BLOCKSIZE, BLOCKSIZE);
            world[x][y][i][WORLDSIZE*3/4-3]=new Leaf(i*BLOCKSIZE, (WORLDSIZE*3/4-3)*BLOCKSIZE, BLOCKSIZE);  
            world[x][y][i-1][WORLDSIZE*3/4-3]=new Leaf((i-1)*BLOCKSIZE, (WORLDSIZE*3/4-3)*BLOCKSIZE, BLOCKSIZE);  
            world[x][y][i+1][WORLDSIZE*3/4-3]=new Leaf((i+1)*BLOCKSIZE, (WORLDSIZE*3/4-3)*BLOCKSIZE, BLOCKSIZE);            
            world[x][y][i][WORLDSIZE*3/4-4]=new Leaf(i*BLOCKSIZE, (WORLDSIZE*3/4-4)*BLOCKSIZE, BLOCKSIZE);
         }
      }
   }
   
   public void respawn() {
      myPlayer=new Player(FRAME/2);
      myBlob=new Blob(FRAME-20, (int)(FRAME*3/4));
      myInventory=new Inventory(BLOCKSIZE);
      myHealthBar=new HealthBar();
      left=false;
      right=false;
      up=false;
      down=false;
      updown=false;
      inv=false;
      downblob=false;
      upcount=0;
      knockbackleft=11;
      knockbackright=11;
      gravitypower=1;
      jumppower=2;
      blobinchunk=true;
   }
   
   public void paintComponent(Graphics g) {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }
   
   public void gravity() {
      int x1=myPlayer.getX()/BLOCKSIZE;
      int x2=myPlayer.getRightX()/BLOCKSIZE;
      int y=myPlayer.getBottomY()/BLOCKSIZE;
      if (!(x1<0||x1>=WORLDSIZE||x2<0||x2>=WORLDSIZE||y<0||y>=WORLDSIZE)) {
         if (down&&(world[chunkX][chunkY][x1][y]!=null||world[chunkX][chunkY][x2][y]!=null)) {
            down=false;
            myPlayer.adddy(-gravitypower);
         }
         else if (!down&&world[chunkX][chunkY][x1][y]==null&&world[chunkX][chunkY][x2][y]==null) {
            down=true;
            myPlayer.adddy(gravitypower);
         }
      }
      if (myBlob!=null&&blobinchunk) {
         int x3=myBlob.getX()/BLOCKSIZE;
         int x4=myBlob.getRightX()/BLOCKSIZE;
         int y2=myBlob.getBottomY()/BLOCKSIZE;
         if (downblob&&(world[chunkX][chunkY][x3][y2]!=null||world[chunkX][chunkY][x4][y2]!=null)) {
            downblob=false;
            myBlob.adddy(-gravitypower);
         }
         else if (!downblob&&world[chunkX][chunkY][x3][y2]==null&&world[chunkX][chunkY][x4][y2]==null) {
            downblob=true;
            myBlob.adddy(gravitypower);
         }
      }
   }
   
   public boolean canMove(int x, int y) {
      if ((x+myPlayer.getWidth())/BLOCKSIZE>=WORLDSIZE||x/BLOCKSIZE<0||(y+myPlayer.getHeight())/BLOCKSIZE>=WORLDSIZE||y/BLOCKSIZE<0) return true;
      return (world[chunkX][chunkY][x/BLOCKSIZE][y/BLOCKSIZE]==null
      &&world[chunkX][chunkY][(x+myPlayer.getWidth())/BLOCKSIZE][y/BLOCKSIZE]==null
      &&world[chunkX][chunkY][x/BLOCKSIZE][(y+myPlayer.getHeight()-1)/BLOCKSIZE]==null
      &&world[chunkX][chunkY][(x+myPlayer.getWidth())/BLOCKSIZE][(y+myPlayer.getHeight()-1)/BLOCKSIZE]==null);
   }
   
   public boolean canMoveBlob(int x, int y) {
      return (world[chunkX][chunkY][x/BLOCKSIZE][y/BLOCKSIZE]==null
      &&world[chunkX][chunkY][(x+myBlob.getWidth())/BLOCKSIZE][y/BLOCKSIZE]==null
      &&world[chunkX][chunkY][x/BLOCKSIZE][(y+myBlob.getHeight()-1)/BLOCKSIZE]==null
      &&world[chunkX][chunkY][(x+myBlob.getWidth())/BLOCKSIZE][(y+myBlob.getHeight()-1)/BLOCKSIZE]==null);
   }
   
   public void jumpanimate() {
      if (up) {
         if (updown) upcount-=myPlayer.getSpeed()*(jumppower-gravitypower);
         else upcount+=myPlayer.getSpeed()*(jumppower-gravitypower);
         if (upcount==0) {
            up=false;
            updown=false;
         }
      }
      if (upcount>BLOCKSIZE*1.5) {
         updown=true;
         myPlayer.adddy(jumppower);
      }
   }
   
   public void animate() {
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,FRAME,FRAME);
      for (int i=0; i<WORLDSIZE; i++) {
         for (int j=0; j<WORLDSIZE; j++) {
            if (world[chunkX][chunkY][i][j]!=null) world[chunkX][chunkY][i][j].draw(myBuffer);
         }
      }
      for (int i=0; i<dropped.length; i++) {
         if (dropped[i]!=null&&myPlayer.itemInRange(dropped[i], BLOCKSIZE)&&myInventory.pickUp(dropped[i])) {
            dropped[i]=null;
         }
      }
      for (int i=0; i<dropped.length; i++) {
         if (dropped[i]!=null) dropped[i].drawItem(myBuffer);
      }
      jumpanimate();
      gravity();
      if (myBlob!=null) blobinchunk=(chunkX==myBlob.getChunkX()&&chunkY==myBlob.getChunkY());
      if (myBlob!=null&&blobinchunk&&knockbackleft>10 && knockbackright>10) {
         if (myBlob.hitPlayer(myPlayer)) {
            myHealthBar.getHit(10);
            if (myPlayer.getMiddleX()>myBlob.getMiddleX()) {
               knockbackleft=0;
            }
            else {
               knockbackright=0;
            }
         }
      }
      else if (knockbackleft<11) {
         myPlayer.setdx(1);
         knockbackleft++;
         if (knockbackleft==11){
            myPlayer.setdx(0);
         }
      }
      else if (knockbackright<11){
         myPlayer.setdx(-1);
         knockbackright++;
         if (knockbackright==11) {
            myPlayer.setdx(0);
         }
      }
      if (myBlob!=null&&blobinchunk) {
         myBlob.trackPlayer(myPlayer);
         if (canMoveBlob(myBlob.checkX(), myBlob.getY())) {
            myBlob.moveX();
         }
         if (canMoveBlob(myBlob.getX(), myBlob.checkY())) {
            myBlob.moveY();
         }
         myBlob.draw(myBuffer);
      }
      if (canMove(myPlayer.checkX(), myPlayer.getY())) {
         myPlayer.moveX();
      }
      if (canMove(myPlayer.getX(), myPlayer.checkY())) {
         myPlayer.moveY();
      }
      if (checkChunk()) {
         boolean loaded=false;
         for (int i=0; i<WORLDSIZE; i++) {
            for (int j=0; j<WORLDSIZE; j++) {
               if (world[chunkX][chunkY][i][j]!=null) {
                  loaded=true;
               }
            }
         }
         if (!loaded) generate(chunkX, chunkY);
      }
      if (myHealthBar.getHealth()<=0) {
         respawn();
      }
      myPlayer.draw(myBuffer);
      myInventory.drawHotbar(myBuffer);
      if (inv) myInventory.draw(myBuffer);
      myHealthBar.draw(myBuffer);
      repaint();
   }
   
   public double distance(int x1, int y1, int x2, int y2) {
      return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
   }
   
   public boolean inRange(int x, int y) {
      int playerx=myPlayer.getMiddleX();
      int playery=myPlayer.getMiddleY();
      x=x*BLOCKSIZE+BLOCKSIZE/2;
      y=y*BLOCKSIZE+BLOCKSIZE/2;
      double dist=distance(playerx, playery, x, y)/BLOCKSIZE;
      boolean next=true;
      for (double i=0.0; i<1; i+=1/dist) {
         int tempx=(int)((playerx+i*(x-playerx))/BLOCKSIZE);
         int tempy=(int)((playery+i*(y-playery))/BLOCKSIZE);
         if ((tempx!=x/BLOCKSIZE||tempy!=y/BLOCKSIZE)&&world[chunkX][chunkY][tempx][tempy]!=null) {
            next=false;
            break;
         }
      }
      playerx/=BLOCKSIZE;
      playery/=BLOCKSIZE;
      x/=BLOCKSIZE;
      y/=BLOCKSIZE;
      if (Math.abs(playerx-x)==1&&Math.abs(playery-y)==1) {
         if (world[chunkX][chunkY][playerx][y]!=null&&world[chunkX][chunkY][x][playery]!=null) next=false;
      }
      return (dist<=3&&next);
   }
   
   public void breakBlock(int x, int y) {
      if (inRange(x, y)) {
         int index=0;
         for (int i=0; i<dropped.length; i++) {
            if (dropped[i]==null) {
               index=i;
               break;
            }
         }
         dropped[index]=world[chunkX][chunkY][x][y];
         world[chunkX][chunkY][x][y]=null;
      }
   }
   
   public boolean validPlace(int x, int y) {
      int x1=x*BLOCKSIZE;
      int x2=x*(BLOCKSIZE+1);
      int y1=y*BLOCKSIZE;
      int y2=y*(BLOCKSIZE+1);
      return (myPlayer.getRightX()<=x1||myPlayer.getX()>=x2||myPlayer.getBottomY()<=y1||myPlayer.getY()>=y2);
   }
   
   public void placeBlock(int x, int y) {
      if (inRange(x, y)&&validPlace(x, y)&&myInventory.getHeldItem()!=null&&myInventory.getHeldItem().isPlaceable()) {
         if (myInventory.getHeldItem().getItem().equals("Dirt")) world[chunkX][chunkY][x][y]=new Dirt(x*BLOCKSIZE, y*BLOCKSIZE, BLOCKSIZE);
         else if (myInventory.getHeldItem().getItem().equals("Wood")) world[chunkX][chunkY][x][y]=new Wood(x*BLOCKSIZE, y*BLOCKSIZE, BLOCKSIZE);
         else if (myInventory.getHeldItem().getItem().equals("Leaf")) world[chunkX][chunkY][x][y]=new Leaf(x*BLOCKSIZE, y*BLOCKSIZE, BLOCKSIZE);
         else if (myInventory.getHeldItem().getItem().equals("Stone")) world[chunkX][chunkY][x][y]=new Stone(x*BLOCKSIZE, y*BLOCKSIZE, BLOCKSIZE);
         myInventory.dropHeldItem();
      }
   }
   
   public void attack(int x, int y) {
      if (x>=myBlob.getX()&&x<=myBlob.getRightX()&&y>=myBlob.getY()&&y<=myBlob.getBottomY()) {
         myBlob.getHit(myInventory.getHeldItem().getDamage());
      }
      if (!myBlob.isAlive()) myBlob=null;
   }

   public void click(MouseEvent e) {
      int x=(int)(e.getX()*FRAME/(getWidth()));
      int y=(int)(e.getY()*FRAME/(getHeight()));
      if ((e.getModifiers()&InputEvent.BUTTON1_MASK)!=0) {
         if (world[chunkX][chunkY][x/BLOCKSIZE][y/BLOCKSIZE]!=null) breakBlock(x/BLOCKSIZE, y/BLOCKSIZE);
         else if (myBlob!=null&&blobinchunk) attack(x, y);
      }
      else {
         if (world[chunkX][chunkY][x/BLOCKSIZE][y/BLOCKSIZE]==null) placeBlock(x/BLOCKSIZE, y/BLOCKSIZE);
      }
   }
   
   public boolean checkChunk() {
      if (myPlayer.getX()<0) {
         chunkX--;
         myPlayer.shiftChunk(-1, 0, FRAME);
         return true;
      }
      else if (myPlayer.getX()>=FRAME) {
         chunkX++;
         myPlayer.shiftChunk(1, 0, FRAME);
         return true;
      }
      if (myPlayer.getY()<0) {
         chunkY--;
         myPlayer.shiftChunk(0, -1, FRAME);
         return true;
      }
      else if (myPlayer.getY()>=FRAME) {
         chunkY++;
         myPlayer.shiftChunk(0, 1, FRAME);
         return true;
      }
      return false;
   }
   
   private class AnimationListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         animate();
      }
   }

   private class ClickListener implements MouseListener {
      public void mousePressed(MouseEvent e) {
         click(e);
      }
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
      public void mouseClicked(MouseEvent e) {}
   }
   
   private class Key extends KeyAdapter {
      public void keyPressed(KeyEvent e) {
         if ((e.getKeyCode() == KeyEvent.VK_A) && !left) {
            myPlayer.setdx(-1);
            left=true;
         }
         else if ((e.getKeyCode() == KeyEvent.VK_D) && !right) {
            myPlayer.setdx(1);
            right=true;
         }
         else if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) && !up && !down) {
            myPlayer.setdy(-jumppower);
            up=true;
         }
         else if (e.getKeyCode() == KeyEvent.VK_E) {
            if (inv) inv=false;
            else inv=true;
         }
         else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            myInventory.shiftHeldItem(-1);
         }
         else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            myInventory.shiftHeldItem(1);
         }
      }
      public void keyReleased(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_A) {
            myPlayer.setdx(0);
            left=false;
         }
         else if (e.getKeyCode() == KeyEvent.VK_D) {
            myPlayer.setdx(0);
            right=false;
         }
      }
   }
}