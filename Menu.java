import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Menu extends JPanel implements ActionListener{
    private static BufferedImage myPicture;
    private static JButton startButton;
    private static JButton leaderboardButton;
    private static JButton settingButton;
    private static JButton exitButton;
    private static JLabel LogoLabel;
    Menu(){
        startButton = new JButton("Start Game");
        leaderboardButton = new JButton("Leaderboard");
        settingButton = new JButton("Setting");
        exitButton = new JButton("Exit");

        this.setVisible(true);
        this.setBounds(140, 20, 520, 520);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3,true));
        this.setLayout(null);

        startButton.setSize(200, 40);
        startButton.setLocation(160, 280);
        startButton.setBackground(new Color(29,201,88));
        startButton.setForeground(new Color(89,68,52));
        startButton.setFont(GameFrame.getTerrariaFont(30));
        //startButton.setBorder(new LineBorder(new Color(239,239,239,255),2));
        this.add(startButton);
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                GameFrame.playSE(12);
            }
        });

        leaderboardButton.setSize(180, 30);
        leaderboardButton.setLocation(170, 350);
        leaderboardButton.setBackground(Color.gray);
        leaderboardButton.setForeground(new Color(193,221,196,255));
        leaderboardButton.setFont(GameFrame.getTerrariaFont(20));
        //leaderboardButton.setBorder(new LineBorder(Color.WHITE,2));
        this.add(leaderboardButton);
        leaderboardButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                GameFrame.playSE(12);
            }
        });

        settingButton.setSize(180, 30);
        settingButton.setLocation(170, 410);
        settingButton.setBackground(Color.gray);
        settingButton.setForeground(new Color(193,221,196,255));
        settingButton.setFont(GameFrame.getTerrariaFont(20));
        //settingButton.setBorder(new LineBorder(Color.WHITE,2));
        this.add(settingButton);
        settingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                GameFrame.playSE(12);
            }
        });

        exitButton.setSize(180, 30);
        exitButton.setLocation(170, 470);
        exitButton.setBackground(Color.gray);
        exitButton.setForeground(new Color(193,221,196,255));
        exitButton.setFont(GameFrame.getTerrariaFont(20));
        //exitButton.setBorder(new LineBorder(Color.WHITE,2));
        this.add(exitButton);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                GameFrame.playSE(12);
            }
        });

        try {
            myPicture = ImageIO.read(new File("Assets/Image/Terraris.png"));
       } catch (IOException e) {
            e.printStackTrace(); 
        }
        LogoLabel = new JLabel(new ImageIcon(myPicture));
        LogoLabel.setSize(400, 300);
        LogoLabel.setLocation(70, 0);
        this.add(LogoLabel);

        startButton.addActionListener(this);
        leaderboardButton.addActionListener(this);
        settingButton.addActionListener(this);
        exitButton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton){
            this.setVisible(false);
            GameFrame.playSE(7);
            GameFrame.startGame();
        }
        else if(e.getSource() == leaderboardButton){
            this.setVisible(false);
            GameFrame.playSE(7);
            GameFrame.getLeaderboard().setVisible(true);
        }
        else if(e.getSource() == settingButton){
            this.setVisible(false);
            GameFrame.playSE(7);
            GameFrame.getSettingPanel().setVisible(true);
            SettingPanel.setNewKey(-1);
            SettingPanel.setSettingKey(null);
            SettingPanel.setSetting(false);
            SettingPanel.enableButton();
        }
        else if(e.getSource() == exitButton){
            GameFrame.playSE(7);
            Leaderboard.saveLeaderboard();
            SettingPanel.saveSetting();
            System.exit(0);
        }
    }
}
