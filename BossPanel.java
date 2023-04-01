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
    public PlayZone playZone;
    private JLabel bossTitle;
    private Image bossImage;
    private Boss boss;

    public int spawnRate;
    private Random random;
    public boolean canSpawn;

    public Timer spawnTimer;

    public BossPanel(){
        spawnRate = 0;
        canSpawn = false;
        
        random = new Random();

        spawnTimer = new Timer(100, this);
        spawnTimer.stop();
        
        playZone = GameFrame.getPlayZone();
        bossTitle = new JLabel();
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
        if(!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying()){
            if (e.getSource() == spawnTimer)
                attemptSpawn();
            if(boss != null){
                if (e.getSource() == boss.getAnimateTimer()){
                    animate();
                }
                if (e.getSource() == boss.getAttackTimer()){
                    attack();
                }
            }
        }
        if (playZone.isGameOver()){
            boss.stopAnimateTimer();
            spawnTimer.stop();
            boss.stopAttackTimer();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(boss != null){
            drawBoss(g);
            drawHP(g);
        }
    }
    
    public void attemptSpawn(){
        if(canSpawn){
            int number = random.nextInt(101);
            if(number <= spawnRate){
                spawnBoss();
            }
            else{
                spawnRate++;
            }
            System.out.println(spawnRate + ("%"));
        }
    }

    public void spawnBoss(){
        if(boss == null){
            if(LevelPanel.getLevel() <= 5){
                boss = new Boss("KingSlime",5000,5000,10000);
                bossTitle.setText("King Slime");
            }
            else if (LevelPanel.getLevel() <= 10){
                boss = new Boss("EyeOfCthulhu",4000,4000,15000);
                bossTitle.setText("Eye Of Cthulhu");
            }
            enterFight();
        }
    }

    public void updateState(){
        int HP = boss.getHP();
        double maxHP = boss.getMaxHP();
        if(HP <= maxHP/4.0)
            boss.setState(3);
        else if(HP <= maxHP/2.0)
            boss.setState(2);
    }

    public void updatePhase(){
        String name = boss.getName();
        int phase = boss.getPhase();
        if(name == "KingSlime"){
            if(phase >= 2){
                exitFight();
            }
        }
        else if(name == "EyeOfCthulhu"){
            if(phase == 2){
                boss.setMaxHP(4000);
                boss.setHP(4000);
            }
            else if(phase >= 3){
                exitFight();
            }
        }
    }

    public void enterFight(){
        spawnTimer.stop();
        canSpawn = false;
        spawnRate = 0;
        System.out.println("Boss Spawn Deactive");
    }

    public void exitFight(){
        boss.stopAnimateTimer();
        boss.stopAttackTimer();
        repaint();
        boss = null;
        spawnRate = 0;
        bossTitle.setText("");
        spawnTimer.restart();
        repaint();
    }

    public void damageToBoss(int damage){
        int HP = boss.getHP();
        if(HP > 0){
            boss.applyDamage(damage);
            updateState();
        }
        if(HP <= 0){
            boss.changePhase();
            System.out.println("phase is now : "+boss.getPhase());
            updatePhase();
        }
        repaint();
    }

    private void drawHP(Graphics g) {
        if (boss != null){
            int HP = boss.getHP();
            double maxHP = boss.getMaxHP();
            int pixel = (int)((HP/maxHP) * 210) + 1;
            System.out.println("HP : " + HP);
            g.setColor(Color.red);
            g.fillRect(20, 40,pixel,2);
        }
    }

    public void attack(){
        System.out.println("U Stupid");
    }

    public void animate(){
        repaint();
        boss.setFrame((boss.getFrame() + 1) % 8);
    }

    public void drawBoss(Graphics g){  
        String name = boss.getName();
        int state = boss.getState();
        int frame = boss.getFrame();
        int phase = boss.getPhase();
        int x = 0;
        int y = 0;
        String path = "Assets/Image/Bosses/" + name;
        switch (name) {
            case "KingSlime":
                x = 28;
                y = 50;
                path += "/Idle_" + state + "_" + (frame % 4)+ ".png"; 
                break;
            case "EyeOfCthulhu":
                x = 40;
                y = 50;
                if (phase == 1){
                    path += "/Idle_" + phase + "_" + frame + ".png";  
                }
                else if (phase == 2){
                    path += "/Idle_" + phase + "_" + (frame % 4)+ ".png"; 
                }
                break;
        }
        bossImage = new ImageIcon(path).getImage();
        g.drawImage(bossImage ,x, y,null);
    }

    public Boss getBoss() {
        return boss;
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

    public int getSpawnChance() {
        return spawnRate;
    }

    public void setSpawnChance(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    public boolean isCanSpawn() {
        return canSpawn;
    }

    public void setCanSpawn(boolean canSpawn) {
        this.canSpawn = canSpawn;
    }
}

