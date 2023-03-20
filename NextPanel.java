

import java.awt.*;
import javax.swing.*;

import javax.swing.border.LineBorder;

public class NextPanel extends JPanel{

    public static JLabel nextLabel = new JLabel();
    public static PlayZone pZone = GameFrame.pZone;
    public TetrisTexture texture;

    private int gridCols;
    private int gridRows;

    private int blockSize;

    protected TetrisPiece block;

    public NextPanel(){

        nextLabel.setText("NEXT");
        nextLabel.setForeground(new Color(193,221,196,255));
        nextLabel.setFont(new Font("Futura",Font.BOLD,20));

        //setting panel
        this.setBounds(545, 50, 100, 100);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));

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