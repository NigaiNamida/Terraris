import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameFrame extends JFrame{
    private static Menu menu;
    private static RewardPanel rewardPanel;
    private static PlayZone playZone;
    private static HoldPanel holdPanel;
    private static NextPanel nextPanel;
    private static ChatPanel chatPanel;
    private static LevelPanel levelPanel;
    private static BossPanel bossPanel;
    private static XPPanel XPPanel;
    private static PausePanel pausePanel;
    private static SettingPanel settingPanel;
    private static AnyKeyPanel anyKeyPanel;
    private static HighScorePanel highscorePanel;
    private static GameOverPanel gameOverPanel;
    
    private static KeyHandler keyHandler;
    private static Leaderboard leaderboard;
    private static GameThread gameThread;
    private static AnimationThread animationThread;
    private static Sound effect;
    private static Sound music;

    private static boolean isPlaying;

    private static Font terrariaFont;

    public GameFrame(){
        try {
            terrariaFont = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Font/ANDYB.TTF")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Font/ANDYB.TTF")));
        } catch (Exception e) {
            System.err.println("NO FONT");
        }
        
        effect = new Sound();
        music = new Sound();
        highscorePanel = new HighScorePanel();
        gameOverPanel = new GameOverPanel();
        holdPanel = new HoldPanel();
        nextPanel = new NextPanel();
        chatPanel = new ChatPanel();
        levelPanel = new LevelPanel();
        bossPanel = new BossPanel();
        XPPanel = new XPPanel();
        playZone = new PlayZone();
        keyHandler = new KeyHandler();
        rewardPanel = new RewardPanel();
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
        this.setResizable(false);
        this.getContentPane().setBackground(Color.black);
        
        //add component
        this.add(anyKeyPanel);
        this.add(highscorePanel);
        this.add(leaderboard);
        this.add(gameOverPanel);
        this.add(pausePanel);
        this.add(rewardPanel);
        this.add(settingPanel);
        this.add(chatPanel);
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
        rewardPanel.setVisible(false);
        pausePanel.setVisible(false);
        chatPanel.setVisible(false);
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
        this.remove(pausePanel);
        this.remove(rewardPanel);
        this.remove(bossPanel);
        this.remove(highscorePanel);
        this.remove(gameOverPanel);
        this.remove(chatPanel);
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
        nextPanel = new NextPanel();
        chatPanel = new ChatPanel();
        levelPanel = new LevelPanel();
        bossPanel = new BossPanel();
        XPPanel = new XPPanel();
        playZone = new PlayZone();
        keyHandler = new KeyHandler();
        rewardPanel = new RewardPanel();
        pausePanel = new PausePanel();
        isPlaying = false;
        
        //add component
        this.add(pausePanel);
        this.add(rewardPanel);
        this.add(bossPanel);
        this.add(highscorePanel);
        this.add(gameOverPanel);
        this.add(chatPanel);
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
        rewardPanel.setVisible(false);
        pausePanel.setVisible(false);
        chatPanel.setVisible(false);
        holdPanel.setVisible(false);
        nextPanel.setVisible(false);
        levelPanel.setVisible(false);
        XPPanel.setVisible(false);
        playZone.setVisible(false);
        
        this.addKeyListener(keyHandler);

        startGame();
    }

    public void backToMenu(){
        if(GameFrame.music != null){
            GameFrame.stopMusic();
        }

        this.remove(bossPanel);
        this.remove(highscorePanel);
        this.remove(gameOverPanel);
        this.remove(rewardPanel);
        this.remove(pausePanel);
        this.remove(chatPanel);
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
        nextPanel = new NextPanel();
        chatPanel = new ChatPanel();
        levelPanel = new LevelPanel();
        bossPanel = new BossPanel();
        XPPanel = new XPPanel();
        playZone = new PlayZone();
        keyHandler = new KeyHandler();
        rewardPanel = new RewardPanel();
        pausePanel = new PausePanel();
        isPlaying = false;
        
        //add component
        this.add(pausePanel);
        this.add(rewardPanel);
        this.add(bossPanel);
        this.add(highscorePanel);
        this.add(gameOverPanel);
        this.add(chatPanel);
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
        rewardPanel.setVisible(false);
        pausePanel.setVisible(false);
        chatPanel.setVisible(false);
        holdPanel.setVisible(false);
        nextPanel.setVisible(false);
        levelPanel.setVisible(false);
        XPPanel.setVisible(false);
        playZone.setVisible(false);
        menu.setVisible(true);
        
        this.addKeyListener(keyHandler);
        playMusic(6);
    }

    public static void loadLeaderboard(){
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

    public static void loadSetting(){
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

    public static void pauseGame(){
        GameFrame.playSE(7);
        pausePanel.setVisible(true);
        pausePanel.repaint();
    }

    public static void continueGame(){
        GameFrame.playSE(8);
        pausePanel.setVisible(false);
    }

    public static void startGame(){
        bossPanel.setVisible(true);
        chatPanel.setVisible(true);
        holdPanel.setVisible(true);
        nextPanel.setVisible(true);
        levelPanel.setVisible(true);
        XPPanel.setVisible(true);
        playZone.setVisible(true);
        isPlaying = true;
        
        gameThread = new GameThread();
        animationThread = new AnimationThread();
        gameThread.start();
        animationThread.start();
        if(music.getClip() != null){
            music.stopSound();
        }
        playMusic(0);
    }

    public static void playMusic(int i){
        music.setFiles(i);
        music.playSound();
        music.setVolume(SettingPanel.getMusicVolume());
        music.loopSound();
    }

    public static void stopMusic(){
        music.stopSound();
    }

    public static void playSE(int i){
        effect.setFiles(i);
        effect.setVolume(SettingPanel.getFXVolume());
        effect.playSound();
    }

    public static RewardPanel getRewardPanel() {
        return rewardPanel;
    }

    public static void setRewardPanel(RewardPanel rewardPanel) {
        GameFrame.rewardPanel = rewardPanel;
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

    public static ChatPanel getChatPanel() {
        return chatPanel;
    }

    public static PlayZone getPlayZone() {
        return playZone;
    }
    public static GameOverPanel getGameOverPanel() {
        return gameOverPanel;
    }

    public static HighScorePanel getHighScorePanel() {
        return highscorePanel;
    }

    public static LevelPanel getLevelPanel() {
        return levelPanel;
    }

    public static XPPanel getXPPanel() {
        return XPPanel;
    }

    public static PausePanel getPausePanel() {
        return pausePanel;
    }

    public static BossPanel getBossPanel() {
        return bossPanel;
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

    public static Font getTerrariaFont(float size) {
        return terrariaFont.deriveFont(size+5);
    }
    
}

