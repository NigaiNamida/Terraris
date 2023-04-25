import javax.swing.Timer;
import java.awt.event.*;

public class Gravity implements ActionListener{
    private PlayZone playZone;
    private Timer timer;
    private Timer lastTimer;
    private double fallSpeed;

    public Gravity(PlayZone pZone,double fallSpeed){
        this.fallSpeed = fallSpeed;
        this.playZone = pZone;
        timer = new Timer((int)(fallSpeed*1000), this);
        lastTimer = new Timer(800, this);
        startTimer();
    }
    
    void increaseFallSpeed(int level){
        fallSpeed = Math.pow(0.8-((level-1)*0.007),(level-1));
        stopTimer();
        timer = new Timer((int)(fallSpeed*1000), this);
        startTimer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!playZone.isGameOver() && !KeyHandler.isPause() && GameFrame.isPlaying()){
            playZone.applyGravity();
        }
    }
    public void setTimer(){
        if(timer != null){
            timer.stop();
        }
        timer = new Timer((int)(fallSpeed*1000), this);
    }

    public void setTimerScale(double scale){
        if(timer != null){
            timer.stop();
        }
        timer = new Timer((int)(fallSpeed*1000*(1+scale)), this);
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
