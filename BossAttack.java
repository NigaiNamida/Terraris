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
    private static boolean isHealed;
    private static boolean isDashed;

    private static int[] projectileLength;
    private static int[] isBlocked;

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

        isHealed = false;
        isDashed = true;

        isBlocked = new int[1];
        
    }

    public void attack(String bossName, int phase, int state) {
        switch (bossName) {
            case "KingSlime":
                KingSlimeAttack(state);
                break;
            case "EyeOfCthulhu":
                EyeOfCthulhuAttack(phase, state);
                break;
            case "EaterOfWorld":
                EaterOfWorldAttack(state);
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
                isDashed = true;
                break;
            case "EaterOfWorld":
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
    
    public void EaterOfWorldAttack(int state){
        setDevourerColumn(state);
        projectileTimer = new Timer(50, this);
        projectileTimer.restart();
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
        
        projectileDirection = new int[projectileCount];
        
        projectileDelay = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileDelay[i] = i*20;
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i <= 10; i++) {
            list.add(i);
        }

        Collections.shuffle(list);
        
        projectileCoordinate = new int[projectileCount];
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
        isHealed = false;
        isDashed = false;

        ran = new Random();
        projectileCoordinate[0] = ran.nextInt(3)+5;
        projectileDirection[0] = ran.nextInt(2);

        if(projectileDirection[0] == 1){
            projectileDelay[0] = -250;
        }
        else{
            projectileDelay[0] = 500;
        }

    }
    
    public void setDevourerColumn(int state) {
        Random ran = new Random();
        projectileCount = state;

        isBlocked = new int[projectileCount];
        
        projectileDelay = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileDelay[i] = -i*75;
        }

        projectileLength = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileLength[i] = ran.nextInt(3)+5;
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++) {
            list.add(i*25);
        }
        
        Collections.shuffle(list);
        
        projectileCoordinate = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileCoordinate[i] = list.get(i);
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
                    case "EaterOfWorld":
                        movingDevourer();
                        break;
                    default:
                        break;
                }
                repaintPlayZone();
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
    }

    public void movingDemonEye(){   
        for (int i = 0; i < projectileCount; i++) {
            pushBlock(i);
            if(projectileDirection[i] == 1){
                projectileDelay[i] += 10;
            }
            else{
                projectileDelay[i] -= 10;
            }
        }
    }

    public void movingEoC(){
        pushBlock(0);
        if(projectileDirection[0] == 1){
            projectileDelay[0] += 30;
        }
        else{
            projectileDelay[0] -= 30;
        }
    }

    public void movingDevourer() {
        playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();
        for (int i = 0; i < projectileCount; i++) {
            if(isBlocked[i] != 1){
            projectileDelay[i] += 7;
            blockWorm(i);
                if((projectileDelay[i]/25 >= 0 && projectileDelay[i]/25 <= 19) && backgroundBlock[projectileDelay[i]/25][projectileCoordinate[i]/25] != null){
                    backgroundBlock[projectileDelay[i]/25][projectileCoordinate[i]/25] = null;
                }
            }
            else{
                if(projectileCoordinate[i]<=100){
                    projectileCoordinate[i] -= 7;
                }
                else{
                    projectileCoordinate[i] += 7;
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
                case "EaterOfWorld":
                    drawEaterOfWorldAttack(g);
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
        if(!isDashed){
            g.setColor(new Color(255, 0, 0, 100));
            g.fillRect(0 , projectileCoordinate[0]*25, 250 , 100);
        }
        if(projectileDelay[0] >= -250 && projectileDelay[0] <= 500){
            if(projectileDirection[0] == 0){
                bossAttackImage = new ImageIcon(path + name + "/Attack/Right_EoC.png").getImage();  
            }
            else{
                bossAttackImage = new ImageIcon(path + name + "/Attack/Left_EoC.png").getImage();   
            }
            g.drawImage(bossAttackImage, projectileDelay[0], projectileCoordinate[0]*25 - 5, null);
        }
        else{
            isDashed = true;
        }
    }
    
    public static void drawEaterOfWorldAttack(Graphics g) {
        Boss boss = GameFrame.getBossPanel().getBoss();;
        for (int i = 0; i < projectileCount; i++) {
            int offset = 0;
            if(isBlocked[i] == 0){
                g.setColor(new Color(255, 0, 0, 100));
            }
            else{
                g.setColor(new Color(50, 255, 0, 100));
            }
            g.fillRect(projectileCoordinate[i] , projectileDelay[i], 25 , 25);
            for (int part = projectileLength[i]; part >= 0 ; part--) {
                if(part == projectileLength[i]){
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Devourer_Head_" + isBlocked[i] + ".png").getImage(); 
                    g.drawImage(bossAttackImage, (projectileCoordinate[i])-2 , projectileDelay[i]-offset, null);
                }
                else if(part == 0){
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Devourer_Tail_" + isBlocked[i] + ".png").getImage(); 
                    g.drawImage(bossAttackImage, (projectileCoordinate[i])+2 , projectileDelay[i]-offset-7, null);
                }
                else{
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Devourer_Body_" + isBlocked[i] + ".png").getImage(); 
                    g.drawImage(bossAttackImage, (projectileCoordinate[i])+2 , projectileDelay[i]-offset, null);
                }
                offset += 25;
            }
        }
    }

    public static boolean isHit(int i){
        PlayZone playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();
        slimePuddleColor = PlayZone.getSlimePuddleColor();
        
        return projectileDelay[i]/25 >= playZone.getGridRows() - 1 || (projectileDelay[i]/25 >= 0 && backgroundBlock[projectileDelay[i]/25 + 1][projectileCoordinate[i]] != null && backgroundBlock[projectileDelay[i]/25 + 1][projectileCoordinate[i]] != slimePuddleColor);
    }

    public void applyBlindness(int state){
        playZone = GameFrame.getPlayZone();
        playZone.setBlindness((state-1)*15);
    }

    public void pushBlock(int i) {
        TetrisPiece block = playZone.getBlock();
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int[][] shape = block.getShape();
        int x = block.getX();
        int y = block.getY();
        int phase = boss.getPhase();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                if(shape[row][col] == 1){
                    if(phase==1){
                        if(projectileDirection[i] == 1 && col+x == (projectileDelay[i]+36)/25 && row+y ==  projectileCoordinate[i]){
                            playZone.moveRight();
                            projectileDirection[i] = Math.abs(projectileDirection[i]-1);
                        }
                        else if(projectileDirection[i] != 1 && col+x == projectileDelay[i]/25 && row+y ==  projectileCoordinate[i]){
                            playZone.moveLeft();
                            projectileDirection[i] = Math.abs(projectileDirection[i]-1);
                        }
                    }
                    else{
                        if((col+x <= (projectileDelay[i]+146)/25 && col+x >= projectileDelay[i]/25) && (row+y <= projectileCoordinate[i]+3 && row+y >= projectileCoordinate[i])){
                            if(projectileDirection[i] == 1){
                                playZone.moveRight();
                            }
                            else if(projectileDirection[i] != 1){
                                playZone.moveLeft();
                            }
                            if(!isHealed){
                                boss.setHP(boss.getHP()+300);
                                isHealed = true;
                                if(boss.getHP()>boss.getMaxHP()){
                                    boss.setHP((int)(boss.getMaxHP()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void blockWorm(int i) {
        TetrisPiece block = playZone.getBlock();
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int[][] shape = block.getShape();
        int x = block.getX();
        int y = block.getY();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                if(shape[row][col] == 1){
                    if(col+x == projectileCoordinate[i]/25 && row+y == projectileDelay[i]/25){
                        isBlocked[i]=1;
                    } 
                }
            }
        }
    }

    public void stopAllTimer(){
        if(projectileTimer != null){
            projectileTimer.stop();
        }
    }

    public int getProjectileDelay() {
        return projectileDelay[0];
    }

    public void setProjectileDelay(int projectileDelay) {
        BossAttack.projectileDelay[0] = projectileDelay;
    } 
}
