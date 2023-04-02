import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Sound {
    private long pauseTime;
    private Clip clip;
    private URL[] soundURL = new URL[10];
    private FloatControl gainControl;

    public Sound(){
        pauseTime = -1;
        soundURL[0] = getClass().getResource(Theme.Day.getBGMusicPath());//bgm
        soundURL[1] = getClass().getResource("Assets/Sound/SoftDrop.wav");//soft
        soundURL[2] = getClass().getResource("Assets/Sound/HardDrop.wav");//hard
        soundURL[3] = getClass().getResource("Assets/Sound/HoldPiece.wav");//hold
        soundURL[4] = getClass().getResource("Assets/Sound/ClearLine.wav");//clear
        soundURL[5] = getClass().getResource("Assets/Sound/GameOver.wav");//over
        soundURL[6] = getClass().getResource(Theme.Title.getBGMusicPath());//over
        soundURL[7] = getClass().getResource("Assets/Sound/OpenMenu.wav");//open pause menu
        soundURL[8] = getClass().getResource("Assets/Sound/CloseMenu.wav");//close pause menu
        
    }

    public void setFiles(int i){
        Theme stage = GameFrame.getBossPanel().getStage();
        if(i == 0){
            soundURL[i] = getClass().getResource(stage.getBGMusicPath());//bgm
        }
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void playSound(){
        clip.start();
    }

    public float getVolume() {
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }
    
    public void setVolume(float volume) {
        if(clip != null){
            try {
                volume = volume/100;
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
                gainControl.setValue(20f * (float) Math.log10(volume));
            } catch (IllegalArgumentException e) {}
            catch(NullPointerException e){}
        }
    }
    
    public void loopSound(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopSound(){
        clip.stop();
        pauseTime = -1;
    }

    public void pauseSound(){
        pauseTime = clip.getMicrosecondPosition() % clip.getMicrosecondLength();
        clip.stop();
    }
    
    public void resumeSound(){
        clip.setMicrosecondPosition(pauseTime);
        clip.start();
        loopSound();
    }

    public Clip getClip() {
        return clip;
    }
}
