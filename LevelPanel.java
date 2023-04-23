import java.awt.*;

import javax.swing.*;
public class LevelPanel extends DataPanel{
    private static int level;
    private static JLabel levelScore;
    private static BossPanel bossPanel;
    private JLabel levelLabel;

    public LevelPanel(){
        super();
        level = 1;
        levelLabel = new JLabel("LEVEL", SwingConstants.CENTER);
        levelScore = new JLabel(""+level, SwingConstants.CENTER);
        //setting panel
        this.setBounds(10, 395, 125, 125);

        //add component
        this.add(levelLabel);
        this.add(levelScore);
        this.setLayout(new FlowLayout());
        levelLabel.setForeground(new Color(193,221,196,255));
        levelScore.setForeground(new Color(193,221,196,255));
        levelLabel.setPreferredSize(new Dimension(80, 30));
        levelScore.setPreferredSize(new Dimension(80, 30));
        levelLabel.setFont(GameFrame.getTerrariaFont(25));
        levelScore.setFont(GameFrame.getTerrariaFont(35));
        levelScore.setText(""+level);

        bossPanel = GameFrame.getBossPanel();
    }

    public static int getLevel() {
        return level;
    }

    public static void addLevel() {
        level++;
        bossPanel = GameFrame.getBossPanel();
        bossPanel.spawnChance += 5;
        if(bossPanel.getBoss() == null){
            bossPanel.setCanSpawn(true);
            bossPanel.restartSpawnTimer();
            System.out.println("Boss Spawn Active");
            System.out.println(bossPanel.spawnChance + "%");
        }
    }

    public static JLabel getLevelScore() {
        return levelScore;
    }

}