import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Cruiser extends Ship {
   public Cruiser(int x, int y, Color c) {
      super(c, 20, 20, 2, x, y, 5);
   }
}