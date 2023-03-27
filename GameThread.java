public class GameThread extends Thread{

    private PlayZone pZone;
    private KeyHandler keyHandler;
    private PausePanel pausePanel;
    private static int FPS;
    private static long leftHoldingTime;
    private static long rightHoldingTime;
    @Override
    public void run() {
        keyHandler = GameFrame.getKeyHandler();
        pZone = GameFrame.getPlayZone();
        pausePanel = GameFrame.getPausePanel();

        double interval = 1000000000/FPS;;
        double nextTime = System.nanoTime() + interval;
        leftHoldingTime = rightHoldingTime = 0;
        while(!pZone.isGameOver()){
            if(GameFrame.getEffect().getClip() != null)
                GameFrame.getEffect().setVolume(SettingPanel.effectVolume/2);
            pausePanel.repaint();
            if(!KeyHandler.isPause()){
                try{
                    double remainingTime = nextTime - System.nanoTime();
                    remainingTime /= 1000000;
                    if(remainingTime < 0){
                        remainingTime = 0;
                    }
                    update();
                    repaint();
                    Thread.sleep((long) remainingTime);
                    nextTime += interval;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        while(pZone.isGameOver()){
            if(GameFrame.getHighScorePanel().isVisible()){
                GameFrame.getHighScorePanel().repaint();
            }
            else{
                GameFrame.getGameOverPanel().repaint();
            }
        }
    }
    

    public static void setFPS(int fps) {
        FPS = fps;
    }


    public static void resetLeftHoldingTime() {
        leftHoldingTime = 0;
    }


    public static void resetRightHoldingTime() {
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
                    pZone.moveRight();
                }
                rightHoldingTime++;
            }
            else if(keyHandler.isRightFirst() && keyHandler.isLeftPressed()){
                Thread.sleep(40);
                pZone.moveLeft();
            }   
            else{
                Thread.sleep(40);
                pZone.moveRight();
            }
        }
        if(keyHandler.isLeftPressed()){
            if(leftHoldingTime <= 8){
                if(leftHoldingTime == 0){
                    Thread.sleep(40);
                    pZone.moveLeft();
                }
                leftHoldingTime++;
            }
            else if(keyHandler.isLeftFirst() && keyHandler.isRightPressed()){
                Thread.sleep(40);
                pZone.moveRight();
            }   
            else{
                Thread.sleep(40);
                pZone.moveLeft();
            }
        }
        if(keyHandler.isHoldPressed()){
            pZone.holdBlock();
        }
        if(keyHandler.isHardDropPressed() && !keyHandler.isHoldingHardDrop()){
            keyHandler.setHoldingHardDrop(true);
            pZone.hardDrop();
        }
        if(keyHandler.isSoftDropPressed()){
            Thread.sleep(40);
            pZone.softDrop();
        }
        if(keyHandler.isisRotateCWPressed() && !keyHandler.isHoldingCWRotate()){
            keyHandler.setHoldingCWRotate(true);
            pZone.rotate("CW");
        }
        if(keyHandler.isRotateCT_CWPressed() && !keyHandler.isHoldingCT_CWRotate()){
            keyHandler.setHoldingCT_CWRotate(true);
            pZone.rotate("CT-CW");
        }
    }

    void repaint(){
        pZone.repaint();
    }
}
