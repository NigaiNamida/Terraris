import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RewardSlotPanel extends JPanel implements MouseListener {

    private String currentSlotPanel;
    private int slotNumber;
    private int slotCount;
    private BlockTexture texture;
    private JLabel rewardSlotLabel;
    private Image rewardSlotImage;
    private String path = "Assets/Image/Background/RewardPanel/";

    public RewardSlotPanel(String currentPanel, int i, int length, BlockTexture texture){
        this.currentSlotPanel = currentPanel;
        this.slotCount = length;
        switch (currentPanel) {
            case "RewardPanel":
                createRewardSlot(i);
                break;        
            case "AddPanel":
                createAddSlot(i, length, texture);
                break;
            case "UpgradePanel":
                createUpgradeSlot(i, texture);
                break;   
            case "RemovePanel":
                createRemoveSlot(i, texture);
                break;
            default:
                break;
        }
    }

    public void createRewardSlot(int i) {
        String text;
        switch (i) {
            case 0:
                text = "Add";
                break;
            case 1:
                text = "Upgrade";
                break;
            case 2:
                text = "Remove";
                break;            
            default:
                text = "Skip";
                break;
        }

        this.slotNumber = i;
        this.setBackground(Color.black);
        this.addMouseListener(this);

        rewardSlotLabel = new JLabel(text);
        rewardSlotLabel.setFont(GameFrame.getTerrariaFont(25));
        rewardSlotLabel.setBounds(0, 30, 150, 25);

        rewardSlotLabel.setForeground(new Color(193,221,196,255));
        rewardSlotLabel.setHorizontalAlignment(JLabel.CENTER);

        rewardSlotImage = new ImageIcon(path + slotNumber + "_Close.png").getImage();

        this.add(rewardSlotLabel);
    }

    public void createAddSlot(int i, int length, BlockTexture texture) {
        String text;

        if(i == length-1){
            text = "Back";
        }

        else{
            this.texture = texture;
            switch (texture) {
                case Sand_Lv1:
                    text = "Sand";
                    break;
                case Cloud_Lv1:
                    text = "Cloud";
                    break;
                case Bubble_Lv1: 
                    text = "Bubble";
                    break;
                case Dynamite_Lv1:
                    text = "Dynamite";
                    break;
                default:
                    text = "";
                    break;
            }
        }

        this.slotNumber = i;
        this.setBackground(Color.black);
        this.addMouseListener(this);

        rewardSlotLabel = new JLabel(text);
        rewardSlotLabel.setFont(GameFrame.getTerrariaFont(25));
        rewardSlotLabel.setBounds(0, 30, 150, 25);

        rewardSlotLabel.setForeground(new Color(193,221,196,255));
        rewardSlotLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(rewardSlotLabel);
    }

    public void createUpgradeSlot(int i, BlockTexture texture) {
        String text;

        if(i == slotCount-1){
            text = "Back";
        }
        else{
            this.texture = texture;
            text = "";
        }

        this.slotNumber = i;
        this.setBackground(Color.black);
        this.addMouseListener(this);

        rewardSlotLabel = new JLabel(text);
        rewardSlotLabel.setFont(GameFrame.getTerrariaFont(25));
        rewardSlotLabel.setBounds(0, 30, 100, 25);

        rewardSlotLabel.setForeground(new Color(193,221,196,255));
        rewardSlotLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(rewardSlotLabel);
    }
    
    public void createRemoveSlot(int i, BlockTexture texture) {
        String text;

        if(i == slotCount-1){
            text = "Back";
        }
        else{
            this.texture = texture;
            text = "";
        }

        this.slotNumber = i;
        this.setBackground(Color.black);
        this.addMouseListener(this);

        rewardSlotLabel = new JLabel(text);
        rewardSlotLabel.setFont(GameFrame.getTerrariaFont(25));
        rewardSlotLabel.setBounds(0, 30, 100, 25);

        rewardSlotLabel.setForeground(new Color(193,221,196,255));
        rewardSlotLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(rewardSlotLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // drawBackGround(g);
        // drawBorder(g);
        if (currentSlotPanel != "RewardPanel") {
            drawBlock(g);
            drawLevel(g);
        }
        else {
            drawAsset(g);
        }

    }

    public void drawBlock(Graphics g){
        char[][] isAround;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:    
                    isAround = new char[][]{{'D','0','D'},{'0','1','1'},{'D','1','1'}};
                    break;
                case 1:
                    isAround = new char[][]{{'D','0','D'},{'1','1','0'},{'1','1','D'}};
                    break;
                case 2:
                    isAround = new char[][]{{'D','1','1'},{'0','1','1'},{'D','0','D'}};
                    break;
                default:
                    isAround = new char[][]{{'1','1','D'},{'1','1','0'},{'D','0','D'}};
                    break;
            }
            if(currentSlotPanel == "AddPanel" && texture != null){
                TetrisTexture.mergePileTexture(g, texture.getColor(), 50 + (i%2)*25, 75 + (i/2)*25, isAround);
            }
            else if (currentSlotPanel != "RewardPanel" && texture != null) {
                TetrisTexture.mergePileTexture(g, texture.getColor(), (i%2)*25, (i/2)*25, isAround);
            }
        }
    }

    public void drawLevel(Graphics g) {
        if(texture != null){
            switch (texture) {
                case Sand_Lv1:
                case Cloud_Lv1:
                case Bubble_Lv1: 
                case Dynamite_Lv1:       
                    rewardSlotImage = new ImageIcon(path + "Lv1.png").getImage();
                    break;
                case Sand_Lv2:
                case Cloud_Lv2:
                case Bubble_Lv2: 
                case Dynamite_Lv2:       
                    rewardSlotImage = new ImageIcon(path + "Lv2.png").getImage();
                    break;
                case Sand_Lv3:
                case Cloud_Lv3:
                case Bubble_Lv3: 
                case Dynamite_Lv3:       
                    rewardSlotImage = new ImageIcon(path + "Lv3.png").getImage();
                    break;
                default:
                    rewardSlotImage = new ImageIcon(path + "Lv0.png").getImage();
                    break;
            }
        
            if(currentSlotPanel == "AddPanel"){
                g.drawImage(rewardSlotImage, 85, 110, null);
            }
            else if (currentSlotPanel != "RewardPanel") {
                g.drawImage(rewardSlotImage, 35, 35, null);
            }
        }
    }

    public void drawAsset(Graphics g){
        switch (slotNumber) {
            case 0:  
                g.drawImage(rewardSlotImage, 43, 90, null);  
                break;
            case 1:
                g.drawImage(rewardSlotImage, 43, 58, null);
                break;
            case 2:
                g.drawImage(rewardSlotImage, 47, 76, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }


    @Override
    public void mouseReleased(MouseEvent e) {
        RewardPanel rewardPanel = GameFrame.getRewardPanel();
        switch (currentSlotPanel) {
            case "RewardPanel":
                if(slotNumber == 3){
                    rewardPanel.selectExit();
                }
                else{
                    rewardPanel.selectReward(slotNumber);
                }
                break;
            case "AddPanel":
                if(slotNumber == slotCount-1){
                    rewardPanel.promptReward();
                }
                else{
                    ArrayList<BlockTexture> addedTexture = new ArrayList<>();
                    addedTexture.add(texture);
                    addedTexture.add(BlockTexture.Grass);
                    addedTexture.add(BlockTexture.Stone);
                    TetrisPiece.addTexture(addedTexture);
                    rewardPanel.selectExit();
                }
                break;
            case "UpgradePanel":
                if(slotNumber == slotCount-1){
                    rewardPanel.promptReward();
                }
                else{
                    if(texture != null){
                        ArrayList<BlockTexture> addedTexture = new ArrayList<>();
                        switch (texture) {
                            case Sand_Lv1:
                                addedTexture.add(BlockTexture.Sand_Lv2);
                                break;
                            case Cloud_Lv1:
                                addedTexture.add(BlockTexture.Cloud_Lv2);
                                break;
                            case Bubble_Lv1: 
                                addedTexture.add(BlockTexture.Bubble_Lv2);
                                break;
                            case Dynamite_Lv1:       
                                addedTexture.add(BlockTexture.Dynamite_Lv2);
                                break;
                            case Sand_Lv2:
                                addedTexture.add(BlockTexture.Sand_Lv3);
                                break;
                            case Cloud_Lv2:
                                addedTexture.add(BlockTexture.Cloud_Lv3);
                                break;
                            case Bubble_Lv2: 
                                addedTexture.add(BlockTexture.Bubble_Lv3);
                                break;
                            case Dynamite_Lv2:       
                                addedTexture.add(BlockTexture.Dynamite_Lv3);
                                break;
                            default:
                                break;
                        }
                        TetrisPiece.removeTextureBlock(texture);
                        TetrisPiece.addTexture(addedTexture);
                        rewardPanel.selectExit();
                    }
                }
                break;
            case "RemovePanel":
                if(slotNumber == slotCount-1){
                    rewardPanel.promptReward();
                }
                else{
                    TetrisPiece.removeTextureIndex(slotNumber);
                    rewardPanel.selectExit();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        rewardSlotImage = new ImageIcon(path + slotNumber + "_Open.png").getImage();
        GameFrame.playSE(12);
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        rewardSlotImage = new ImageIcon(path + slotNumber + "_Close.png").getImage();
        repaint();
    }
}
