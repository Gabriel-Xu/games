import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class GamePanel extends JPanel {
   public static final int FRAME = 400;
   private static final Color BACKGROUND = new Color(0, 0, 0);
   
   private BufferedImage myImage;
   private Graphics myBuffer;
   private Ship myShip;
   private Rocket[] myRockets;
   private EnemyGrid myEnemyGrid;
   private int time, level;
   private Powerup[] myPowerups;
   private Protector[] myProtectors;
   
   private Timer t;
   private boolean up, down, left, right, running;
   private Scoreboard myScoreboard;
   
   public GamePanel() {
      myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();
      
      addKeyListener(new Key());
      setFocusable(true);
      
      t = new Timer(5, new AnimationListener());
      up=false;
      down=false;
      left=false;
      right=false;
   }
   
   public void paintComponent(Graphics g) {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }

   public void reset(String shipType, Color newColor) {
      if (shipType.equals("Cruiser")) myShip = new Cruiser(200, 350, newColor);
      else if (shipType.equals("Striker")) myShip = new Striker(200, 350, newColor);
      else myShip = new Tanker(200, 350, newColor);
      setUp();
      myEnemyGrid = new EnemyGrid(level);
      myEnemyGrid.draw(myBuffer);
      myProtectors=new Protector[5];
      for (int i=0; i<myProtectors.length; i++) myProtectors[i]=new Protector(20+76*i, 300, (int)(level*2/3+3));
      t.start();
   }
   
   public void setUp() {
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,FRAME,FRAME);
      time=0;
      level=1;
      myShip.draw(myBuffer);
      myRockets=new Rocket[20];
      myScoreboard=new Scoreboard();
      myPowerups=new Powerup[3];
      repaint();
      running=true;
   }
   
   public boolean isRunning() {
      return running;
   }
   
   public int getScore() {
      return myScoreboard.getScore();
   }
   
   public void animate() {
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,FRAME,FRAME);
      myShip.move();
      myEnemyGrid.move();
      myEnemyGrid.attack();
      if (time%4000==1000) {
         int temp=(int)Math.floor(Math.random()*3);
         int tempindex=0;
         for (int i=0; i<myPowerups.length; i++) {
            if (myPowerups[i]==null) {
               tempindex=i;
               break;
            }
         }
         if (temp==0) myPowerups[tempindex]=new DoubleRocketPowerup((int)Math.floor(Math.random()*380),(int)Math.floor(Math.random()*350));
         else if (temp==1) myPowerups[tempindex]=new SpeedPowerup((int)Math.floor(Math.random()*380),(int)Math.floor(Math.random()*350));
         else myPowerups[tempindex]=new SizePowerup((int)Math.floor(Math.random()*390),(int)Math.floor(Math.random()*350));
      }
      for (int i=0; i<myRockets.length; i++) {
         if (myRockets[i]!=null) {
            if (myRockets[i].move()) {
               myRockets[i]=null;
            }
            if (myRockets[i]!=null) {
               myRockets[i].draw(myBuffer);
               if (myEnemyGrid.hit(myRockets[i])) {
                  myRockets[i]=null;
                  if (myEnemyGrid.isDead()) myScoreboard.addScore(30);
               }
            }
            for (int j=0; j<myPowerups.length; j++) {
               if (myRockets[i]!=null && myPowerups[j]!=null && myPowerups[j].hit(myRockets[i])) {
                  myRockets[i]=null;
                  myPowerups[j].powerup(myShip);
                  myPowerups[j]=null;
                  myScoreboard.addScore(10);
               }
            }
         }
      }
      if (myEnemyGrid.gameOver()) {
         myScoreboard.addScore(100*level);
         level++;
         myEnemyGrid=new EnemyGrid(level);
         for (int i=0; i<myProtectors.length; i++) myProtectors[i]=new Protector(20+76*i, 300, (int)(level*2/3+3));
      }
      if (myEnemyGrid.hitShip(myShip)) {
         myScoreboard.addScore(-10);
         if (myShip.hit()) {
            running=false;
         }
      }
      for (int i=0; i<myProtectors.length; i++) {
         if (myProtectors[i]!=null) myEnemyGrid.hitProtector(myProtectors[i]);
         if (myProtectors[i]!=null && myProtectors[i].isDead()) myProtectors[i]=null;
         if (myProtectors[i]!=null) myProtectors[i].draw(myBuffer);
      }
      myEnemyGrid.draw(myBuffer);
      myShip.draw(myBuffer);
      myScoreboard.addScore(.05);
      myBuffer.setColor(Color.WHITE);
      myBuffer.setFont(new Font("Serif", Font.BOLD, 15));
      myBuffer.drawString("Level "+level, 5, 20);
      myScoreboard.draw(myBuffer);
      for (int i=0; i<myPowerups.length; i++) {
         if (myPowerups[i]!=null) myPowerups[i].draw(myBuffer);
      }
      if (!running) {
         t.stop();
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0,0,FRAME,FRAME);
         myScoreboard.endGame(myBuffer);
      }
      repaint();
   }
   
   private class AnimationListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         time+=5;
         animate();
      }
   }
   
   private class Key extends KeyAdapter
   {
      public void keyPressed(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_LEFT && !left) {
            myShip.adddx(-1);
            left=true;
         }
         else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !right) {
            myShip.adddx(1);
            right=true;
         }
         else if(e.getKeyCode() == KeyEvent.VK_UP && !up) {
            myShip.adddy(-1);
            up=true;
         }
         else if(e.getKeyCode() == KeyEvent.VK_DOWN && !down) {
            myShip.adddy(1);
            down=true;
         }
         if (e.getKeyCode()==KeyEvent.VK_SPACE) {
            for (int i=0; i<myRockets.length; i++) {
               if (myRockets[i]==null) {
                  if (myShip.hasDoubleRocket()&&i<myRockets.length-1) {
                     myRockets[i]=new Rocket(myShip.getX()+myShip.getWidth()/2-myShip.getRocketWidth()-1, myShip.getY()-5, 0, -10, myShip.getRocketWidth());
                     myRockets[i+1]=new Rocket(myShip.getX()+myShip.getWidth()/2+1, myShip.getY()-5, 0, -10, myShip.getRocketWidth());
                  }
                  else myRockets[i]=new Rocket(myShip.getX()+myShip.getWidth()/2-myShip.getRocketWidth()/2, myShip.getY()-5, 0, -10, myShip.getRocketWidth());
                  myScoreboard.addScore(-1);
                  break;
               }
            }
         }
      }
      
      public void keyReleased(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            myShip.adddx(1);
            left=false;
         }
         else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            myShip.adddx(-1);
            right=false;
         }
         else if(e.getKeyCode() == KeyEvent.VK_UP) {
            myShip.adddy(1);
            up=false;
         }
         else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            myShip.adddy(-1);
            down=false;
         }
      }
   }
}