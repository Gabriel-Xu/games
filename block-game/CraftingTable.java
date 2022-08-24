import java.awt.*;

public class CraftingTable extends Block {
   public CraftingTable(int x, int y, int size) {
      super(x, y, size, size);
   }
   
   public void drawItem(Graphics g) {
      g.setColor(new Color(50,25,0));
      g.fillRect(myX+myWidth/4, myY+myWidth/4, myWidth/2, myHeight/2);
   }
   
   public void draw(Graphics g) {
      g.setColor(new Color(50,25,0));
      g.fillRect(myX, myY, myWidth, myHeight);
   }
   
   public String getItem() {
      return "Crafting Table";
   }
}