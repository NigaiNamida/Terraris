import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RewardPanel extends JPanel implements ActionListener{
    private static JLabel rewardLabel;
    private static RewardSlotPanel[] rewards;
    private static ArrayList<BlockTexture> textures;
    
    public RewardPanel(){
        textures = new ArrayList<>();
        textures.addAll(Arrays.asList(BlockTexture.getSpecialTexture()));
        rewardLabel = new JLabel("Reward");
        rewards = new RewardSlotPanel[3];

        this.setOpaque(true);
        this.setBounds(275, 120, 400, 300);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3,true));
        this.setVisible(false);
        this.setLayout(null);

        rewardLabel.setForeground(new Color(193,221,196,255));
        rewardLabel.setFont(new Font("Futura",Font.BOLD,35));
        rewardLabel.setBounds(0, 30, 400, 40);
        rewardLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(rewardLabel);

    }

    public void promptReward(){
        Collections.shuffle(textures);
        ArrayList<BlockTexture> temp = new ArrayList<>();
        temp.addAll(textures);
        for (int i = 0; i < rewards.length; i++) {
            if(rewards[i] != null){
                this.remove(rewards[i]);
            }
        }
        for (int i = 0; i < rewards.length; i++) {
            BlockTexture slotTexture = temp.get(i);
            rewards[i] = new RewardSlotPanel(slotTexture);
            rewards[i].setBounds(20 + (i*120), 100, 100, 100);
            rewards[i].calculateGrid(5);
            rewards[i].repaint();
            this.add(rewards[i]);
            
        }
        this.setVisible(true);
        KeyHandler.setPause(true);
        repaint();
    }

    public void selectSlot(RewardSlotPanel slot){
        if(slot.isUpgrade()){
            upgrade(slot.getTexture());
        }
        else{
            TetrisPiece.UnlockSpecialTexture(slot.getTexture());
        }
        this.setVisible(false);
        KeyHandler.setPause(false);
    }

    private void upgrade(BlockTexture texture) {
        PlayZone playZone = GameFrame.getPlayZone();
        switch (texture) {
            case Sand:
                
                break;
            case Dynamite:
                playZone.addRadius();
                playZone.addDynamiteDamage();
                break;
            case Cloud:
                playZone.addCloudGravityScale();
                break;
            case Bubble:
                
                break;
            default:
                break;
        }
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if(e.getSource() == resumeButton){
        //     if(GameFrame.getMusic().getClip() != null)
        //         GameFrame.getMusic().resumeSound();
        //     GameFrame.continueGame();
        //     KeyHandler.setPause(false);
        // }
        // else if(e.getSource() == settingButton){
        //     GameFrame.playSE(7);
        //     this.setVisible(false);
        //     SettingPanel.setNewKey(-1);
        //     SettingPanel.setSettingKey(null);
        //     SettingPanel.setSetting(false);
        //     SettingPanel.enableButton();
        //     GameFrame.getSettingPanel().setVisible(true);
        // }
        // else if(e.getSource() == exitButton){
        //     GameFrame.playSE(7);
        //     Leaderboard.saveLeaderboard();
        //     SettingPanel.saveSetting();
        //     GameFrame.getPlayZone().setGameOver(true);
        //     GameFrame.getBossPanel().stopAllTimer();
        //     Terraris.getGameFrame().backToMenu();
        // }
    }
}
