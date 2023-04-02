import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TetrisTexture extends JPanel{
    private static String path = "Assets/Image/";
    private static Image blockImage;
    private static Image pileImage;
    
    public static void drawBlockTexture(Graphics g, String name, int variant, int x, int y){
        String imagePath = null;
        if(name == "O")
            imagePath = path+name+"/Full_Block.png";
        else if(name == "I" || name == "S" || name == "Z")
            if(variant % 2 == 0)
                imagePath = path+name+"/Horizontal.png";
            else
                imagePath = path+name+"/Vertical.png";
        else if(name == "T" || name == "L" || name == "J")
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

    public static void drawPuddleTexture(Graphics g, String middle, int x, int y){
        if(middle == "Slime_Puddle"){
            pileImage = new ImageIcon("Assets/Image/Bosses/KingSlime/Attack/Slime_Puddle.png").getImage();
            g.drawImage(pileImage,x,y,null);
        }
    }

    public static void mergePileTexture(Graphics g, String middle, int x, int y,boolean[][] sideCheck){
        boolean painted = false;
        boolean Top = sideCheck [0][1];
        boolean Left = sideCheck [1][0];
        boolean Right = sideCheck [1][2];
        boolean Bottom = sideCheck [2][1];
        boolean Top_Left = sideCheck [0][0];
        boolean Top_Right = sideCheck [0][2];
        boolean Bottom_Left = sideCheck [2][0];
        boolean Bottom_Right = sideCheck [2][2];
        if(!painted && middle == "O"){
            if(!painted && Top && Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Middle.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Side_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Side_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Side_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Side_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Hat_Top_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Hat_Bottom_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Hat_Top_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Hat_Bottom_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Side_Horizontal.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Side_Vertical.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }            
            else if(!painted && !Top && !Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Hat_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Hat_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Hat_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }            
            else if(!painted && !Top && Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Hat_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/O/Single.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }

        }
        else if(!painted && middle == "T"){
            if(!painted && Top && Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Middle.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Side_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Side_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Side_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Side_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Hat_Top_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Hat_Bottom_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Hat_Top_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Hat_Bottom_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Side_Horizontal.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Side_Vertical.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }            
            else if(!painted && !Top && !Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Hat_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Hat_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Hat_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }            
            else if(!painted && !Top && Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Hat_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/T/Single.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }

        }
        else if(!painted && middle == "I"){
            if(!painted && Top && Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Middle.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Side_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Side_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Side_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Side_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Hat_Top_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Hat_Bottom_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Hat_Top_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Hat_Bottom_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Side_Horizontal.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Side_Vertical.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }            
            else if(!painted && !Top && !Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Hat_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Hat_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Hat_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }            
            else if(!painted && !Top && Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Hat_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/I/Single.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
        }
        else if(!painted && middle == "L"){
            if(!painted && Top && Left && Right & Bottom){
                if(!painted && Top_Left && Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Middle.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Single_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Single_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Single_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Single_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Double_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }                
                else if(!painted && Top_Left && Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Double_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Double_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Double_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Double_Diagonal_Slash.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Double_Diagonal_BackSlash.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Triple_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Triple_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Triple_Bottom_left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Triple_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Full.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && Left && Right & Bottom){
                if(!painted && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Single_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Single_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Double_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && !Left && Right & Bottom){
                if(!painted && Top_Right && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Right && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Single_Left_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Right && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Single_Left_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Right && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Double_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && Left && !Right & Bottom){
                if(!painted && Top_Left && Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Single_Right_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Single_Right_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Double_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && Left && Right & !Bottom){
                if(!painted && Top_Left && Top_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Single_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Single_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Side_Double_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && !Left && Right & Bottom){
                if(!painted && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Hat_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Hat_Single_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && Left && !Right & Bottom){
                if(!painted && Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/L/Hat_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/L/Hat_Single_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && !Left && Right & !Bottom){
                if(!painted && Top_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Hat_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Right){
                    pileImage = new ImageIcon("Assets/Image/L/Hat_Single_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && Left && !Right & !Bottom){
                if(!painted && Top_Left){
                    pileImage = new ImageIcon("Assets/Image/L/Hat_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left){
                    pileImage = new ImageIcon("Assets/Image/L/Hat_Single_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && Left && Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/L/Side_Horizontal.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right & Bottom){
                pileImage = new ImageIcon("Assets/Image/L/Side_Vertical.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/L/Hat_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right & Bottom){
                pileImage = new ImageIcon("Assets/Image/L/Hat_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && !Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/L/Hat_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/L/Hat_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/L/Single.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
        }
        else if(!painted && middle == "J"){
            if(!painted && Top && Left && Right & Bottom){
                if(!painted && Top_Left && Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Middle.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Single_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Single_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Single_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Single_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Double_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }                
                else if(!painted && Top_Left && Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Double_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Double_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Double_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Double_Diagonal_Slash.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Double_Diagonal_BackSlash.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Triple_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Triple_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Triple_Bottom_left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Triple_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Full.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && Left && Right & Bottom){
                if(!painted && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Single_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Single_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Double_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && !Left && Right & Bottom){
                if(!painted && Top_Right && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Right && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Single_Left_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Right && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Single_Left_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Right && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Double_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && Left && !Right & Bottom){
                if(!painted && Top_Left && Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Single_Right_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Single_Right_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Double_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && Left && Right & !Bottom){
                if(!painted && Top_Left && Top_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Single_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Single_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Side_Double_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && !Left && Right & Bottom){
                if(!painted && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Hat_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Hat_Single_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && Left && !Right & Bottom){
                if(!painted && Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/J/Hat_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/J/Hat_Single_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && !Left && Right & !Bottom){
                if(!painted && Top_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Hat_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Right){
                    pileImage = new ImageIcon("Assets/Image/J/Hat_Single_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && Left && !Right & !Bottom){
                if(!painted && Top_Left){
                    pileImage = new ImageIcon("Assets/Image/J/Hat_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left){
                    pileImage = new ImageIcon("Assets/Image/J/Hat_Single_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && Left && Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/J/Side_Horizontal.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right & Bottom){
                pileImage = new ImageIcon("Assets/Image/J/Side_Vertical.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/J/Hat_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right & Bottom){
                pileImage = new ImageIcon("Assets/Image/J/Hat_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && !Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/J/Hat_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/J/Hat_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/J/Single.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
        }
        else if(!painted && middle == "S"){
            if(!painted && Top && Left && Right & Bottom){
                if(!painted && Top_Left && Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Middle.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Single_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Single_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Single_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Single_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Double_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }                
                else if(!painted && Top_Left && Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Double_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Double_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Double_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Double_Diagonal_Slash.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Double_Diagonal_BackSlash.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Triple_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Triple_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Triple_Bottom_left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Triple_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Full.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && Left && Right & Bottom){
                if(!painted && Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Left && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Single_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Single_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Left && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Double_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && !Left && Right & Bottom){
                if(!painted && Top_Right && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Right && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Single_Left_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Right && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Single_Left_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Right && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Double_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && Left && !Right & Bottom){
                if(!painted && Top_Left && Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Single_Right_Top.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Single_Right_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Double_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && Left && Right & !Bottom){
                if(!painted && Top_Left && Top_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && Top_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Single_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && Top_Left && !Top_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Single_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left && !Top_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Side_Double_Bottom.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && !Left && Right & Bottom){
                if(!painted && Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Hat_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Hat_Single_Top_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && Left && !Right & Bottom){
                if(!painted && Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/S/Hat_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Bottom_Left){
                    pileImage = new ImageIcon("Assets/Image/S/Hat_Single_Top_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && !Left && Right & !Bottom){
                if(!painted && Top_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Hat_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Right){
                    pileImage = new ImageIcon("Assets/Image/S/Hat_Single_Bottom_Left.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && Top && Left && !Right & !Bottom){
                if(!painted && Top_Left){
                    pileImage = new ImageIcon("Assets/Image/S/Hat_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
                else if(!painted && !Top_Left){
                    pileImage = new ImageIcon("Assets/Image/S/Hat_Single_Bottom_Right.png").getImage();
                    g.drawImage(pileImage,x,y,null);
                    painted = true;
                }
            }
            else if(!painted && !Top && Left && Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/S/Side_Horizontal.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right & Bottom){
                pileImage = new ImageIcon("Assets/Image/S/Side_Vertical.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/S/Hat_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right & Bottom){
                pileImage = new ImageIcon("Assets/Image/S/Hat_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && !Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/S/Hat_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/S/Hat_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right & !Bottom){
                pileImage = new ImageIcon("Assets/Image/S/Single.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
        }
        else if(!painted && middle == "Z"){
            if(!painted && Top && Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Middle.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Side_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Side_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Side_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Side_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Hat_Top_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Hat_Bottom_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Hat_Top_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Hat_Bottom_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Side_Horizontal.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Side_Vertical.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }            
            else if(!painted && !Top && !Left && !Right && Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Hat_Top.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && Top && !Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Hat_Bottom.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Hat_Left.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }            
            else if(!painted && !Top && Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Hat_Right.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
            else if(!painted && !Top && !Left && !Right && !Bottom){
                pileImage = new ImageIcon("Assets/Image/Z/Single.png").getImage();
                g.drawImage(pileImage,x,y,null);
                painted = true;
            }
        }
    }
}