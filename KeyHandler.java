
import java.awt.event.*;

public class KeyHandler implements KeyListener{
    PlayZone pZone = GameFrame.pZone;
    Gravity gravity = pZone.gravity;
    boolean rightPressed,leftPressed,softDropPressed,hardDropPressed,rotateCWPressed,rotateCT_CWPressed,holdPressed;
    boolean isHoldingCWRotate,isHoldingCT_CWRotate,isHoldingHardDrop,isHoldingPause;
    boolean leftFirst,rightFirst;
    static boolean isPause;

    KeyHandler(){
        isPause = false;
        isHoldingPause = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code != 27 && SettingPanel.isSetting && !SettingPanel.Duplicate(code)){
            SettingPanel.newKey = code;
            SettingPanel.setNewKey();
            GameFrame.newKeyPanel.setVisible(false);
        }
        if((code == 27) && !SettingPanel.isSetting && !GameFrame.isPlaying && !GameFrame.menu.isVisible() && 
        (GameFrame.settingPanel.isVisible() || GameFrame.leaderboard.isVisible())){
            GameFrame.menu.setVisible(true);
            GameFrame.settingPanel.setVisible(false);
            GameFrame.leaderboard.setVisible(false);
        }
        else if((code == 27 || code == 112) && !SettingPanel.isSetting && GameFrame.isPlaying && !GameFrame.menu.isVisible() && GameFrame.settingPanel.isVisible()){
            GameFrame.pausePanel.setVisible(true);
            GameFrame.settingPanel.setVisible(false);
            GameFrame.leaderboard.setVisible(false);
        }
        else if(!pZone.isGameOver && !SettingPanel.isSetting){
            if((code == 27 || code == 112) && !isHoldingPause && GameFrame.isPlaying){
                isHoldingPause = true;
                isPause = !isPause;
                if(isPause){
                    if(GameFrame.music.clip != null)
                        GameFrame.music.pauseSound();
                    GameFrame.pauseGame();
                }
                else{
                    if(GameFrame.music.clip != null)
                        GameFrame.music.resumeSound();
                    GameFrame.continueGame();
                }
            }
            if(!isPause){
                if(code == SettingPanel.moveRightKey){
                    rightPressed = true;
                    if(!rightFirst && !leftFirst){
                        rightFirst = true;
                    }
                    else{
                        rightFirst = !leftFirst;
                    }
                }
                else if(code == SettingPanel.moveLeftKey){
                    leftPressed = true;
                    if(!rightFirst && !leftFirst){
                        leftFirst = true;
                    }
                    else{
                        leftFirst = !rightFirst;
                    }
                }
                else if(code == SettingPanel.softDropKey){
                    softDropPressed = true;
                }
                else if(code == SettingPanel.rotateCWKey || code == 88){
                    rotateCWPressed = true;
                }
                else if(code ==  SettingPanel.rotateCT_CWKey || code == 17){
                    rotateCT_CWPressed = true;
                }
                else if(code ==  SettingPanel.hardDropKey){
                    hardDropPressed = true;
                }
                else if(code ==  SettingPanel.holdKey || code == 16){
                    holdPressed = true;
                }
            }
        }  
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(!pZone.isGameOver){
            if(code == 27 || code == 112){
                isHoldingPause = false;
            }
            else if(code == SettingPanel.moveRightKey){
                rightPressed = false;
                rightFirst = false;
                GameThread.rightHoldingTime = 0;
            }
            else if(code == SettingPanel.moveLeftKey){
                leftPressed = false;
                leftFirst = false;
                GameThread.leftHoldingTime = 0;
            }
            else if(code == SettingPanel.softDropKey){
                softDropPressed = false;
            }
            else if(code == SettingPanel.rotateCWKey || code == 88){
                rotateCWPressed = false;
                isHoldingCWRotate = false;
            }
            else if(code ==  SettingPanel.rotateCT_CWKey || code == 17){
                rotateCT_CWPressed = false;
                isHoldingCT_CWRotate = false;
            }
            else if(code ==  SettingPanel.hardDropKey){
                hardDropPressed = false;
                isHoldingHardDrop = false;
            }
            else if(code ==  SettingPanel.holdKey || code == 16){
                holdPressed = false;
            }
        }
    }
}
