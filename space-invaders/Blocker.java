import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Blocker extends Enemy
{
   public Blocker(int x, int y, int hp) {
      super(x, y, 4, false, Color.RED, hp);
   }
   
   public void attack() {
      
   }
   
   public boolean hitShip(Ship ship) {
      if (!(myX>=ship.getX()+ship.getWidth()
      ||myX+myWidth<=ship.getX()
      ||myY>=ship.getY()+ship.getHeight()
      ||myY+myHeight<=ship.getY())) {
         myRocket=null;
         getHit();
         getHit();
         return true;
      }
      return false;
   }
}