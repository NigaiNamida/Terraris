import javax.swing.Timer;
import java.awt.event.*;

public class Gravity implements ActionListener{
    KeyHandler keyHandler = GameFrame.keyHandler;
    PlayZone pZone;
    Timer timer;
    Timer lastTimer;
    double fallSpeed;

    public Gravity(PlayZone pZone,double fallSpeed){
        this.fallSpeed = fallSpeed;
        this.pZone = pZone;
        timer = new Timer((int)(fallSpeed*1000), this);
        lastTimer = new Timer(500, this);
        timer.start();
    }

    void repaint(){
        pZone.repaint();
    }
    
    void increaseFallSpeed(int level){
        fallSpeed = Math.pow(0.8-((level-1)*0.007),(level-1));
        timer.stop();
        timer = new Timer((int)(fallSpeed*1000), this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!pZone.isGameOver && !KeyHandler.isPause && GameFrame.isPlaying){
            pZone.applyGravity();
            repaint();
        }
    }
}
