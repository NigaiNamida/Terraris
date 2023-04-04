import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class BossAttack implements ActionListener{

    private PlayZone playZone;
    private Boss boss;
    private Timer projectileTimer;
    private static int[] slimeFallsColumn;
    private static int[] slimeFallsDelay;
    private static int[] slimePuddleColumn;
    private static int slimeFallsCount;

    private static int [] demonEyeRow;
    private static int[] demonEyeDelay;
    private static int[] demonEyeDirection;
    private static int demonEyeCount;

    private static int EoCDashRow;
    private static int EoCDashDelay;
    private static int EoCDashDirection;
    private static int EoCNextFrame;
    private static int EoCFrame;

    private static Color slimePuddleColor;
    private static Color slimeBlockColor;
    private static Image bossAttackImage;
    private static String path = "Assets/Image/Bosses/";
    
    public BossAttack(){
        playZone = GameFrame.getPlayZone();
        projectileTimer = new Timer(25, this);

        slimeFallsColumn = new int[1];
        slimeFallsDelay = new int[1];
        slimePuddleColumn = new int[1];
        slimeFallsCount = 0;

        demonEyeRow = new int[1];
        demonEyeDelay = new int[1];
        demonEyeDirection = new int[1];
        demonEyeCount = 0;

        EoCDashRow = 0;
        EoCDashDelay = 1000;
        EoCDashDirection = 0;
        EoCFrame = 0;
    }

    public void attack(String bossName, int phase, int state) {
        switch (bossName) {
            case "KingSlime":
                KingSlimeAttack(state);
                break;
            case "EyeOfCthulhu":
                EyeOfCthulhuAttack(phase, state);
                break;
            default:
                break;
        }
    }

    
    public void BossesDefeat(String bossName) {
        switch (bossName) {
            case "KingSlime":;
                slimeFallsColumn = new int[1];
                slimeFallsDelay = new int[1];
                slimePuddleColumn = new int[1];
                slimeFallsCount = 0;
                break;
            case "EyeOfCthulhu":;
                demonEyeRow = new int[1];
                demonEyeDelay = new int[1];
                demonEyeDirection = new int[1];
                demonEyeCount = 0;
                EoCDashRow = 0;
                EoCDashDelay = 1000;
                EoCDashDirection = 0;
                EoCFrame = 0;
                break;
            default:
                break;
        }
    }

    public void KingSlimeAttack(int state){
        setSlimeRainColumn(state);
        projectileTimer.restart();
    }
    
    public void EyeOfCthulhuAttack(int phase, int state) {
        if(phase == 1){
            setDemonEyeRow(state);
            projectileTimer = new Timer(50, this);
            projectileTimer.restart();
        }
        else{
            setEoCDashRow();
            projectileTimer = new Timer(50, this);
            projectileTimer.restart();
            GameFrame.playSE(9);
        }
    }

    public void applyBlindness(int state){
        playZone = GameFrame.getPlayZone();
        playZone.setBlindness((state-1)*15);
    }

    public void setDemonEyeRow(int state){    

        demonEyeCount = state*2;
        
        demonEyeRow = new int[demonEyeCount];
        demonEyeDirection = new int[demonEyeCount];
        
        demonEyeDelay = new int[demonEyeCount];
        for(int i = 0; i < demonEyeCount; i++) {
            demonEyeDelay[i] = i*20;
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 3; i < 11; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        for(int i = 0; i < demonEyeCount; i++) {
            demonEyeRow[i] = list.get(i);
        }

        for (int i = 0; i < demonEyeCount; i++) {
            Random ran = new Random();
            demonEyeDirection[i] = ran.nextInt(2);
            if(demonEyeDirection[i] == 1){
                demonEyeDelay[i] *= -1;
            }
            else{
                demonEyeDelay[i] += 250;
            }
        }
    }

    public void setEoCDashRow(){
        Random ran;

        ran = new Random();
        EoCDashRow = ran.nextInt(3)+5;

        ran = new Random();
        EoCDashDirection = ran.nextInt(2);

        if(EoCDashDirection == 1){
            EoCDashDelay = -100;
        }
        else{
            EoCDashDelay = 350;
        }

    }

    public void movingDemonEye(){
        for (int i = 0; i < demonEyeCount; i++) {
            if(demonEyeDirection[i] == 1){
                demonEyeDelay[i] += 10;
            }
            else{
                demonEyeDelay[i] -= 10;
            }
        }
    }

    public void movingEoC(){
            if(EoCDashDirection == 1){
                EoCDashDelay += 10;
            }
            else{
                EoCDashDelay -= 10;
            }
    }
    
    public void setSlimeRainColumn(int state){
        
        switch (state) {
            case 1:
                slimeFallsCount = 3;
                break;
            case 2:
                slimeFallsCount = 6;
                break;
            case 3:
                slimeFallsCount = 10;
                break;
        }

        slimeFallsColumn = new int[slimeFallsCount];
        slimePuddleColumn = new int[slimeFallsCount];

        slimeFallsDelay = new int[slimeFallsCount];
        for(int i = 0; i < slimeFallsCount; i++) {
            slimeFallsDelay[i] = -i*20;
        }


        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        for(int i = 0; i < slimeFallsCount; i++) {
            slimeFallsColumn[i] = list.get(i);
        }
    }

    public void movingSlimeRain(){
        playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();
        slimePuddleColor = PlayZone.getSlimePuddleColor();
        slimeBlockColor = PlayZone.getSlimeBlockColor();
        for(int i = 0; i < slimeFallsCount; i++){
            if(!isHit(i) && slimePuddleColumn[i] == 0){        
                slimeFallsDelay[i] += 5;
            }
            else {
                slimePuddleColumn[i] ++;
                if(slimePuddleColumn[i] == 1 ){
                    Color backgroundColor = backgroundBlock[slimeFallsDelay[i]/25][slimeFallsColumn[i]];
                    Color backgroundBelowColor = null;
                    if(slimeFallsDelay[i]/25+1 < playZone.getGridRows()){
                        backgroundBelowColor = backgroundBlock[slimeFallsDelay[i]/25+1][slimeFallsColumn[i]];
                    }
                    if (backgroundColor == null && backgroundBelowColor != slimeBlockColor){
                        playZone.setBackgroundBlock(slimeFallsDelay[i]/25, slimeFallsColumn[i], slimePuddleColor);
                    }
                    else if (backgroundColor == slimePuddleColor){
                        playZone.setBackgroundBlock(slimeFallsDelay[i]/25, slimeFallsColumn[i], slimeBlockColor);
                    }
                }
            }
        }
        playZone.checkFullLine();
        repaintPlayZone();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        playZone = GameFrame.getPlayZone();
        boss = GameFrame.getBossPanel().getBoss();
        if(!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying() && e.getSource() == projectileTimer){
            if (boss != null){
                switch (boss.getName()) {
                    case "KingSlime":
                        movingSlimeRain();
                        break;
                    case "EyeOfCthulhu":
                        if(boss.getPhase()==1){
                            movingDemonEye();
                        }
                        else{
                            movingEoC();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    void repaintPlayZone(){
        playZone.repaint();
    }

    public static void drawBossAttack(Graphics g){
        Boss boss = GameFrame.getBossPanel().getBoss();
        if(boss != null){
            switch (boss.getName()) {
                case "KingSlime":
                    drawKingSlimeAttack(g);
                    break;
                case "EyeOfCthulhu":
                    drawEyeOfCthulhuAttack(g);
                    break;
                default:
                    break;
            }
        }
    }

    public static void drawKingSlimeAttack(Graphics g){
        Boss boss = GameFrame.getBossPanel().getBoss();
        for(int i = 0; i < slimeFallsCount; i++){
            if(!isHit(i) && slimePuddleColumn[i] == 0){
                bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Slime_Falls.png").getImage();
                g.drawImage(bossAttackImage, slimeFallsColumn[i]*25, slimeFallsDelay[i], null);    
            }
        }
    }

    public static void drawEyeOfCthulhuAttack(Graphics g){
        Boss boss = GameFrame.getBossPanel().getBoss();
        if(boss.getPhase()==1){
            for(int i = 0; i < demonEyeCount; i++){
                if(demonEyeDirection[i] == 0){
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Right_Demon_Eye.png").getImage();  
                }
                else{
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Left_Demon_Eye.png").getImage();   
                }
                g.drawImage(bossAttackImage, demonEyeDelay[i], demonEyeRow[i]*25, null);  
            }
        }
        else{
            if(EoCDashDelay >= -100 && EoCDashDelay <= 350){
                if(EoCDashDirection == 0){
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Right_EoC_" + EoCNextFrame%3 + ".png").getImage();  
                }
                else{
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Left_EoC_" + EoCNextFrame%3 + ".png").getImage();   
                }
                g.drawImage(bossAttackImage, EoCDashDelay, EoCDashRow*25 - 5, null);
                EoCFrame++;
                if(EoCFrame == 10){
                    EoCNextFrame++;
                    EoCFrame = 0;
                }
            }
        }
    }
    
    public static boolean isHit(int i){
        PlayZone playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();
        slimePuddleColor = PlayZone.getSlimePuddleColor();
        
        return slimeFallsDelay[i]/25 >= playZone.getGridRows() - 1 || (slimeFallsDelay[i]/25 >= 0 && backgroundBlock[slimeFallsDelay[i]/25 + 1][slimeFallsColumn[i]] != null && backgroundBlock[slimeFallsDelay[i]/25 + 1][slimeFallsColumn[i]] != slimePuddleColor);
    }

    public void stopAllTimer(){
        projectileTimer.stop();
    }

}
