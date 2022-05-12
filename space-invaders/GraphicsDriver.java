import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class GraphicsDriver {
   public static void main(String[] args) {
      JFrame frame = new JFrame("SPACE INVADERS");
      frame.setSize(850, 600);
      frame.setLocation(20, 20);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new GUIPanel());
      frame.setVisible(true);
   }
}
