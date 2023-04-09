import java.awt.*;
import javax.swing.*;

public class ChatPanel extends JPanel{
    private Font terrariaFont;
    public ChatPanel(){
        terrariaFont = GameFrame.getTerrariaFont(20);
        this.setBounds(455, 290, 250, 250);
        this.setBackground(Color.black);
        JLabel text = new JLabel("Stupid");
        text.setForeground(new Color(193,221,196,255));
        text.setFont(terrariaFont);
        this.add(text);
    }
}