import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class AnyKeyPanel extends JPanel{
    private static JLabel anyKeyLabel;

    public AnyKeyPanel(){
        anyKeyLabel = new JLabel("Press any Key to set Key");
        this.setOpaque(true);
        this.setBounds(100, 230, 600, 100);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3));
        this.setVisible(false);
        this.setLayout(null);

        anyKeyLabel.setForeground(new Color(193,221,196,255));
        anyKeyLabel.setFont(new Font("Futura",Font.BOLD,40));
        anyKeyLabel.setBounds(25, 30, 550, 40);
        anyKeyLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(anyKeyLabel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
