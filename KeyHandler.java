
import java.awt.event.*;

public class KeyHandler implements KeyListener{
    private GameFrame gameFrame;
    private PlayZone playZone;
    private GameThread gameThread;
    private AnyKeyPanel anyKeyPanel;
    private Menu menu;
    private SettingPanel settingPanel;
    private Leaderboard leaderboard;
    private PausePanel pausePanel;
    private Sound music;
    private boolean isRightPressed,isLeftPressed,isSoftDropPressed,isHardDropPressed,isRotateCWPressed,isRotateCT_CWPressed,isHoldPressed;
    private boolean isHoldingCWRotate,isHoldingCT_CWRotate,isHoldingHardDrop,isHoldingPause;
    private boolean isLeftFirst,isRightFirst;
    private boolean isPause;

    KeyHandler(){
        gameFrame = Terraris.getGameFrame();
        playZone = gameFrame.getPlayZone();
        gameThread = gameFrame.getGameThread();
        anyKeyPanel = gameFrame.getAnyKeyPanel();
        menu = gameFrame.getMenu();
        settingPanel = gameFrame.getSettingPanel();
        leaderboard = gameFrame.getLeaderboard();
        pausePanel = gameFrame.getPausePanel();
        music = gameFrame.getMusic();
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

    public void checkNull(){
        if(gameThread == null){
            gameThread = gameFrame.getGameThread();
        }
        if(menu == null){
            menu = gameFrame.getMenu();
        }
        if(settingPanel == null){
            settingPanel = gameFrame.getSettingPanel();
        }
        if(leaderboard == null){
            leaderboard = gameFrame.getLeaderboard();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        checkNull();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        checkNull();
        int code = e.getKeyCode();
        if(code != 27 && SettingPanel.isSetting() && !SettingPanel.Duplicate(code)){
            SettingPanel.setNewKey(code);
            SettingPanel.setNewKey();
            anyKeyPanel.setVisible(false);
        }
        if((code == 27) && !SettingPanel.isSetting() && !gameFrame.isPlaying() && !menu.isVisible() && 
        (settingPanel.isVisible() || leaderboard.isVisible())){
            menu.setVisible(true);
            settingPanel.setVisible(false);
            SettingPanel.saveSetting();
            leaderboard.setVisible(false);
        }
        else if((code == 27 || code == 112) && !SettingPanel.isSetting() && gameFrame.isPlaying() && !menu.isVisible() && settingPanel.isVisible()){
            pausePanel.setVisible(true);
            SettingPanel.saveSetting();
            settingPanel.setVisible(false);
            leaderboard.setVisible(false);
        }
        else if(!playZone.isGameOver() && !SettingPanel.isSetting()){
            if((code == 27 || code == 112) && !isHoldingPause && gameFrame.isPlaying()){
                isHoldingPause = true;
                isPause = !isPause;
                if(isPause){
                    if(music.getClip() != null)
                        music.pauseSound();
                    gameFrame.pauseGame();
                }
                else{
                    if(music.getClip() != null)
                        music.resumeSound();
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
        checkNull();
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
