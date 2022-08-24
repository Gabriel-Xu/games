import java.awt.*;

public class Stone extends Block {
   public Stone(int x, int y, int size) {
      super(x, y, size, size);
   }
   
   public void drawItem(Graphics g) {
      g.setColor(new Color(75,75,75));
      g.fillRect(myX+myWidth/4, myY+myWidth/4, myWidth/2, myHeight/2);
   }
   
   public void draw(Graphics g) {
      g.setColor(new Color(75,75,75));
      g.fillRect(myX, myY, myWidth, myHeight);
   }
   
   public String getItem() {
      return "Stone";
   }
}