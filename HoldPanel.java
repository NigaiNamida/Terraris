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
    }

    public TetrisPiece getBlock() {
        return block;
    }

    public void setBlock(TetrisPiece block) {
        this.block = block;
    }
}