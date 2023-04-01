import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PausePanel extends JPanel implements ActionListener{
    private static JLabel pauseLabel;
    private static JButton resumeButton;
    private static JButton settingButton;
    private static JButton exitButton;

    public PausePanel(){
        pauseLabel = new JLabel("PAUSED");
        resumeButton = new JButton("RESUME");
        settingButton = new JButton("SETTING");
        exitButton = new JButton("EXIT TO MENU");

        this.setOpaque(true);
        this.setBounds(275, 120, 250, 300);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3));
        this.setVisible(false);
        this.setLayout(null);

        pauseLabel.setForeground(new Color(193,221,196,255));
        pauseLabel.setFont(new Font("Futura",Font.BOLD,40));
        pauseLabel.setBounds(50, 30, 150, 40);
        pauseLabel.setHorizontalAlignment(JLabel.CENTER);

        resumeButton.setForeground(new Color(89,68,52));
        resumeButton.setFont(new Font("Futura",Font.BOLD,20));
        //resumeButton.setBorder(new LineBorder(Color.WHITE,2));
        resumeButton.setBounds(25, 115, 200, 30);
        resumeButton.setHorizontalAlignment(JLabel.CENTER);
        resumeButton.setFocusable(false);
        resumeButton.setBackground(new Color(29,201,88));
        resumeButton.addActionListener(this);

        settingButton.setForeground(new Color(193,221,196,255));
        settingButton.setFont(new Font("Futura",Font.BOLD,20));
        //settingButton.setBorder(new LineBorder(Color.WHITE,2));
        settingButton.setBounds(25, 175, 200, 30);
        settingButton.setHorizontalAlignment(JLabel.CENTER);
        settingButton.setFocusable(false);
        settingButton.setBackground(Color.gray);
        settingButton.addActionListener(this);
        
        exitButton.setForeground(new Color(193,221,196,255));
        exitButton.setFont(new Font("Futura",Font.BOLD, 20));
        //exitButton.setBorder(new LineBorder(Color.WHITE,2));
        exitButton.setBounds(25, 235, 200, 30);
        exitButton.setHorizontalAlignment(JLabel.CENTER);
        exitButton.setFocusable(false);
        exitButton.setBackground(Color.gray);
        exitButton.addActionListener(this);

        this.add(pauseLabel);
        this.add(resumeButton);
        this.add(settingButton);
        this.add(exitButton);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == resumeButton){
            GameFrame.continueGame();
            KeyHandler.setPause(false);
        }
        else if(e.getSource() == settingButton){
            this.setVisible(false);
            SettingPanel.setNewKey(-1);
            SettingPanel.setSettingKey(null);
            SettingPanel.setSetting(false);
            SettingPanel.enableButton();
            GameFrame.getSettingPanel().setVisible(true);
        }
        else if(e.getSource() == exitButton){
            Leaderboard.saveLeaderboard();
            SettingPanel.saveSetting();
            GameFrame.getPlayZone().setGameOver(true);
            GameFrame.getBossPanel().stopAllTimer();
            Terraris.getGameFrame().backToMenu();
        }
    }
}
