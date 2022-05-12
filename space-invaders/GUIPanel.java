import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class GUIPanel extends JPanel {
   JTextField r, g, b, h, w;
   LoadingPanel loading;
   GamePanel gfx;
   CoverPanel cover;
   JButton resetButton;
   private Timer t;
   private HighScores scoreSubpanel;
   private loadingListener myLoadingListener;
   private resetListener myResetListener;
   
   public GUIPanel() {
      setLayout(new BorderLayout());
      
      t=new Timer(1000, new EndListener());
      
      JLabel title = new JLabel("Space Invaders");
      title.setFont(new Font("Serif", Font.BOLD, 30));
      title.setHorizontalAlignment(SwingConstants.CENTER);
      add(title, BorderLayout.NORTH);
      
      scoreSubpanel = new HighScores();
      add(scoreSubpanel, BorderLayout.EAST);
      
      JPanel westSubpanel = new JPanel();
      westSubpanel.setLayout(new GridLayout(3, 2));
      JLabel redLabel = new JLabel("Red: ");
      redLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      westSubpanel.add(redLabel);
      r = new JTextField("255", 3);
      r.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(r);
      JLabel greenLabel = new JLabel("     Green: ");
      greenLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      westSubpanel.add(greenLabel);
      g = new JTextField("255", 3);
      g.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(g);
      JLabel blueLabel = new JLabel("Blue: ");
      blueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      westSubpanel.add(blueLabel);
      b = new JTextField("255", 3);
      b.setHorizontalAlignment(SwingConstants.CENTER);
      westSubpanel.add(b);
      add(westSubpanel, BorderLayout.WEST);
      
      JPanel cover=new CoverPanel();
      add(cover);
      
      resetButton = new JButton("Start Game");
      myLoadingListener=new loadingListener();
      resetButton.addActionListener(myLoadingListener);
      add(resetButton, BorderLayout.SOUTH);
   }
   
   private class loadingListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if (cover!=null) {
            remove(cover);
            cover=null;
         }
         t.stop();
         if (gfx!=null) {
            remove(gfx);
            gfx=null;
         }
         loading=new LoadingPanel();
         add(loading);
         loading.requestFocusInWindow();
         resetButton.removeActionListener(myLoadingListener);
         myResetListener=new resetListener();
         resetButton.addActionListener(myResetListener);
         resetButton.setText("Choose Ship");
         
         scoreSubpanel.disableButton();
      }
   }
   
   private class resetListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         remove(loading);
         gfx=new GamePanel();
         add(gfx);
         int rInt = Math.min(255, Integer.parseInt(r.getText()));
         int gInt = Math.min(255, Integer.parseInt(g.getText()));
         int bInt = Math.min(255, Integer.parseInt(b.getText()));
         Color c = new Color(rInt, gInt, bInt);
         gfx.reset(loading.getShip(), c);
         gfx.requestFocusInWindow();
         t.start();
         resetButton.removeActionListener(myResetListener);
         resetButton.addActionListener(myLoadingListener);
         resetButton.setText("Start Game");
         loading=null;
      }
   }
   
   private class EndListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if (!gfx.isRunning()) {
            t.stop();
            scoreSubpanel.recordScore(gfx.getScore());
         }
      }
   }
}