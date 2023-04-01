import javax.swing.Timer;

public class Boss{
    private String name;
    private double maxHP;
    private int HP;
    private int state;   
    private int phase;
    private int frame;
    private int cooldown;
    private Timer animateTimer;
    public Timer attackTimer;

    Boss(String name,int HP,double maxHP,int cooldown){
        BossPanel bossPanel;
        bossPanel = GameFrame.getBossPanel();
        this.name = name;
        this.maxHP = maxHP;
        this.HP = HP;
        this.cooldown = cooldown;
        state = 1;
        phase = 1;
        frame = 0;
        animateTimer = new Timer(200, bossPanel);
        attackTimer = new Timer(cooldown, bossPanel);
        startAnimateTimer();
        startAttackTimer();
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public Timer getAnimateTimer() {
        return animateTimer;
    }

    public void setAnimateTimer(Timer animateTimer) {
        this.animateTimer = animateTimer;
    }

    public Timer getAttackTimer() {
        return attackTimer;
    }

    public void setAttackTimer(Timer attackTimer) {
        this.attackTimer = attackTimer;
    }

    public void startAnimateTimer(){
        animateTimer.start();
    }

    public void stopAnimateTimer(){
        animateTimer.stop();
    }

    public void startAttackTimer(){
        attackTimer.start();
    }

    public void stopAttackTimer(){
        attackTimer.stop();
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPhase() {
        return phase;
    }

    public void changePhase(){
        phase++;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void applyDamage(int damage){
        HP -= damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(double maxHP) {
        this.maxHP = maxHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    
}
