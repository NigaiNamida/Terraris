import javax.swing.Timer;
import java.awt.event.*;

public class Gravity implements ActionListener{
    private GameFrame gameFrame;
    private PlayZone playZone;
    private KeyHandler keyHandler;
    private Timer timer;
    private Timer lastTimer;
    private double fallSpeed;

    public Gravity(PlayZone pZone,double fallSpeed){
        this.fallSpeed = fallSpeed;
        this.playZone = pZone;
        gameFrame = Terraris.getGameFrame();
        keyHandler = gameFrame.getKeyHandler();
        timer = new Timer((int)(fallSpeed*1000), this);
        lastTimer = new Timer(500, this);
        startTimer();
    }

    void repaintPlayZone(){
        playZone.repaint();
    }
    
    void increaseFallSpeed(int level){
        fallSpeed = Math.pow(0.8-((level-1)*0.007),(level-1));
        stopTimer();
        timer = new Timer((int)(fallSpeed*1000), this);
        startTimer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(keyHandler == null){
            keyHandler = gameFrame.getKeyHandler();
        }
        if(!playZone.isGameOver() && !keyHandler.isPause() && gameFrame.isPlaying()){
            playZone.applyGravity();
            repaintPlayZone();
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public Timer getLastTimer() {
        return lastTimer;
    }

    public void startTimer(){
        timer.start();
    }

    public void startLastTimer(){
        lastTimer.start();
    }

    public void restartTimer(){
        timer.restart();
    }

    public void restartLastTimer(){
        lastTimer.restart();
    }

    public void stopTimer(){
        timer.stop();
    }

    public void stopLastTimer(){
        lastTimer.stop();
    }
}
