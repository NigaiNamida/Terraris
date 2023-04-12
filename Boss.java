public class Boss{
    private String name;
    private double maxHP;
    private int HP;
    private int state;   
    private int phase;
    private int frame;
    private int cooldownSeconds;

    Boss(String name,int HP,double maxHP,int cooldownSeconds){
        this.name = name;
        this.maxHP = maxHP;
        this.HP = HP;
        this.cooldownSeconds = cooldownSeconds;
        state = 1;
        phase = 1;
        frame = 0;
    }

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public void setCooldownSeconds(int cooldownSeconds) {
        this.cooldownSeconds = cooldownSeconds;
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
