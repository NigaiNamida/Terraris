import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
public class PlayZone extends JPanel{
    private GameFrame gameFrame;
    private HoldPanel holdPanel;
    private XPPanel XPPanel;
    private NextPanel nextPanel;
    private GoalPanel goalPanel;
    private HighScorePanel highScorePanel;
    private Gravity gravity;
    
    private Color gridLineColor;
    private int gridCols;
    private int gridRows;
    private int blockSize;
    private int lastAction;

    private boolean isUseHold;
    private boolean isGameOver;
    private boolean isMiniTSpin;
    private boolean isTSpin;
    private boolean isRotated;

    private TetrisPiece block;
    private ArrayList<String> blockQueue = new ArrayList<String>();
    
    private Color[][] background;

    private int[][] testSet;

    public PlayZone(){
        gameFrame = Terraris.getGameFrame();
        holdPanel = gameFrame.getHoldPanel();
        nextPanel = gameFrame.getNextPanel();
        XPPanel = gameFrame.getXPPanel();
        goalPanel = gameFrame.getGoalPanel();
        highScorePanel = gameFrame.getHighScorePanel();
        lastAction = 0;
        blockQueue = new ArrayList<String>();
        isUseHold = false;
        isGameOver = false;

        isRotated = false;
        isMiniTSpin = false;
        isTSpin = false;

        gridLineColor = new Color(36, 36, 36);

        this.setOpaque(true);
        this.setBounds(145, 20, 250, 500);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));
        this.setDoubleBuffered(true);

        gridCols = 10;
        blockSize = this.getWidth()/ gridCols;
        gridRows = this.getHeight() / blockSize;
        background = new Color[gridRows][gridCols];

        TetrisPiece.queueBlock(blockQueue,true);
        
        gravity = new Gravity(this,1);
    }

    public void resetTSpin() {
        isRotated = false;
        isMiniTSpin = false;
        isTSpin = false;
    }

    public Gravity getGravity() {
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
    public void addQueueIfLow(){
        if(blockQueue.size() <= 1){
            TetrisPiece.queueBlock(blockQueue,false);
        }
    }

    //return Tetris piece in queue next order and reset Hold usage
    public TetrisPiece getNextPiece(){
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
                            if(background[y+blockHeight+downRow-row][x+col] != null){
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
        if(nextPanel == null){
            nextPanel = gameFrame.getNextPanel();
        }
        nextPanel.setBlock(getNextPiece());
        nextPanel.repaint();
        int x = block.getX();
        int y = block.getY();
        for(int row = 0; row < block.getHeight() && !isGameOver; row++) {
            for(int col = 0; col < block.getWidth(); col++) {
                if(background[row+y][col+x] != null){
                    gameFrame.playSE(5);
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
        gameFrame.setPlaying(false);
        if(gameFrame.getMusic() != null){
            gameFrame.stopMusic();
        }
        int[] topXP = Leaderboard.getTopScore();
        int XP = XPPanel.getXP();
        for (int i = 0; i< topXP.length; i++) {
            if(XP > topXP[i]){
                isHighXP = true;
                highScorePanel.getScore().setText(String.format("%,d",XP));
                highScorePanel.setVisible(true);
                break;
            }
        }
        if(!isHighXP){
            gameFrame.getGameOverPanel().setVisible(true);
        }
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
            gameFrame.playSE(3);
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
                        if(rotated[row][col] == 1 && background[y+row+yTest][x+col+xTest] != null){
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
                    if(shape[row][col] == 1 && background[y+row+yOffset][x+col+xOffset] != null){
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
        gameFrame.playSE(2);
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
            gameFrame.playSE(1);
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
            gameFrame.playSE(2);
            checkFullLine();
            createBlock();
            lastTimerTrigger();
            repaint();       
        }
    }

    //update the new value of background to keep track of placed Tetris piece
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
                    background[y+row][x+col] = color;
            }
        }
    }

    public boolean haveBlock(int r,int c){
        int x = block.getX();
        int y = block.getY();
        if(y+r < gridRows && x+c < gridCols && y+r >= 0 && x+c >= 0 && background[y+r][x+c] == null ){
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
                if(background[row][col] == null){
                    isFullRow = false;
                    break;
                }
            }
            if(isFullRow){
                fullLineAmount++;
                goalPanel.addGoal();
                goalPanel.getGoalScore().setText(""+goalPanel.getGoal());
                shiftRow(row);//bottom row to shift
            }
        }
        if(isMiniTSpin || isTSpin){
            XPPanel.addTSpinXP(isTSpin,fullLineAmount);
        }
        else if(fullLineAmount > 0){
            gameFrame.playSE(4);
            XPPanel.addFullLineXP(fullLineAmount);
        }
    }

    //delete a full row and shift all Background block downward by 1
    public void shiftRow(int bottomRow){
        for (int row = bottomRow; row >= 1; row--) {
            // assign values of the previous row to current row
            for (int col = 0; col < gridCols; col++) {
                background[row][col] = background[row - 1][col];
            }
        }
        for (int col = 0; col < gridCols; col++) {
            background[0][col] = null;
        }
    }

    //paint component once
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//not have = no panel bg color
        drawGridLine(g);
        drawPhantomBlock(g);
        drawColorBlock(g);
        drawPile(g);
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
        String[][] blockCordinate = new String[20][10];
        boolean[][] sideCheck = new boolean[3][3];
        while(lap<3){
            for (int row = 0; row < gridRows; row++) {
                for (int col = 0; col < gridCols; col++) {
                    color = background[row][col];
                    if(color != null && lap == 1){
                        int x = col * blockSize; //coordinate + offset
                        int y = row * blockSize; //coordinate + offset
                        paintBlocks(g,color,x,y);
                        if(color.equals(TetrisPiece.getBlock("O").getColor()))
                            blockCordinate[row][col] = "O";
                        else if(color.equals(TetrisPiece.getBlock("I").getColor()))
                            blockCordinate[row][col] = "I";
                        else if(color.equals(TetrisPiece.getBlock("T").getColor()))
                            blockCordinate[row][col] = "T";
                        else if(color.equals(TetrisPiece.getBlock("L").getColor()))
                            blockCordinate[row][col] = "L";
                        else if(color.equals(TetrisPiece.getBlock("J").getColor()))
                            blockCordinate[row][col] = "J";
                        else if(color.equals(TetrisPiece.getBlock("S").getColor()))
                            blockCordinate[row][col] = "S";
                        else if(color.equals(TetrisPiece.getBlock("Z").getColor()))
                            blockCordinate[row][col] = "Z";
                    }
                    if(blockCordinate[row][col] != "null" && lap == 2){
                        for(int checkrow = 0; checkrow <= 2; checkrow++){
                            for(int checkcol = 0; checkcol <= 2; checkcol++){
                                if(col+checkcol-1 >= 0 && col+checkcol-1 < gridCols && row+checkrow-1 >= 0 && row+checkrow-1 < gridRows){
                                    if(blockCordinate[row+checkrow-1][col+checkcol-1] == blockCordinate[row][col]){
                                        sideCheck[checkrow][checkcol] = true;
                                    }
                                }
                            }
                        }
                        int x = col * blockSize; //coordinate + offset
                        int y = row * blockSize; //coordinate + offset
                        middle = blockCordinate[row][col];
                        TetrisTexture.mergePileTexture(g,middle,x,y,sideCheck); 
                        sideCheck = new boolean[3][3];
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
                        g.setColor(Color.WHITE.darker().darker().darker());
                        g.fillRect(x, y, blockSize, blockSize);
                        g.setColor(Color.WHITE.darker());
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