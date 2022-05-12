import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class SpeedPowerup extends Powerup {
   public SpeedPowerup(int newX, int newY) {
      super(newX, newY, Color.GREEN, 20, 'S');
   }
   
   public void powerup(Ship ship) {
      ship.speedUp(1);
   }
}