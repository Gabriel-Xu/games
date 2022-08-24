import java.awt.*;

public class Wood extends Block {
   public Wood(int x, int y, int size) {
      super(x, y, size, size);
   }
   
   public void drawItem(Graphics g) {
      g.setColor(new Color(56, 32, 7));
      g.fillRect(myX+myWidth/4, myY+myWidth/4, myWidth/2, myHeight/2);
   }
   
   public void draw(Graphics g) {
      g.setColor(new Color(56, 32, 7));
      g.fillRect(myX, myY, myWidth, myHeight);
   }
   
   public String getItem() {
      return "Wood";
   }
}