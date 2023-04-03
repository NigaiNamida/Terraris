import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class NextPanel extends JPanel{
    private JLabel nextLabel;
    private int gridCols;
    private int gridRows;

    private int blockSize;

    private TetrisPiece block;

    public NextPanel(){
        nextLabel = new JLabel("NEXT");
        nextLabel.setForeground(new Color(193,221,196,255));
        nextLabel.setFont(new Font("Futura",Font.BOLD,20));

        //setting panel
        this.setBounds(10, 150, 125, 125);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3,true));

        //add component
        this.add(nextLabel);
        block = PlayZone.getNextPiece();

        //set value of var for grid
        gridCols = 5;
        blockSize = this.getWidth()/ gridCols;
        gridRows = this.getHeight() / blockSize;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //not have = no panel bg color
        drawBlock(g);
    }

    private void drawBlock(Graphics g){
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int x = (int)(((gridCols-blockWidth)/2.0) * blockSize); //coordinate + offset
        int y = (int)(((gridRows-blockHeight)*(2/3.0)) * blockSize); //coordinate + offset
        TetrisTexture.drawBlockTexture(g,block.getName(),block.getVariant(),x,y);
    }

    public TetrisPiece getBlock() {
        return block;
    }

    public void setBlock(TetrisPiece block) {
        this.block = block;
    }
}