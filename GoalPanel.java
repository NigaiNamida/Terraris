

import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class GoalPanel extends JPanel{
    public static int goal = 0;
    public static JLabel goalLabel = new JLabel("LINES", SwingConstants.CENTER);
    public static JLabel goalScore = new JLabel(""+goal, SwingConstants.CENTER);
    

    public GoalPanel(){
        //setting panel
        goal = 0;
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
}