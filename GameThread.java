public class GameThread extends Thread{

    KeyHandler keyHandler = GameFrame.keyHandler;
    PlayZone pZone = GameFrame.pZone;
    PausePanel pausePanel = GameFrame.pausePanel;
    static int FPS = 80000;
    static long leftHoldingTime;
    static long rightHoldingTime;
    @Override
    public void run() {
        double interval = 1000000000/FPS;;
        double nextTime = System.nanoTime() + interval;
        //update every frame
        leftHoldingTime = rightHoldingTime = 0;
        while(!pZone.isGameOver){
            if(GameFrame.effect.clip != null)
                GameFrame.effect.setVolume(SettingPanel.effectVolume/2);
            pausePanel.repaint();
            if(!KeyHandler.isPause){
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
        while(pZone.isGameOver){
            if(GameFrame.highScorePanel.isVisible()){
                GameFrame.highScorePanel.repaint();
            }
            else{
                GameFrame.gameOverPanel.repaint();
            }
        }
    }

    void update() throws InterruptedException{
        if(!keyHandler.leftFirst && !keyHandler.rightFirst){
            if(keyHandler.rightPressed)
                keyHandler.rightFirst = true;
            else if(keyHandler.leftPressed)
                keyHandler.leftFirst = true;

        }
        if(keyHandler.rightPressed){
            if(rightHoldingTime <= 8){
                if(rightHoldingTime == 0){
                    Thread.sleep(40);
                    pZone.moveRight();
                }
                rightHoldingTime++;
            }
            else if(keyHandler.rightFirst && keyHandler.leftPressed){
                Thread.sleep(40);
                pZone.moveLeft();
            }   
            else{
                Thread.sleep(40);
                pZone.moveRight();
            }
        }
        if(keyHandler.leftPressed){
            if(leftHoldingTime <= 8){
                if(leftHoldingTime == 0){
                    Thread.sleep(40);
                    pZone.moveLeft();
                }
                leftHoldingTime++;
            }
            else if(keyHandler.leftFirst && keyHandler.rightPressed){
                Thread.sleep(40);
                pZone.moveRight();
            }   
            else{
                Thread.sleep(40);
                pZone.moveLeft();
            }
        }
        if(keyHandler.holdPressed){
            pZone.holdBlock();
        }
        if(keyHandler.hardDropPressed && !keyHandler.isHoldingHardDrop){
            keyHandler.isHoldingHardDrop = true;
            pZone.hardDrop();
        }
        if(keyHandler.softDropPressed){
            Thread.sleep(40);
            pZone.softDrop();
        }
        if(keyHandler.rotateCWPressed && !keyHandler.isHoldingCWRotate){
            keyHandler.isHoldingCWRotate = true;
            pZone.rotate("CW");
        }
        if(keyHandler.rotateCT_CWPressed && !keyHandler.isHoldingCT_CWRotate){
            keyHandler.isHoldingCT_CWRotate = true;
            pZone.rotate("CT-CW");
        }
    }

    void repaint(){
        pZone.repaint();
    }
}
