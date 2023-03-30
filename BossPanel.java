import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.util.Random;

public class BossPanel extends JPanel implements ActionListener{
    private static JLabel bossTitle;
    private static double bossMaxHP;
    private static int bossHP;
    private static String bossName;

    private static int frame;
    private static int state;   
    public static int phase;
    private static double spawn;
    public static int spawnChance;
    private static Random random;
    private static boolean transformed;
    public static boolean canSpawn;
    private static Image bossImage;
    private static Timer animateTimer;
    public static Timer spawnTimer;
    public static PlayZone playZone;

    public BossPanel(){
        bossName = "";
        spawn = 0;
        frame = 0;
        state = 0;
        phase = 0;
        spawnChance = 0;
        canSpawn = false;
        transformed = false;
        
        random = new Random();
        animateTimer = new Timer(200, this);
        spawnTimer = new Timer(10000, this);
        animateTimer.stop();
        spawnTimer.stop();
        playZone = GameFrame.getPlayZone();
        bossTitle = new JLabel(bossName);
        bossTitle.setForeground(new Color(193,221,196,255));
        bossTitle.setFont(new Font("Futura",Font.BOLD,15));
        this.add(bossTitle);

        this.setBounds(455, 20, 250, 250);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(playZone == null){
            playZone = GameFrame.getPlayZone();
        }
        if (!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying() && e.getSource() == animateTimer)
            animate();
        if (!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying() && e.getSource() == spawnTimer)
            randomSpawn();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHP(g);
        drawBoss(g);
    }

    public static void randomSpawn(){
        if (canSpawn){
            spawn = random.nextInt(101);
            if(spawn <= spawnChance){
                spawnBoss();
            }
            else spawnChance++;
            System.out.println(spawnChance + ("%"));
        }
    }

    public static void spawnBoss(){
        if(phase == 0){
            if(LevelPanel.getLevel() <= 5){
                bossName = "KingSlime";
                bossMaxHP = 2000;
                bossHP = 2000;
                bossTitle.setText("King Slime");
            }
            else if (LevelPanel.getLevel() <= 10){
                bossName = "EyeOfCthulhu";
                bossMaxHP = 4000;
                bossHP = 4000;
                bossTitle.setText("Eye Of Cthulhu");
            }
            enterFight();
        }

        if(bossName == "KingSlime"){
            if(bossHP <= 500)
                state = 3;
            else if(bossHP <= 1000)
                state = 2;
            if(phase >= 2)
                exitFight();
        }

        else if(bossName == "EyeOfCthulhu"){
            if(phase == 2 && !transformed){
                bossMaxHP = 4000;
                bossHP = 4000;
                transformed = true;
            }
            else if(phase >= 3){
                exitFight();
            }
        }
    }

    public static void enterFight(){
        state++;
        phase++;
        animateTimer.restart();
        spawnTimer.stop();
        canSpawn = false;
        spawnChance = 0;
        System.out.println("Boss Spawn Deactive");
    }

    public static void exitFight(){
        bossName = "null";
        phase = 0;
        state = 0;
        transformed = false;
        spawnChance = 0;
        bossTitle.setText("");
        animateTimer.stop();
        spawnTimer.restart();
    }

    public void damageToBoss(int damage){
        if (phase != 0){
            if(bossHP > 0){
                bossHP -= damage;
                spawnBoss();
            }
            if(bossHP <= 0){
                phase ++;
                spawnBoss();
            }
            repaint();
        }
    }

    private void drawHP(Graphics g) {
        if (phase != 0){
            int pixel = (int)((bossHP/bossMaxHP) * 210);
            g.setColor(Color.red);
            g.fillRect(20, 40,pixel,2);
        }
    }

    public void animate(){
        repaint();
        frame = (frame + 1) % 8;
    }

    public void drawBoss(Graphics g){  
        if (bossName == "KingSlime"){       
            if(frame % 2 == 0){
                bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + state + "_0.png").getImage();
                g.drawImage(bossImage ,40, 50,null);}
            else if(frame % 4 == 1){
                bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + state + "_1.png").getImage();
                g.drawImage(bossImage ,40, 50, null);}
            else if(frame % 4 == 3){
                bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + state + "_2.png").getImage();
                g.drawImage(bossImage ,40, 50, null);}  
        }  
        else if (bossName == "EyeOfCthulhu"){       
            if (phase == 1){
                if(frame % 4 == 0){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_0.png").getImage();
                    g.drawImage(bossImage ,25, 50,null);}
                else if(frame == 1){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_1.png").getImage();
                    g.drawImage(bossImage ,25, 50, null);}
                else if(frame == 2){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_2.png").getImage();
                    g.drawImage(bossImage ,25, 50, null);}  
                else if(frame == 3){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_3.png").getImage();
                    g.drawImage(bossImage ,25, 50, null);}
                else if(frame == 5){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_4.png").getImage();
                    g.drawImage(bossImage ,25, 50, null);}
                else if(frame == 6){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_5.png").getImage();
                    g.drawImage(bossImage ,25, 50, null);}  
                else if(frame == 7){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_6.png").getImage();
                    g.drawImage(bossImage ,25, 50, null);}    
            }
            else if (phase == 2){
                if(frame % 2 == 0){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_0.png").getImage();
                    g.drawImage(bossImage ,25, 50,null);}
                else if(frame % 4 == 1){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_1.png").getImage();
                    g.drawImage(bossImage ,25, 50, null);}
                else if(frame % 4 == 3){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + phase + "_2.png").getImage();
                    g.drawImage(bossImage ,25, 50, null);}  
            }
        }  
    }
}

