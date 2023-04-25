import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RewardPanel extends JPanel implements ActionListener{
    private static JLabel rewardLabel;
    private static JButton resumeButton;
    private static JButton settingButton;
    private static JButton exitButton;

    public RewardPanel(){
        rewardLabel = new JLabel("Reward");
        resumeButton = new JButton("RESUME");
        settingButton = new JButton("SETTING");
        exitButton = new JButton("EXIT TO MENU");

        this.setOpaque(true);
        this.setBounds(275, 120, 250, 300);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3,true));
        this.setVisible(false);
        this.setLayout(null);

        rewardLabel.setForeground(new Color(193,221,196,255));
        rewardLabel.setFont(new Font("Futura",Font.BOLD,35));
        rewardLabel.setBounds(50, 30, 150, 40);
        rewardLabel.setHorizontalAlignment(JLabel.CENTER);

        resumeButton.setForeground(new Color(89,68,52));
        resumeButton.setFont(new Font("Futura",Font.BOLD,20));
        //resumeButton.setBorder(new LineBorder(Color.WHITE,2));
        resumeButton.setBounds(25, 115, 200, 30);
        resumeButton.setHorizontalAlignment(JLabel.CENTER);
        resumeButton.setFocusable(false);
        resumeButton.setBackground(new Color(29,201,88));
        resumeButton.addActionListener(this);
        resumeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                GameFrame.playSE(12);
            }
        });

        settingButton.setForeground(new Color(193,221,196,255));
        settingButton.setFont(new Font("Futura",Font.BOLD,20));
        //settingButton.setBorder(new LineBorder(Color.WHITE,2));
        settingButton.setBounds(25, 175, 200, 30);
        settingButton.setHorizontalAlignment(JLabel.CENTER);
        settingButton.setFocusable(false);
        settingButton.setBackground(Color.gray);
        settingButton.addActionListener(this);
        settingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                GameFrame.playSE(12);
            }
        });
        
        exitButton.setForeground(new Color(193,221,196,255));
        exitButton.setFont(new Font("Futura",Font.BOLD, 20));
        //exitButton.setBorder(new LineBorder(Color.WHITE,2));
        exitButton.setBounds(25, 235, 200, 30);
        exitButton.setHorizontalAlignment(JLabel.CENTER);
        exitButton.setFocusable(false);
        exitButton.setBackground(Color.gray);
        exitButton.addActionListener(this);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                GameFrame.playSE(12);
            }
        });

        this.add(rewardLabel);
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
            if(GameFrame.getMusic().getClip() != null)
                GameFrame.getMusic().resumeSound();
            GameFrame.continueGame();
            KeyHandler.setPause(false);
        }
        else if(e.getSource() == settingButton){
            GameFrame.playSE(7);
            this.setVisible(false);
            SettingPanel.setNewKey(-1);
            SettingPanel.setSettingKey(null);
            SettingPanel.setSetting(false);
            SettingPanel.enableButton();
            GameFrame.getSettingPanel().setVisible(true);
        }
        else if(e.getSource() == exitButton){
            GameFrame.playSE(7);
            Leaderboard.saveLeaderboard();
            SettingPanel.saveSetting();
            GameFrame.getPlayZone().setGameOver(true);
            GameFrame.getBossPanel().stopAllTimer();
            Terraris.getGameFrame().backToMenu();
        }
    }
}
