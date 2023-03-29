import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class XPPanel extends JPanel{
    private double maxXP;
    private int XP;

    public XPPanel(){
        maxXP = 2000;
        XP = 0;

        this.setBounds(415, 20, 20, 500);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBar(g);
    }

    public void checkLevel(){
        System.out.println(XP);
        if(XP >= maxXP){
            LevelPanel.addLevel();
            LevelPanel.getLevelScore().setText(""+LevelPanel.getLevel());
            if(LevelPanel.getLevel()%2 == 1)
                PlayZone.getGravity().increaseFallSpeed(LevelPanel.getLevel()/2+1);
            XP %= maxXP;
            maxXP += 1000;
        }
        repaint();
    }

    public void paintBar(Graphics g){
        int pixel = (int)((XP/maxXP) * 500);
        g.setColor(new Color(134,219,51));
        g.fillRect(0, 500-pixel, 20,pixel);
    }

    public int getXP() {
        return XP;
    }

    public void deductXP(int point){
        XP -= point;
        checkLevel();
    }

    public void addFullLineXP(int fullLineAmount){
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
        XP += (int)(LevelPanel.getLevel()/1.5 * point);
        checkLevel();
    }

    public void addSoftDropXP(){
        XP ++;
        checkLevel();
    }

    public void addHardDropXP(int m){
        XP += 2*m;
        checkLevel();
    }
}