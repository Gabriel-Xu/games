import java.awt.*;

public class Inventory {
   private Item[] items;
   private int[] itemcounts;
   private int helditem;
   private int BLOCKSIZE;
   
   public Inventory(int blocksize) {
      items=new Item[20];
      itemcounts=new int[items.length];
      helditem=0;
      BLOCKSIZE=blocksize;
      pickUp(new Sword(100, 100));
   }
   
   public boolean pickUp(Item item) {
      for (int i=0; i<items.length; i++) {
         if (items[i]!=null&&items[i].getItem().equals(item.getItem())) {
            if (itemcounts[i]<16) {
               itemcounts[i]++;
               return true;
            }
         }
      }
      for (int i=0; i<items.length; i++) {
         if (items[i]==null) {
            items[i]=item;
            itemcounts[i]=1;
            return true;
         }
      }
      return false;
   }
   
   public void dropHeldItem() {
      if (itemcounts[helditem]>1) itemcounts[helditem]--;
      else {
         items[helditem]=null;
      }
   }
   
   public Item getHeldItem() {
      return items[helditem];
   }
   
   public void shiftHeldItem(int x) {
      helditem+=x;
      if (helditem<0) helditem=items.length-1;
      if (helditem>=items.length) helditem=0;
   }
   
   public void draw(Graphics g) {
      g.setColor(Color.WHITE);
      g.fillRect(100,100,200,50);
      g.fillRect(175,150,50,50);
      for (int i=0; i<items.length; i++) {
         if (items[i]!=null) {
            items[i].setPosition(100+(i%10)*BLOCKSIZE, 100+i/10*BLOCKSIZE);
            items[i].drawItem(g);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Monospace", Font.BOLD, 15));
            if (itemcounts[i]<10) g.drawString(""+itemcounts[i], 100+(i%10)*BLOCKSIZE+BLOCKSIZE*3/5, 100+(i/10+1)*BLOCKSIZE);
            else g.drawString(""+itemcounts[i], 100+(i%10)*BLOCKSIZE+BLOCKSIZE/2, 100+(i/10+1)*BLOCKSIZE);
         }
      }
      g.setColor(Color.BLACK);
      g.drawRect(180,155,15,15);
      g.drawRect(205,155,15,15);
      g.drawRect(180,180,15,15);
      g.drawRect(205,180,15,15);
   }
   
   public void drawHotbar(Graphics g) {
      g.setColor(Color.WHITE);
      g.fillRect(100,0,200,BLOCKSIZE);
      for (int i=0; i<10; i++) {
         if (items[i]!=null) {
            items[i].setPosition(100+(i%10)*BLOCKSIZE, 0);
            items[i].drawItem(g);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Monospace", Font.BOLD, 15));
            if (itemcounts[i]<10) {
               g.drawString(""+itemcounts[i], 100+(i%10)*BLOCKSIZE+BLOCKSIZE*3/5, BLOCKSIZE);
            }
            else {
               g.drawString(""+itemcounts[i], 100+(i%10)*BLOCKSIZE+BLOCKSIZE/5, BLOCKSIZE);
            }
            g.drawRect(100+(helditem%10)*BLOCKSIZE,0,BLOCKSIZE,BLOCKSIZE);
         }
      }
   }
}