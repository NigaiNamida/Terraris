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
    private static int[] projectileCoordinate;
    private static int[] projectileDelay;
    private static int projectileCount;

    private static int[] slimePuddleColumn;

    private static int[] projectileDirection;

    private static Color slimePuddleColor;
    private static Color slimeBlockColor;
    private static Image bossAttackImage;
    private static String path = "Assets/Image/Bosses/";
    
    public BossAttack(){
        playZone = GameFrame.getPlayZone();

        projectileCoordinate = new int[1];
        projectileDelay = new int[1];
        projectileCount = 0;

        slimePuddleColumn = new int[1];
        projectileDirection = new int[1];
        
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
        projectileCoordinate = new int[1];
        projectileDelay = new int[1];
        projectileCount = 0;
        switch (bossName) {
            case "KingSlime":;
                slimePuddleColumn = new int[1];
                break;
            case "EyeOfCthulhu":;
                projectileDirection = new int[1];
                break;
            default:
                break;
        }
    }

    public void KingSlimeAttack(int state){
        setSlimeRainColumn(state);
        projectileTimer = new Timer(25, this);
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
    
    public void setSlimeRainColumn(int state){
        
        switch (state) {
            case 1:
                projectileCount = 3;
                break;
            case 2:
                projectileCount = 6;
                break;
            case 3:
                projectileCount = 10;
                break;
        }

        projectileCoordinate = new int[projectileCount];
        slimePuddleColumn = new int[projectileCount];

        projectileDelay = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileDelay[i] = -i*20;
        }


        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        for(int i = 0; i < projectileCount; i++) {
            projectileCoordinate[i] = list.get(i);
        }
    }

    public void setDemonEyeRow(int state){    

        projectileCount = state+1;
        
        projectileCoordinate = new int[projectileCount];
        projectileDirection = new int[projectileCount];
        
        projectileDelay = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileDelay[i] = i*20;
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 3; i < 11; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        for(int i = 0; i < projectileCount; i++) {
            projectileCoordinate[i] = list.get(i);
        }

        for (int i = 0; i < projectileCount; i++) {
            Random ran = new Random();
            projectileDirection[i] = ran.nextInt(2);
            if(projectileDirection[i] == 1){
                projectileDelay[i] *= -1;
            }
            else{
                projectileDelay[i] += 250;
            }
        }
    }

    public void setEoCDashRow(){
        Random ran;

        ran = new Random();
        projectileCoordinate[0] = ran.nextInt(3)+5;

        ran = new Random();
        projectileDirection[0] = ran.nextInt(2);

        if(projectileDirection[0] == 1){
            projectileDelay[0] = -100;
        }
        else{
            projectileDelay[0] = 350;
        }

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
    
    public void movingSlimeRain(){
        playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();
        slimePuddleColor = PlayZone.getSlimePuddleColor();
        slimeBlockColor = PlayZone.getSlimeBlockColor();
        for(int i = 0; i < projectileCount; i++){
            if(!isHit(i) && slimePuddleColumn[i] == 0){        
                projectileDelay[i] += 5;
            }
            else {
                slimePuddleColumn[i] ++;
                if(slimePuddleColumn[i] == 1 ){
                    Color backgroundColor = backgroundBlock[projectileDelay[i]/25][projectileCoordinate[i]];
                    Color backgroundBelowColor = null;
                    if(projectileDelay[i]/25+1 < playZone.getGridRows()){
                        backgroundBelowColor = backgroundBlock[projectileDelay[i]/25+1][projectileCoordinate[i]];
                    }
                    if (backgroundColor == null && backgroundBelowColor != slimeBlockColor){
                        playZone.setBackgroundBlock(projectileDelay[i]/25, projectileCoordinate[i], slimePuddleColor);
                    }
                    else if (backgroundColor == slimePuddleColor){
                        playZone.setBackgroundBlock(projectileDelay[i]/25, projectileCoordinate[i], slimeBlockColor);
                    }
                }
            }
        }
        playZone.checkFullLine();
        repaintPlayZone();
    }

    public void movingDemonEye(){
        for (int i = 0; i < projectileCount; i++) {
            if(projectileDirection[i] == 1){
                projectileDelay[i] += 10;
            }
            else{
                projectileDelay[i] -= 10;
            }
        }
    }

    public void movingEoC(){
            if(projectileDirection[0] == 1){
                projectileDelay[0] += 30;
            }
            else{
                projectileDelay[0] -= 30;
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
        for(int i = 0; i < projectileCount; i++){
            if(!isHit(i) && slimePuddleColumn[i] == 0){
                bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Slime_Falls.png").getImage();
                g.drawImage(bossAttackImage, projectileCoordinate[i]*25, projectileDelay[i], null);    
            }
        }
    }

    public static void drawEyeOfCthulhuAttack(Graphics g){
        Boss boss = GameFrame.getBossPanel().getBoss();
        String name = boss.getName();
        if(boss.getPhase()==1){
            drawDemonEye(g, name);
        }
        else{
            drawEoCDash(g, name);
        }
    }

    public static void drawDemonEye(Graphics g, String name) {
        for(int i = 0; i < projectileCount; i++){
            if(projectileDelay[i] >= -50 && projectileDelay[i] <= 300){
                if(projectileDirection[i] == 0){
                    bossAttackImage = new ImageIcon(path + name + "/Attack/Right_Demon_Eye.png").getImage();  
                }
                else{
                    bossAttackImage = new ImageIcon(path + name + "/Attack/Left_Demon_Eye.png").getImage();   
                }
            g.drawImage(bossAttackImage, projectileDelay[i], projectileCoordinate[i]*25, null); 
            }
        }
    }

    public static void drawEoCDash(Graphics g, String name) {
        if(projectileDelay[0] >= -100 && projectileDelay[0] <= 350){
            if(projectileDirection[0] == 0){
                bossAttackImage = new ImageIcon(path + name + "/Attack/Right_EoC.png").getImage();  
            }
            else{
                bossAttackImage = new ImageIcon(path + name + "/Attack/Left_EoC.png").getImage();   
            }
            g.drawImage(bossAttackImage, projectileDelay[0], projectileCoordinate[0]*25 - 5, null);
        }
    }
    
    public static boolean isHit(int i){
        PlayZone playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();
        slimePuddleColor = PlayZone.getSlimePuddleColor();
        
        return projectileDelay[i]/25 >= playZone.getGridRows() - 1 || (projectileDelay[i]/25 >= 0 && backgroundBlock[projectileDelay[i]/25 + 1][projectileCoordinate[i]] != null && backgroundBlock[projectileDelay[i]/25 + 1][projectileCoordinate[i]] != slimePuddleColor);
    }

    public void stopAllTimer(){
        projectileTimer.stop();
    }

    public int getProjectileDelay() {
        return projectileDelay[0];
    }

    public void setProjectileDelay(int projectileDelay) {
        BossAttack.projectileDelay[0] = projectileDelay;
    } 
}
