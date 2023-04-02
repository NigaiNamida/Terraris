import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
public class PlayZone extends JPanel{
    private HoldPanel holdPanel;
    private XPPanel XPPanel;
    private NextPanel nextPanel;
    private static Gravity gravity;
    private Color gridLineColor;
    private int gridCols;
    private int gridRows;
    private static int blockSize;
    private static int lastAction;
    private float brightness;

    private static boolean isUseHold;
    private boolean isGameOver;
    private boolean isMiniTSpin;
    private boolean isTSpin;
    private boolean isRotated;

    private TetrisPiece block;
    private static ArrayList<String> blockQueue = new ArrayList<String>();
    
    private static Color[][] backgroundBlock;
    private static Color slimePuddleColor;

    private int[][] testSet;

    public PlayZone(){
        lastAction = 0;
        brightness = (60)/100.0f;
        blockQueue = new ArrayList<String>();
        isUseHold = false;
        isGameOver = false;

        isRotated = false;
        isMiniTSpin = false;
        isTSpin = false;

        holdPanel = GameFrame.getHoldPanel();
        nextPanel = GameFrame.getNextPanel();
        XPPanel = GameFrame.getXPPanel();

        gridLineColor = new Color(150, 150, 150, 125);
        slimePuddleColor = new Color(255, 255, 255, 0);

        this.setOpaque(true);
        this.setBounds(145, 20, 250, 500);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3,true));
        this.setDoubleBuffered(true);

        gridCols = 10;
        blockSize = this.getWidth()/ gridCols;
        gridRows = this.getHeight() / blockSize;
        backgroundBlock = new Color[gridRows][gridCols];

        TetrisPiece.queueBlock(blockQueue,true);
        
        gravity = new Gravity(this,1);
        createBlock();
    }

    public void resetTSpin() {
        isRotated = false;
        isMiniTSpin = false;
        isTSpin = false;
    }

    

    public int getGridCols() {
        return gridCols;
    }

    public int getGridRows() {
        return gridRows;
    }

    public static Color[][] getBackgroundBlock() {
        return backgroundBlock;
    }

    public void setBackgroundBlock(int row, int column, Color color) {
        backgroundBlock[row][column] = color;
    }

    public Color getSlimePuddleColor() {
        return slimePuddleColor;
    }

    public static Gravity getGravity() {
        return gravity;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    //check if tetris piece reach the bottom
    public boolean isBottom(){  
        return block.getY() + block.getHeight() == gridRows;
    }

    //add more 7 shuffle piece to queue
    public static void addQueueIfLow(){
        if(blockQueue.size() <= 1){
            TetrisPiece.queueBlock(blockQueue,false);
        }
    }

    //return Tetris piece in queue next order and reset Hold usage
    public static TetrisPiece getNextPiece(){
        isUseHold = false;
        addQueueIfLow();
        return TetrisPiece.getBlock(blockQueue.get(0));
    }

    //find the lowest point index point (y) of obstacle that playing Tetris piece will hit
    public int lowestPoint(){
        int lowestPointIndex = gridRows;
        int x = block.getX();
        int y = block.getY();
        int[][] shape = block.getShape();
        int blockWidth = block.getWidth();
        int blockHeight = block.getHeight();
        for(int row = 0;row < blockHeight;row++){
            for (int col = 0; col < blockWidth; col++){
                for (int downRow = 0; y+blockHeight+downRow < gridRows ; downRow++) {
                    if(shape[blockHeight-1-row][col] == 1){
                        if(y+blockHeight+downRow-row >= 0 && x+col < gridCols && x+col >=0 && y+blockHeight+downRow-row < gridRows){
                            if(backgroundBlock[y+blockHeight+downRow-row][x+col] != null && backgroundBlock[y+blockHeight+downRow-row][x+col] != slimePuddleColor){
                                if(y+blockHeight+downRow < lowestPointIndex){
                                    lowestPointIndex = y+blockHeight+downRow;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return lowestPointIndex;
    }

    public void lastTimerTrigger(){
        if (isBottom() || !canGo("Down")){
            gravity.stopTimer();
            gravity.startLastTimer();
        }
        else if(!isBottom() || canGo("Down")){
            gravity.stopLastTimer();
            gravity.restartTimer();
        }
    }

    public void lastTimerReset(){
        gravity.stopLastTimer();
        gravity.restartLastTimer();
    }

    public void TowerSlide(){
        if(isBottom() || !canGo("Down")){
            if (lastAction <= 0){
                int m = (lowestPoint()-block.getHeight()) - block.getY();
                XPPanel.deductXP(m);
                hardDrop();
                repaint();
            }
            gravity.restartTimer();
            gravity.stopTimer();
            gravity.startLastTimer();
        }
        else if(!isBottom() || canGo("Down")){
            gravity.stopLastTimer();;
            gravity.startTimer();
        }
    }

    //get a new playable Tetris piece from queue and set position
    public void createBlock(){
        resetTSpin();
        addQueueIfLow();
        lastTimerReset();
        lastAction = 15;
        block = TetrisPiece.getBlock(blockQueue.remove(0));
        block.spawnTetris(gridCols);
        nextPanel.setBlock(getNextPiece());
        nextPanel.repaint();
        int x = block.getX();
        int y = block.getY();
        for(int row = 0; row < block.getHeight() && !isGameOver; row++) {
            for(int col = 0; col < block.getWidth(); col++) {
                if(backgroundBlock[row+y][col+x] != null){
                    GameFrame.playSE(5);
                    gameOver();
                    break;
                }
            }
        }
    }
    
    public void gameOver(){
        boolean isHighXP = false;
        gravity.stopTimer();
        gravity.stopLastTimer();;
        isGameOver = true;
        GameFrame.setPlaying(false);
        if(GameFrame.getMusic() != null){
            GameFrame.stopMusic();
        }
        int[] topXP = Leaderboard.getTopScore();
        int XP = XPPanel.getXP();
        for (int i = 0; i< topXP.length; i++) {
            if(XP > topXP[i]){
                isHighXP = true;
                HighScorePanel.getScore().setText(String.format("%,d",XP));
                GameFrame.getHighScorePanel().setVisible(true);
                break;
            }
        }
        if(!isHighXP){
            GameFrame.getGameOverPanel().setVisible(true);
        }
        GameFrame.getBossPanel().stopAllTimer();
    }

    //hold the current playing Tetris piece
    public void holdBlock(){
        if(!isUseHold){
            if(holdPanel.getBlock() == null){
                holdPanel.setBlock(TetrisPiece.getBlock(block.getName()));
                nextPanel.setBlock(getNextPiece()); 
                holdPanel.getBlock().spawnTetris(gridCols);
                createBlock();
            }
            else{
                TetrisPiece temp;
                temp = holdPanel.getBlock();
                holdPanel.setBlock(TetrisPiece.getBlock(block.getName()));
                block = temp;
                holdPanel.getBlock().spawnTetris(gridCols);
                resetTSpin();
            }
            isUseHold = true;
            nextPanel.repaint();
            holdPanel.repaint();
            repaint();
            GameFrame.playSE(3);
        }
    }

    public int wallKickTest(String direction,int xOffset,int yOffset){
        int x = block.getX() + xOffset;
        int y = block.getY() + yOffset;
        if(direction == "CW" && block.getName() != "I"){
            switch (block.getVariant()) {
                case 0:
                    testSet = new int[][]{{0,0},{-1,0},{-1,-1},{0,2},{-1,2}};
                    break;
                case 1:
                    testSet = new int[][]{{0,0},{1,0},{1,1},{0,-2},{1,-2}};
                    break;
                case 2:
                    testSet = new int[][]{{0,0},{1,0},{1,-1},{0,2},{1,2}};
                    break;
                case 3:
                    testSet = new int[][]{{0,0},{-1,0},{-1,1},{0,-2},{-1,-2}};
                    break;
            }
        }
        else if(direction == "CT-CW" && block.getName() != "I"){
            switch (block.getVariant()) {
                case 0:
                    testSet = new int[][]{{0,0},{1,0},{1,-1},{0,2},{1,2}};
                    break;
                case 1:
                    testSet = new int[][]{{0,0},{1,0},{1,1},{0,-2},{1,-2}};
                    break;
                case 2:
                    testSet = new int[][]{{0,0},{-1,0},{-1,-1},{0,2},{-1,2}};
                    break;
                case 3:
                    testSet = new int[][]{{0,0},{-1,0},{-1,1},{0,-2},{-1,-2}};
                    break;
            }
        }
        else if(direction == "CW" && block.getName() == "I"){
            switch (block.getVariant()) {
                case 0:
                    testSet = new int[][]{{0,0},{-2,0},{1,0},{-2,1},{1,-2}};
                    break;
                case 1:
                    testSet = new int[][]{{0,0},{-1,0},{2,0},{-1,-2},{2,1}};
                    break;
                case 2:
                    testSet = new int[][]{{0,0},{2,0},{-1,0},{2,-1},{-1,2}};
                    break;
                case 3:
                    testSet = new int[][]{{0,0},{1,0},{-2,0},{1,2},{-2,-1}};
                    break;
            }
        }
        else{
            switch (block.getVariant()) {
                case 0:
                    testSet = new int[][]{{0,0},{-1,0},{2,0},{-1,-2},{2,1}};
                    break;
                case 1:
                    testSet = new int[][]{{0,0},{2,0},{-1,0},{2,-1},{-1,2}};
                    break;
                case 2:
                    testSet = new int[][]{{0,0},{1,0},{-2,0},{1,2},{-2,-1}};
                    break;
                case 3:
                    testSet = new int[][]{{0,0},{-2,0},{1,0},{-2,1},{1,-2}};
                    break;
            }
        }
        for (int i = 0; i < testSet.length; i++){
            int[][] rotated = rotateShape(direction);;
            int blockHeight = rotated.length;
            int blockWidth = rotated[0].length;
            boolean isPassCase = true;
            int xTest = testSet[i][0];
            int yTest = testSet[i][1];
            for (int row = 0; row < blockHeight; row++) {
                for (int col = 0; col < blockWidth; col++){
                    if(y+row+yTest >= 0 && y+row+yTest < gridRows && x+col+xTest >= 0 && x+col+xTest < gridCols){
                        if(rotated[row][col] == 1 && (backgroundBlock[y+row+yTest][x+col+xTest] != null && backgroundBlock[y+row+yTest][x+col+xTest] != slimePuddleColor)){
                            isPassCase = false;
                            break;
                        }
                    }
                    else if(y+row+yTest < 0){
                        continue;
                    }
                    else{
                        isPassCase = false;
                        break;
                    }
                }
            }
            if(isPassCase){
                isRotated = true;
                return i;
            }
        }
        return -1;
    }


    //rotate array
    public int[][] rotateShape(String direction){
        int [][] shape = block.getShape();
        final int blockHeight = block.getHeight();
        final int blockWidth = block.getWidth();
        int[][] rotated;
        if(direction == "CW"){
            rotated = new int[blockWidth][blockHeight];
            for(int row = 0; row < blockHeight; row++) {
                for(int col = 0; col < blockWidth; col++) {
                    rotated[col][blockHeight-1-row] = shape[row][col];
                }
            }
        }
        else{
            rotated = new int[blockWidth][blockHeight];
            for(int row = 0; row < blockHeight; row++) {
                for(int col = 0; col < blockWidth; col++) {
                    rotated[blockWidth-1-col][row] = shape[row][col];
                }
            }
        }
        return rotated;
    }
    //rotate current playing Tetris piece 90 degree clockwise
    public void rotate(String direction) {
        //rotate 2D array of shape

        int xOffset = 0;
        int yOffset = 0;
        int rotateResult = 0;
        
        //offset for block new position after rotate
        if(direction == "CW"){
            if(block.getName() != "O" && block.getName() != "I"){
                switch (block.getVariant()) {
                    case 0:
                        xOffset = 1;
                        break;
                    case 1:
                        xOffset = -1;
                        yOffset = 1;
                        break;
                    case 2:
                        yOffset = -1;
                        break;
                }
            }
            if(block.getName() == "I"){
                switch (block.getVariant()) {
                    case 0:
                        xOffset = 2;
                        yOffset = -1;
                        break;
                    case 1:
                        xOffset = -2;
                        yOffset = 2;
                        break;
                    case 2:
                        xOffset = 1;
                        yOffset = -2;
                        break;
                    case 3:
                        xOffset = -1;
                        yOffset = 1;
                        break;
                }
            }
        }
        else{
            if(block.getName() != "O" && block.getName() != "I"){
                switch (block.getVariant()) {
                    case 1:
                        xOffset = -1;
                        break;
                    case 2:
                        xOffset = 1;
                        yOffset = -1;
                        break;
                    case 3:
                        yOffset = 1;
                        break;
                }
            }
            if(block.getName() == "I"){
                switch (block.getVariant()) {
                    case 1:
                        xOffset = -2;
                        yOffset = 1;
                        break;
                    case 2:
                        xOffset = 2;
                        yOffset = -2;
                        break;
                    case 3:
                        xOffset = -1;
                        yOffset = 2;
                        break;
                    case 0:
                        xOffset = 1;
                        yOffset = -1;
                        break;
                }
            }
        }

        rotateResult = wallKickTest(direction,xOffset,yOffset);
        if(rotateResult != -1){
            if(direction == "CW"){
                block.setVariant((block.getVariant()+1) % 4);
            }
            else{
                if((block.getVariant()-1) < 0){
                    block.setVariant(3);
                }
                else
                    block.setVariant((block.getVariant()-1) % 4);
            }
            if(isBottom() || !canGo("Down")){
                TowerSlide();
                lastAction--;
                if(lastAction > 0)
                    lastTimerReset();
            }
            int newX = block.getX() + testSet[rotateResult][0] + xOffset;
            int newY = block.getY() + testSet[rotateResult][1] + yOffset;
            block.setPosition(newX, newY);
            block.setShape(rotateShape(direction));
            if(!isBottom() || canGo("Down"))
                TowerSlide();
            repaint();
        }
    }

    //check if playing Tetris piece can move without stack with other placed Tetris piece in desire direction
    public boolean canGo(String direction){
        int yOffset = 0;
        int xOffset = 0;
        switch (direction) {
            case "Down":
                yOffset=1;
                xOffset=0;
                break;
            case "Left":
                yOffset=0;
                xOffset=-1;
                break;    
            case "Right":
                yOffset=0;
                xOffset=1;
                break; 
        }
        int x = block.getX();
        int y = block.getY();
        int[][] shape = block.getShape();
        int blockWidth = block.getWidth();
        int blockHeight = block.getHeight();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++){
                if(y+row+yOffset >= 0 && y+row+yOffset < gridRows && x+col+xOffset >= 0 && x+col+xOffset < gridCols){
                    if(shape[row][col] == 1 && (backgroundBlock[y+row+yOffset][x+col+xOffset] != null && backgroundBlock[y+row+yOffset][x+col+xOffset] != slimePuddleColor)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //instant drop playing Tetris piece to bottom
    public void hardDrop(){
        int m = (lowestPoint()-block.getHeight()) - block.getY();
        if(m != 0){
            resetTSpin();
        }
        XPPanel.addHardDropXP(m);
        block.setPosition(block.getX(), lowestPoint()-block.getHeight());
        repaint();
        nextPanel.repaint();
        nextPanel.setBlock(getNextPiece());
        updateBackGround();
        GameFrame.playSE(2);
        checkFullLine();
        createBlock();
        gravity.restartTimer();
        repaint();  
    }

    //move playing Tetris piece to the right
    public void moveRight(){
        if(block.getX() + block.getWidth() < gridCols && canGo("Right")){
            if(isBottom() || !canGo("Down")){
                TowerSlide();
                lastAction--;
                if(lastAction > 0)
                    lastTimerReset();
            }
            block.moveRight();
            if(!isBottom() || canGo("Down"))
                TowerSlide();
            repaint();
        }
    }

    //move playing Tetris piece to the left
    public void moveLeft(){
        if(block.getX() > 0 && canGo("Left")){
            if(isBottom() || !canGo("Down")){
                TowerSlide();
                lastAction--;
                if(lastAction > 0)
                    lastTimerReset();
            }
            block.moveLeft();
            if(!isBottom() || canGo("Down"))
                TowerSlide();
            repaint();
        }
    }

    //constantly pull down the playing Tetris piece to the bottom
    public void softDrop(){
        if(!isBottom() && canGo("Down")){
            applyGravity();
            XPPanel.addSoftDropXP();
            GameFrame.playSE(1);
        }
    }

    public void applyGravity(){
        if(!isBottom() && canGo("Down")){
            resetTSpin();
            block.moveDown();
            lastTimerTrigger();
            repaint();
        }
        else{//reach the end (bottom or hit block)
            nextPanel.repaint();
            nextPanel.setBlock(getNextPiece());
            updateBackGround();
            GameFrame.playSE(2);
            checkFullLine();
            createBlock();
            lastTimerTrigger();
            repaint();       
        }
    }

    //update the new value of backgroundBlock to keep track of placed Tetris piece
    public void updateBackGround(){
        int x = block.getX();
        int y = block.getY();
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int[][] shape = block.getShape();
        Color color = block.getColor();
        //assign last placed Tetris piece to Background
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                if(shape[row][col] == 1 && y+row < gridRows && x+col < gridCols && y+row >= 0 && x+col >= 0)
                    backgroundBlock[y+row][x+col] = color;
            }
        }
    }

    public boolean haveBlock(int r,int c){
        int x = block.getX();
        int y = block.getY();
        if(y+r < gridRows && x+c < gridCols && y+r >= 0 && x+c >= 0 && (backgroundBlock[y+r][x+c] == null || backgroundBlock[y+r][x+c] == slimePuddleColor)){
            return false;
        }
        return true;
    }

    public boolean[] checkSide(){
        boolean[] sideCheck = new boolean[4];//A B C D
        switch (block.getVariant()) {
            case 0:
                sideCheck[0] = haveBlock(0,0);
                sideCheck[1] = haveBlock(0,2);
                sideCheck[2] = haveBlock(2,0);
                sideCheck[3] = haveBlock(2,2);
                break;
            case 1:
                sideCheck[0] = haveBlock(0,1);
                sideCheck[1] = haveBlock(2,1);
                sideCheck[2] = haveBlock(0,-1);
                sideCheck[3] = haveBlock(2,-1);
                break;
            case 2:
                sideCheck[0] = haveBlock(1,2);
                sideCheck[1] = haveBlock(1,0);
                sideCheck[2] = haveBlock(-1,2);
                sideCheck[3] = haveBlock(-1,0);
                break;
            default:
                sideCheck[0] = haveBlock(2,0);
                sideCheck[1] = haveBlock(0,0);
                sideCheck[2] = haveBlock(2,2);
                sideCheck[3] = haveBlock(0,2);
                break;
        }
        return sideCheck;
    }

    //check for the row full of block to delete and get XP
    public void checkFullLine(){
        if(block.getName() == "T" && isRotated){
            boolean[] sideCheck = checkSide();
            if(sideCheck[0] && sideCheck[1] && (sideCheck[2] || sideCheck[3])){
                isTSpin = true;
            }
            else if(sideCheck[2] && sideCheck[3] && (sideCheck[0] || sideCheck[1])){
                isMiniTSpin = true;
            }
        }
        int fullLineAmount = 0;
        for(int row = 0; row < gridRows; row++) {
            boolean isFullRow = true;
            for (int col = 0; col < gridCols; col++) {
                if(backgroundBlock[row][col] == null || backgroundBlock[row][col] == slimePuddleColor){
                    isFullRow = false;
                    break;
                }
            }
            if(isFullRow){
                fullLineAmount++;
                GoalPanel.addGoal();
                GoalPanel.getGoalScore().setText(""+GoalPanel.getGoal());
                shiftRow(row);//bottom row to shift
            }
        }
        if(isMiniTSpin || isTSpin){
            XPPanel.addTSpinXP(isTSpin,fullLineAmount);
        }
        else if(fullLineAmount > 0){
            GameFrame.playSE(4);
            XPPanel.addFullLineXP(fullLineAmount);
        }
    }

    //delete a full row and shift all Background block downward by 1
    public void shiftRow(int bottomRow){
        for (int row = bottomRow; row >= 1; row--) {
            // assign values of the previous row to current row
            for (int col = 0; col < gridCols; col++) {
                backgroundBlock[row][col] = backgroundBlock[row - 1][col];
            }
        }
        for (int col = 0; col < gridCols; col++) {
            backgroundBlock[0][col] = null;
        }
    }

    //paint component once
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//not have = no panel bg color
        BossPanel bossPanel = GameFrame.getBossPanel();
        Image BGImage = new ImageIcon(bossPanel.getStage().getBGImagePath()).getImage();
        g.drawImage(BGImage, 0, 0, null);
        int brightness = (int)(256 - 256 * this.brightness);
        g.setColor(new Color(0,0,0,brightness));
        g.fillRect(0, 0, getWidth(), getHeight());

        drawGridLine(g);
        drawPhantomBlock(g);
        drawColorBlock(g);
        drawPile(g);
        BossAttack.drawBossAttack(g);
        if (bossPanel.getStage() == Theme.Night || bossPanel.getStage() == Theme.EyeOfCthulhu){
            g.setColor(new Color(0,0,0,90));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
	//paint playzone Background gridline
    private void drawGridLine(Graphics g){
        for (int row = -1; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                int x = col * blockSize; //coordinate + offset
                int y = row * blockSize; //coordinate + offset
                g.setColor(gridLineColor);
                g.drawRect(x, y, blockSize, blockSize);  
            }
        }
    }
    
    //paint all placed Tetris pieces in the Background
    private void drawPile(Graphics g){
        int lap = 1;
        Color color;
        String middle;
        String[][] blockCoordinate = new String[20][10];
        boolean[][] sideCheck = new boolean[3][3];
        while(lap<4){
            for (int row = 0; row < gridRows; row++) {
                for (int col = 0; col < gridCols; col++) {
                    color = backgroundBlock[row][col];
                    if(color != null && lap == 1){
                        int x = col * blockSize; //coordinate + offset
                        int y = row * blockSize; //coordinate + offset
                        paintBlocks(g,color,x,y);
                        if(color.equals(TetrisPiece.getBlock("O").getColor()))
                            blockCoordinate[row][col] = "O";
                        else if(color.equals(TetrisPiece.getBlock("I").getColor()))
                            blockCoordinate[row][col] = "I";
                        else if(color.equals(TetrisPiece.getBlock("T").getColor()))
                            blockCoordinate[row][col] = "T";
                        else if(color.equals(TetrisPiece.getBlock("L").getColor()))
                            blockCoordinate[row][col] = "L";
                        else if(color.equals(TetrisPiece.getBlock("J").getColor()))
                            blockCoordinate[row][col] = "J";
                        else if(color.equals(TetrisPiece.getBlock("S").getColor()))
                            blockCoordinate[row][col] = "S";
                        else if(color.equals(TetrisPiece.getBlock("Z").getColor()))
                            blockCoordinate[row][col] = "Z";
                        else if(color == slimePuddleColor)
                            blockCoordinate[row][col] = "Slime_Puddle";
                    }
                    if(blockCoordinate[row][col] != "null" && lap == 2){
                        for(int checkrow = 0; checkrow <= 2; checkrow++){
                            for(int checkcol = 0; checkcol <= 2; checkcol++){
                                if(col+checkcol-1 >= 0 && col+checkcol-1 < gridCols && row+checkrow-1 >= 0 && row+checkrow-1 < gridRows){
                                    if(blockCoordinate[row+checkrow-1][col+checkcol-1] == blockCoordinate[row][col]){
                                        sideCheck[checkrow][checkcol] = true;
                                    }
                                }
                            }
                        }
                        int x = col * blockSize; //coordinate + offset
                        int y = row * blockSize; //coordinate + offset
                        middle = blockCoordinate[row][col];
                        TetrisTexture.mergePileTexture(g, middle, x, y, sideCheck); 
                        sideCheck = new boolean[3][3];
                    }
                    if(blockCoordinate[row][col] != "null" && lap == 3){
                        int x = col * blockSize; //coordinate + offset
                        int y = row * blockSize; //coordinate + offset
                        middle = blockCoordinate[row][col];
                        if(row + 1 < gridRows){
                            if (color == slimePuddleColor && blockCoordinate[row+1][col] == null){
                                middle = null;
                            }
                        }
                        TetrisTexture.drawPuddleTexture(g, middle, x, y);
                    }             
                }
            }
            lap++;
        }
    }

    //paint current playing Tetris piece
    private void drawColorBlock(Graphics g){
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        Color color = block.getColor();
        int[][] shape = block.getShape();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                //paint where block == 1
                if(shape[row][col] == 1){
                    int x = (block.getX() + col) * blockSize; //coordinate + offset
                    int y = (block.getY() + row) * blockSize; //coordinate + offset
                    paintBlocks(g,color,x,y);
                    if (block.getY() + row >= 0 && block.getX() + col >= 0 && block.getY() + row < gridRows && block.getX() + col < gridCols){
                        if(backgroundBlock[block.getY() + row][block.getX() + col] == slimePuddleColor){
                            hardDrop();
                        }
                    }
                }
            }
        }
        TetrisTexture.drawBlockTexture(g,block.getName(),block.getVariant(),block.getX()*blockSize,block.getY()*blockSize);
    }

    //paint predicted phantom piece of current playing Tetris piece
    private void drawPhantomBlock(Graphics g){
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        // Color color = block.getColor();
        int[][] shape = block.getShape();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                //paint where block == 1
                if(row < blockHeight && col < blockWidth){
                    if(shape[row][col] == 1){
                        int x = (block.getX() + col) * blockSize; //coordinate + offset
                        int y = (lowestPoint() + row - blockHeight) * blockSize; //coordinate + offset
                        g.setColor(new Color(255, 255, 255,125));
                        g.fillRect(x, y, blockSize, blockSize);
                        g.setColor(new Color(100, 100, 100));
                        g.drawRect(x, y, blockSize, blockSize);  
                    }
                }
            }
        }
    }


    //paint a single square block on desire position
    private void paintBlocks(Graphics g,Color color,int x,int y){
        g.setColor(color);
        g.fillRect(x, y, blockSize, blockSize);
    }
}