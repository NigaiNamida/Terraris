import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SettingPanel extends JPanel implements ActionListener{
    public static String settingFile = "setting";
    public static int moveRightKey = 39;//Right
    public static int moveLeftKey = 37;//Left
    public static int holdKey = 67;//C
    public static int softDropKey = 40;//down
    public static int hardDropKey = 32;//space
    public static int rotateCWKey = 38;//up
    public static int rotateCT_CWKey = 90;//Z
    public static boolean isSetting;
    public static int settingKey;
    public static int newKey;

    public static int musicVolume = 40;
    public static int effectVolume = 50;

    public static JLabel settingLabel = new JLabel("SETTING");
    public static JLabel musicVolumeLabel = new JLabel("MUSIC VOLUME");
    public static JLabel musicVolumeValue = new JLabel(musicVolume+"%");
    public static JButton musicDecreaseVolumeButton = new RoundButton("-");
    public static JButton musicIncreaseVolumeButton = new RoundButton("+");
    public static JLabel effectVolumeLabel = new JLabel("SOUND FX VOLUME");
    public static JLabel effectVolumeValue = new JLabel(effectVolume+"%");
    public static JButton effectDecreaseVolumeButton = new RoundButton("-");
    public static JButton effectIncreaseVolumeButton = new RoundButton("+");

    public static JLabel moveRightLabel = new JLabel("Move Right");
    public static JButton moveRightButton = new RoundButton();
    public static JLabel moveLeftLabel = new JLabel("Move Left");
    public static JButton moveLeftButton = new RoundButton();
    public static JLabel rotateCWLabel = new JLabel("Rotate Clockwise");
    public static JButton rotateCWButton = new RoundButton();
    public static JLabel rotateCT_CWLabel = new JLabel("Rotate Counter-Clockwise");
    public static JButton rotateCT_CWButton = new RoundButton();
    public static JLabel softDropLabel = new JLabel("Soft Drop");
    public static JButton softDropButton = new RoundButton();
    public static JLabel hardDropLabel = new JLabel("Hard Drop");
    public static JButton hardDropButton = new RoundButton();
    public static JLabel holdLabel = new JLabel("Hold");
    public static JButton holdButton = new RoundButton();

    public Font font = new Font("Futura",Font.PLAIN,20);
    public SettingPanel(){
        isSetting = false;
        settingKey = -1;

        this.setOpaque(true);
        this.setBounds(470, 155, 600, 550);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3));
        this.setLayout(null);

        settingLabel.setForeground(new Color(193,221,196,255));
        settingLabel.setFont(new Font("Futura",Font.BOLD,40));
        settingLabel.setBounds(200, 30, 200, 40);
        settingLabel.setHorizontalAlignment(JLabel.CENTER);

        musicVolumeLabel.setForeground(new Color(193,221,196,255));
        musicVolumeLabel.setFont(font);
        musicVolumeLabel.setBounds(50, 95, 200, 25);
        musicVolumeLabel.setHorizontalAlignment(JLabel.CENTER);

        musicDecreaseVolumeButton.setForeground(new Color(193,221,196,255));
        musicDecreaseVolumeButton.setFont(font);
        musicDecreaseVolumeButton.setBounds(293, 95, 60, 25);
        musicDecreaseVolumeButton.setHorizontalAlignment(JLabel.CENTER);
        musicDecreaseVolumeButton.setFocusable(false);
        musicDecreaseVolumeButton.setBackground(Color.gray);
        musicDecreaseVolumeButton.addActionListener(this);

        musicVolumeValue.setForeground(new Color(193,221,196,255));
        musicVolumeValue.setFont(font);
        musicVolumeValue.setBounds(395, 95, 60, 25);
        musicVolumeValue.setHorizontalAlignment(JLabel.CENTER);

        musicIncreaseVolumeButton.setForeground(new Color(193,221,196,255));
        musicIncreaseVolumeButton.setFont(font);
        musicIncreaseVolumeButton.setBounds(497, 95, 60, 25);
        musicIncreaseVolumeButton.setHorizontalAlignment(JLabel.CENTER);
        musicIncreaseVolumeButton.setFocusable(false);
        musicIncreaseVolumeButton.setBackground(Color.gray);
        musicIncreaseVolumeButton.addActionListener(this);
        //-----------------------------------------------------------
        effectVolumeLabel.setForeground(new Color(193,221,196,255));
        effectVolumeLabel.setFont(font);
        effectVolumeLabel.setBounds(50, 145, 200, 25);
        effectVolumeLabel.setHorizontalAlignment(JLabel.CENTER);

        effectDecreaseVolumeButton.setForeground(new Color(193,221,196,255));
        effectDecreaseVolumeButton.setFont(font);
        effectDecreaseVolumeButton.setBounds(293, 145, 60, 25);
        effectDecreaseVolumeButton.setHorizontalAlignment(JLabel.CENTER);
        effectDecreaseVolumeButton.setFocusable(false);
        effectDecreaseVolumeButton.setBackground(Color.gray);
        effectDecreaseVolumeButton.addActionListener(this);

        effectVolumeValue.setForeground(new Color(193,221,196,255));
        effectVolumeValue.setFont(font);
        effectVolumeValue.setBounds(395, 145, 60, 25);
        effectVolumeValue.setHorizontalAlignment(JLabel.CENTER);


        effectIncreaseVolumeButton.setForeground(new Color(193,221,196,255));
        effectIncreaseVolumeButton.setFont(font);
        effectIncreaseVolumeButton.setBounds(497, 145, 60, 25);
        effectIncreaseVolumeButton.setHorizontalAlignment(JLabel.CENTER);
        effectIncreaseVolumeButton.setFocusable(false);
        effectIncreaseVolumeButton.setBackground(Color.gray);
        effectIncreaseVolumeButton.addActionListener(this);
        //----------------------------------------------------
        moveRightLabel.setForeground(new Color(193,221,196,255));
        moveRightLabel.setFont(font);
        moveRightLabel.setBounds(50, 195, 200, 25);
        moveRightLabel.setHorizontalAlignment(JLabel.CENTER);

        moveRightButton.setText(showName(moveRightKey));
        moveRightButton.setForeground(new Color(193,221,196,255));
        moveRightButton.setFont(font);
        moveRightButton.setBounds(350, 195, 150, 25);
        moveRightButton.setHorizontalAlignment(JLabel.CENTER);
        moveRightButton.setFocusable(false);
        moveRightButton.setBackground(Color.gray);
        moveRightButton.addActionListener(this);
        //---------------------------------------
        moveLeftLabel.setForeground(new Color(193,221,196,255));
        moveLeftLabel.setFont(font);
        moveLeftLabel.setBounds(50, 245, 200, 25);
        moveLeftLabel.setHorizontalAlignment(JLabel.CENTER);

        moveLeftButton.setText(showName(moveLeftKey));
        moveLeftButton.setForeground(new Color(193,221,196,255));
        moveLeftButton.setFont(font);
        moveLeftButton.setBounds(350, 245, 150, 25);
        moveLeftButton.setHorizontalAlignment(JLabel.CENTER);
        moveLeftButton.setFocusable(false);
        moveLeftButton.setBackground(Color.gray);
        moveLeftButton.addActionListener(this);
        //---------------------------------------
        rotateCWLabel.setForeground(new Color(193,221,196,255));
        rotateCWLabel.setFont(font);
        rotateCWLabel.setBounds(50, 295, 200, 25);
        rotateCWLabel.setHorizontalAlignment(JLabel.CENTER);

        rotateCWButton.setText(showName(rotateCWKey));
        rotateCWButton.setForeground(new Color(193,221,196,255));
        rotateCWButton.setFont(font);
        rotateCWButton.setBounds(350, 295, 150, 25);
        rotateCWButton.setHorizontalAlignment(JLabel.CENTER);
        rotateCWButton.setFocusable(false);
        rotateCWButton.setBackground(Color.gray);
        rotateCWButton.addActionListener(this);
        //---------------------------------------
        rotateCT_CWLabel.setForeground(new Color(193,221,196,255));
        rotateCT_CWLabel.setFont(font);
        rotateCT_CWLabel.setBounds(25, 345,250, 25);
        rotateCT_CWLabel.setHorizontalAlignment(JLabel.CENTER);

        rotateCT_CWButton.setText(showName(rotateCT_CWKey));
        rotateCT_CWButton.setForeground(new Color(193,221,196,255));
        rotateCT_CWButton.setFont(font);
        rotateCT_CWButton.setBounds(350, 345, 150, 25);
        rotateCT_CWButton.setHorizontalAlignment(JLabel.CENTER);
        rotateCT_CWButton.setFocusable(false);
        rotateCT_CWButton.setBackground(Color.gray);
        rotateCT_CWButton.addActionListener(this);
        //---------------------------------------
        softDropLabel.setForeground(new Color(193,221,196,255));
        softDropLabel.setFont(font);
        softDropLabel.setBounds(50, 395, 200, 25);
        softDropLabel.setHorizontalAlignment(JLabel.CENTER);


        softDropButton.setText(showName(softDropKey));
        softDropButton.setForeground(new Color(193,221,196,255));
        softDropButton.setFont(font);
        softDropButton.setBounds(350, 395, 150, 25);
        softDropButton.setHorizontalAlignment(JLabel.CENTER);
        softDropButton.setFocusable(false);
        softDropButton.setBackground(Color.gray);
        softDropButton.addActionListener(this);
        //---------------------------------------
        hardDropLabel.setForeground(new Color(193,221,196,255));
        hardDropLabel.setFont(font);
        hardDropLabel.setBounds(50, 445, 200, 25);
        hardDropLabel.setHorizontalAlignment(JLabel.CENTER);

        hardDropButton.setText(showName(hardDropKey));
        hardDropButton.setForeground(new Color(193,221,196,255));
        hardDropButton.setFont(font);
        hardDropButton.setBounds(350, 445, 150, 25);
        hardDropButton.setHorizontalAlignment(JLabel.CENTER);
        hardDropButton.setFocusable(false);
        hardDropButton.setBackground(Color.gray);
        hardDropButton.addActionListener(this);
        //---------------------------------------
        holdLabel.setForeground(new Color(193,221,196,255));
        holdLabel.setFont(font);
        holdLabel.setBounds(50, 495, 200, 25);
        holdLabel.setHorizontalAlignment(JLabel.CENTER);

        holdButton.setText(showName(holdKey));
        holdButton.setForeground(new Color(193,221,196,255));
        holdButton.setFont(font);
        holdButton.setBounds(350, 495, 150, 25);
        holdButton.setHorizontalAlignment(JLabel.CENTER);
        holdButton.setFocusable(false);
        holdButton.setBackground(Color.gray);
        holdButton.addActionListener(this);
        //---------------------------------------

        this.add(settingLabel);
        this.add(musicVolumeLabel);
        this.add(musicVolumeValue);
        this.add(musicIncreaseVolumeButton);
        this.add(musicDecreaseVolumeButton);
        this.add(effectVolumeLabel);
        this.add(effectVolumeValue);
        this.add(effectIncreaseVolumeButton);
        this.add(effectDecreaseVolumeButton);

        this.add(moveRightLabel);
        this.add(moveRightButton);
        this.add(moveLeftLabel);
        this.add(moveLeftButton);
        this.add(rotateCWLabel);
        this.add(rotateCWButton);
        this.add(rotateCT_CWLabel);
        this.add(rotateCT_CWButton);
        this.add(softDropLabel);
        this.add(softDropButton);
        this.add(hardDropLabel);
        this.add(hardDropButton);
        this.add(holdLabel);
        this.add(holdButton);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    public static void disableButton(){
        musicDecreaseVolumeButton.setEnabled(false);
        musicIncreaseVolumeButton.setEnabled(false);
        effectDecreaseVolumeButton.setEnabled(false);
        effectIncreaseVolumeButton.setEnabled(false);
        moveLeftButton.setEnabled(false);
        moveRightButton.setEnabled(false);
        rotateCWButton.setEnabled(false);
        rotateCT_CWButton.setEnabled(false);
        softDropButton.setEnabled(false);
        hardDropButton.setEnabled(false);
        holdButton.setEnabled(false);
    }

    public static void enableButton(){
        musicDecreaseVolumeButton.setEnabled(true);
        musicIncreaseVolumeButton.setEnabled(true);
        effectDecreaseVolumeButton.setEnabled(true);
        effectIncreaseVolumeButton.setEnabled(true);
        moveLeftButton.setEnabled(true);
        moveRightButton.setEnabled(true);
        rotateCWButton.setEnabled(true);
        rotateCT_CWButton.setEnabled(true);
        softDropButton.setEnabled(true);
        hardDropButton.setEnabled(true);
        holdButton.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == musicDecreaseVolumeButton && musicVolume!= 0){
            musicVolume -= 10;
            musicVolumeValue.setText((musicVolume)+"%");
            GameFrame.getMusic().setVolume(musicVolume/2);
        }
        else if(e.getSource() == musicIncreaseVolumeButton && musicVolume != 100){
            musicVolume += 10;
            musicVolumeValue.setText((musicVolume)+"%");
            GameFrame.getMusic().setVolume(musicVolume/2);
        }
        else if(e.getSource() == effectDecreaseVolumeButton && effectVolume!= 0){
            effectVolume -= 10;
            effectVolumeValue.setText((effectVolume)+"%");
            
        }
        else if(e.getSource() == effectIncreaseVolumeButton && effectVolume != 100){
            effectVolume += 10;
            effectVolumeValue.setText((effectVolume)+"%");
            GameFrame.getEffect().setVolume(effectVolume/2);
        }
        else{
            if(e.getSource() == moveLeftButton){
                settingKey = 1;
                isSetting = true;
                GameFrame.getAnyKeyPanel().setVisible(true);
            }
            else if(e.getSource() == moveRightButton){
                settingKey = 2;
                isSetting = true;
                GameFrame.getAnyKeyPanel().setVisible(true);
            }
            else if(e.getSource() == rotateCWButton){
                settingKey = 3;
                isSetting = true;
                GameFrame.getAnyKeyPanel().setVisible(true);
            }
            else if(e.getSource() == rotateCT_CWButton){
                settingKey = 4;
                isSetting = true;
                GameFrame.getAnyKeyPanel().setVisible(true);
            }
            else if(e.getSource() == softDropButton){
                settingKey = 5;
                isSetting = true;
                GameFrame.getAnyKeyPanel().setVisible(true);
            }
            else if(e.getSource() == hardDropButton){
                settingKey = 6;
                isSetting = true;
                GameFrame.getAnyKeyPanel().setVisible(true);
            }
            else if(e.getSource() == holdButton){
                settingKey = 7;
                isSetting = true;
                GameFrame.getAnyKeyPanel().setVisible(true);
            }
            disableButton();
        }
    }

    public static void setNewKey(){
        switch(settingKey){
            case 1:
                moveLeftKey = newKey;
                moveLeftButton.setText(showName(moveLeftKey));
                break;
            case 2:
                moveRightKey = newKey;
                moveRightButton.setText(showName(moveRightKey));
                break;
            case 3:
                rotateCWKey = newKey;
                rotateCWButton.setText(showName(rotateCWKey));
                break;
            case 4:
                rotateCT_CWKey = newKey;
                rotateCT_CWButton.setText(showName(rotateCT_CWKey));
                break;
            case 5:
                softDropKey = newKey;
                softDropButton.setText(showName(softDropKey));
                break;
            case 6:
                hardDropKey = newKey;
                hardDropButton.setText(showName(hardDropKey));
                break;
            case 7:
                holdKey = newKey;
                holdButton.setText(showName(holdKey));
                break;
        }
        newKey = -1;
        settingKey = -1;
        isSetting = false;
        enableButton();
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
        if(code == moveLeftKey && settingKey != 1){
            return true;
        }
        if(code == moveRightKey && settingKey != 2){
            return true;
        }
        if(code == rotateCWKey && settingKey != 3){
            return true;
        }
        if(code == rotateCT_CWKey && settingKey != 4){
            return true;
        }
        if(code == softDropKey && settingKey != 5){
            return true;
        }
        if(code == hardDropKey && settingKey != 6){
            return true;
        }
        if(code == holdKey && settingKey != 7){
            return true;
        }
        return false;
    }
}
