import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class EnemyGrid {
   private Enemy[][] enemies;
   
   public EnemyGrid(int x) {
      enemies=new Enemy[4][5];
      for (int i=0; i<4; i++) {
         for (int j=0; j<5; j++) {
            if (i==0) enemies[i][j]=new Shooter(95+45*j, 10+30*i, x*3);
            else enemies[i][j]=new Blocker(95+45*j, 10+30*i, x*5);
         }
      }
   }
   
   public void move() {
      boolean reverse=false;
      for (int i=0; i<enemies.length; i++) {
         for (int j=0; j<enemies[0].length; j++) {
            if (enemies[i][j]!=null && enemies[i][j].offScreen()) enemies[i][j]=null;
            if (enemies[i][j]!=null && enemies[i][j].move()) {
               reverse=true;
            }
         }
      }
      if (reverse) for (int i=0; i<enemies.length; i++) for (int j=0; j<enemies[0].length; j++) if (enemies[i][j]!=null) enemies[i][j].reverse();
   }
   
   public void attack() {
      for (int i=0; i<enemies.length; i++) {
         for (int j=0; j<enemies[0].length; j++) {
            if (enemies[i][j]!=null) enemies[i][j].attack();
         }
      }
   }
   
   public boolean hit(Rocket rocket) {
      for (int i=0; i<enemies.length; i++) {
         for (int j=0; j<enemies[0].length; j++) {
            if (enemies[i][j]!=null) {
               if (enemies[i][j].hit(rocket)) {
                  return true;
               }
            }
         }
      }
      return false;
   }
   
   public boolean isDead() {
      for (int i=0; i<enemies.length; i++) {
         for (int j=0; j<enemies[0].length; j++) {
            if (enemies[i][j]!=null) {
               if (enemies[i][j].isDead()) {
                  enemies[i][j]=null;
                  return true;
               }
            }
         }
      }
      return false;
   }
   
   public void setDead() {
      for (int i=0; i<enemies.length; i++) {
         for (int j=0; j<enemies[0].length; j++) {
            if (enemies[i][j]!=null) {
               if (enemies[i][j].isDead()) {
                  enemies[i][j]=null;
               }
            }
         }
      }
   }
   
   public boolean hitShip(Ship ship) {
      for (int i=0; i<enemies.length; i++) {
         for (int j=0; j<enemies[0].length; j++) {
            if (enemies[i][j]!=null && enemies[i][j].hitShip(ship)) {
               setDead();
               return true;
            }
         }
      }
      return false;
   }
   
   public void hitProtector(Protector protector) {
      for (int i=0; i<enemies.length; i++) {
         for (int j=0; j<enemies[0].length; j++) {
            if (enemies[i][j]!=null && protector.hit(enemies[i][j].getRocket())) {
               enemies[i][j].removeRocket();
            }
         }
      }
   }
   
   public boolean gameOver() {
      for (int i=0; i<enemies.length; i++) {
         for (int j=0; j<enemies[0].length; j++) {
            if (enemies[i][j]!=null) return false;
         }
      }
      return true;
   }
   
   public void draw(Graphics myBuffer) {
      for (int i=0; i<enemies.length; i++) {
         for (int j=0; j<enemies[0].length; j++) {
            if (enemies[i][j]!=null) enemies[i][j].draw(myBuffer);
         }
      }
   }
}