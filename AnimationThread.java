public class AnimationThread extends Thread{

    private static PlayZone playZone;
    private static int FPS;
    private static double interval;
    private static double nextTime;
    private static int i;
    private static int j;

    @Override
    public void run() {
        setInitialValue();
        i = 0;
        while(GameFrame.isPlaying()){
            if(!playZone.isGameOver()){
                if(GameFrame.getEffect().getClip() != null)
                    GameFrame.getEffect().setVolume(SettingPanel.getFXVolume()/2);
                if(!KeyHandler.isPause()){
                    updateFrame();
                    animate(i);
                    i++;
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
        if(boss != null){
            int cooldown = boss.getCooldownSeconds();
            if(i % (FPS/8) == 0){
                idle();
            }
            if(cooldown != 0 && i % (FPS*cooldown) == 0){
                attack();
            }
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
        i = 0;
    }

    public static void pauseFrame() {
        j = i;
    }

    public static void resumeFrame() {
        i = j;
        j = 0;
    }
}
