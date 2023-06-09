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
    private int powerMultiplier;

    public Timer spawnTimer;
    
    private Font terrariaFont;
    private float brightness;

    public BossPanel(){
        stage = Theme.Day;
        terrariaFont = GameFrame.getTerrariaFont(20);
        playZone = GameFrame.getPlayZone();
        changeBGBrightness(stage);
        spawnDelay = 100;
        spawnChance = -5;
        tickCount = 0;
        canSpawn = false;
        powerMultiplier = 1;

        random = new Random();
        
        spawnTimer = new Timer(100, this);
        
        bossAttack = new BossAttack();
        bossTitle = new JLabel();
        bossTitle.setForeground(new Color(193,221,196,255));
        bossTitle.setFont(terrariaFont);
        this.add(bossTitle);

        this.setBounds(455, 20, 250, 250);
        this.setBackground(Color.black);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playZone = GameFrame.getPlayZone();
        if(!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying()){
            if (e.getSource() == spawnTimer){
                attemptSpawn();
            }
        }
        if (playZone.isGameOver()){
            stopAllTimer();
        }
    }

    public void stopAllTimer(){
        spawnTimer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackgroundImage(g);
        drawBackgroundBrightness(g);
        drawBorderImage(g);
        if(isBossAlive()){
            drawBoss(g);
            drawHP(g);
            if (boss.getName() == "WallOfFlesh") {
                drawBorderImage(g);
            }
        }
    }

    private void drawBackgroundImage(Graphics g){
        BossPanel bossPanel = GameFrame.getBossPanel();
        Image BGImage = new ImageIcon(bossPanel.getStage().getBossPanelBGImagePath()).getImage();
        g.drawImage(BGImage, 0, 0, null);
    }

    private void drawBackgroundBrightness(Graphics g){
        int brightness = (int)(255 * (1 - this.brightness));
        g.setColor(new Color(0,0,0,brightness));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawBorderImage(Graphics g) {
        BossPanel bossPanel = GameFrame.getBossPanel();
        Image BorderImage = new ImageIcon(bossPanel.getStage().getBossPanelBorderImagePath()).getImage();
        g.drawImage(BorderImage, 0, 0, null);
    }
    
    public void attemptSpawn(){
        if(canSpawn){
            float number = random.nextInt(1001)/10.0f;
            if(number <= spawnChance){
                switch (stage) {
                    case Day:
                    case Night:
                    case Corruption:
                    case Crimson:
                    case Jungle:
                    case Snow:
                    case Dungeon:
                    case UnderWorld:
                        ChatPanel.newMessage(stage, false);
                        stage = stage.next();
                        changeBGBrightness(stage);
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
                spawnChance++;
            }
            System.out.println(spawnChance + ("%"));
        }
    }

    public boolean isBossAlive(){
        return boss != null;
    }

    public void spawnBoss(){
        if(!isBossAlive()){
            switch (stage) {
                case KingSlime:
                    boss = new Boss("KingSlime",5000*powerMultiplier,5000*powerMultiplier,8);
                    bossTitle.setText("King Slime");
                    break;
                case EyeOfCthulhu:
                    boss = new Boss("EyeOfCthulhu",6000*powerMultiplier,6000*powerMultiplier,5);
                    bossTitle.setText("Eye Of Cthulhu");
                    break;
                case EaterOfWorld:
                    boss = new Boss("EaterOfWorld",15000*powerMultiplier,15000*powerMultiplier,10);
                    bossTitle.setText("Eater Of World");
                    break;
                case BrainOfCthulhu:
                    boss = new Boss("BrainOfCthulhu",10000*powerMultiplier,10000*powerMultiplier,10);
                    bossTitle.setText("Brain Of Cthulhu");
                    bossAttack.applyBlindness(stateTimer);
                    break;
                case QueenBee:
                    boss = new Boss("QueenBee",25000*powerMultiplier,25000*powerMultiplier,6);
                    bossTitle.setText("Queen Bee");
                    break;
                case DeerClops:
                    boss = new Boss("DeerClops",32000*powerMultiplier,32000*powerMultiplier,10);
                    bossTitle.setText("Deerclops");
                    break;
                case Skeletron:
                    this.setBounds(455, 20, 250, 306);
                    this.setBackground(Color.black);
                    boss = new Boss("Skeletron",40000*powerMultiplier,40000*powerMultiplier,10);
                    bossTitle.setText("Skeletron");
                    BossAttack.setProjectileDelay(1000);
                    break;
                case WallOfFlesh:
                    boss = new Boss("WallOfFlesh",50000*powerMultiplier,50000*powerMultiplier,15);
                    bossTitle.setText("Wall Of Flesh");
                    break;
                default:
                    break;
            }
            GameFrame.playSE(9);
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
            case "EaterOfWorld" :
            case "QueenBee" :
            case "DeerClops" :
            case "Skeletron" :
            case "WallOfFlesh" :
                if(phase >= 2){
                    exitFight();
                }
                break;
            case "EyeOfCthulhu":
                if(phase == 2){
                    boss.setMaxHP(3000*powerMultiplier);
                    boss.setHP(3000*powerMultiplier);
                    boss.setCooldownSeconds(10);
                    boss.setState(1);
                    GameFrame.playSE(9);
                    playZone = GameFrame.getPlayZone();
                    playZone.setBlindness(0);
                    BossAttack.setProjectileDelay(1000);
                }
                else if(phase >= 3){
                    exitFight();
                }
                break;
            case "BrainOfCthulhu":
                if(phase == 2){
                    boss.setMaxHP(10000*powerMultiplier);
                    boss.setHP(10000*powerMultiplier);
                    boss.setCooldownSeconds(5);
                    bossAttack.BossesDefeat(name);
                    boss.setState(1);
                    GameFrame.playSE(9);
                }
                else if(phase >= 3){
                    exitFight();
                }
                break;
            default:
                break;
        }
    }

    public void updateCooldownTimer(){
        switch (boss.getName()) {
            case "KingSlime":
                if(boss.getState() == 2 && stateTimer != 2){
                    boss.setCooldownSeconds(10);
                    stateTimer = 2;
                }
                else if(boss.getState() == 3  && stateTimer != 3){
                    boss.setCooldownSeconds(12);
                    stateTimer = 3;
                }
                break;
            case "EyeOfCthulhu" :
                if(boss.getPhase() == 1){
                    if(boss.getState() == 2 && stateTimer != 2){
                        boss.setCooldownSeconds(6);
                        stateTimer = 2;
                    }
                    else if(boss.getState() == 3  && stateTimer != 3){
                        boss.setCooldownSeconds(7);
                        stateTimer = 3;
                    }
                    bossAttack.applyBlindness(boss.getState());
                }
                break;
            case "EaterOfWorld":
                if(boss.getState() == 2 && stateTimer != 2){
                    boss.setCooldownSeconds(15);
                    stateTimer = 2;
                }
                else if(boss.getState() == 3  && stateTimer != 3){
                    boss.setCooldownSeconds(20);
                    stateTimer = 3;
                }
            case "BrainOfCthulhu":
                if(boss.getPhase() == 1){
                    if(boss.getState() == 2 && stateTimer != 2){
                        boss.setCooldownSeconds(6);
                        stateTimer = 2;
                    }
                    else if(boss.getState() == 3  && stateTimer != 3){
                        boss.setCooldownSeconds(7);
                        stateTimer = 3;
                    }
                }
                break;
            case "DeerClops":
                if(boss.getPhase() == 1){
                    if(boss.getState() == 2 && stateTimer != 2){
                        boss.setCooldownSeconds(7);
                        stateTimer = 2;  
                    }
                    else if(boss.getState() == 3  && stateTimer != 3){
                        boss.setCooldownSeconds(9);
                        stateTimer = 3;
                    }
                }
            case "Skeletron":
                if(boss.getPhase() == 1){
                    if(boss.getState() == 2 && stateTimer != 2){
                        boss.setCooldownSeconds(7);
                        stateTimer = 2;  
                        HoldPanel holdPanel = GameFrame.getHoldPanel();
                        holdPanel.repaint();
                    }
                    else if(boss.getState() == 3  && stateTimer != 3){
                        boss.setCooldownSeconds(5);
                        stateTimer = 3;
                        NextPanel nextPanel = GameFrame.getNextPanel();
                        nextPanel.repaint();
                    }
                }
            case "WallOfFlesh":
                if(boss.getPhase() == 1){
                    if(boss.getState() == 2 && stateTimer != 2){
                        boss.setCooldownSeconds(13);
                        stateTimer = 2;  
                    }
                    else if(boss.getState() == 3  && stateTimer != 3){
                        boss.setCooldownSeconds(10);
                        stateTimer = 3;
                    }
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
        AnimationThread.resetFrame();
        GameFrame.playMusic(0);
    }

    public void exitFight(){
        this.setBounds(455, 20, 250, 250);
        this.setBackground(Color.black);
        ChatPanel.newMessage(stage, true);
        GameFrame.stopMusic();
        GameFrame.getXPPanel().addBossXP((int)boss.getMaxHP());
        bossAttack.BossesDefeat(boss.getName());
        if(boss.getName().equals("WallOfFlesh")){
            powerMultiplier *= 10;
        }
        boss = null;
        spawnChance = 0;
        bossTitle.setText("");
        restartSpawnTimer();
        stage = stage.next();
        changeBGBrightness(stage);
        HoldPanel holdPanel = GameFrame.getHoldPanel();
        holdPanel.repaint();
        NextPanel nextPanel = GameFrame.getNextPanel();
        nextPanel.repaint();
        LevelPanel levelPanel = GameFrame.getLevelPanel();
        levelPanel.repaint();
        repaint();
        GameFrame.playSE(9);
        GameFrame.playMusic(0);
    }

    public void changeBGBrightness(Theme stage) {
        playZone = GameFrame.getPlayZone();
        switch (stage) {
            case Night:
            case EyeOfCthulhu:
            case Dungeon:
            case Skeletron:
                brightness = (10)/100.0f;
                break;
            default:
                brightness = (80)/100.0f;
                break;
        }
    }

    public void damageToBoss(int damage){
        if(isBossAlive()){
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
    }

    private void drawHP(Graphics g) {
        if (isBossAlive()){
            String path = "Assets/Image/Bosses/HealthBar/";
            Image bgImage = new ImageIcon(path + "BGBossBar.png").getImage();
            Image hpImage = new ImageIcon(path + "BossBar.png").getImage();
            Image mainImage = new ImageIcon(path + "MainHealth.png").getImage();
            Image redImage = new ImageIcon(path + "RedBossBar.png").getImage();
            Image iconImage = new ImageIcon(path + boss.getName() + "_" + boss.getPhase() + ".png").getImage();
            int HP = boss.getHP();
            double maxHP = boss.getMaxHP();
            int pixel = (int)((HP/maxHP) * 190);
            g.drawImage(bgImage, 20, 45,null);
            for (int i = 0; i < pixel; i++) {
                g.drawImage(mainImage, 30+i, 45,null);
            }
            g.drawImage(redImage, 30+pixel, 45,null);
            g.drawImage(hpImage, 20, 40,null);
            g.drawImage(iconImage, 21, 42,null);
        }
    }

    public void attack(){
        bossAttack.Attack(boss.getName(), boss.getPhase(), boss.getState());
    }

    public void animate(){
        repaint();
        boss.setFrame((boss.getFrame() + 1) % 8);
        if(stage == Theme.Snow && stage == Theme.DeerClops){
            PlayZone playZone = GameFrame.getPlayZone();
            playZone.repaint();
        }
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
                    path += (frame % 8) + ".png";  
                }
                else if (phase == 2 && (bossAttack.getProjectileDelay() < -250 || bossAttack.getProjectileDelay() > 500)){
                    path += (frame % 4)+ ".png"; 
                }
                break;
            case "EaterOfWorld":
                x = 50;
                y = 80;
                path += (frame % 4)+ ".png"; 
                break;
            case "BrainOfCthulhu":
                x = 50;
                y = 70;
                path += (frame % 8) + ".png";  
                break;
            case "QueenBee":
                x = 41;
                y = 60;
                path += (frame % 8) + ".png";  
                break;
            case "DeerClops":
                x = 50;
                y = 15;
                path += (frame % 8) + ".png";
                if(AnimationThread.getActonTick() >= 24){
                    path = "Assets/Image/Bosses/" + name + "/Blink_" + phase%8 + "_" + state + "_" + (frame % 8) + ".png";
                }
                break;  
            case "Skeletron":
                x = 29;
                y = 90;
                path += (frame % 8) + ".png";
                break;
            case "WallOfFlesh":
                x = 0;
                y = 0;
                path += (frame % 8) + ".png";
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

