import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ChatPanel extends JPanel{
    private static int lines;
    private static JLabel[] chat;
    private static Font terrariaFont;
    private static Color warningColor;
    private static Color bossColor;
    public ChatPanel(){
        warningColor = new Color(93,226,156);
        bossColor = new Color(160,97,216);
        terrariaFont = GameFrame.getTerrariaFont(20);
        lines = 5;
        chat = new JLabel[lines];

        for (int i = 0; i < chat.length; i++) {
            chat[i] = new JLabel();
            chat[i].setBounds(0, (i)*40+20, 300, 20);
            chat[i].setFont(terrariaFont);
            chat[i].setForeground(Color.WHITE);
            this.add(chat[i]);
        }
        this.setLayout(null);
        this.setBounds(455, 290, 300, 250);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,1,true));
    }

    public static void newMessage(Theme stage,boolean isDefeat){
        for (int i = 0; i < chat.length-1; i++) {
            chat[i].setForeground(chat[i+1].getForeground());
            chat[i].setText(chat[i+1].getText());
        }
        String text = "";
        JLabel recentMessage = chat[chat.length-1];
        switch (stage) {
            case Day:
                recentMessage.setForeground(warningColor);
                text = "Slime is falling from the sky!";
                break;
            case KingSlime:
                recentMessage.setForeground(bossColor);
                text = "King Slime has ";
                text += (isDefeat) ? "been defeated!" : "awoken!";
                break;
            case Night:
                recentMessage.setForeground(warningColor);
                text = "You feel an evil presence watching you...";
                break;
            case EyeOfCthulhu:
                recentMessage.setForeground(bossColor);
                text = "Eye of Cthulhu has ";
                text += (isDefeat) ? "been defeated!" : "awoken!";
                break;
            case Corruption:
                recentMessage.setForeground(warningColor);
                //text = "A horrible chill goes down your spine...";
                text = "Screams echo around you...";
                break;
            case EaterOfWorld:
                recentMessage.setForeground(bossColor);
                text = "Eater of Worlds has ";
                text += (isDefeat) ? "been defeated!" : "awoken!";
                break;
            default:
                break;
        }
        chat[chat.length-1].setText(text);
    }
}