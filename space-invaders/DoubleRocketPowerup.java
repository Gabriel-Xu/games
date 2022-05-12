import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class DoubleRocketPowerup extends Powerup {
   public DoubleRocketPowerup(int newX, int newY) {
      super(newX, newY, Color.RED, 20, '2');
   }
   
   public void powerup(Ship ship) {
      ship.doubleRockets();
   }
}