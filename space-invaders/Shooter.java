import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Shooter extends Enemy
{
   public Shooter(int x, int y, int hp) {
      super(x, y, 2, true, Color.GREEN, hp);
   }
   
   public void attack() {
      if (myRocket!=null&&myRocket.move()) myRocket=null;
      if (myRocket==null) makeRocket();
   }
   
   public boolean hitShip(Ship ship) {
      if (!(myRocket.getX()>ship.getX()+ship.getWidth()
      ||myRocket.getX()+myRocket.getWidth()<ship.getX()
      ||myRocket.getY()>ship.getY()+ship.getHeight()
      ||myRocket.getY()+myRocket.getHeight()<ship.getY())) {
         myRocket=null;
         return true;
      }
      return false;
   }
}