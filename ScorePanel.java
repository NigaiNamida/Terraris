

import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class ScorePanel extends JPanel{
    private static int score;
    private static JLabel scoreLabel;
    private static JLabel scorePoint;

    public ScorePanel(){
        score = 0;
        scoreLabel = new JLabel("SCORE", SwingConstants.CENTER);
        scorePoint = new JLabel(String.format("%,d",score), SwingConstants.CENTER);
        //setting panel
        this.setBounds(925, 580, 200, 100);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));
        //add component
        this.add(scoreLabel);
        this.add(scorePoint);
        this.setLayout(new FlowLayout());
        scoreLabel.setForeground(new Color(193,221,196,255));
        scorePoint.setForeground(new Color(193,221,196,255));
        scoreLabel.setPreferredSize(new Dimension(100, 30));
        scorePoint.setPreferredSize(new Dimension(100, 30));
        scoreLabel.setFont(new Font("Futura",Font.BOLD,20));
        scorePoint.setFont(new Font("Futura",Font.BOLD,25));
        scorePoint.setText(String.format("%,d",score));
    }

    public static int getScore() {
        return score;
    }

    public static void deductScore(int point){
        score -= point;
    }

    public static void addFullLineScore(int fullLineAmount){
        int point = 0;
        switch (fullLineAmount) {
            case 1:
                point = 100;
                break;
            case 2:
                point = 300;
                break;
            case 3:
                point = 500;
                break;
            default:
                point = 800;
                break;
        }
        score += LevelPanel.getLevel() * point;
        scorePoint.setText(String.format("%,d",score));
    }

    public static void addSoftDropScore(){
        score ++;
        scorePoint.setText(String.format("%,d",score));
    }

    public static void addHardDropScore(int m){
        score += 2*m;
        scorePoint.setText(String.format("%,d",score));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//not have = no panel bg color
    }
}