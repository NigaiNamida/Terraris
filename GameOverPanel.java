import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel implements ActionListener{
    private static JLabel gameOverLabel;
    private static JButton retryButton;
    private static JButton exitButton;

    public GameOverPanel(){
        gameOverLabel = new JLabel("GAME OVER");new JLabel("GAME OVER");
        retryButton = new JButton("RETRY");
        exitButton = new JButton("EXIT TO MENU");
        
        this.setOpaque(true);
        this.setBounds(225, 155, 350, 250);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3,true));
        this.setVisible(false);
        this.setLayout(null);

        gameOverLabel.setForeground(Color.red);
        gameOverLabel.setFont(GameFrame.getTerrariaFont(40));
        gameOverLabel.setBounds(35, 40, 280, 40);
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);

        retryButton.setForeground(new Color(89,68,52));
        retryButton.setFont(GameFrame.getTerrariaFont(20));
        //retryButton.setBorder(new LineBorder(Color.WHITE,2));
        retryButton.setBounds(75, 120, 200, 30);
        retryButton.setHorizontalAlignment(JLabel.CENTER);
        retryButton.setFocusable(false);
        retryButton.setBackground(new Color(29,201,88));
        retryButton.addActionListener(this);

        exitButton.setForeground(new Color(193,221,196,255));
        exitButton.setFont(GameFrame.getTerrariaFont(20));
        //exitButton.setBorder(new LineBorder(Color.WHITE,2));
        exitButton.setBounds(75, 180, 200, 30);
        exitButton.setHorizontalAlignment(JLabel.CENTER);
        exitButton.setFocusable(false);
        exitButton.setBackground(Color.gray);
        exitButton.addActionListener(this);


        this.add(gameOverLabel);
        this.add(retryButton);
        this.add(exitButton);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == retryButton){
            GameFrame.getBossPanel().stopAllTimer();
            Terraris.getGameFrame().retry();
            this.setVisible(false);
            Terraris.getGameFrame().repaint();
        }
        else if(e.getSource() == exitButton){
            Leaderboard.saveLeaderboard();
            SettingPanel.saveSetting();
            GameFrame.getBossPanel().stopAllTimer();
            Terraris.getGameFrame().backToMenu();
        }
    }
}
