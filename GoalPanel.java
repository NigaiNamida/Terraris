import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class GoalPanel extends JPanel{
    private static int goal;
    private static JLabel goalLabel;
    private static JLabel goalScore;

    public GoalPanel(){
        //setting panel
        goal = 0;
        goalLabel = new JLabel("LINES", SwingConstants.CENTER);
        goalScore = new JLabel(""+goal, SwingConstants.CENTER);

        this.setBounds(150, 400, 100, 100);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));

        //add component
        this.add(goalLabel);
        this.add(goalScore);
        this.setLayout(new FlowLayout());
        goalLabel.setForeground(new Color(193,221,196,255));
        goalScore.setForeground(new Color(193,221,196,255));
        goalLabel.setPreferredSize(new Dimension(80, 30));
        goalScore.setPreferredSize(new Dimension(80, 30));
        goalLabel.setFont(new Font("Futura",Font.BOLD,20));
        goalScore.setFont(new Font("Futura",Font.BOLD,30));
        goalScore.setText(""+goal);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//not have = no panel bg color
    }

    public static int getGoal() {
        return goal;
    }

    public static void addGoal() {
        goal++;
    }

    public static JLabel getGoalScore() {
        return goalScore;
    }
    

    
}