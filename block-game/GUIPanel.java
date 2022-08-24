import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIPanel extends JPanel { 
   GamePanel gfx;
   JButton restartButton;
   RestartListener restartListener;
   
   public GUIPanel() {
      setLayout(new BorderLayout());
      restartButton=new JButton("New World");
      restartListener=new RestartListener();
      restartButton.addActionListener(restartListener);
      add(restartButton, BorderLayout.SOUTH);
      gfx=new GamePanel();
      add(gfx);
      gfx.requestFocusInWindow();
   }
   
   private class RestartListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         gfx.restart();
         gfx.requestFocusInWindow();
      }
   }
}