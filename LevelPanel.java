import java.awt.*;

import javax.swing.*;

import javax.swing.border.LineBorder;

public class LevelPanel extends JPanel{
    private GameFrame gameFrame;
    private int level;
    private JLabel levelLabel;
    private JLabel levelScore;
    private BossPanel bossPanel;

    public LevelPanel(){
        gameFrame = Terraris.getGameFrame();

        level = 1;
        levelLabel = new JLabel("LEVEL", SwingConstants.CENTER);
        levelScore = new JLabel(""+level, SwingConstants.CENTER);
        //setting panel
        this.setBounds(20, 280, 100, 100);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));

        //add component
        this.add(levelLabel);
        this.add(levelScore);
        this.setLayout(new FlowLayout());
        levelLabel.setForeground(new Color(193,221,196,255));
        levelScore.setForeground(new Color(193,221,196,255));
        levelLabel.setPreferredSize(new Dimension(80, 30));
        levelScore.setPreferredSize(new Dimension(80, 30));
        levelLabel.setFont(new Font("Futura",Font.BOLD,20));
        levelScore.setFont(new Font("Futura",Font.BOLD,30));
        levelScore.setText(""+level);
    }

    public int getLevel() {
        return level;
    }

    public void addLevel() {
        level++;
        if(bossPanel == null){
            bossPanel = gameFrame.getBossPanel();
        }
        bossPanel.spawnChance += 5;
        if(bossPanel.phase == 0){
            bossPanel.canSpawn = true;
            bossPanel.restartSpawnTimer();
            System.out.println("Boss Spawn Active");
            System.out.println(bossPanel.spawnChance + "%");
        }
    }

    public JLabel getLevelScore() {
        return levelScore;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//not have = no panel bg color
    }
}