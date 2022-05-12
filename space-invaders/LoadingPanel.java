import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class LoadingPanel extends JPanel {
   public static final int FRAME = 400;
   private static final Color BACKGROUND = new Color(204, 204, 204);
   private BufferedImage myImage;
   private Graphics myBuffer;
   private Timer t;
   private Pointer myPointer;
   private int time, shipdy1, shipdy2, shipdy3;
   Striker striker;
   Cruiser cruiser;
   Tanker tanker;
   
   public LoadingPanel() {
      myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,FRAME,FRAME);
      myPointer=new Pointer(30, 20);
      myPointer.draw(myBuffer);
      striker=new Striker(90,250,Color.WHITE);
      cruiser=new Cruiser(200,250,Color.WHITE);
      tanker=new Tanker(310,250,Color.WHITE);
      striker.disableHP();
      cruiser.disableHP();
      tanker.disableHP();
      addKeyListener(new Key());
      setFocusable(true);
      t = new Timer(10, new AnimationListener());
      t.start();
      time=0;
      shipdy1=1;
      shipdy2=1;
      shipdy3=1;
      repaint();
   }
   
   public void paintComponent(Graphics g) {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }
   
   public void animate() {
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,FRAME,FRAME);
      myBuffer.setFont(new Font("Serif", Font.BOLD, 30));
      myBuffer.setColor(Color.BLACK);
      myBuffer.drawString("Choose Your Ship", 80, 30);
      myBuffer.setFont(new Font("Serif", Font.BOLD, 20));
      myBuffer.drawString("Striker", 60, 100);
      myBuffer.drawString("Cruiser", 170, 100);
      myBuffer.drawString("Tanker", 280, 100);
      if (time%200==0) myPointer.shift();
      myPointer.draw(myBuffer);
      boolean changedy1=false, changedy2=false, changedy3=false;
      if (striker.demoMove(shipdy1)) changedy1=true;
      if (cruiser.demoMove(shipdy2)) changedy2=true;
      if (tanker.demoMove(shipdy3)) changedy3=true;
      if (changedy1) shipdy1*=-1;
      if (changedy2) shipdy2*=-1;
      if (changedy3) shipdy3*=-1;
      striker.draw(myBuffer);
      cruiser.draw(myBuffer);
      tanker.draw(myBuffer);
      repaint();
   }
   
   public String getShip() {
      if (myPointer.getX()<200) return "Striker";
      else if (myPointer.getX()>200) return "Tanker";
      else return "Cruiser";
   }
   
   private class AnimationListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         time+=10;
         animate();
      }
   }
   
   private class Key extends KeyAdapter
   {
      public void keyPressed(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            myPointer.move(-1);
         }
         else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            myPointer.move(1);
         }
      }
   }
}