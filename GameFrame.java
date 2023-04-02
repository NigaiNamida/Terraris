import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameFrame extends JFrame{
    private static Menu menu;
    private static PlayZone playZone;
    private static HoldPanel holdPanel;
    private static NextPanel nextPanel;
    private static GoalPanel goalPanel;
    private static LevelPanel levelPanel;
    private static ScorePanel scorePanel;
    private static PausePanel pausePanel;
    private static SettingPanel settingPanel;
    private static AnyKeyPanel anyKeyPanel;
    private static HighScorePanel highScorePanel;
    private static GameOverPanel gameOverPanel;
    
    private static KeyHandler keyHandler;
    private static Leaderboard leaderboard;
    private static GameThread gameThread;
    private static Sound effect;
    private static Sound music;

    private static boolean isPlaying;

    public GameFrame(){
        effect = new Sound();
        music = new Sound();
        highScorePanel = new HighScorePanel();
        gameOverPanel = new GameOverPanel();
        holdPanel = new HoldPanel();
        nextPanel = new NextPanel();
        goalPanel = new GoalPanel();
        levelPanel = new LevelPanel();
        scorePanel = new ScorePanel();
        playZone = new PlayZone();
        keyHandler = new KeyHandler();
        pausePanel = new PausePanel();
        settingPanel = new SettingPanel();
        menu = new Menu();
        leaderboard = new Leaderboard();
        anyKeyPanel = new AnyKeyPanel();
        isPlaying = false;
        loadLeaderboard();
        Leaderboard.updateScoreBoard();

        //setting game frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Terraris");
        this.setLayout(null);
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.black);
        
        //add component
        this.add(anyKeyPanel);
        this.add(highScorePanel);
        this.add(leaderboard);
        this.add(gameOverPanel);
        this.add(pausePanel);
        this.add(settingPanel);
        this.add(goalPanel);
        this.add(holdPanel);
        this.add(nextPanel);
        this.add(levelPanel);
        this.add(scorePanel);
        this.add(playZone);
        anyKeyPanel.setVisible(false);
        highScorePanel.setVisible(false);
        leaderboard.setVisible(false);
        gameOverPanel.setVisible(false);
        settingPanel.setVisible(false);
        pausePanel.setVisible(false);
        goalPanel.setVisible(false);
        holdPanel.setVisible(false);
        nextPanel.setVisible(false);
        levelPanel.setVisible(false);
        scorePanel.setVisible(false);
        playZone.setVisible(false);
        
        this.setFocusable(true);
        this.addKeyListener(keyHandler);

        ImageIcon icon = new ImageIcon("Assets/Image/Tetris.png");
        this.setIconImage(icon.getImage());
        
        this.add(menu);
        playMusic(6);
        music.setVolume(SettingPanel.musicVolume);
    }

    public static void loadLeaderboard(){
        try (Scanner input = new Scanner(Paths.get(Leaderboard.getLeaderboardFile()))) {
            String[] topName = Leaderboard.getTopName();
            int[] topScore = Leaderboard.getTopScore();
            for (int i = 0; input.hasNextLine() ; i++) {
                topName[i] = input.next();
                topScore[i] = input.nextInt();
            }
            Leaderboard.updateScoreBoard();
        } 
        catch (Exception e) {}
    }

    public void retry(){
        this.remove(highScorePanel);
        this.remove(gameOverPanel);
        this.remove(pausePanel);
        this.remove(goalPanel);
        this.remove(holdPanel);
        this.remove(nextPanel);
        this.remove(levelPanel);
        this.remove(scorePanel);
        this.remove(playZone);
        this.remove(menu);
        this.removeKeyListener(keyHandler);

        highScorePanel = new HighScorePanel();
        gameOverPanel = new GameOverPanel();
        holdPanel = new HoldPanel();
        nextPanel = new NextPanel();
        goalPanel = new GoalPanel();
        levelPanel = new LevelPanel();
        scorePanel = new ScorePanel();
        playZone = new PlayZone();
        keyHandler = new KeyHandler();
        pausePanel = new PausePanel();
        isPlaying = false;
        
        //add component
        this.add(highScorePanel);
        this.add(gameOverPanel);
        this.add(pausePanel);
        this.add(goalPanel);
        this.add(holdPanel);
        this.add(nextPanel);
        this.add(levelPanel);
        this.add(scorePanel);
        this.add(playZone);
        anyKeyPanel.setVisible(false);
        highScorePanel.setVisible(false);
        leaderboard.setVisible(false);
        gameOverPanel.setVisible(false);
        settingPanel.setVisible(false);
        pausePanel.setVisible(false);
        goalPanel.setVisible(false);
        holdPanel.setVisible(false);
        nextPanel.setVisible(false);
        levelPanel.setVisible(false);
        scorePanel.setVisible(false);
        playZone.setVisible(false);
        
        this.addKeyListener(keyHandler);

        startGame();
    }

    public void backToMenu(){
        if(GameFrame.music != null){
            GameFrame.stopMusic();
        }

        this.remove(highScorePanel);
        this.remove(gameOverPanel);
        this.remove(pausePanel);
        this.remove(goalPanel);
        this.remove(holdPanel);
        this.remove(nextPanel);
        this.remove(levelPanel);
        this.remove(scorePanel);
        this.remove(playZone);
        this.remove(menu);
        this.removeKeyListener(keyHandler);

        highScorePanel = new HighScorePanel();
        gameOverPanel = new GameOverPanel();
        holdPanel = new HoldPanel();
        nextPanel = new NextPanel();
        goalPanel = new GoalPanel();
        levelPanel = new LevelPanel();
        scorePanel = new ScorePanel();
        playZone = new PlayZone();
        keyHandler = new KeyHandler();
        pausePanel = new PausePanel();
        isPlaying = false;
        
        
        //add component
        this.add(highScorePanel);
        this.add(gameOverPanel);
        this.add(pausePanel);
        this.add(goalPanel);
        this.add(holdPanel);
        this.add(nextPanel);
        this.add(levelPanel);
        this.add(scorePanel);
        this.add(playZone);
        this.add(menu);
        anyKeyPanel.setVisible(false);
        highScorePanel.setVisible(false);
        leaderboard.setVisible(false);
        gameOverPanel.setVisible(false);
        settingPanel.setVisible(false);
        pausePanel.setVisible(false);
        goalPanel.setVisible(false);
        holdPanel.setVisible(false);
        nextPanel.setVisible(false);
        levelPanel.setVisible(false);
        scorePanel.setVisible(false);
        playZone.setVisible(false);
        menu.setVisible(true);
        
        this.addKeyListener(keyHandler);
        playMusic(6);
    }

    public static Menu getMenu() {
        return menu;
    }

    public static Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public static SettingPanel getSettingPanel() {
        return settingPanel;
    }

    public static AnyKeyPanel getAnyKeyPanel() {
        return anyKeyPanel;
    }

    public static HoldPanel getHoldPanel() {
        return holdPanel;
    }

    public static NextPanel getNextPanel() {
        return nextPanel;
    }

    public static GoalPanel getGoalPanel() {
        return goalPanel;
    }

    public static PlayZone getPlayZone() {
        return playZone;
    }
    public static GameOverPanel getGameOverPanel() {
        return gameOverPanel;
    }

    public static HighScorePanel getHighScorePanel() {
        return highScorePanel;
    }

    public static PausePanel getPausePanel() {
        return pausePanel;
    }

    public static KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public static Sound getEffect() {
        return effect;
    }

    public static Sound getMusic() {
        return music;
    }

    public static boolean isPlaying() {
        return isPlaying;
    }
    

    public static void setPlaying(boolean isPlaying) {
        GameFrame.isPlaying = isPlaying;
    }

    public static void pauseGame(){
        GameFrame.playSE(7);
        pausePanel.setVisible(true);
    }

    public static void continueGame(){
        GameFrame.playSE(8);
        pausePanel.setVisible(false);
    }

    public static void startGame(){
        goalPanel.setVisible(true);
        holdPanel.setVisible(true);
        nextPanel.setVisible(true);
        levelPanel.setVisible(true);
        scorePanel.setVisible(true);
        playZone.setVisible(true);
        isPlaying = true;
        
        gameThread = new GameThread();
        gameThread.start();
        if(music.getClip() != null){
            music.stopSound();
        }
        playMusic(0);
    }

    public static void playMusic(int i){
        music.setFiles(i);
        music.playSound();
        music.setVolume(SettingPanel.musicVolume);
        music.loopSound();
    }

    public static void stopMusic(){
        music.stopSound();
    }

    public static void playSE(int i){
        effect.setFiles(i);
        effect.setVolume(SettingPanel.effectVolume);
        effect.playSound();
    }
}

