import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class CoverPanel extends JPanel {
   public void paintComponent(Graphics g) {
      g.setColor(Color.BLACK);
      g.fillRect(0,0,600,600);
   
      g.setFont(new Font("Serif", Font.BOLD, 50));
      g.setColor(Color.WHITE);
      g.drawString("Space Invaders", 100, 250);
   }
}