import java.awt.*;

public class HealthBar {
   private int playerHealth;
   private Color myColor;
   private int myWidth;
   private int myHeight;

   public HealthBar() {
      myWidth = 100;
      myHeight = 20;
      myColor = new Color(200,100,100);
      playerHealth = 100;
   }

   public void draw(Graphics g) {
      g.setColor(myColor);
      g.drawRect(150,35,myWidth,myHeight);
      g.fillRect(150,35,playerHealth,myHeight);
   }

   public void getHit(int x) {
      playerHealth-=x;
   }

   public int getHealth() {
      return playerHealth;
   }
}