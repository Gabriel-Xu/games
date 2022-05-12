import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class SizePowerup extends Powerup {
   public SizePowerup(int newX, int newY) {
      super(newX, newY, Color.BLUE, 20, '^');
   }
   
   public void powerup(Ship ship) {
      ship.sizeUp(2);
   }
}