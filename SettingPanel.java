import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SettingPanel extends JPanel implements ActionListener{
    private static String settingFile;
    private static HashMap<String,Integer> keyBind;
    private static HashMap<String,JLabel> settingLabel;
    private static HashMap<String,JButton> settingButton;

    private static boolean isSetting;
    private static String settingKey;
    private static int newKey;

    private static int musicVolume;
    private static int FXVolume;

    private Font font = new Font("Futura",Font.PLAIN,20);
    public SettingPanel(){

        keyBind = new HashMap<String,Integer>();
        settingLabel = new HashMap<String,JLabel>();
        settingButton = new HashMap<String,JButton>();
        musicVolume = 40;
        FXVolume = 50;
        settingFile = "setting.txt";
        isSetting = false;
        settingKey = null;

        keyBind.put("Right", 39);
        keyBind.put("Left", 37);
        keyBind.put("Hold", 67);
        keyBind.put("SoftDrop", 40);
        keyBind.put("HardDrop", 32);
        keyBind.put("RotateCW", 38);
        keyBind.put("RotateCT_CW", 90);

        settingLabel.put("Title", new JLabel("SETTING"));
        settingLabel.put("MusicLabel", new JLabel("MUSIC VOLUME"));
        settingLabel.put("MusicVolume", new JLabel(musicVolume+"%"));
        settingLabel.put("FXLabel", new JLabel("SOUND FX VOLUME"));
        settingLabel.put("FXVolume", new JLabel(FXVolume+"%"));
        settingLabel.put("Right", new JLabel("Move Right"));
        settingLabel.put("Left", new JLabel("Move Left"));
        settingLabel.put("RotateCW", new JLabel("Rotate Clockwise"));
        settingLabel.put("RotateCT_CW", new JLabel("Rotate Counter-Clockwise"));
        settingLabel.put("SoftDrop", new JLabel("Soft Drop"));
        settingLabel.put("HardDrop", new JLabel("Hard Drop"));
        settingLabel.put("Hold", new JLabel("Hold"));

        settingButton.put("MusicVolumeDecrease", new JButton("-"));
        settingButton.put("MusicVolumeIncrease", new JButton("+"));
        settingButton.put("FXVolumeDecrease", new JButton("-"));
        settingButton.put("FXVolumeIncrease", new JButton("+"));
        settingButton.put("Right", new JButton("Move Right"));
        settingButton.put("Left", new JButton("Move Left"));
        settingButton.put("RotateCW", new JButton("Rotate Clockwise"));
        settingButton.put("RotateCT_CW", new JButton("Rotate Counter-Clockwise"));
        settingButton.put("SoftDrop", new JButton("Soft Drop"));
        settingButton.put("HardDrop", new JButton("Hard Drop"));
        settingButton.put("Hold", new JButton("Hold"));

        this.setOpaque(true);
        this.setBounds(100, 5, 600, 550);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3,true));
        this.setLayout(null);

        for (var labelName : settingLabel.keySet()) {
            JLabel label = settingLabel.get(labelName);
            label.setForeground(new Color(193,221,196,255));
            label.setHorizontalAlignment(JLabel.CENTER);
            this.add(label);
        }

        settingLabel.get("Title").setFont(new Font("Futura",Font.BOLD,40));

        for (var buttonName : settingButton.keySet()) {
            JButton button = settingButton.get(buttonName);
            if(buttonName != "MusicVolumeDecrease" && buttonName != "MusicVolumeIncrease" 
            && buttonName != "FXVolumeDecrease" && buttonName != "FXVolumeIncrease"){
                button.setText(showName(keyBind.get(buttonName)));
            }
            button.setForeground(new Color(193,221,196,255));
            button.setFont(font);
            button.setHorizontalAlignment(JLabel.CENTER);
            button.setFocusable(false);
            button.setBackground(Color.gray);
            button.addActionListener(this);
            this.add(button);
        }
        settingLabel.get("Title").setBounds(200, 30, 200, 40);
        settingLabel.get("MusicLabel").setBounds(50, 95, 200, 25);
        settingLabel.get("MusicVolume").setBounds(395, 95, 60, 25);
        settingLabel.get("FXLabel").setBounds(50, 145, 200, 25);
        settingLabel.get("FXVolume").setBounds(395, 145, 60, 25);
        settingLabel.get("Right").setBounds(50, 195, 200, 25);
        settingLabel.get("Left").setBounds(50, 245, 200, 25);
        settingLabel.get("RotateCW").setBounds(50, 295, 200, 25);
        settingLabel.get("RotateCT_CW").setBounds(25, 345,250, 25);
        settingLabel.get("SoftDrop").setBounds(50, 395, 200, 25);
        settingLabel.get("HardDrop").setBounds(50, 445, 200, 25);
        settingLabel.get("Hold").setBounds(50, 495, 200, 25);
        
        settingButton.get("MusicVolumeDecrease").setBounds(293, 95, 60, 25);
        settingButton.get("MusicVolumeIncrease").setBounds(497, 95, 60, 25);
        settingButton.get("FXVolumeDecrease").setBounds(293, 145, 60, 25);
        settingButton.get("FXVolumeIncrease").setBounds(497, 145, 60, 25);
        settingButton.get("Right").setBounds(350, 195, 150, 25);
        settingButton.get("Left").setBounds(350, 245, 150, 25);
        settingButton.get("RotateCW").setBounds(350, 295, 150, 25);
        settingButton.get("RotateCT_CW").setBounds(350, 345, 150, 25);
        settingButton.get("SoftDrop").setBounds(350, 395, 150, 25);
        settingButton.get("HardDrop").setBounds(350, 445, 150, 25);
        settingButton.get("Hold").setBounds(350, 495, 150, 25);
    }
    
    public static boolean isSetting() {
        return isSetting;
    }

    public static void setSetting(boolean isSetting) {
        SettingPanel.isSetting = isSetting;
    }

    public static String getSettingKey() {
        return settingKey;
    }

    public static void setSettingKey(String settingKey) {
        SettingPanel.settingKey = settingKey;
    }

    public static String getSettingFile() {
        return settingFile;
    }

    public static int getNewKey() {
        return newKey;
    }

    public static void setNewKey(int newKey) {
        SettingPanel.newKey = newKey;
    }

    public static int getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(int musicVolume) {
        SettingPanel.musicVolume = musicVolume;
    }

    public static int getFXVolume() {
        return FXVolume;
    }

    public static void setFXVolume(int fXVolume) {
        FXVolume = fXVolume;
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    public static void disableButton(){
        for (var buttonName : settingButton.keySet()) {
            JButton button = settingButton.get(buttonName);
            button.setEnabled(false);
        }
    }

    public static void enableButton(){
        for (var buttonName : settingButton.keySet()) {
            JButton button = settingButton.get(buttonName);
            button.setEnabled(true);
        }
    }

    public static void saveSetting(){
        Path path = Paths.get(settingFile);
        try (PrintWriter output = new PrintWriter(path.getFileName().toString())) {
            output.printf("%d%n",musicVolume);
            output.printf("%d%n",FXVolume);
            for (var entry : keyBind.entrySet()) {
                String key = entry.getKey();
                int code = entry.getValue();
                output.printf("%s %d%n",key,code);
            }
        } 
        catch (Exception e) {}
    }

    public static void updateSetting() {
        settingLabel.get("MusicVolume").setText(musicVolume+"%");
        settingLabel.get("FXVolume").setText(FXVolume+"%");
        for (var buttonName : settingButton.keySet()) {
            JButton button = settingButton.get(buttonName);
            if(buttonName != "MusicVolumeDecrease" && buttonName != "MusicVolumeIncrease" 
            && buttonName != "FXVolumeDecrease" && buttonName != "FXVolumeIncrease"){
                button.setText(showName(keyBind.get(buttonName)));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(button == settingButton.get("MusicVolumeDecrease") && musicVolume != 0){
            musicVolume -= 10;
            settingLabel.get("MusicVolume").setText((musicVolume)+"%");
            GameFrame.getMusic().setVolume(musicVolume/2);
        }
        else if(button == settingButton.get("MusicVolumeIncrease") && musicVolume != 100){
            musicVolume += 10;
            settingLabel.get("MusicVolume").setText((musicVolume)+"%");
            GameFrame.getMusic().setVolume(musicVolume/2);
        }
        else if(button == settingButton.get("FXVolumeDecrease")  && FXVolume != 0){
            FXVolume -= 10;
            settingLabel.get("FXVolume").setText((FXVolume)+"%");
            
        }
        else if(button == settingButton.get("FXVolumeIncrease") && FXVolume != 100){
            FXVolume += 10;
            settingLabel.get("FXVolume").setText((FXVolume)+"%");
            GameFrame.getEffect().setVolume(FXVolume/2);
        }
        else if(settingButton.containsValue(button) && FXVolume != 0 && FXVolume != 100 && musicVolume != 0 && musicVolume != 100){
            settingKey = getKeyFromButton((JButton) e.getSource());
            isSetting = true;
            GameFrame.getAnyKeyPanel().setVisible(true);
            disableButton();
        }
    }

    public static void setNewKey(){
        keyBind.put(settingKey, newKey);
        settingButton.get(settingKey).setText(showName(newKey));
        newKey = -1;
        settingKey = null;
        isSetting = false;
        enableButton();
    }
    
    public static int getKeyBind(String key) {
        return keyBind.get(key);
    }

    public static void addKeyBind(String key, int code){
        keyBind.put(key, code);
    }

    public static String showName(int Key){
        switch (Key) {
            case 0:
                return "Fn";
            case 8:
                return "Backspace";
            case 10:
                return "Enter";
            case 16:
                return "Shift";
            case 17:
                return "Ctrl";
            case 18:
                return "Alt";
            case 20:
                return "Caps Lock";
            case 32:
                return "Space";
            case 33:
                return "Page Up";
            case 34:
                return "Page Down";
            case 36:
                return "Home";
            case 37:
                return "Left";
            case 38:
                return "Up";
            case 39:
                return "Right";
            case 40:
                return "Down";
            default:
                return ("" + (char) Key);
        }
    }

    public static boolean Duplicate(int code) {
        if(keyBind.containsValue(code) && settingKey != getKeyFromValue(code)){
            return true;
        }
        return false;
    }

    public static String getKeyFromValue(int code){
        for(var key : keyBind.entrySet()) {
            if(key.getValue() == code) {
                return key.getKey();
            }
        }
        return null;
    }

    public static String getKeyFromButton(JButton btn){
        for(var button : settingButton.entrySet()) {
            if(button.getValue() == btn) {
                return button.getKey();
            }
        }
        return null;
    }
}
