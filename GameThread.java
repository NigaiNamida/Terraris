public class GameThread extends Thread{

    private static PlayZone playZone;
    private static KeyHandler keyHandler;
    private static int FPS;
    private static long leftHoldingTime;
    private static long rightHoldingTime;
    private static double interval;
    private static double nextTime;
    @Override
    public void run() {
        setInitialValue();
        while(GameFrame.isPlaying()){
            if(!playZone.isGameOver()){
                if(GameFrame.getEffect().getClip() != null)
                    GameFrame.getEffect().setVolume(SettingPanel.getFXVolume()/2);
                if(!KeyHandler.isPause()){
                    updateGame();
                }
            }
        }
        if(GameFrame.getHighScorePanel().isVisible()){
            GameFrame.getHighScorePanel().repaint();
        }
        else{
            GameFrame.getGameOverPanel().repaint();
        }
    }

    public static void updateGame(){
        try{
            double remainingTime = nextTime - System.nanoTime();
            remainingTime /= 1000000;
            if(remainingTime < 0){
                remainingTime = 0;
            }
            updateInput();
            Thread.sleep((long) remainingTime);
            nextTime += interval;
        } catch (InterruptedException e) {}
    }

    public static void setInitialValue(){
        keyHandler = GameFrame.getKeyHandler();
        playZone = GameFrame.getPlayZone();

        FPS = 60;
        interval = 1000000000/FPS;
        nextTime = System.nanoTime() + interval;
        leftHoldingTime = rightHoldingTime = 0;
    }

    public static void resetLeftHoldingTime() {
        leftHoldingTime = 0;
    }


    public static void resetRightHoldingTime() {
        rightHoldingTime = 0;
    }


    public static void updateInput() throws InterruptedException{
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
                    if(BossAttack.getConfuseCount()>0){
                        playZone.moveLeft();
                    }
                    else{
                        playZone.moveRight();
                    }
                }
                rightHoldingTime++;
            }
            else if(keyHandler.isRightFirst() && keyHandler.isLeftPressed()){
                Thread.sleep(40);
                if(BossAttack.getConfuseCount()>0){
                    playZone.moveRight();
                }
                else{
                    playZone.moveLeft();
                }
            }   
            else{
                Thread.sleep(40);
                if(BossAttack.getConfuseCount()>0){
                    playZone.moveLeft();
                }
                else{
                    playZone.moveRight();
                }
            }
        }
        if(keyHandler.isLeftPressed()){
            if(leftHoldingTime <= 8){
                if(leftHoldingTime == 0){
                    Thread.sleep(40);
                    if(BossAttack.getConfuseCount()>0){
                        playZone.moveRight();
                    }
                    else{
                        playZone.moveLeft();
                    }
                }
                leftHoldingTime++;
            }
            else if(keyHandler.isLeftFirst() && keyHandler.isRightPressed()){
                Thread.sleep(40);
                    if(BossAttack.getConfuseCount()>0){
                        playZone.moveLeft();
                    }
                    else{
                        playZone.moveRight();
                    }
            }   
            else{
                Thread.sleep(40);
                if(BossAttack.getConfuseCount()>0){
                    playZone.moveRight();
                }
                else{
                    playZone.moveLeft();
                }
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
        if(keyHandler.isRotateCWPressed() && !keyHandler.isHoldingCWRotate()){
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
