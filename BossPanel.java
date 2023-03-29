import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class BossPanel extends JPanel{
    private JLabel bossName;
    private double bossMaxHP;
    private int bossHP;

    public BossPanel(){
        bossMaxHP = 2000;
        bossHP = 2000;

        bossName = new JLabel("Eye of Okamoto");
        bossName.setForeground(new Color(193,221,196,255));
        bossName.setFont(new Font("Futura",Font.BOLD,20));

        //setting panel
        this.setBounds(455, 20, 250, 250);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));

        //add component
        this.add(bossName);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHP(g);
    }

    public void damageToBoss(int damage){
        if(bossHP > 0){
            bossHP -= damage;
        }
        if(bossHP <= 0){
            System.out.println("I'm ded");
        }
        repaint();
    }

    private void drawHP(Graphics g) {
        int pixel = (int)((bossHP/bossMaxHP) * 210);
        g.setColor(Color.red);
        g.fillRect(20, 40,pixel,20);
    }
}
