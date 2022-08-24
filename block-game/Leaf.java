import java.awt.*;

public class Leaf extends Block {
   public Leaf(int x, int y, int size) {
      super(x, y, size, size);
   }
   
   public void drawItem(Graphics g) {
      g.setColor(new Color(0, 60, 0));
      g.fillRect(myX+myWidth/4, myY+myWidth/4, myWidth/2, myHeight/2);
   }
   
   public void draw(Graphics g) {
      g.setColor(new Color(0, 60, 0));
      g.fillRect(myX, myY, myWidth, myHeight);
   }
   
   public String getItem() {
      return "Leaf";
   }
}