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
    private Theme stage;

    public float spawnChance;
    private Random random;
    public boolean canSpawn;
    private int stateTimer;
    private int tickCount;
    private int spawnDelay;

    public Timer spawnTimer;
    
    private Font terrariaFont;

    public BossPanel(){
        stage = Theme.Day;
        terrariaFont = GameFrame.getTerrariaFont(20);
        spawnDelay = 100;
        spawnChance = -5;
        tickCount = 0;
        canSpawn = false;

        random = new Random();
        
        spawnTimer = new Timer(100, this);
        
        playZone = GameFrame.getPlayZone();
        bossAttack = new BossAttack();
        bossTitle = new JLabel();
        bossTitle.setForeground(new Color(193,221,196,255));
        bossTitle.setFont(terrariaFont);
        this.add(bossTitle);

        this.setBounds(455, 20, 250, 250);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3,true));
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
        bossAttack.stopAllTimer();
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
                switch (stage) {
                    case Day:
                    case Night:
                    case Corruption:
                        ChatPanel.newMessage(stage, false);
                        stage = stage.next();
                        spawnChance = 100;
                        break;
                    default:
                        tickCount++;
                        if(tickCount == 15000/spawnDelay){
                            tickCount = 0;
                            spawnBoss();
                        }
                        break;
                }
            }
            else{
                spawnChance+=0.5;
            }
            System.out.println(spawnChance + ("%"));
        }
    }

    public void spawnBoss(){
        if(boss == null){
            switch (stage) {
                case KingSlime:
                    boss = new Boss("KingSlime",5000,5000,8000);
                    bossTitle.setText("King Slime");
                    GameFrame.playSE(9);
                    break;
                case EyeOfCthulhu:
                    boss = new Boss("EyeOfCthulhu",6000,6000,5000);
                    bossTitle.setText("Eye Of Cthulhu");
                    GameFrame.playSE(9);
                    break;
                case EaterOfWorld:
                    boss = new Boss("EaterOfWorld",15000,15000,10000);
                    bossTitle.setText("Eater Of World");
                    GameFrame.playSE(9);
                    break;
                default:
                    break;
            }
            enterFight();
        }
    }

    public void updateState(){
        int HP = boss.getHP();
        double maxHP = boss.getMaxHP();
        if(HP <= maxHP*0.3)
            boss.setState(3);
        else if(HP <= maxHP*0.6)
            boss.setState(2);
        updateCooldownTimer();
    }

    public void updatePhase(){
        String name = boss.getName();
        int phase = boss.getPhase();
        switch (name) {
            case "KingSlime":
                if(phase >= 2){
                    exitFight();
                }
                break;
            case "EyeOfCthulhu":
                if(phase == 2){
                    bossAttack.stopAllTimer();
                    boss.setMaxHP(3000);
                    boss.setHP(3000);
                    boss.setCooldown(15000);
                    GameFrame.playSE(9);
                    playZone = GameFrame.getPlayZone();
                    playZone.setBlindness(0);
                    bossAttack.setProjectileDelay(1000);
                }
                else if(phase >= 3){
                    exitFight();
                }
                break;
            case "EaterOfWorld" :
                if(phase >= 2){
                    exitFight();
                }
            default:
                break;
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
            case "EyeOfCthulhu" :
                if(boss.getPhase() == 1){
                    if(boss.getState() == 2 && stateTimer != 2){
                        boss.attackTimer = new Timer(6000, this);
                        boss.attackTimer.restart();
                        stateTimer = 2;
                    }
                    else if(boss.getState() == 3  && stateTimer != 3){
                        boss.attackTimer = new Timer(7000, this);
                        boss.attackTimer.restart();
                        stateTimer = 3;
                    }
                    bossAttack.applyBlindness(boss.getState());
                }
                break;
            case "EaterOfWorld":
                if(boss.getState() == 2 && stateTimer != 2){
                    boss.attackTimer = new Timer(15000, this);
                    boss.attackTimer.restart();
                    stateTimer = 2;
                }
                else if(boss.getState() == 3  && stateTimer != 3){
                    boss.attackTimer = new Timer(20000, this);
                    boss.attackTimer.restart();
                    stateTimer = 3;
                }
                break;
            default:
                break;
        }
    }

    public void enterFight(){
        ChatPanel.newMessage(stage, false);
        GameFrame.stopMusic();
        spawnTimer.stop();
        canSpawn = false;
        spawnChance = 0;
        stateTimer = 1;
        System.out.println("Boss Spawn DeActive");
        GameFrame.playMusic(0);
    }

    public void exitFight(){
        ChatPanel.newMessage(stage, true);
        GameFrame.stopMusic();
        boss.stopAnimateTimer();
        boss.stopAttackTimer();
        GameFrame.getXPPanel().addBossXP((int)boss.getMaxHP());
        bossAttack.BossesDefeat(boss.getName());
        boss = null;
        spawnChance = 0;
        bossTitle.setText("");
        restartSpawnTimer();
        stage = stage.next();
        repaint();
        GameFrame.playSE(9);
        GameFrame.playMusic(0);
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
        bossAttack.attack(boss.getName(), boss.getPhase(), boss.getState());
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
        String path = "Assets/Image/Bosses/" + name + "/Idle_" + phase + "_" + state + "_" ;
        switch (name) {
            case "KingSlime":
                x = 28;
                y = 50;
                path += (frame % 4)+ ".png"; 
                break;
            case "EyeOfCthulhu":
                x = 40;
                y = 50;
                if (phase == 1){
                    path += frame + ".png";  
                }
                else if (phase == 2 && (bossAttack.getProjectileDelay() < -250 || bossAttack.getProjectileDelay() > 500)){
                    path += (frame % 4)+ ".png"; 
                }
                break;
            case "EaterOfWorld":
                x = 50;
                y = 50;
                path += (frame % 4)+ ".png"; 
                break;
        }
        bossImage = new ImageIcon(path).getImage();
        g.drawImage(bossImage ,x, y,null);
    }

    
    public Theme getStage() {
        return stage;
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

