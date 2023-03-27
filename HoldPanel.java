

import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class HoldPanel extends JPanel{
    private JLabel holdLabel;
    
    private int gridCols;
    private int gridRows;

    private int blockSize;

    private TetrisPiece block;

    public HoldPanel(){
        holdLabel = new JLabel("HOLD");
        holdLabel.setForeground(new Color(193,221,196,255));
        holdLabel.setFont(new Font("Futura",Font.BOLD,20));
        //setting panel
        this.setBounds(150, 50, 100, 100);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));

        //add component
        this.add(holdLabel);

        //set value of var for grid
        gridCols = 5;
        blockSize = this.getWidth() / gridCols;
        gridRows = this.getHeight() / blockSize;

    }

    public TetrisPiece getBlock() {
        return block;
    }

    public void setBlock(TetrisPiece block) {
        this.block = block;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//not have = no panel bg color
        drawBlock(g);
    }

    private void drawBlock(Graphics g){
        if(block == null) return;
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        Color color = block.getColor();
        int[][] shape = block.getShape();

        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                //paint where block == 1
                if(shape[row][col] == 1){
                    int x = (int)(((gridCols-blockWidth)/2.0 + col) * blockSize); //coordinate + offset
                    int y = (int)(((gridRows-blockHeight)*(2/3.0)+ row) * blockSize); //coordinate + offset
                    paintBlocks(g,color,x,y);
                }
            }
        }
    }

    private void paintBlocks(Graphics g,Color color,int x,int y){
        g.setColor(color);
        g.fillRect(x, y, blockSize, blockSize);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, blockSize, blockSize);    
    }
}