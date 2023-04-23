import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public abstract class DataPanel extends JPanel{
    private float brightness;
    private int gridCols;
    private int gridRows;
    private int blockSize;
    DataPanel(){
        brightness = (60)/100.0f;
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3,true));
    }
    
    public void calculateGrid(int col) {
        gridCols = col;
        blockSize = this.getWidth()/ gridCols;
        gridRows = this.getHeight() / blockSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackgroundImage(g);
        drawBackgroundBrightness(g);
    }

    private void drawBackgroundImage(Graphics g){
        BossPanel bossPanel = GameFrame.getBossPanel();
        Image BGImage = new ImageIcon(bossPanel.getStage().getBGImagePath()).getImage();
        g.drawImage(BGImage, 0, 0, null);
    }

    private void drawBackgroundBrightness(Graphics g){
        int brightness = (int)(255 * (1 - getBrightness()));
        g.setColor(new Color(0,0,0,brightness));
        g.fillRect(0, 0, getWidth(), getHeight());
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
    
    public float getBrightness() {
        return brightness;
    }
    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
}
