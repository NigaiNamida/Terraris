public class GameThread extends Thread{
    private GameFrame gameFrame;
    private PlayZone playZone;
    private HighScorePanel highScorePanel;
    private KeyHandler keyHandler;
    private PausePanel pausePanel;
    private Sound effect;

    private int FPS;
    private long leftHoldingTime;
    private long rightHoldingTime;
    private double interval;
    private double nextTime;

    @Override
    public void run() {
        setInitialValue();
        while(gameFrame.isPlaying()){
            if(!playZone.isGameOver()){
                if(effect.getClip() != null)
                    effect.setVolume(SettingPanel.getFXVolume()/2);
                pausePanel.repaint();
                if(!keyHandler.isPause()){
                    try{
                        double remainingTime = nextTime - System.nanoTime();
                        remainingTime /= 1000000;
                        if(remainingTime < 0){
                            remainingTime = 0;
                        }
                        update();
                        repaintPlayZone();
                        Thread.sleep((long) remainingTime);
                        nextTime += interval;
                    } catch (InterruptedException e) {}
                }
            }
            else{
                if(highScorePanel.isVisible()){
                    highScorePanel.repaint();
                }
                else{
                    highScorePanel.repaint();
                }
            }
        }
    }

    public void setInitialValue(){
        gameFrame = Terraris.getGameFrame();
        keyHandler = gameFrame.getKeyHandler();
        highScorePanel = gameFrame.getHighScorePanel();
        playZone = gameFrame.getPlayZone();
        pausePanel = gameFrame.getPausePanel();
        effect = gameFrame.getEffect();

        FPS = 60;
        interval = 1000000000/FPS;
        nextTime = System.nanoTime() + interval;
        leftHoldingTime = rightHoldingTime = 0;
    }

    public void resetLeftHoldingTime() {
        leftHoldingTime = 0;
    }


    public void resetRightHoldingTime() {
        rightHoldingTime = 0;
    }


    void update() throws InterruptedException{
        if(!keyHandler.isLeftFirst() && !keyHandler.isRightFirst()){
            if(keyHandler.isRightPressed())
                keyHandler.setRightFirst(true);
            else if(keyHandler.isLeftPressed())
                keyHandler.setLeftFirst(true);

        }
        if(keyHandler.isRightPressed()){
            if(rightHoldingTime <= 8){
                if(rightHoldingTime == 0){
                    Thread.sleep(40);
                    playZone.moveRight();
                }
                rightHoldingTime++;
            }
            else if(keyHandler.isRightFirst() && keyHandler.isLeftPressed()){
                Thread.sleep(40);
                playZone.moveLeft();
            }   
            else{
                Thread.sleep(40);
                playZone.moveRight();
            }
        }
        if(keyHandler.isLeftPressed()){
            if(leftHoldingTime <= 8){
                if(leftHoldingTime == 0){
                    Thread.sleep(40);
                    playZone.moveLeft();
                }
                leftHoldingTime++;
            }
            else if(keyHandler.isLeftFirst() && keyHandler.isRightPressed()){
                Thread.sleep(40);
                playZone.moveRight();
            }   
            else{
                Thread.sleep(40);
                playZone.moveLeft();
            }
        }
        if(keyHandler.isHoldPressed()){
            playZone.holdBlock();
        }
        if(keyHandler.isHardDropPressed() && !keyHandler.isHoldingHardDrop()){
            keyHandler.setHoldingHardDrop(true);
            playZone.hardDrop();
        }
        if(keyHandler.isSoftDropPressed()){
            Thread.sleep(40);
            playZone.softDrop();
        }
        if(keyHandler.isisRotateCWPressed() && !keyHandler.isHoldingCWRotate()){
            keyHandler.setHoldingCWRotate(true);
            playZone.rotate("CW");
        }
        if(keyHandler.isRotateCT_CWPressed() && !keyHandler.isHoldingCT_CWRotate()){
            keyHandler.setHoldingCT_CWRotate(true);
            playZone.rotate("CT-CW");
        }
    }

    void repaintPlayZone(){
        playZone.repaint();
    }
}
