import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class LevelPanel extends JPanel{
    private static int level;
    private static JLabel levelLabel;
    private static JLabel levelScore;

    public LevelPanel(){
        level = 1;
        levelLabel = new JLabel("LEVEL", SwingConstants.CENTER);
        levelScore = new JLabel(""+level, SwingConstants.CENTER);
        //setting panel
        this.setBounds(20, 280, 100, 100);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));

        //add component
        this.add(levelLabel);
        this.add(levelScore);
        this.setLayout(new FlowLayout());
        levelLabel.setForeground(new Color(193,221,196,255));
        levelScore.setForeground(new Color(193,221,196,255));
        levelLabel.setPreferredSize(new Dimension(80, 30));
        levelScore.setPreferredSize(new Dimension(80, 30));
        levelLabel.setFont(new Font("Futura",Font.BOLD,20));
        levelScore.setFont(new Font("Futura",Font.BOLD,30));
        levelScore.setText(""+level);
    }

    public static int getLevel() {
        return level;
    }

    public static void addLevel() {
        level++;
    }

    public static JLabel getLevelScore() {
        return levelScore;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//not have = no panel bg color
    }
}