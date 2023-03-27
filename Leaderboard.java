import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Leaderboard extends JPanel{
    private static JLabel leaderboardLabel;
    private static int[] topScore;
    private static String[] topName;
    private static JLabel[] scoreBoard;
    private static Font font;
    private static String leaderboardFile;
    
    public Leaderboard(){
        leaderboardLabel = new JLabel("LEADERBOARD");
        topScore = new int[5];
        topName = new String[5];
        scoreBoard = new JLabel[5];
        leaderboardFile = "leaderboard.txt";

        font = new Font("Futura",Font.PLAIN,20);
        this.setOpaque(true);
        this.setBounds(150, 70, 500, 400);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.WHITE,3));
        this.setLayout(null);

        leaderboardLabel.setForeground(new Color(193,221,196,255));
        leaderboardLabel.setFont(new Font("Futura",Font.BOLD,40));
        leaderboardLabel.setBounds(50, 40, 400, 40);
        leaderboardLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(leaderboardLabel);

        if(topScore[0] == 0){
            for (int i = 0; i < topScore.length; i++) {
                topScore[i] = 0;
                if(topScore[i] == 0)
                    scoreBoard[i] = new JLabel("No Score");
                else
                    scoreBoard[i] = new JLabel(topName[i]+" : "+topScore[i]);
                scoreBoard[i].setForeground(new Color(193,221,196,255));
                scoreBoard[i].setFont(font);
                scoreBoard[i].setBounds(150, (60*(i+1))+40, 200, 30);
                scoreBoard[i].setHorizontalAlignment(JLabel.CENTER);
                this.add(scoreBoard[i]);
            }
        }
    }

    public static String[] getTopName() {
        return topName;
    }

    public static int[] getTopScore() {
        return topScore;
    }

    public static String getLeaderboardFile() {
        return leaderboardFile;
    }

    public static void newRank(String name,int score,int replaceRank){
        for (int i = topScore.length-1; i > replaceRank; i--) {
            topScore[i] = topScore[i-1];
            topName[i] =  topName[i-1];
            if(topScore[i] != 0)
                scoreBoard[i].setText(topName[i]+" : "+topScore[i]);
        }
        topScore[replaceRank] = score;
        topName[replaceRank] = name;
        scoreBoard[replaceRank].setText(topName[replaceRank]+" : "+topScore[replaceRank]);
        updateScoreBoard();
    }

    public static void saveLeaderboard(){
        Path path = Paths.get(leaderboardFile);
        try (PrintWriter output = new PrintWriter(path.getFileName().toString())) {
            for (int i = 0; i < topName.length; i++) {
                output.printf("%s %d%n",topName[i],topScore[i]);
            }
        } 
        catch (Exception e) {}
    }

    public static void updateScoreBoard() {
        for (int i = 0; i < topScore.length; i++) {
            if(topScore[i] == 0)
                scoreBoard[i].setText("No Score");
            else
                scoreBoard[i].setText(topName[i]+" : "+topScore[i]);
            scoreBoard[i].setForeground(new Color(193,221,196,255));
            scoreBoard[i].setFont(font);
            scoreBoard[i].setBounds(150, (60*(i+1))+40, 200, 30);
            scoreBoard[i].setHorizontalAlignment(JLabel.CENTER);
        }
    }
}
