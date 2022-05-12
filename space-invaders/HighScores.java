import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.StringTokenizer;

public class HighScores extends JPanel {
   private String[] scores;
   private JLabel[] scorelabels;
   private JTextField username;
   private JButton recordButton;
   private RecordListener myRecordListener;
   private int highScore;
   BufferedReader br;
   PrintWriter pw;
   
   public HighScores() {
      setLayout(new GridLayout(23,1));
      scores=new String[20];
      scorelabels=new JLabel[20];
      
      JLabel title = new JLabel("  HIGH SCORES  ");
      title.setFont(new Font("Serif", Font.BOLD, 20));
      title.setHorizontalAlignment(SwingConstants.CENTER);
      add(title);
      
      for (int i=0; i<scores.length; i++) {
         scores[i]="";
         scorelabels[i]=new JLabel();
         scorelabels[i].setFont(new Font("Serif", Font.BOLD, 15));
         scorelabels[i].setHorizontalAlignment(SwingConstants.CENTER);
         add(scorelabels[i]);
      }
      try {
         update();
      }
      catch(Exception e) {
         System.out.println("IO Error");
      }
      
      username=new JTextField("", 10);
      username.setHorizontalAlignment(SwingConstants.CENTER);
      add(username);
      recordButton=new JButton("Record Score");
      recordButton.setEnabled(false);
      add(recordButton);
   }
   
   public void recordScore(int score) {
      recordButton.setEnabled(true);
      myRecordListener=new RecordListener();
      recordButton.addActionListener(myRecordListener);
      highScore=score;
   }
   
   public void update() throws IOException {
      br = new BufferedReader(new FileReader("highscores.txt"));
      StringTokenizer st;
      String temp, temp2;
      for (int i=0; i<20; i++) {
         st = new StringTokenizer(br.readLine());
         temp=st.nextToken();
         temp2=st.nextToken();
         scores[i]=temp+" "+temp2;
         if (scores[i].equals("00000 00000")) scorelabels[i].setText("");
         else scorelabels[i].setText(scores[i]);
      }
   }
   
   public void disableButton() {
      recordButton.removeActionListener(myRecordListener);
      recordButton.setEnabled(false);
   }
   
   private class RecordListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         int index=0;
         String[] temparr;
         for (int i=0; i<scores.length; i++) {
            temparr=scores[i].split(" ");
            if (Integer.parseInt(temparr[1])>=highScore) index=i+1;
            else break;
         }
         if (index<20) {
            for (int i=scores.length-2; i>=index; i--) scores[i+1]=scores[i];
            scores[index]=username.getText()+" "+highScore;
         }
         try {
            pw = new PrintWriter("highscores.txt");
            for (int i=0; i<scores.length; i++) {
               pw.println(scores[i]);
            }
            pw.close();
         }
         catch (Exception f) {
            System.out.println("IO Error");
         }
         try {
            update();
         }
         catch(Exception d) {
            System.out.println("IO Error");
         }
         recordButton.removeActionListener(myRecordListener);
         recordButton.setEnabled(false);
      }
   }
}