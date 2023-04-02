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
    private BossAttack bossAttack;

    public float spawnChance;
    private Random random;
    public boolean canSpawn;
    private int stateTimer;

    public Timer spawnTimer;

    public BossPanel(){
        random = new Random();
        spawnChance = -5;
        canSpawn = false;
        
        spawnTimer = new Timer(100, this);
        
        playZone = GameFrame.getPlayZone();
        bossAttack = new BossAttack();
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
        playZone = GameFrame.getPlayZone();
        if(!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying()){
            if (e.getSource() == spawnTimer){
                attemptSpawn();
            }
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
            stopAllTimer();
        }
    }

    public void stopAllTimer(){
        spawnTimer.stop();
        if(boss != null){
            boss.stopAnimateTimer();
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
            float number = random.nextInt(1001)/10.0f;
            if(number <= spawnChance){
                spawnBoss();
            }
            else{
                spawnChance+=0.5;
            }
            System.out.println(spawnChance + ("%"));
        }
    }

    public void spawnBoss(){
        if(boss == null){
            if(LevelPanel.getLevel() <= 5){
                boss = new Boss("KingSlime",5000,5000,8000);
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
        if(HP <= maxHP*0.25)
            boss.setState(3);
        else if(HP <= maxHP*0.5)
            boss.setState(2);
        updateCooldownTimer();
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

    public void updateCooldownTimer(){
        switch (boss.getName()) {
            case "KingSlime":
                if(boss.getState() == 2 && stateTimer != 2){
                    boss.attackTimer = new Timer(10000, this);
                    boss.attackTimer.restart();
                    stateTimer = 2;
                }
                else if(boss.getState() == 3  && stateTimer != 3){
                    boss.attackTimer = new Timer(12000, this);
                    boss.attackTimer.restart();
                    stateTimer = 3;
                }
                break;
            default:
                break;
        }
    }

    public void enterFight(){
        spawnTimer.stop();
        canSpawn = false;
        spawnChance = 0;
        stateTimer = 1;
        System.out.println("Boss Spawn Deactive");
    }

    public void exitFight(){
        boss.stopAnimateTimer();
        boss.stopAttackTimer();
        GameFrame.getXPPanel().addBossXP((int)boss.getMaxHP());
        repaint();
        bossAttack.BossesDefeat(boss.getName());
        boss = null;
        spawnChance = 0;
        bossTitle.setText("");
        restartSpawnTimer();
        repaint();
    }

    public void damageToBoss(int damage){
        if(boss.getHP() > 0){
            boss.applyDamage(damage);
            updateState();
        }
        if(boss.getHP() <= 0){
            boss.changePhase();
            updatePhase();
        }
        repaint();
    }

    private void drawHP(Graphics g) {
        if (boss != null){
            int HP = boss.getHP();
            double maxHP = boss.getMaxHP();
            int pixel = (int)((HP/maxHP) * 210) + 1;
            g.setColor(Color.red);
            g.fillRect(20, 40,pixel,2);
        }
    }

    public void attack(){
        bossAttack.BossesAttack(boss.getName(), boss.getPhase(), boss.getState());
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

    public float getSpawnChance() {
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

