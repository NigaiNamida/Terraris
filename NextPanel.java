import java.awt.*;
import javax.swing.*;

public class NextPanel extends DataPanel{
    private JLabel nextLabel;
    private TetrisPiece block;

    public NextPanel(){
        super();
        nextLabel = new JLabel("NEXT");
        nextLabel.setForeground(new Color(193,221,196,255));
        nextLabel.setFont(GameFrame.getTerrariaFont(20));

        //setting panel
        this.setBounds(10, 208, 125, 125);

        //add component
        this.add(nextLabel);
        block = PlayZone.getNextPiece();

        //set value of var for grid
        super.calculateGrid(5);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.drawBlock(g, block);
    }

    public TetrisPiece getBlock() {
        return block;
    }

    public void setBlock(TetrisPiece block) {
        this.block = block;
    }
}