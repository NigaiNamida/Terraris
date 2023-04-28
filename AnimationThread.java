public class AnimationThread extends Thread{

    private static PlayZone playZone;
    private static int FPS;
    private static double interval;
    private static double nextTime;
    private static int frame;
    private static int freezeFrame;
    private static int actionTick;
    private static int freezeActionTick;
    private static int snowFallSpeed;

    @Override
    public void run() {
        setInitialValue();
        frame = 0;
        actionTick = 0;
        snowFallSpeed = 3;
        while(GameFrame.isPlaying()){
            if(!playZone.isGameOver()){
                if(GameFrame.getEffect().getClip() != null)
                    GameFrame.getEffect().setVolume(SettingPanel.getFXVolume()/2);
                if(!KeyHandler.isPause()){
                    updateFrame();
                    animate(frame);
                    frame++;
                    if(frame % (FPS/8) == 0){
                        actionTick++;
                        if(actionTick == 32){
                            actionTick = 0;
                        }
                    }
                }
            }
        }
    }

    public static void updateFrame(){
        try{
            double remainingTime = nextTime - System.nanoTime();
            remainingTime /= 1000000;
            if(remainingTime < 0){
                remainingTime = 0;
            }
            Thread.sleep((long) remainingTime);
            nextTime += interval;
        } catch (InterruptedException e) {}
    }

    private void attack() {
        BossPanel bossPanel = GameFrame.getBossPanel();
        Boss boss = bossPanel.getBoss();
        if(boss != null){
            bossPanel.attack();
        }
    }

    private void animate(int i) {
        BossPanel bossPanel = GameFrame.getBossPanel();
        Boss boss = bossPanel.getBoss();
        playZone = GameFrame.getPlayZone();
        if(boss != null){
            int cooldown = boss.getCooldownSeconds();
            if(frame % (FPS/8) == 0){
                idle();
            }
            if(cooldown != 0 && i % (FPS*cooldown) == 0){
                attack();
            }
            if(!KeyHandler.isPause() && BossAttack.getProjectileSpeed() != 0 && frame % (BossAttack.getProjectileSpeed()) == 0){
                BossAttack.movingAttack();
            }
        }
        if(bossPanel.getStage() == Theme.Snow || bossPanel.getStage()==Theme.DeerClops ){
            snowFallSpeed = 3;
            if(boss != null){
                snowFallSpeed = snowFallSpeed-(boss.getState()-1);
            }
            if(frame % (snowFallSpeed) == 0){
                playZone.setSnowFallFrame((playZone.getSnowFallFrame() + 1) % 12);
                playZone.repaint();
            }
        }
        else{
            playZone.setSnowFallFrame(0);
        }
    }

    private void idle() {
        BossPanel bossPanel = GameFrame.getBossPanel();
        Boss boss = bossPanel.getBoss();
        if(boss != null){
            bossPanel.animate();
        }
    }

    public static void setInitialValue(){
        playZone = GameFrame.getPlayZone();

        FPS = 60;
        interval = 1000000000/FPS;
        nextTime = System.nanoTime() + interval;
    }

    void repaintPlayZone(){
        playZone.repaint();
    }

    public static void resetFrame() {
        frame = 0;
        actionTick = 0;
    }

    public static void pauseFrame() {
        freezeFrame = frame;
        freezeActionTick = actionTick;
    }

    public static void resumeFrame() {
        frame = freezeFrame;
        actionTick = freezeActionTick;
        freezeFrame = 0;
        freezeActionTick = 0;
    }

    public static int getActonTick() {
        return actionTick;
    }

}
