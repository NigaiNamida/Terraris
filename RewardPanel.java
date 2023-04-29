import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RewardPanel extends JPanel{

    private JLabel rewardTitleLabel;
    private JLabel rewardDescriptionLabel;
    private ArrayList<BlockTexture> textures;
    private RewardSlotPanel[] rewardSlots;
    private String currentPanel;

    public RewardPanel(){

        textures = new ArrayList<>();
        textures.addAll(Arrays.asList(BlockTexture.getSpecialTexture()));

        rewardTitleLabel = new JLabel();
        rewardDescriptionLabel = new JLabel();

        currentPanel = "";

        this.add(rewardTitleLabel);

        ArrayList<BlockTexture> addedTexture = new ArrayList<>();
        TetrisPiece.addTexture(addedTexture);

    }

    public void promptReward(){

        currentPanel = "RewardPanel";   
        rewardSlots = new RewardSlotPanel[4];
        createNewPanel(currentPanel,rewardSlots.length);

        for (int i = 0; i < rewardSlots.length; i++) {
            rewardSlots[i] = new RewardSlotPanel(currentPanel, i, rewardSlots.length, null);
            rewardSlots[i].setBorder(new LineBorder(Color.WHITE,3,true));
            if(i != 3){
                rewardSlots[i].setBounds(25 + (i*200), 100, 150, 200);
            }
            else{
                rewardSlots[i].setBounds(425, 325, 150, 50);
            }
            rewardSlots[i].repaint();
            this.add(rewardSlots[i]);
        }

        this.setVisible(true);
        KeyHandler.setPause(true);
        repaint();
    }

    public void promptAddReward(){

        currentPanel = "AddPanel";
        rewardSlots = new RewardSlotPanel[4];
        
        ArrayList<BlockTexture> temp = new ArrayList<>();
        temp.addAll(textures);
        
        if(TetrisPiece.getAddedTextureSet().size() > 15){
            rewardSlots = new RewardSlotPanel[1];
        }

        createNewPanel(currentPanel,rewardSlots.length);
        
        for (int i = 0; i < rewardSlots.length; i++) {
            rewardSlots[i] = new RewardSlotPanel(currentPanel, i, rewardSlots.length, temp.get(i));
            rewardSlots[i].setBorder(new LineBorder(Color.WHITE,3,true));
            if(i != rewardSlots.length-1){
                rewardSlots[i].setBounds(25 + (i*200), 100, 150, 200);
            }
            else{
                rewardSlots[i].setBounds(425, 325, 150, 50);
            }
            rewardSlots[i].repaint();
            this.add(rewardSlots[i]);
        }

        this.setVisible(true);
        repaint();
    }

    public void promptUpgradeReward(){
        currentPanel = "UpgradePanel";
        ArrayList<BlockTexture> temp = new ArrayList<BlockTexture>();

        for (BlockTexture t : TetrisPiece.getAddedTextureSet()) {
            for (BlockTexture ut : BlockTexture.getUpgradableTexture()) {
                if (t == ut){
                    temp.add(t);
                }
            }
        }

        rewardSlots = new RewardSlotPanel[temp.size()+1];

        createNewPanel(currentPanel,rewardSlots.length);
        
        for (int i = 0; i < rewardSlots.length; i++) {
        
            if (i == rewardSlots.length-1){
                rewardSlots[i] = new RewardSlotPanel(currentPanel, i, rewardSlots.length, null);
            }
            else{
                rewardSlots[i] = new RewardSlotPanel(currentPanel, i, rewardSlots.length, temp.get(i));
            }

            if(i != rewardSlots.length-1){
                rewardSlots[i].setBounds(25 + (i%6)*100, 100 + ((i/6)*100), 50, 50);
            }
            else{
                rewardSlots[i].setBounds(425, 425, 150, 50);
                rewardSlots[i].setBorder(new LineBorder(Color.WHITE,3,true));
            }
            rewardSlots[i].repaint();
            this.add(rewardSlots[i]);
        }

        this.setVisible(true);
        repaint();
    }

    public void promptRemoveReward(){

        currentPanel = "RemovePanel";
        ArrayList<BlockTexture> temp = TetrisPiece.getAddedTextureSet();

        rewardSlots = new RewardSlotPanel[temp.size()+1];

        createNewPanel(currentPanel,rewardSlots.length);
        
        for (int i = 0; i < rewardSlots.length; i++) {
        
            if (i == rewardSlots.length-1){
                rewardSlots[i] = new RewardSlotPanel(currentPanel, i, rewardSlots.length, null);
            }
            else{
                rewardSlots[i] = new RewardSlotPanel(currentPanel, i, rewardSlots.length, temp.get(i));
            }

            if(i != rewardSlots.length-1){
                rewardSlots[i].setBounds(25 + (i%6)*100, 100 + ((i/6)*100), 50, 50);
            }
            else{
                rewardSlots[i].setBounds(425, 425, 150, 50);
                rewardSlots[i].setBorder(new LineBorder(Color.WHITE,3,true));
            }
            rewardSlots[i].repaint();
            this.add(rewardSlots[i]);
        }

        this.setVisible(true);
        repaint();
    }

    public void selectReward(int slotNumber){
        switch (slotNumber) {
            case 0:
                promptAddReward();
                break;
            case 1:
                promptUpgradeReward();
                break;
            case 2:
                promptRemoveReward();
                break;
            default:
                break;
        }
    }

    public void selectExit(){
        this.setVisible(false);
        KeyHandler.setPause(false);
    }

    public void createNewPanel(String currentPanel, int rewardSlots){
        this.removeAll();
        rewardTitleLabel.removeAll();
        rewardDescriptionLabel.removeAll();

        switch (currentPanel) {
            case "RewardPanel":
                rewardTitleLabel = new JLabel("Choose level up reward!");
                BossPanel bossPanel = GameFrame.getBossPanel();
                switch (bossPanel.getStage()) {
                    case Day:
                    case Night:
                    case Corruption:
                    case Crimson:
                    case Jungle:
                    case Snow:
                    case Dungeon:
                    case UnderWorld:
                        rewardDescriptionLabel = new JLabel("Prepare for a fight.");
                        break;            
                    default:
                        rewardDescriptionLabel = new JLabel("Entering to the next biome.");
                        break;
                        
                }
                this.setBounds(100, 70, 600, 400);
                rewardDescriptionLabel.setBounds(25, 325, 350, 50);
                rewardDescriptionLabel.setForeground(Color.WHITE);
                rewardDescriptionLabel.setFont(GameFrame.getTerrariaFont(25));
                rewardDescriptionLabel.setHorizontalAlignment(JLabel.CENTER);
                this.add(rewardDescriptionLabel);
                break;
            case "AddPanel":
                if(rewardSlots != 1){
                    rewardTitleLabel = new JLabel("Choose new blocks to add into your deck!");
                }
                else{
                    rewardTitleLabel = new JLabel("There are to many blocks in yor deck.");
                    rewardDescriptionLabel = new JLabel("Try remove some.");
                    rewardDescriptionLabel.setBounds(0, 200, 600, 25);
                    rewardDescriptionLabel.setForeground(Color.WHITE);
                    rewardDescriptionLabel.setFont(GameFrame.getTerrariaFont(25));
                    rewardDescriptionLabel.setHorizontalAlignment(JLabel.CENTER);
                    this.add(rewardDescriptionLabel);
                }
                this.setBounds(100, 70, 600, 400);
                break;
            case "UpgradePanel":
                if(rewardSlots != 1){
                    rewardTitleLabel = new JLabel("Choose special block to enchant it's ability!");
                }
                else{
                    rewardTitleLabel = new JLabel("There are no special block in yor deck.");
                    rewardDescriptionLabel = new JLabel("Try add some.");
                    rewardDescriptionLabel.setBounds(0, 250, 600, 25);
                    rewardDescriptionLabel.setForeground(Color.WHITE);
                    rewardDescriptionLabel.setFont(GameFrame.getTerrariaFont(25));
                    rewardDescriptionLabel.setHorizontalAlignment(JLabel.CENTER);
                    this.add(rewardDescriptionLabel);
                }
                this.setBounds(100, 20, 600, 500);
                break;  
            case "RemovePanel":
                if(rewardSlots != 1){
                    rewardTitleLabel = new JLabel("Choose added block to add remove your deck!");;
                }
                else{
                    rewardTitleLabel = new JLabel("There are added block in yor deck.");
                    rewardDescriptionLabel = new JLabel("Try add some.");
                    rewardDescriptionLabel.setBounds(0, 250, 600, 25);
                    rewardDescriptionLabel.setForeground(Color.WHITE);
                    rewardDescriptionLabel.setFont(GameFrame.getTerrariaFont(25));
                    rewardDescriptionLabel.setHorizontalAlignment(JLabel.CENTER);
                    this.add(rewardDescriptionLabel);
                }
                this.setBounds(100, 20, 600, 500);
                break;
            default:
                break;
        }

        this.setOpaque(true);
        this.setVisible(false);
        this.setLayout(null);
        this.setOpaque(true);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3,true));

        rewardTitleLabel.setForeground(Color.WHITE);
        rewardTitleLabel.setFont(GameFrame.getTerrariaFont(25));
        rewardTitleLabel.setBounds(0, 30, 600, 40);
        rewardTitleLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(rewardTitleLabel);

    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    public void randomNewBlock(){
        textures = new ArrayList<>();
        textures.addAll(Arrays.asList(BlockTexture.getSpecialTexture()));
        Collections.shuffle(textures);
    }

}
