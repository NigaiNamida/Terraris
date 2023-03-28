import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class ScorePanel extends JPanel{
    private double maxScore;
    private int score;
    // private static JLabel scoreLabel;
    // private static JLabel scorePoint;

    public ScorePanel(){
        maxScore = 2000;
        score = 0;
        // scoreLabel = new JLabel("SCORE", SwingConstants.CENTER);
        // scorePoint = new JLabel(String.format("%,d",score), SwingConstants.CENTER);

        this.setBounds(415, 20, 20, 500);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));
        // this.add(scoreLabel);
        // this.add(scorePoint);
        // this.setLayout(new FlowLayout());
        // scoreLabel.setForeground(new Color(193,221,196,255));
        // scorePoint.setForeground(new Color(193,221,196,255));
        // scoreLabel.setPreferredSize(new Dimension(100, 30));
        // scorePoint.setPreferredSize(new Dimension(100, 30));
        // scoreLabel.setFont(new Font("Futura",Font.BOLD,20));
        // scorePoint.setFont(new Font("Futura",Font.BOLD,25));
        // scorePoint.setText(String.format("%,d",score));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBar(g);
    }

    public void checkLevel(){
        System.out.println(score);
        if(score >= maxScore){
            LevelPanel.addLevel();
            LevelPanel.getLevelScore().setText(""+LevelPanel.getLevel());
            if(LevelPanel.getLevel()%2 == 1)
                PlayZone.getGravity().increaseFallSpeed(LevelPanel.getLevel()/2+1);
            score %= maxScore;
            maxScore += 1000;
        }
        repaint();
    }

    public void paintBar(Graphics g){
        int pixel = (int)((score/maxScore) * 500);
        g.setColor(Color.yellow);
        g.fillRect(0, 500-pixel, 20,pixel);
    }

    public int getScore() {
        return score;
    }

    public void deductScore(int point){
        score -= point;
        checkLevel();
    }

    public void addFullLineScore(int fullLineAmount){
        int point = 0;
        switch (fullLineAmount) {
            case 1:
                point = 100;
                break;
            case 2:
                point = 225;
                break;
            case 3:
                point = 350;
                break;
            default:
                point = 500;
                break;
        }
        score += (int)(LevelPanel.getLevel()/1.5 * point);
        checkLevel();
    }

    public void addSoftDropScore(){
        score ++;
        checkLevel();
    }

    public void addHardDropScore(int m){
        score += 2*m;
        checkLevel();
        // scorePoint.setText(String.format("%,d",score));
    }
}