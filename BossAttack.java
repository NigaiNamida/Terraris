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
    private Timer projectileTimer;
    private static int[] slimeFallsColumn = new int[1];
    private static int slimeFallsCount = 0;
    private static Image bossAttackImage;
    private static String path = "Assets/Image/Bosses/";
    
    public BossAttack(){
        playZone = GameFrame.getPlayZone();
        projectileTimer = new Timer(10, this);
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
            case "KingSlime":
                slimeFallsColumn = new int[1];
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

    @Override
    public void actionPerformed(ActionEvent e) {
        playZone = GameFrame.getPlayZone();
        if(!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying()){
            repaintPlayZone();
        }
    }

    void repaintPlayZone(){
        playZone.repaint();
    }

    public static void drawBossAtack(Graphics g){
        Boss boss = GameFrame.getBossPanel().getBoss();
        if(boss != null){
            switch (boss.getName()) {
                case "KingSlime":
                    drawKingSlimeAtack(g);
                    break;
                default:
                    break;
            }
        }
    }

    public static void drawKingSlimeAtack(Graphics g){
        Boss boss = GameFrame.getBossPanel().getBoss();
        for(int i = 0; i < slimeFallsCount; i++){
            bossAttackImage = new ImageIcon(path + boss.getName() + "/Attack/Slime_Falls.png").getImage();
            g.drawImage(bossAttackImage, slimeFallsColumn[i]*25, 0, null);
        }
    }

}
