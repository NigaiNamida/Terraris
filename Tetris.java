public class Tetris {
    private static GameFrame gameFrame;
    public static void main(String[] args) {
        gameFrame = new GameFrame();
        gameFrame.setVisible(true);
    }

    public static GameFrame getGameFrame() {
        return gameFrame;
    }
}