import java.awt.*;

public interface Item {
   public void drawItem(Graphics g);
   public int getMiddleX();
   public int getMiddleY();
   public void setPosition(int x, int y);
   public String getItem();
   public boolean isPlaceable();
   public int getDamage();
}