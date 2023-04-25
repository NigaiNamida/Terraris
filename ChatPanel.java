import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ChatPanel extends JPanel implements ActionListener{
    private static JLabel chat;
    private static Font terrariaFont;
    private static Color warningColor;
    private static Color bossColor;
    private static Timer showTimer;
    private static Timer glowTimer;
    private static int darkLevel;
    private static boolean isDarker;
    private static final float FACTOR = 0.95f;
    public ChatPanel(){
        //bright rgba(133,69,195,255)
        //dark rgba(79,40,133,255)
        darkLevel = 0;
        isDarker = true;
        glowTimer = new Timer(150, this);
        showTimer = new Timer(10000, this);
        warningColor = new Color(93,226,156);
        bossColor = new Color(133,69,195);
        terrariaFont = GameFrame.getTerrariaFont(15);
        chat = new JLabel();
        chat.setBounds(0, (0)*40+20, 300, 20);
        chat.setFont(terrariaFont);
        chat.setForeground(Color.WHITE);
        this.add(chat);

        this.setLayout(null);
        this.setBounds(455, 350, 300, 250);
        this.setBackground(Color.BLACK);
    }

    public static void newMessage(Theme stage,boolean isDefeat){
        String text = "";
        switch (stage) {
            case Day:
                chat.setForeground(warningColor);
                text = "Slime is falling from the sky!";
                break;
            case KingSlime:
                chat.setForeground(bossColor);
                text = "King Slime has ";
                text += (isDefeat) ? "been defeated!" : "awoken!";
                break;
            case Night:
                chat.setForeground(warningColor);
                text = "You feel an evil presence watching you...";
                break;
            case EyeOfCthulhu:
                chat.setForeground(bossColor);
                text = "Eye of Cthulhu has ";
                text += (isDefeat) ? "been defeated!" : "awoken!";
                break;
            case Corruption:
                chat.setForeground(warningColor);
                //text = "A horrible chill goes down your spine...";
                text = "Screams echo around you...";
                break;
            case EaterOfWorld:
                chat.setForeground(bossColor);
                text = "Eater of Worlds has ";
                text += (isDefeat) ? "been defeated!" : "awoken!";
                break;
            default:
                break;
        }
        chat.setText(text);
        showTimer.restart();
        glowTimer.restart();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == showTimer){
            chat.setText(null);
            showTimer.stop();
            GameFrame.getPausePanel().repaint();
        }
        if(e.getSource() == glowTimer && !KeyHandler.isPause()){
            if(chat.getText() != null){
                if(darkLevel < 3){
                    darkLevel++;
                }
                if(darkLevel % 3 == 0){
                    darkLevel = 0;
                    isDarker = !isDarker;
                }
                if(isDarker){
                    chat.setForeground(getDarker(chat.getForeground()));
                }
                else{
                    chat.setForeground(getBrighter(chat.getForeground()));
                }
                GameFrame.getPausePanel().repaint();
            }
        }
    }

    private Color getDarker(Color color){
        return new Color(Math.max((int)(color.getRed() *FACTOR), 0),
                        Math.max((int)(color.getGreen()*FACTOR), 0),
                        Math.max((int)(color.getBlue() *FACTOR), 0));
    }

    private Color getBrighter(Color color){
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        return new Color(Math.min((int)(r/FACTOR), 255),
                         Math.min((int)(g/FACTOR), 255),
                         Math.min((int)(b/FACTOR), 255));
    }
}