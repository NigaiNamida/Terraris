
import java.awt.event.*;

public class KeyHandler implements KeyListener{
    private PlayZone playZone;
    private boolean isRightPressed,isLeftPressed,isSoftDropPressed,isHardDropPressed,isRotateCWPressed,isRotateCT_CWPressed,isHoldPressed;
    private boolean isHoldingCWRotate,isHoldingCT_CWRotate,isHoldingHardDrop,isHoldingPause;
    private boolean isLeftFirst,isRightFirst;
    private static boolean isPause;

    KeyHandler(){
        playZone = GameFrame.getPlayZone();
        isPause = false;
        isHoldingPause = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(SettingPanel.isSetting()){
            if(!isDuplicateKey(code)){
                setNewButtonKey(code);
            }
        }
        else{
            if(isESCKey(code) && (isOnLeaderBoard(code) || isOnSetting(code))){
                backToMenu();
            }
            else if((isESCKey(code) || isF1Key(code)) && isOnSettingInGame(code)){
                backToPause();
            }
            else if(!playZone.isGameOver()){
                if((isESCKey(code) || isF1Key(code)) && !isHoldingPause && GameFrame.isPlaying() && !GameFrame.getRewardPanel().isVisible()){
                    isHoldingPause = true;
                    isPause = !isPause;
                    if(isPause){
                        pauseGame();
                    }
                    else{
                        continueGame();
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(!playZone.isGameOver()){
            if(isESCKey(code) || code == 112){
                isHoldingPause = false;
            }
            else if(code == SettingPanel.getKeyBind("Right")){
                isRightPressed = false;
                isRightFirst = false;
                GameThread.resetRightHoldingTime();
            }
            else if(code == SettingPanel.getKeyBind("Left")){
                isLeftPressed = false;
                isLeftFirst = false;
                GameThread.resetLeftHoldingTime();
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

    public boolean isESCKey(int code){
        return code == 27;
    }

    public boolean isF1Key(int code){
        return code == 112;
    }

    public boolean isOnLeaderBoard(int code){
        return !GameFrame.isPlaying() && !GameFrame.getMenu().isVisible() && 
            GameFrame.getLeaderboard().isVisible();
    }
    public boolean isOnSetting(int code){
        return !GameFrame.isPlaying() && !GameFrame.getMenu().isVisible() && 
            GameFrame.getSettingPanel().isVisible();
    }

    public boolean isOnSettingInGame(int code){
        return GameFrame.isPlaying() && !GameFrame.getMenu().isVisible() && GameFrame.getSettingPanel().isVisible();
    }

    public boolean isDuplicateKey(int code){
        return isESCKey(code) || SettingPanel.Duplicate(code);
    }

    public void setNewButtonKey(int code){
        SettingPanel.setNewKey(code);
        SettingPanel.setNewKey();
        GameFrame.getAnyKeyPanel().setVisible(false);
    }

    public void backToMenu(){
        GameFrame.getMenu().setVisible(true);
        GameFrame.playSE(7);
        GameFrame.getSettingPanel().setVisible(false);
        SettingPanel.saveSetting();
        GameFrame.getLeaderboard().setVisible(false);
    }

    public void backToPause(){
        GameFrame.getPausePanel().setVisible(true);
        GameFrame.playSE(7);
        SettingPanel.saveSetting();
        GameFrame.getSettingPanel().setVisible(false);
        GameFrame.getLeaderboard().setVisible(false);
    }

    public static void pauseGame(){
        if(GameFrame.getMusic().getClip() != null)
            GameFrame.getMusic().pauseSound();
        GameFrame.pauseGame();
        AnimationThread.pauseFrame();
    }

    public static void continueGame(){
        if(GameFrame.getMusic().getClip() != null)
            GameFrame.getMusic().resumeSound();
        GameFrame.continueGame();
        AnimationThread.resumeFrame();
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

    public boolean isRotateCWPressed() {
        return isRotateCWPressed;
    }

    public void setRotateCWPressed(boolean isRotateCWPressed) {
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

    public static boolean isPause() {
        return isPause;
    }

    public static void setPause(boolean isPause) {
        KeyHandler.isPause = isPause;
    }
}
