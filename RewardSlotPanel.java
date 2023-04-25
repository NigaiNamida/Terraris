import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class RewardSlotPanel extends JPanel implements MouseListener {
    private int gridCols;
    private int gridRows;
    private int blockSize;
    private BlockTexture texture;

    public RewardSlotPanel(BlockTexture texture){
        this.texture = texture;
        this.setBackground(Color.black);
        this.addMouseListener(this);
    }

    public void calculateGrid(int col) {
        gridCols = col;
        blockSize = this.getWidth() / gridCols;
        gridRows = this.getHeight() / blockSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBlock(g, TetrisPiece.getBlock(Tetris.O, texture));
    }
    protected void drawBlock(Graphics g, TetrisPiece block){
        if(block != null){
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
    }

    public BlockTexture getTexture() {
        return texture;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        RewardPanel rewardPanel = GameFrame.getRewardPanel();
        rewardPanel.selectSlot(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        GameFrame.playSE(12);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}


