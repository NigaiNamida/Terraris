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
    private static boolean[] slimePuddleColumn;
    private static int slimeFallsCount;
    private static Image bossAttackImage;
    private static String path = "Assets/Image/Bosses/";
    
    public BossAttack(){
        playZone = GameFrame.getPlayZone();
        projectileTimer = new Timer(5, this);
        slimeFallsColumn = new int[1];
        slimeFallsDelay = new int[1];
        slimePuddleColumn = new boolean[1];
        slimeFallsCount = 0;
    }

    public void BossesAttack(String bossName, int phase, int state) {
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
                slimePuddleColumn = new boolean[1];
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
        slimePuddleColumn = new boolean[slimeFallsCount];

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
        for(int i = 0; i < slimeFallsCount; i++){
            if(!isHit(i) && !slimePuddleColumn[i]){        
                slimeFallsDelay[i] ++;
            }
            else {
                slimePuddleColumn[i] = true;
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
            if(!isHit(i) && !slimePuddleColumn[i]){
                bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Slime_Falls.png").getImage();
                g.drawImage(bossAttackImage, slimeFallsColumn[i]*25, slimeFallsDelay[i], null);    
            }
        }
    }
    
    public static boolean isHit(int i){
        PlayZone playZone = GameFrame.getPlayZone();
        Color[][] backgroundBlack = PlayZone.getBackgroundBlock();
        
        return slimeFallsDelay[i]/25 >= playZone.getGridRows() - 1 || (slimeFallsDelay[i]/25 >= 0 && backgroundBlack[slimeFallsDelay[i]/25 + 1][slimeFallsColumn[i]] != null);
    }

    public void stopAllTimer(){
        projectileTimer.stop();
    }

}
