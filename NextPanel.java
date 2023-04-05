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
        drawBlock(g);
    }

    private void drawBlock(Graphics g){
        Color color = block.getColor();
        char[][] isAround = {{'0','0','0'},{'0','0','0'},{'0','0','0'}};
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int[][] shape = block.getShape();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                //paint where block == 1
                if(shape[row][col] == 1){
                    for(int offRow = -1;offRow <= 1;offRow++){
                        for (int offCol = -1; offCol <= 1; offCol++) {
                            if(col+offCol >= 0 && col+offCol < blockWidth && row+offRow >= 0 && row+offRow < blockHeight){
                                if(shape[row+offRow][col+offCol] == 1){
                                    isAround[offRow+1][offCol+1] = '1';
                                }
                            }
                        }
                    }
                    int x = (int)(((gridCols-blockWidth)/2.0 + col) * blockSize); //coordinate + offset
                    int y = (int)(((gridRows-blockHeight)*(2/3.0) + row) * blockSize); //coordinate + offset
                    TetrisTexture.mergePileTexture(g, color, x, y, isAround); 
                    isAround = new char[][]{{'0','0','0'},{'0','0','0'},{'0','0','0'}};
                }   
            }
        }
    }

    public TetrisPiece getBlock() {
        return block;
    }

    public void setBlock(TetrisPiece block) {
        this.block = block;
    }
}