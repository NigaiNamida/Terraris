
import java.awt.event.*;

public class KeyHandler implements KeyListener{
    private GameFrame gameFrame;
    private PlayZone playZone;
    private GameThread gameThread;
    private boolean isRightPressed,isLeftPressed,isSoftDropPressed,isHardDropPressed,isRotateCWPressed,isRotateCT_CWPressed,isHoldPressed;
    private boolean isHoldingCWRotate,isHoldingCT_CWRotate,isHoldingHardDrop,isHoldingPause;
    private boolean isLeftFirst,isRightFirst;
    private boolean isPause;

    KeyHandler(){
        gameFrame = Terraris.getGameFrame();
        playZone = gameFrame.getPlayZone();
        gameThread = gameFrame.getGameThread();
        isPause = false;
        isHoldingPause = false;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public void setRightPressed(boolean isRightPressed) {
        this.isRightPressed = isRightPressed;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    public void setLeftPressed(boolean isLeftPressed) {
        this.isLeftPressed = isLeftPressed;
    }

    public boolean isSoftDropPressed() {
        return isSoftDropPressed;
    }

    public void setSoftDropPressed(boolean isSoftDropPressed) {
        this.isSoftDropPressed = isSoftDropPressed;
    }

    public boolean isHardDropPressed() {
        return isHardDropPressed;
    }

    public void setHardDropPressed(boolean isHardDropPressed) {
        this.isHardDropPressed = isHardDropPressed;
    }

    public boolean isisRotateCWPressed() {
        return isRotateCWPressed;
    }

    public void setisRotateCWPressed(boolean isRotateCWPressed) {
        this.isRotateCWPressed = isRotateCWPressed;
    }

    public boolean isRotateCT_CWPressed() {
        return isRotateCT_CWPressed;
    }

    public void setRotateCT_CWPressed(boolean isRotateCT_CWPressed) {
        this.isRotateCT_CWPressed = isRotateCT_CWPressed;
    }

    public boolean isHoldPressed() {
        return isHoldPressed;
    }

    public void setHoldPressed(boolean isHoldPressed) {
        this.isHoldPressed = isHoldPressed;
    }

    public boolean isHoldingCWRotate() {
        return isHoldingCWRotate;
    }

    public void setHoldingCWRotate(boolean isHoldingCWRotate) {
        this.isHoldingCWRotate = isHoldingCWRotate;
    }

    public boolean isHoldingCT_CWRotate() {
        return isHoldingCT_CWRotate;
    }

    public void setHoldingCT_CWRotate(boolean isHoldingCT_CWRotate) {
        this.isHoldingCT_CWRotate = isHoldingCT_CWRotate;
    }

    public boolean isHoldingHardDrop() {
        return isHoldingHardDrop;
    }

    public void setHoldingHardDrop(boolean isHoldingHardDrop) {
        this.isHoldingHardDrop = isHoldingHardDrop;
    }

    public boolean isHoldingPause() {
        return isHoldingPause;
    }

    public void setHoldingPause(boolean isHoldingPause) {
        this.isHoldingPause = isHoldingPause;
    }

    public boolean isLeftFirst() {
        return isLeftFirst;
    }

    public void setLeftFirst(boolean leftFirst) {
        this.isLeftFirst = leftFirst;
    }

    public boolean isRightFirst() {
        return isRightFirst;
    }

    public void setRightFirst(boolean rightFirst) {
        this.isRightFirst = rightFirst;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(gameThread == null){
            gameThread = gameFrame.getGameThread();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameThread == null){
            gameThread = gameFrame.getGameThread();
        }
        int code = e.getKeyCode();
        if(code != 27 && SettingPanel.isSetting() && !SettingPanel.Duplicate(code)){
            SettingPanel.setNewKey(code);
            SettingPanel.setNewKey();
            gameFrame.getAnyKeyPanel().setVisible(false);
        }
        if((code == 27) && !SettingPanel.isSetting() && !gameFrame.isPlaying() && !gameFrame.getMenu().isVisible() && 
        (gameFrame.getSettingPanel().isVisible() || gameFrame.getLeaderboard().isVisible())){
            gameFrame.getMenu().setVisible(true);
            gameFrame.getSettingPanel().setVisible(false);
            SettingPanel.saveSetting();
            gameFrame.getLeaderboard().setVisible(false);
        }
        else if((code == 27 || code == 112) && !SettingPanel.isSetting() && gameFrame.isPlaying() && !gameFrame.getMenu().isVisible() && gameFrame.getSettingPanel().isVisible()){
            gameFrame.getPausePanel().setVisible(true);
            SettingPanel.saveSetting();
            gameFrame.getSettingPanel().setVisible(false);
            gameFrame.getLeaderboard().setVisible(false);
        }
        else if(!playZone.isGameOver() && !SettingPanel.isSetting()){
            if((code == 27 || code == 112) && !isHoldingPause && gameFrame.isPlaying()){
                isHoldingPause = true;
                isPause = !isPause;
                if(isPause){
                    if(gameFrame.getMusic().getClip() != null)
                        gameFrame.getMusic().pauseSound();
                    gameFrame.pauseGame();
                }
                else{
                    if(gameFrame.getMusic().getClip() != null)
                        gameFrame.getMusic().resumeSound();
                    gameFrame.continueGame();
                }
            }
            if(!isPause){
                if(code == SettingPanel.getKeyBind("Right")){
                    isRightPressed = true;
                    if(!isRightFirst && !isLeftFirst){
                        isRightFirst = true;
                    }
                    else{
                        isRightFirst = !isLeftFirst;
                    }
                }
                else if(code == SettingPanel.getKeyBind("Left")){
                    isLeftPressed = true;
                    if(!isRightFirst && !isLeftFirst){
                        isLeftFirst = true;
                    }
                    else{
                        isLeftFirst = !isRightFirst;
                    }
                }
                else if(code == SettingPanel.getKeyBind("SoftDrop")){
                    isSoftDropPressed = true;
                }
                else if(code == SettingPanel.getKeyBind("RotateCW") || code == 88){
                    isRotateCWPressed = true;
                }
                else if(code ==  SettingPanel.getKeyBind("RotateCT_CW") || code == 17){
                    isRotateCT_CWPressed = true;
                }
                else if(code ==  SettingPanel.getKeyBind("HardDrop")){
                    isHardDropPressed = true;
                }
                else if(code ==  SettingPanel.getKeyBind("Hold") || code == 16){
                    isHoldPressed = true;
                }
            }
        }  
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(gameThread == null){
            gameThread = gameFrame.getGameThread();
        }
        int code = e.getKeyCode();
        if(!playZone.isGameOver()){
            if(code == 27 || code == 112){
                isHoldingPause = false;
            }
            else if(code == SettingPanel.getKeyBind("Right")){
                isRightPressed = false;
                isRightFirst = false;
                gameThread.resetRightHoldingTime();
            }
            else if(code == SettingPanel.getKeyBind("Left")){
                isLeftPressed = false;
                isLeftFirst = false;
                gameThread.resetLeftHoldingTime();
            }
            else if(code == SettingPanel.getKeyBind("SoftDrop")){
                isSoftDropPressed = false;
            }
            else if(code == SettingPanel.getKeyBind("RotateCW") || code == 88){
                isRotateCWPressed = false;
                isHoldingCWRotate = false;
            }
            else if(code ==  SettingPanel.getKeyBind("RotateCT_CW") || code == 17){
                isRotateCT_CWPressed = false;
                isHoldingCT_CWRotate = false;
            }
            else if(code ==  SettingPanel.getKeyBind("HardDrop")){
                isHardDropPressed = false;
                isHoldingHardDrop = false;
            }
            else if(code ==  SettingPanel.getKeyBind("Hold") || code == 16){
                isHoldPressed = false;
            }
        }
    }
}
