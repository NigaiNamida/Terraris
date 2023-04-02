import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BossAttack extends JPanel implements ActionListener{

    private PlayZone playZone;
    private Boss boss;
    private Timer projectileTimer;
    private static int[] slimeFallsColumn;
    private static int[] slimeFallsDelay;
    private static boolean[] slimePuddleSet;
    private static int slimeFallsCount;
    private static Image bossAttackImage;
    private static String path = "Assets/Image/Bosses/";
    
    public BossAttack(){
        playZone = GameFrame.getPlayZone();
        projectileTimer = new Timer(5, this);
        slimeFallsColumn = new int[1];
        slimeFallsDelay = new int[1];
        slimePuddleSet = new boolean[1];
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
                slimePuddleSet = new boolean[1];
                slimeFallsCount = 0;
                break;
            default:
                break;
        }
    }
    
    public void KingSlimeAttack(int phase, int state){
        SlimeRainColumnSet(phase, state);
        projectileTimer.restart();
    }

    public void SlimeRainColumnSet(int phase, int state){
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
        slimePuddleSet = new boolean[slimeFallsCount];

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
            if(!isHit(i) && !slimePuddleSet[i]){        
                slimeFallsDelay[i] ++;
            }
            else {
                slimePuddleSet[i] = true;
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        playZone = GameFrame.getPlayZone();
        boss = GameFrame.getBossPanel().getBoss();
        if(!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying()){
            if (boss != null){
                switch (boss.getName()) {
                    case "KingSlime":
                    applyGravitySlimeRain();
                        break;
                    default:
                        break;
                }
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBossAttack(g);
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
        PlayZone playZone = GameFrame.getPlayZone();
        for(int i = 0; i < slimeFallsCount; i++){
            if(!isHit(i) && !slimePuddleSet[i]){
                bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Slime_Falls.png").getImage();
                g.drawImage(bossAttackImage, slimeFallsColumn[i]*25, slimeFallsDelay[i], playZone);    
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
