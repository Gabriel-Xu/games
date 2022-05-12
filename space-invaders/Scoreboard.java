import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Scoreboard {
   private double score;
   
   public Scoreboard() {
      score=1000;
   }
   
   public Scoreboard(int x) {
      score=x;
   }
   
   public void addScore(double x) {
      score+=x;
      if (score<0) score=0;
   }
   
   public int getScore() {
      return (int)score;
   }
   
   public void endGame(Graphics myBuffer) {
      myBuffer.setFont(new Font("Monospace", Font.BOLD, 60));
      myBuffer.setColor(Color.WHITE);
      myBuffer.drawString("GAME OVER", 15, 200);
      myBuffer.setFont(new Font("Serif", Font.BOLD, 40));
      myBuffer.drawString("Final Score: "+(int)score, 15, 250);
   }
   
   public void draw(Graphics myBuffer) {
      myBuffer.setFont(new Font("Serif", Font.BOLD, 15));
      myBuffer.setColor(Color.WHITE);
      myBuffer.drawString("Score: "+(int)score, 5, 40);
   }
}