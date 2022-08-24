import javax.swing.*;

public class GraphicsDriver {
   public static void main(String[] args) {
      JFrame frame = new JFrame("2D Minecraft");
      frame.setSize(600, 650);
      frame.setLocation(20, 20);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new GUIPanel());
      frame.setVisible(true);
   }
}