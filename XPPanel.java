import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class XPPanel extends JPanel{
    private BossPanel bossPanel;
    private boolean isBackToBack;
    private double maxXP;
    private int XP;

    public XPPanel(){
        bossPanel = GameFrame.getBossPanel();
        isBackToBack = false;
        maxXP = 2000;
        XP = 1900;

        this.setBounds(415, 20, 20, 500);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3,true));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBar(g);
    }

    public void checkLevel(){
        while(XP >= maxXP){
            levelUp();
            RewardPanel rewardPanel = GameFrame.getRewardPanel();
            rewardPanel.promptReward();
        }
        repaint();
    }

    private void levelUp() {
        LevelPanel.addLevel();
        LevelPanel.getLevelScore().setText(""+LevelPanel.getLevel());
        if(LevelPanel.getLevel()%2 == 1)
            PlayZone.getGravity().increaseFallSpeed(LevelPanel.getLevel()/2+1);
        XP %= maxXP;
        maxXP += 1000;
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
        if(bossPanel.isBossAlive()){
            bossPanel.damageToBoss(-point);
        }
        else{
            XP -= point;
        }
        checkLevel();
    }

    public void addTSpinXP(boolean isTSpin,int fullLineAmount) {
        String text = null;
        int point = 0;
        if(isTSpin){
            switch (fullLineAmount) {
                case 0:
                    point = 250;
                    text = "T-SPIN!";
                    break;
                case 1:
                    point = 500;
                    text ="T-SPIN! SINGLE!";
                    break;
                case 2:
                    point = 750;
                    text ="T-SPIN! DOUBLE!";
                    break;
                case 3:
                    point = 1000;
                    text ="T-SPIN! TRIPLE!";
                    break;
            }
        }
        else{
            switch (fullLineAmount) {
                case 0:
                    point = 60;
                    text ="MINI T-SPIN!";
                    break;
                case 1:
                    point = 125;
                    text ="MINI T-SPIN! SINGLE!";
                    break;
            }
        }

        if(text != null){
            ChatPanel.comboMessage(text);
        }

        if(bossPanel.isBossAlive()){
            PlayZone.queueSpecialTexture();
        }

        point = (int)(LevelPanel.getLevel()/1.5 * point);
        if(isBackToBack && fullLineAmount != 0){
            point *= 1.5;
        }
        if(!isBackToBack){
            System.out.println("Back To Back!");
        }
        isBackToBack = true;

        if(bossPanel.isBossAlive()){
            bossPanel.damageToBoss(point);
        }
        else{
            XP += point;
        }
        checkLevel();
    }

    public void addFullLineXP(int fullLineAmount){
        String text = null;
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
                text = "TETRIS!";
                break;
        }

        if(text != null){
            ChatPanel.comboMessage(text);
        }
        
        if(bossPanel.isBossAlive()){
            PlayZone.queueSpecialTexture();
        }

        point = (int)(LevelPanel.getLevel()/1.5 * point);
        if(!isBackToBack && fullLineAmount == 4){
            System.out.println("Starting Back To Back");
        }
        else if(isBackToBack && fullLineAmount != 4){
            System.out.println("Back to back broke");
        }
        if(isBackToBack && (fullLineAmount == 4)){
            point *= 1.5;
        }
        isBackToBack = (fullLineAmount == 4);

        if(bossPanel.isBossAlive()){
            bossPanel.damageToBoss(point);
        }
        else{
            XP += point;
        }
        checkLevel();
    }

    public void addBossXP(int HP){
        int point = 0;
        point = (int)(LevelPanel.getLevel()/1.5 * HP);
        XP += point;
        checkLevel();
    }

    public void addSoftDropXP(){
        if(bossPanel.isBossAlive()){
            bossPanel.damageToBoss(1);
        }
        else{
            XP++;
        }
        checkLevel();
    }

    public void addHardDropXP(int m){
        if(bossPanel.isBossAlive()){
            bossPanel.damageToBoss(2*m);
        }
        else{
            XP += 2*m;
        }
        checkLevel();
    }

}