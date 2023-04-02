import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    private static Color slimePuddleColor;
    private static Image bossAttackImage;
    private static String path = "Assets/Image/Bosses/";
    
    public BossAttack(){
        playZone = GameFrame.getPlayZone();
        projectileTimer = new Timer(25, this);
        slimeFallsColumn = new int[1];
        slimeFallsDelay = new int[1];
        slimePuddleColumn = new int[1];
        slimeFallsCount = 0;
    }

    public void attack(String bossName, int phase, int state) {
        switch (bossName) {
            case "KingSlime":
                KingSlimeAttack(phase, state);
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
            default:
                break;
        }
    }
    
    public void KingSlimeAttack(int phase, int state){
        setSlimeRainColumn(phase, state);
        projectileTimer.restart();
    }

    public void setSlimeRainColumn(int phase, int state){
        slimeFallsCount = 0;
        
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

        System.out.println(Arrays.toString(slimeFallsColumn));
    }

    public void applyGravitySlimeRain(){
        playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();
        slimePuddleColor = playZone.getSlimePuddleColor();
        for(int i = 0; i < slimeFallsCount; i++){
            if(!isHit(i) && slimePuddleColumn[i] == 0){        
                slimeFallsDelay[i] += 5;
            }
            else {
                slimePuddleColumn[i] ++;
                if(slimePuddleColumn[i] == 1 && backgroundBlock[slimeFallsDelay[i]/25][slimeFallsColumn[i]] == null){
                    playZone.setBackgroundBlock(slimeFallsDelay[i]/25, slimeFallsColumn[i], slimePuddleColor);
                }
                playZone.repaint();
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        playZone = GameFrame.getPlayZone();
        boss = GameFrame.getBossPanel().getBoss();
        if(!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying() && e.getSource() == projectileTimer){
            repaintPlayZone();
            if (boss != null){
                switch (boss.getName()) {
                    case "KingSlime":
                        applyGravitySlimeRain();
                        break;
                    default:
                        break;
                }
            }
            repaintPlayZone();
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
    
    public static boolean isHit(int i){
        PlayZone playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlock = PlayZone.getBackgroundBlock();
        slimePuddleColor = playZone.getSlimePuddleColor();
        
        return slimeFallsDelay[i]/25 >= playZone.getGridRows() - 1 || (slimeFallsDelay[i]/25 >= 0 && backgroundBlock[slimeFallsDelay[i]/25 + 1][slimeFallsColumn[i]] != null && backgroundBlock[slimeFallsDelay[i]/25 + 1][slimeFallsColumn[i]] != slimePuddleColor);
    }

    public void stopAllTimer(){
        projectileTimer.stop();
    }

}
