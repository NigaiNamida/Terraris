public class Terraris {
    private static GameFrame gameFrame;
    public static void main(String[] args) {
        gameFrame = new GameFrame();
        gameFrame.start();
        gameFrame.setVisible(true);
    }

    public static GameFrame getGameFrame() {
        return gameFrame;
    }
}