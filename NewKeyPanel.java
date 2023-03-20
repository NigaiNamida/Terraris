import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class NewKeyPanel extends JPanel{
    public static JLabel newKeyLabel = new JLabel("Press any Key to set Key");
    public static int keyToSet;

    public NewKeyPanel(){
        newKeyLabel = new JLabel("Press any Key to set Key");
        this.setOpaque(true);
        this.setBounds(100, 230, 600, 100);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3));
        this.setVisible(false);
        this.setLayout(null);

        newKeyLabel.setForeground(new Color(193,221,196,255));
        newKeyLabel.setFont(new Font("Futura",Font.BOLD,40));
        newKeyLabel.setBounds(25, 30, 550, 40);
        newKeyLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(newKeyLabel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
