import java.awt.*;
import javax.swing.*;

public class HoldPanel extends DataPanel{
    private JLabel holdLabel;
    private TetrisPiece block;

    public HoldPanel(){
        super();
        holdLabel = new JLabel("HOLD");
        holdLabel.setForeground(new Color(193,221,196,255));
        holdLabel.setFont(GameFrame.getTerrariaFont(20));
        //setting panel
        this.setBounds(10, 20, 125, 125);

        //add component
        this.add(holdLabel);

        //set value of var for grid
        super.calculateGrid(5);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//not have = no panel bg color
        super.drawBlock(g, block);
        drawLevel(g);
        BossPanel bossPanel = GameFrame.getBossPanel();
        if(bossPanel.getBoss() != null && bossPanel.getBoss().getName() == "Skeletron" && bossPanel.getBoss().getState() > 1){
            drawSkeletronAttack(g);
            drawBorder(g);
        }
    }

    public void drawLevel(Graphics g){
        if(block != null){
            BlockTexture texture = BlockTexture.colorToBlockTexture(block.getColor());
            String path = "Assets/Image/Background/RewardPanel/";
            Image LvImage;
            switch (texture) {
                case Sand_Lv1:
                case Cloud_Lv1:
                case Bubble_Lv1: 
                case Dynamite_Lv1:       
                    LvImage = new ImageIcon(path + "Lv1.png").getImage();
                    break;
                case Sand_Lv2:
                case Cloud_Lv2:
                case Bubble_Lv2: 
                case Dynamite_Lv2:       
                    LvImage = new ImageIcon(path + "Lv2.png").getImage();
                    break;
                case Sand_Lv3:
                case Cloud_Lv3:
                case Bubble_Lv3: 
                case Dynamite_Lv3:       
                    LvImage = new ImageIcon(path + "Lv3.png").getImage();
                    break;
                default:
                    LvImage = new ImageIcon(path + "Lv0.png").getImage();
                    break;
            }
            g.drawImage(LvImage, 105, 105, null);
        }
    }

    public void drawSkeletronAttack(Graphics g){
        BossPanel bossPanel = GameFrame.getBossPanel();
        Image BGImage = new ImageIcon(bossPanel.getStage().getDataPanelBGImageDarkPath()).getImage();
        g.drawImage(BGImage, 0, 0, null);
    }

    private void drawBorder(Graphics g) {
        BossPanel bossPanel = GameFrame.getBossPanel();
        Image BGImage = new ImageIcon(bossPanel.getStage().getDataPanelBorderImagePath()).getImage();
        g.drawImage(BGImage, 0, 0, null);
    }

    public TetrisPiece getBlock() {
        return block;
    }

    public void setBlock(TetrisPiece block) {
        this.block = block;
    }
}