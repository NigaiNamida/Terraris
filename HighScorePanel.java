import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighScorePanel extends JPanel implements ActionListener{
    private static JLabel highScoreLabel;
    private static JLabel score;
    private static JLabel initial;
    private static JTextField textField;
    private static JButton okButton;

    public HighScorePanel(){
        highScoreLabel = new JLabel("NEW HIGH SCORE!");
        score = new JLabel();
        initial = new JLabel("ENTER YOUR INITIAL");
        textField = new JTextField();
        textField.setDocument(new JTextFieldLimit(3));
        okButton = new JButton("OK");

        this.setOpaque(true);
        this.setBounds(200, 90, 400, 350);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3));
        this.setVisible(false);
        this.setLayout(null);

        highScoreLabel.setForeground(new Color(193,221,196,255));
        highScoreLabel.setFont(new Font("Futura",Font.BOLD,40));
        highScoreLabel.setBounds(0, 40, 400, 40);
        highScoreLabel.setHorizontalAlignment(JLabel.CENTER);

        score.setForeground(new Color(193,221,196,255));
        score.setFont(new Font("Futura",Font.BOLD,30));
        score.setBounds(100, 110, 200, 40);
        score.setHorizontalAlignment(JLabel.CENTER);

        initial.setForeground(new Color(193,221,196,255));
        initial.setFont(new Font("Futura",Font.BOLD,20));
        initial.setBounds(75, 180, 250, 30);
        initial.setHorizontalAlignment(JLabel.CENTER);

        textField.setForeground(Color.black);
        textField.setFont(new Font("Futura",Font.BOLD,20));
        textField.setBounds(150, 230, 100, 30);
        textField.setHorizontalAlignment(JLabel.CENTER);

        okButton.setForeground(new Color(193,221,196,255));
        okButton.setFont(new Font("Futura",Font.BOLD,20));
        okButton.setBounds(150, 290, 100, 30);
        okButton.setHorizontalAlignment(JLabel.CENTER);
        okButton.setFocusable(false);
        okButton.setBackground(new Color(29,201,88));
        okButton.addActionListener(this);

        this.add(highScoreLabel);
        this.add(initial);
        this.add(score);
        this.add(textField);
        this.add(okButton);
    }

    public static JLabel getScore() {
      return score;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == okButton){
            int[] topScore = Leaderboard.getTopScore();
            int score = ScorePanel.getScore();
            for (int i = 0; i< topScore.length; i++) {
                if(score > topScore[i]){
                    Leaderboard.newRank(textField.getText(), score,i);
                    break;
                }
            }
            this.setVisible(false);
            GameFrame.getGameOverPanel().setVisible(true);
            Leaderboard.saveLeaderboard();
        }

    }
}
class JTextFieldLimit extends PlainDocument {
    private int limit;
    JTextFieldLimit(int limit) {
      super();
      this.limit = limit;
    }
  
    JTextFieldLimit(int limit, boolean upper) {
      super();
      this.limit = limit;
    }
  
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
      if (str == null)
        return;
  
      if ((getLength() + str.length()) <= limit) {
        super.insertString(offset, str, attr);
      }
    }
  }
