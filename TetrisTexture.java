import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TetrisTexture extends JPanel{
    private static String path = "Assets/Image/";
    private static Image blockImage;
    private static Image pileImage;
    
    public static void drawBlockTexture(Graphics g, Tetris name, int variant, int x, int y){
        String imagePath = null;
        if(name == Tetris.O)
            imagePath = path+name+"/Full_Block.png";
        else if(name == Tetris.I || name == Tetris.S || name == Tetris.Z)
            if(variant % 2 == 0)
                imagePath = path+name+"/Horizontal.png";
            else
                imagePath = path+name+"/Vertical.png";
        else if(name == Tetris.T || name == Tetris.L || name == Tetris.J)
            if(variant == 0)
                imagePath = path+name+"/Horizontal_Up.png";
            else if(variant == 1)
                imagePath = path+name+"/Vertical_Right.png";
            else if(variant == 2)
                imagePath = path+name+"/Horizontal_Down.png";
            else
                imagePath = path+name+"/Vertical_Left.png";
        blockImage = new ImageIcon(imagePath).getImage();
        g.drawImage(blockImage ,x, y, null); 
    }

    public static void drawPuddleTexture(Graphics g, Color middle, int x, int y){
        String imageName = null;
        if(middle == PlayZone.getSlimePuddleColor()){
            imageName = "Slime_Puddle";
        }
        else if(middle == PlayZone.getSlimeBlockColor()){
            imageName = "Slime_Block";
        }

        pileImage = new ImageIcon(path+"Bosses/KingSlime/Attack/" + imageName + ".png").getImage();
        g.drawImage(pileImage,x,y,null);
    }

    public static void mergePileTexture(Graphics g, Color middle, int x, int y,char[][] c){
        boolean painted = false;
        char[] n = new char[9];
        n[0] = c[0][0];
        n[1] = c[0][1];
        n[2] = c[0][2];
        n[3] = c[1][0];
        //n[4] = c[1][1];
        n[5] = c[1][2];
        n[6] = c[2][0];
        n[7] = c[2][1];
        n[8] = c[2][2];
        for (int i = 0; i < n.length; i++) {
            if(n[i] == '0'){
                switch (i) {
                    case 1:
                    case 7:
                        n[i-1] = n[i+1] = 'D';
                        break;
                    case 3:
                    case 5:
                        n[i-3] = n[i+3] = 'D';
                        break;
                }
            }
        }

        String name = ""+n[0]+n[1]+n[2]+n[3]+n[5]+n[6]+n[7]+n[8];
        if(!painted && !middle.equals(PlayZone.getSlimeBlockColor()) && !middle.equals(PlayZone.getSlimePuddleColor())){
            pileImage = new ImageIcon(path+"Block/Grass/Normal/"+name+".png").getImage();
            g.drawImage(pileImage,x,y,null);
            painted = true;
        }
    }
}