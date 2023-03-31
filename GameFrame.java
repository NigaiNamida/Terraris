import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameFrame extends JFrame{
    private Menu menu;
    private PlayZone playZone;
    private HoldPanel holdPanel;
    private NextPanel nextPanel;
    private GoalPanel goalPanel;
    private LevelPanel levelPanel;
    private BossPanel bossPanel;
    private XPPanel XPPanel;
    private PausePanel pausePanel;
    private SettingPanel settingPanel;
    private AnyKeyPanel anyKeyPanel;
    private HighScorePanel highscorePanel;
    private GameOverPanel gameOverPanel;
    
    private KeyHandler keyHandler;
    private Leaderboard leaderboard;
    private GameThread gameThread;
    private Sound effect;
    private Sound music;

    private boolean isPlaying;

    public void start(){
        effect = new Sound();
        music = new Sound();
        highscorePanel = new HighScorePanel();
        gameOverPanel = new GameOverPanel();
        holdPanel = new HoldPanel();
        goalPanel = new GoalPanel();
        levelPanel = new LevelPanel();
        bossPanel = new BossPanel();
        XPPanel = new XPPanel();
        playZone = new PlayZone();
        nextPanel = new NextPanel();
        keyHandler = new KeyHandler();
        pausePanel = new PausePanel();
        settingPanel = new SettingPanel();
        menu = new Menu();
        leaderboard = new Leaderboard();
        anyKeyPanel = new AnyKeyPanel();
        isPlaying = false;
        loadLeaderboard();
        loadSetting();
        Leaderboard.updateScoreBoard();
        SettingPanel.updateSetting();

        //setting game frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Terraris");
        this.setLayout(null);
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.black);
        
        //add component
        this.add(anyKeyPanel);
        this.add(highscorePanel);
        this.add(leaderboard);
        this.add(gameOverPanel);
        this.add(pausePanel);
        this.add(settingPanel);
        this.add(goalPanel);
        this.add(holdPanel);
        this.add(nextPanel);
        this.add(levelPanel);
        this.add(XPPanel);
        this.add(bossPanel);
        this.add(playZone);
        bossPanel.setVisible(false);
        anyKeyPanel.setVisible(false);
        highscorePanel.setVisible(false);
        leaderboard.setVisible(false);
        gameOverPanel.setVisible(false);
        settingPanel.setVisible(false);
        pausePanel.setVisible(false);
        goalPanel.setVisible(false);
        holdPanel.setVisible(false);
        nextPanel.setVisible(false);
        levelPanel.setVisible(false);
        XPPanel.setVisible(false);
        playZone.setVisible(false);
        
        this.setFocusable(true);
        this.addKeyListener(keyHandler);

        ImageIcon icon = new ImageIcon("Assets/Image/Terraris.png");
        this.setIconImage(icon.getImage());
        
        this.add(menu);
        playMusic(6);
        music.setVolume(SettingPanel.getMusicVolume());
    }

    public void retry(){
        this.remove(bossPanel);
        this.remove(highscorePanel);
        this.remove(gameOverPanel);
        this.remove(pausePanel);
        this.remove(goalPanel);
        this.remove(holdPanel);
        this.remove(nextPanel);
        this.remove(levelPanel);
        this.remove(XPPanel);
        this.remove(playZone);
        this.remove(menu);
        this.removeKeyListener(keyHandler);

        highscorePanel = new HighScorePanel();
        gameOverPanel = new GameOverPanel();
        holdPanel = new HoldPanel();
        goalPanel = new GoalPanel();
        levelPanel = new LevelPanel();
        bossPanel = new BossPanel();
        XPPanel = new XPPanel();
        playZone = new PlayZone();
        nextPanel = new NextPanel();
        keyHandler = new KeyHandler();
        pausePanel = new PausePanel();
        isPlaying = false;
        
        //add component
        this.add(bossPanel);
        this.add(highscorePanel);
        this.add(gameOverPanel);
        this.add(pausePanel);
        this.add(goalPanel);
        this.add(holdPanel);
        this.add(nextPanel);
        this.add(levelPanel);
        this.add(XPPanel);
        this.add(playZone);
        bossPanel.setVisible(false);
        anyKeyPanel.setVisible(false);
        highscorePanel.setVisible(false);
        leaderboard.setVisible(false);
        gameOverPanel.setVisible(false);
        settingPanel.setVisible(false);
        pausePanel.setVisible(false);
        goalPanel.setVisible(false);
        holdPanel.setVisible(false);
        nextPanel.setVisible(false);
        levelPanel.setVisible(false);
        XPPanel.setVisible(false);
        playZone.setVisible(false);
        
        this.addKeyListener(keyHandler);

        startGame();
    }

    public void backToMenu(){
        if(music != null){
            stopMusic();
        }

        this.remove(bossPanel);
        this.remove(highscorePanel);
        this.remove(gameOverPanel);
        this.remove(pausePanel);
        this.remove(goalPanel);
        this.remove(holdPanel);
        this.remove(nextPanel);
        this.remove(levelPanel);
        this.remove(XPPanel);
        this.remove(playZone);
        this.remove(menu);
        this.removeKeyListener(keyHandler);

        highscorePanel = new HighScorePanel();
        gameOverPanel = new GameOverPanel();
        holdPanel = new HoldPanel();
        goalPanel = new GoalPanel();
        levelPanel = new LevelPanel();
        bossPanel = new BossPanel();
        XPPanel = new XPPanel();
        playZone = new PlayZone();
        nextPanel = new NextPanel();
        keyHandler = new KeyHandler();
        pausePanel = new PausePanel();
        isPlaying = false;
        
        //add component
        this.add(bossPanel);
        this.add(highscorePanel);
        this.add(gameOverPanel);
        this.add(pausePanel);
        this.add(goalPanel);
        this.add(holdPanel);
        this.add(nextPanel);
        this.add(levelPanel);
        this.add(XPPanel);
        this.add(playZone);
        this.add(menu);
        bossPanel.setVisible(false);
        anyKeyPanel.setVisible(false);
        highscorePanel.setVisible(false);
        leaderboard.setVisible(false);
        gameOverPanel.setVisible(false);
        settingPanel.setVisible(false);
        pausePanel.setVisible(false);
        goalPanel.setVisible(false);
        holdPanel.setVisible(false);
        nextPanel.setVisible(false);
        levelPanel.setVisible(false);
        XPPanel.setVisible(false);
        playZone.setVisible(false);
        menu.setVisible(true);
        
        this.addKeyListener(keyHandler);
        playMusic(6);
    }

    public void loadLeaderboard(){
        try (Scanner input = new Scanner(Paths.get(Leaderboard.getLeaderboardFile()))) {
            String[] topName = Leaderboard.getTopName();
            int[] topXP = Leaderboard.getTopScore();
            for (int i = 0; input.hasNextLine() ; i++) {
                topName[i] = input.next();
                topXP[i] = input.nextInt();
            }
            Leaderboard.updateScoreBoard();
        } 
        catch (Exception e) {}
    }

    public void loadSetting(){
        try (Scanner input = new Scanner(Paths.get(SettingPanel.getSettingFile()))) {
            SettingPanel.setMusicVolume(input.nextInt());
            SettingPanel.setFXVolume(input.nextInt());
            while (input.hasNextLine()) {
                SettingPanel.addKeyBind(input.next(), input.nextInt());
            }
            SettingPanel.updateSetting();
        } 
        catch (Exception e) {}
    }

    public Menu getMenu() {
        return menu;
    }

    public GameThread getGameThread() {
        return gameThread;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public SettingPanel getSettingPanel() {
        return settingPanel;
    }

    public AnyKeyPanel getAnyKeyPanel() {
        return anyKeyPanel;
    }

    public HoldPanel getHoldPanel() {
        return holdPanel;
    }

    public NextPanel getNextPanel() {
        return nextPanel;
    }

    public GoalPanel getGoalPanel() {
        return goalPanel;
    }

    public PlayZone getPlayZone() {
        return playZone;
    }
    public GameOverPanel getGameOverPanel() {
        return gameOverPanel;
    }

    public HighScorePanel getHighScorePanel() {
        return highscorePanel;
    }

    public LevelPanel getLevelPanel() {
        return levelPanel;
    }

    public XPPanel getXPPanel() {
        return XPPanel;
    }

    public PausePanel getPausePanel() {
        return pausePanel;
    }

    public BossPanel getBossPanel() {
        return bossPanel;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public Sound getEffect() {
        return effect;
    }

    public Sound getMusic() {
        return music;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
    
    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public void pauseGame(){
        playSE(7);
        pausePanel.setVisible(true);
    }

    public void continueGame(){
        playSE(8);
        pausePanel.setVisible(false);
    }

    public void startGame(){
        bossPanel.setVisible(true);
        goalPanel.setVisible(true);
        holdPanel.setVisible(true);
        nextPanel.setVisible(true);
        levelPanel.setVisible(true);
        XPPanel.setVisible(true);
        playZone.setVisible(true);
        isPlaying = true;
        
        gameThread = new GameThread();
        gameThread.start();
        if(music.getClip() != null){
            music.stopSound();
        }
        playZone.createBlock();
        playMusic(0);
    }

    public void playMusic(int i){
        music.setFiles(i);
        music.playSound();
        music.setVolume(SettingPanel.getMusicVolume());
        music.loopSound();
    }

    public void stopMusic(){
        music.stopSound();
    }

    public void playSE(int i){
        effect.setFiles(i);
        effect.setVolume(SettingPanel.getFXVolume());
        effect.playSound();
    }
}

