import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TetrisTexture extends JPanel{
    private static String path = "Assets/Image/";
    private static Image pileImage;

    public static void drawPuddleTexture(Graphics g, Color middle, int x, int y){
        String imageName = null;
        if(middle == PlayZone.getSlimePuddleColor()){
            if(y<19*25){
                imageName = "Slime_Puddle";
            }
            else{
                imageName = "Slime_Puddle_Floor";
            }
        }
        else if(middle == PlayZone.getSlimeBlockColor()){
            imageName = "Slime_Block";
        }

        pileImage = new ImageIcon(path + "Bosses/KingSlime/Attack/" + imageName + ".png").getImage();
        g.drawImage(pileImage,x,y,null);
    }

    public static void mergePileTexture(Graphics g, Color middle, int x, int y,char[][] c){
        Theme bossStage = GameFrame.getBossPanel().getStage();
        String stage = "Normal";
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
        switch (bossStage) {
            case Day:
            case Night:
            case KingSlime:
            case EyeOfCthulhu:
                break;
            case EaterOfWorld:
                stage = "Corruption";
                break;
            default:
                stage = ""+bossStage;
                break;
        }

        String block = null;
        if(middle.equals(BlockTexture.Grass.getColor())){
            block = "Grass";
        }
        else if(middle.equals(BlockTexture.Stone.getColor())){
            block = "Stone";
        }
        else if(middle.equals(BlockTexture.Sand.getColor())){
            block = "Sand";
        }
        else if(middle.equals(BlockTexture.Dynamite.getColor())){
            block = "Dynamite";
            n[0] = n[2] = n[6] = n[8] = 'D';  
        }
        else if(middle.equals(BlockTexture.PrimeDynamite.getColor())){
            block = "PrimeDynamite";
            n[0] = n[2] = n[6] = n[8] = 'D';  
        }
        else if(middle.equals(BlockTexture.Cloud.getColor())){
            block = "Cloud";
        }
        
        String name = ""+n[0]+n[1]+n[2]+n[3]+n[5]+n[6]+n[7]+n[8];
        if(!middle.equals(PlayZone.getSlimeBlockColor()) && !middle.equals(PlayZone.getSlimePuddleColor())){
            if(block != "Cloud" && block != "Dynamite" && block != "PrimeDynamite"){
                pileImage = new ImageIcon(path+"Block/"+block+"/"+stage+"/"+name+".png").getImage();
            }
            else{
                pileImage = new ImageIcon(path+"Block/"+block+"/"+name+".png").getImage();  
            }
            g.drawImage(pileImage,x,y,null);
        }
        // if(!middle.equals(PlayZone.getSlimeBlockColor()) && !middle.equals(PlayZone.getSlimePuddleColor())){
        //     pileImage = new ImageIcon(path+"Block/"+block+"/"+stage+"/"+name+".png").getImage();
        //     g.drawImage(pileImage,x,y,null);
        // }
    }
}