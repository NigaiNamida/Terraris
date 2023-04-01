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
    private JLabel bossTitle;
    private double bossMaxHP;
    private int bossHP;
    private String bossName;
    public int attack;
    public int cooldown;

    private int frame;
    private int state;   
    public int phase;
    private double spawn;
    public int spawnChance;
    private Random random;
    private boolean transformed;
    public boolean canSpawn;
    private Image bossImage;
    private Timer animateTimer;
    public Timer spawnTimer;
    public Timer attackTimer;
    public PlayZone playZone;

    public BossPanel(){
        bossName = "";
        spawn = 0;
        frame = 0;
        state = 0;
        phase = 0;
        cooldown = 0;
        spawnChance = 0;
        canSpawn = false;
        transformed = false;
        
        random = new Random();
        animateTimer = new Timer(200, this);
        spawnTimer = new Timer(100, this);
        attackTimer = new Timer(cooldown, this);
        animateTimer.stop();
        spawnTimer.stop();
        attackTimer.stop();
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
        if (!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying() && e.getSource() == attackTimer)
            attack();
        if (playZone.isGameOver()){
            animateTimer.stop();
            spawnTimer.stop();
            attackTimer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHP(g);
        drawBoss(g);
    }

    public void randomSpawn(){
        if (canSpawn){
            spawn = random.nextInt(101);
            if(spawn <= spawnChance){
                spawnBoss();
            }
            else spawnChance++;
            System.out.println(spawnChance + ("%"));
        }
    }

    public void spawnBoss(){
        if(phase == 0){
            if(LevelPanel.getLevel() <= 5){
                bossName = "KingSlime";
                bossMaxHP = 5000;
                bossHP = 5000;
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
            if(bossHP <= bossMaxHP/4.0)
                state = 3;
            else if(bossHP <= bossMaxHP/2.0)
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

    public void enterFight(){
        state = 1;
        phase = 1;
        setAttackTimer();
        animateTimer.restart();
        attackTimer.restart();
        spawnTimer.stop();
        canSpawn = false;
        spawnChance = 0;
        System.out.println("Boss Spawn Deactive");
    }

    public void exitFight(){
        bossName = "null";
        phase = 0;
        state = 0;
        transformed = false;
        spawnChance = 0;
        bossTitle.setText("");
        animateTimer.stop();
        attackTimer.stop();
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

    public void setAttackTimer(){
        if (bossName == "KingSlime"){
           cooldown = 10000; 
        }
        else if (bossName == "EyeOfCthulhu"){
            cooldown = 15000; 
        }
        attackTimer = new Timer(cooldown, this);
    }

    public void attack(){
        System.out.println("U Stupid");
    }

    public void animate(){
        repaint();
        frame = (frame + 1) % 8;
    }

    public void drawBoss(Graphics g){  
        if (bossName == "KingSlime"){       
            if (state == 1){
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
            else if (state == 2 || state == 3){
                if(frame % 4 == 0){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + state + "_0.png").getImage();
                    g.drawImage(bossImage ,40, 50,null);}
                else if(frame % 4 == 1){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + state + "_1.png").getImage();
                    g.drawImage(bossImage ,40, 50, null);}
                else if(frame % 4 == 2){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + state + "_2.png").getImage();
                    g.drawImage(bossImage ,40, 50, null);}
                else if(frame % 4 == 3){
                    bossImage = new ImageIcon("Assets/Image/Bosses/" + bossName + "/Idle_" + state + "_3.png").getImage();
                    g.drawImage(bossImage ,40, 50, null);}  
            }
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

    public Timer getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(Timer spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    public void restartSpawnTimer() {
        spawnTimer.restart();
    }

    public void stopSpawnTimer() {
        spawnTimer.stop();
    }

    public int getPhase() {
        return phase;
    }

    public  void setPhase(int phase) {
        this.phase = phase;
    }

    public int getSpawnChance() {
        return spawnChance;
    }

    public void setSpawnChance(int spawnChance) {
        this.spawnChance = spawnChance;
    }

    public boolean isCanSpawn() {
        return canSpawn;
    }

    public void setCanSpawn(boolean canSpawn) {
        this.canSpawn = canSpawn;
    }
}

