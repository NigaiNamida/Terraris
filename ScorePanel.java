

import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class ScorePanel extends JPanel{
    private int score;
    // private static JLabel scoreLabel;
    // private static JLabel scorePoint;

    public ScorePanel(){
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
        System.out.println("Painted");
    }

    public void paintBar(Graphics g){
        int pixel = (int)((score/1000.0) * 500);
        g.setColor(Color.WHITE);
        g.fillRect(0, 500-pixel, 20,pixel);
    }

    public int getScore() {
        return score;
    }

    public void deductScore(int point){
        score -= point;
        System.out.println("-score : "+score);
        this.repaint();
    }

    public void addFullLineScore(int fullLineAmount){
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
        System.out.println("-score : "+score);
        this.repaint();
        // scorePoint.setText(String.format("%,d",score));
    }

    public void addSoftDropScore(){
        score ++;
        System.out.println("-score : "+score);
        this.repaint();
        // scorePoint.setText(String.format("%,d",score));
    }

    public void addHardDropScore(int m){
        score += 2*m;
        System.out.println("-score : "+score);
        this.repaint();
        // scorePoint.setText(String.format("%,d",score));
    }
}