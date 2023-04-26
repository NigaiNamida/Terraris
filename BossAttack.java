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
    private static int projectileCount;
    private static int[] projectileCoordinate;
    private static int[] projectileDelay;
    private static int[] projectileX;
    private static int[] projectileY;

    private static int[] slimePuddleColumn;

    private static int[] projectileDirection;
    private static boolean isHealed;
    private static boolean isDashed;

    private static int[] projectileLength;
    private static int[] turnPointX;
    private static int[] turnPointY;
    private static int[] blocked;

    private static int active;
    private static boolean[] isExploded;

    private static int tentacleGap;
    private static int tentacleX;
    private static int tentacleY;
    private static int tentacleLeft;
    private static int tentacleRight;
    private static int tentacleActive;
    private static int confuseCount;

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

        blocked = new int[1];

        active = 0;    
        tentacleLeft = -182;
        tentacleRight = 250;
        tentacleActive = 0;

        confuseCount = 0;
    }

    public void Attack(String bossName, int phase, int state) {
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
            case "BrainOfCthulhu":
                BrainOfCthulhuAttack(phase, state);
                break;
            case "QueenBee":
                QueenBeeAttack(state);
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
                blocked = new int[1];
                break;
            case "BrainOfCthulhu":
                active = 0;    
                tentacleLeft = -182;
                tentacleRight = 250;
                tentacleActive = 0;
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

    public void BrainOfCthulhuAttack(int phase, int state) {

        System.out.println(phase + " " + state);

        for (int i = 0; i < projectileCount; i++) {
            active = 0;
            if (blocked[i] == 0 && !isExploded[i]){
                active = 1;
                break;
            }
        }

        if(active == 2){
            active = 0;
        }

        if(active == 0){
            setCreeper(phase, state);
            projectileTimer = new Timer(50, this);
            projectileTimer.restart();
        }
        else{
            active = 2;
        }

        if (phase == 2){

            if(tentacleActive == 2){
                tentacleActive = 0;
            }

            if(tentacleActive == 0){
                setTentacle(state);
            }

            else{
                tentacleActive = 2;
            }
        }
    }

    public void QueenBeeAttack(int state){
        HoldPanel holdPanel = GameFrame.getHoldPanel();
        NextPanel nextPanel = GameFrame.getNextPanel();
        playZone = GameFrame.getPlayZone();
        setBeeline(state);
        projectileTimer = new Timer(50, this);
        projectileTimer.restart();
        if(holdPanel.getBlock() != null && holdPanel.getBlock().getColor() != BlockTexture.Honey.getColor()){
            playZone.setHoldHoneyBlock();
        }
        else if(nextPanel.getBlock().getColor() != BlockTexture.Honey.getColor()){
            playZone.setNextHoneyBlock();
        }
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

        blocked = new int[projectileCount];

        projectileX = new int[projectileCount];
        projectileY = new int[projectileCount];

        turnPointX = new int[projectileCount];
        turnPointY = new int[projectileCount];
        
        projectileDelay = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileDelay[i] = -i*75;
        }

        projectileLength = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileLength[i] = ran.nextInt(3)+4;
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 1; i < 9; i++) {
            list.add(i*25);
        }
        
        Collections.shuffle(list);
        
        projectileCoordinate = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileCoordinate[i] = list.get(i);
        }

    }

    public void setCreeper(int phase, int state) {

        projectileCount = state * 2;

        if (phase == 2){
            projectileCount = state - 1;
        }

        projectileX = new int[projectileCount];
        projectileY = new int[projectileCount];
        isExploded = new boolean[projectileCount];

        blocked = new int[projectileCount];

        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 1; i < 9; i++) {
            list.add(i*25);
        }
        
        Collections.shuffle(list);
        
        projectileX = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileX[i] = list.get(i);
        }

        list = new ArrayList<Integer>();
        for(int i = 2; i < 8; i++) {
            list.add(i*25);
        }

        Collections.shuffle(list);

        projectileY = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileY[i] = list.get(i);
        }

        active = 1;
    }

    public void setTentacle(int state) {
        Random ran = new Random();
        tentacleGap = (6-state)*25;

        tentacleX = (ran.nextInt(6-tentacleGap/25)+2)*25;

        tentacleY = (ran.nextInt(3)+9)*25;

        tentacleLeft = -182;

        tentacleRight = 250;

        tentacleActive = 1;
    }

    public void setBeeline(int state){
        Random ran = new Random();

        projectileCount = state*3;
        
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
            projectileDirection[i] = ran.nextInt(2);
            if(projectileDirection[i] == 1){
                projectileDelay[i] *= -1;
            }
            else{
                projectileDelay[i] += 250;
            }
        }

        projectileLength = new int[projectileCount];
        for(int i = 0; i < projectileCount; i++) {
            projectileLength[i] = ran.nextInt(3)+20;
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
                    case "BrainOfCthulhu":
                        movingCreeper();
                        if(boss.getPhase()==2){
                            movingTentacle();
                        }
                        break;
                    case "QueenBee":
                        movingBeeLine();
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
        Color slimePuddleColor = BlockTexture.SlimePuddle.getColor();
        Color slimeBlockColor = BlockTexture.SlimeBlock.getColor();
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
                        playZone.checkFullLine();
                    }
                }
            }
        }

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
            projectileDelay[i] += 7;
            if(blocked[i] == 0){
                blockDevourer(i);
                if((projectileDelay[i]/25 >= 0 && projectileDelay[i]/25 <= 19) && backgroundBlock[projectileDelay[i]/25][projectileCoordinate[i]/25] != null){
                    backgroundBlock[projectileDelay[i]/25][projectileCoordinate[i]/25] = null;
                }
            }
            else {
                if (blocked[i] == 1){
                    projectileX[i] = projectileCoordinate[i];
                    projectileY[i] = projectileDelay[i]; 

                    turnPointX[i] = projectileCoordinate[i];    
                    turnPointY[i] = projectileDelay[i]; 

                    blocked[i]++;
                }
                if (projectileCoordinate[i]/25 <= 4){
                    projectileX[i] -= 7;
                }
                else {
                    projectileX[i] += 7;
                }
            }
        }
    }

    public void movingCreeper(){   
        playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();   
        for (int i = 0; i < projectileCount; i++) {
            blockCreeper(i);
            if(active == 2 && !isExploded[i] && blocked[i] == 0){
                if((projectileY[i]/25 >= 0 && projectileY[i]/25 <= 19) && backgroundBlock[projectileY[i]/25][projectileX[i]/25] != null){
                    for (int row = -1; row <= 1; row++){
                        for (int col = -1; col <= 1; col++){
                            if((projectileY[i]/25)+row < 20 && (projectileX[i]/25)+col >= 0 && (projectileX[i]/25)+col < 10){
                                backgroundBlock[(projectileY[i]/25)+row][(projectileX[i]/25)+col] = null;
                            }
                        }
                    }
                    isExploded[i] = true;
                    GameFrame.playSE(10);
                }

                projectileY[i] += 20;

                if((projectileY[i]/25 >= 20)){
                    isExploded[i] = true;
                }
            }
        }
    }

    public void movingTentacle() {
        if(tentacleActive == 1){
            confuseBlock();
            if(tentacleLeft < tentacleX-182){
                tentacleLeft += 5;
            }
            if(tentacleRight > tentacleX+tentacleGap){
                tentacleRight -= 5;
            }
        }

        if(tentacleActive == 2){
            if(tentacleLeft > -182){
                tentacleLeft -= 5;
            }
            if(tentacleRight < 250){
                tentacleRight += 5;
            }
        }
    }

    public void movingBeeLine() {
        for (int i = 0; i < projectileCount; i++) {
            if(projectileDirection[i] == 1){
                projectileDelay[i] += 10;
            }
            else{
                projectileDelay[i] -= 10;
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
                case "BrainOfCthulhu":
                    drawBrainOfCthulhuAttack(g);
                    break;
                case "QueenBee":
                    drawQueenBeeAttack(g);
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
        Boss boss = GameFrame.getBossPanel().getBoss();
        for (int i = 0; i < projectileCount; i++) {
            int[] offset = new int[4];
            String direction = "";

            if (projectileCoordinate[i]/25 <= 4) {
                offset[0] = 0;
                offset[1] = -25;    
                offset[2] = -10;
                offset[3] = -7;
                direction = "Left";
            }

            else if(projectileCoordinate[i]/25 >= 5){
                offset[0] = 0;
                offset[1] = 25;
                offset[2] = 0;
                offset[3] = 7;
                direction = "Right";
            }

            for (int part = projectileLength[i]; part >= 0 ; part--) {
                if(part == projectileLength[i]){
                    if(projectileDelay[i]-offset[0]+4 <= turnPointY[i] || turnPointY[i] == 0){
                        bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Devourer_Head.png").getImage(); 
                        g.drawImage(bossAttackImage, (projectileCoordinate[i])-2 , projectileDelay[i]-offset[0], null);
                    }
                    if(blocked[i] != 0 && projectileX[i] != 0 && !hitCorner(i, part, offset[1], offset[2])){
                        bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Devourer_Head_" + direction + ".png").getImage(); 
                        g.drawImage(bossAttackImage, (projectileX[i])-offset[1] , projectileY[i]-2, null);
                    }
                }
                else if(part == 0){
                    if(projectileDelay[i]-offset[0]+4 <= turnPointY[i] || turnPointY[i] == 0){
                        bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Devourer_Tail.png").getImage(); 
                        g.drawImage(bossAttackImage, (projectileCoordinate[i])+2 , projectileDelay[i]-offset[0]-7, null);
                    }
                    if(blocked[i] != 0 && projectileX[i] != 0 && !hitCorner(i, part, offset[1], offset[2])){
                        bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Devourer_Tail_" + direction + ".png").getImage(); 
                        g.drawImage(bossAttackImage, (projectileX[i])-offset[1]-offset[3] , projectileY[i]+2, null);
                    }
                }
                else{
                    if(projectileDelay[i]-offset[0]+4 <= turnPointY[i] || turnPointY[i] == 0){
                        bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Devourer_Body.png").getImage(); 
                        g.drawImage(bossAttackImage, (projectileCoordinate[i])+2 , projectileDelay[i]-offset[0], null);
                        }
                    if(blocked[i] != 0 && projectileX[i] != 0 && !hitCorner(i, part, offset[1], offset[2])){
                        bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Devourer_Body_" + direction + ".png").getImage(); 
                        g.drawImage(bossAttackImage, (projectileX[i])-offset[1]-offset[2] , projectileY[i]+2, null);
                    }
                }

                offset[0] += 25;

                if (projectileCoordinate[i]/25 <= 4) {
                    offset[1] -= 25;          
                }

                else if(projectileCoordinate[i]/25 >= 5){
                    offset[1] += 25;
                }
            }
        }
    }

    public static void drawBrainOfCthulhuAttack(Graphics g){
        Boss boss = GameFrame.getBossPanel().getBoss();
        String name = boss.getName();
        drawCreeper(g, name);
        if(boss.getPhase()==2){
            drawTentacle(g, name);
        }
    }

    public static void drawCreeper(Graphics g, String name) {
        for(int i = 0; i < projectileCount; i++){
            if(!isExploded[i] && blocked[i] == 0){
                bossAttackImage = new ImageIcon(path + name + "/Attack/Creeper.png").getImage();
                g.drawImage(bossAttackImage, projectileX[i]-5 , projectileY[i]-5, null);  
            }  
        }
    }

    public static void drawTentacle(Graphics g, String name){
        bossAttackImage = new ImageIcon(path + name + "/Attack/Tentacle_Left.png").getImage();
        g.drawImage(bossAttackImage, tentacleLeft , tentacleY, null);  

        bossAttackImage = new ImageIcon(path + name + "/Attack/Tentacle_Right.png").getImage();
        g.drawImage(bossAttackImage, tentacleRight , tentacleY, null);  
    }

    public static void drawQueenBeeAttack(Graphics g) {
        Boss boss = GameFrame.getBossPanel().getBoss();
        for (int i = 0; i < projectileCount; i++) {
            int offset = 0;
            String direction = "";
            if (projectileDirection[i] == 0) {
                direction = "Left";
            }
            else {
                direction = "Right";
            }
            for (int part = projectileLength[i]; part >= 0 ; part--) {
                if(part == projectileLength[i]){
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Beeline_Head_" + direction +".png").getImage(); 
                    g.drawImage(bossAttackImage, projectileDelay[i]-offset ,(projectileCoordinate[i]*25)-4, null);
                }
                else if(part == 0){
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Beeline_Tail_" + direction +".png").getImage(); 
                    g.drawImage(bossAttackImage, projectileDelay[i]-offset ,(projectileCoordinate[i]*25)-4, null);
                }
                else{
                    bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Beeline_Body_" + direction +".png").getImage(); 
                    g.drawImage(bossAttackImage,projectileDelay[i]-offset ,(projectileCoordinate[i]*25)-4, null);
                }

                if (projectileDirection[i] == 0) {
                    offset -= 25;          
                }

                else {
                    offset += 25;
                }
            }
        }
    }

    public static boolean isHit(int i){
        PlayZone playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();
        Color slimePuddleColor = BlockTexture.SlimePuddle.getColor();
        
        return projectileDelay[i]/25 >= playZone.getGridRows() - 1 || (projectileDelay[i]/25 >= 0 && backgroundBlock[projectileDelay[i]/25 + 1][projectileCoordinate[i]] != null && backgroundBlock[projectileDelay[i]/25 + 1][projectileCoordinate[i]] != slimePuddleColor);
    }

    public static boolean hitCorner(int i, int part, int offset1, int offset2) {
        Boolean hitCorner = true;
        if (projectileCoordinate[i]/25 <= 4) {
            if(part == projectileLength[i] && part == 0){
                if (projectileX[i]-offset1-4 <= turnPointX[i]){
                    hitCorner = false;
                }
            }
            else{
                if (projectileX[i]-offset1-offset2-4 <= turnPointX[i]){
                    hitCorner = false;
                }
            }
        }
        else if(projectileCoordinate[i]/25 >= 5){
            if(part == projectileLength[i] && part == 0){
                if (projectileX[i]-offset1+4 >= turnPointX[i]){
                    hitCorner = false;
                }
            }
            else{
                if (projectileX[i]-offset1-offset2+4 >= turnPointX[i]){
                    hitCorner = false;
                }
            }
        }
        else {
            hitCorner = true;
        }
        return hitCorner;
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

    public void blockDevourer(int i) {
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
                        blocked[i]=1;
                    }

                }
            }
        }
    }

    public void blockCreeper(int i){
        TetrisPiece block = playZone.getBlock();
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int[][] shape = block.getShape();
        int x = block.getX();
        int y = block.getY();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                if(shape[row][col] == 1){
                    if(col+x == projectileX[i]/25 && row+y == projectileY[i]/25){
                        blocked[i]=1;
                    } 
                }
            }
        }
    }

    public void confuseBlock() {
        TetrisPiece block = playZone.getBlock();
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int[][] shape = block.getShape();
        int x = block.getX();
        int y = block.getY();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                if(shape[row][col] == 1){
                    if((col+x < tentacleX/25 || col+x > (tentacleX+tentacleGap)/25) && row+y == tentacleY/25){
                        confuseCount = 3;
                        tentacleActive = 2;
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

    public static int getConfuseCount() {
        return confuseCount;
    }

    public static void setConfuseCount(int confuseCount) {
        BossAttack.confuseCount = confuseCount;
    }

}

