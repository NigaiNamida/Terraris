import java.awt.*;

import javax.swing.*;
import java.util.ArrayList;

public class PlayZone extends JPanel{
    private HoldPanel holdPanel;
    private XPPanel XPPanel;
    private NextPanel nextPanel;
    private static Gravity gravity;

    private static Color[][] backgroundBlock;
    private static int blindness;
    private int gridCols;
    private int gridRows;
    private int radius;
    private int dynamiteDamage;
    private static int blockSize;
    private static int lastAction;
    private float brightness;
    private float cloudGravityScale;

    private static boolean isUseHold;
    private boolean isGameOver;
    private boolean isMiniTSpin;
    private boolean isTSpin;
    private boolean isRotated;
    private boolean isExploded;

    private TetrisPiece block;

    private static ArrayList<Tetris> blockQueue = new ArrayList<>();
    private static ArrayList<BlockTexture> textureQueue = new ArrayList<>();

    private static Color sand = BlockTexture.Sand.getColor(); 
    private static Color slimePuddle = BlockTexture.SlimePuddle.getColor(); 
    private static Color slimeBlock = BlockTexture.SlimeBlock.getColor(); 
    private static Color honey = BlockTexture.Honey.getColor(); 

    private static int snowFallFrame; 

    private int[][] testSet;

    public PlayZone(){
        blindness = 0;
        lastAction = 0;
        radius = 1;
        dynamiteDamage = 200;
        cloudGravityScale = 1.5f;
        brightness = (60)/100.0f;
        blockQueue = new ArrayList<>();
        textureQueue = new ArrayList<>();
        isUseHold = false;
        isGameOver = false;

        isRotated = false;
        isMiniTSpin = false;
        isTSpin = false;

        holdPanel = GameFrame.getHoldPanel();
        nextPanel = GameFrame.getNextPanel();
        XPPanel = GameFrame.getXPPanel();

        this.setOpaque(true);
        this.setBounds(145, 20, 250, 500);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        gridCols = 10;
        blockSize = this.getWidth()/ gridCols;
        gridRows = this.getHeight() / blockSize;
        backgroundBlock = new Color[gridRows][gridCols];

        TetrisPiece.queueBlock(blockQueue,true);
        TetrisPiece.queueTexture(textureQueue, true);
        
        gravity = new Gravity(this,1);

        snowFallFrame = 0;
        createBlock();
    }
    

    public void resetTSpin() {
        isRotated = false;
        isMiniTSpin = false;
        isTSpin = false;
    }

    public boolean isBottom(){  
        return block.getY() + block.getHeight() == gridRows;
    }

    public static void addQueueIfLow(){
        if(blockQueue.size() <= 1){
            TetrisPiece.queueBlock(blockQueue,false);
        }
        if(textureQueue.size() <= 1){
            TetrisPiece.queueTexture(textureQueue,false);
        }
    }

    public static void queueSpecialTexture(){
        TetrisPiece.queueSpecialTexture(textureQueue);
    }

    public static TetrisPiece getNextPiece(){
        isUseHold = false;
        addQueueIfLow();
        return TetrisPiece.getBlock(blockQueue.get(0),textureQueue.get(0));
    }

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
                    if (shape[blockHeight-1-row][col] == 1){
                        if (y+blockHeight+downRow-row >= 0 && x+col < gridCols && x+col >=0 && y+blockHeight+downRow-row < gridRows){
                            if (backgroundBlock[y+blockHeight+downRow-row][x+col] != null && 
                                backgroundBlock[y+blockHeight+downRow-row][x+col] != (slimePuddle)){
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

    public void triggerLastTimer(){
        if (isBottom() || !canGo("Down")){
            gravity.stopTimer();
            gravity.startLastTimer();
        }
        else if(!isBottom() || canGo("Down")){
            gravity.stopLastTimer();
            gravity.restartTimer();
        }
    }

    public void resetLastTimer(){
        gravity.stopLastTimer();
        gravity.restartLastTimer();
    }

    public void doTowerSlide(){
        if(isBottom() || !canGo("Down")){
            if (lastAction <= 0){
                int m = (lowestPoint()-block.getHeight()) - block.getY();
                XPPanel.deductXP(m);
                hardDrop();
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

    public void createBlock(){
        applySandGravity();
        resetTSpin();
        addQueueIfLow();
        resetLastTimer();
        lastAction = 15;
        if(BossAttack.getBoneCurseCount()>0){
            blockQueue.set(0, Tetris.O);
            textureQueue.set(0, BlockTexture.Bone);
            BossAttack.setBoneCurseCount(BossAttack.getBoneCurseCount()-1);
        }
        block = TetrisPiece.getBlock(blockQueue.remove(0),textureQueue.remove(0));
        if(block.getColor().equals(BlockTexture.Cloud.getColor())){
            gravity.setTimerScale(cloudGravityScale);
        }
        else{
            gravity.setTimer();
        }
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
        isExploded = false;
        activeSpecialBlock();
        applySandGravity();
        gravity.restartTimer();
        BossAttack.setConfuseCount(BossAttack.getConfuseCount()-1);
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
        int[] topScore = Leaderboard.getTopScore();
        int score = XPPanel.getScore();
        for (int i = 0; i< topScore.length; i++) {
            if(score > topScore[i]){
                isHighXP = true;
                HighScorePanel.getScore().setText(String.format("%,d",score));
                GameFrame.getHighScorePanel().setVisible(true);
                break;
            }
        }
        if(!isHighXP){
            GameFrame.getGameOverPanel().setVisible(true);
        }
        GameFrame.getBossPanel().stopAllTimer();
    }

    public void holdBlock(){
        if(!isUseHold){
            if(holdPanel.getBlock() == null){
                holdPanel.setBlock(TetrisPiece.getBlock(block.getName(),block.getColor()));
                holdPanel.getBlock().spawnTetris(gridCols);
                createBlock();
            }
            else{
                TetrisPiece temp;
                temp = holdPanel.getBlock();
                holdPanel.setBlock(TetrisPiece.getBlock(block.getName(),block.getColor()));
                block = temp;
                holdPanel.getBlock().spawnTetris(gridCols);
                resetTSpin();
            }
            isUseHold = true;
            holdPanel.repaint();
            repaint();
            GameFrame.playSE(3);
        }
    }

    public int wallKickTest(String direction,int xOffset,int yOffset){
        int x = block.getX() + xOffset;
        int y = block.getY() + yOffset;
        if(direction == "CW" && block.getName() != Tetris.I){
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
        else if(direction == "CT-CW" && block.getName() != Tetris.I){
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
        else if(direction == "CW" && block.getName() == Tetris.I){
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
            int[][] rotated = rotateShape(direction);
            int blockHeight = rotated.length;
            int blockWidth = rotated[0].length;
            boolean isPassCase = true;
            int xTest = testSet[i][0];
            int yTest = testSet[i][1];
            for (int row = 0; row < blockHeight; row++) {
                for (int col = 0; col < blockWidth; col++){
                    if(y+row+yTest >= 0 && y+row+yTest < gridRows && x+col+xTest >= 0 && x+col+xTest < gridCols){
                        if(block.getColor().equals(BlockTexture.Bubble.getColor())){
                            isPassCase = true;
                            break;
                        }
                        else if (rotated[row][col] == 1 && (backgroundBlock[y+row+yTest][x+col+xTest] != null && 
                                 backgroundBlock[y+row+yTest][x+col+xTest] != (slimePuddle))){
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

    public void rotate(String direction) {

        int xOffset = 0;
        int yOffset = 0;
        int rotateResult = 0;
        
        if(direction == "CW"){
            if(block.getName() != Tetris.O && block.getName() != Tetris.I){
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
            if(block.getName() == Tetris.I){
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
            if(block.getName() != Tetris.O && block.getName() != Tetris.I){
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
            if(block.getName() == Tetris.I){
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
                doTowerSlide();
                lastAction--;
                if(lastAction > 0)
                    resetLastTimer();
            }
            int newX = block.getX() + testSet[rotateResult][0] + xOffset;
            int newY = block.getY() + testSet[rotateResult][1] + yOffset;
            block.setPosition(newX, newY);
            block.setShape(rotateShape(direction));
            if(!isBottom() || canGo("Down"))
                doTowerSlide();
            repaint();
            checkSlime();
        }
    }

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
                    if(block.getColor().equals(BlockTexture.Bubble.getColor())){
                        return true;
                    }
                    else if (shape[row][col] == 1 && (backgroundBlock[y+row+yOffset][x+col+xOffset] != null && 
                             backgroundBlock[y+row+yOffset][x+col+xOffset] != (slimePuddle))){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void hardDrop(){    
        gravity.stopTimer();     
        int m = (lowestPoint()-block.getHeight()) - block.getY();
        if(m != 0){
            resetTSpin();
        }
        XPPanel.addHardDropXP(m);
        block.setPosition(block.getX(), lowestPoint()-block.getHeight());
        updateBackGround();
        if(block.getColor().equals(BlockTexture.Cloud.getColor())){
            GameFrame.playSE(11);
        }
        else{
            GameFrame.playSE(2);
        }
        checkFullLine();
        createBlock();
        gravity.stopLastTimer();
        gravity.restartTimer();
        repaint();    
    }

    public void lockAndSpawnBlock(){
        updateBackGround();
        if(block.getColor().equals(BlockTexture.Cloud.getColor())){
            GameFrame.playSE(11);
        }
        else{
            GameFrame.playSE(2);
        }
        checkFullLine();
        createBlock();
        gravity.stopLastTimer();
        gravity.restartTimer();
        repaint();       
    }

    public void moveRight(){
        if(block.getX() + block.getWidth() < gridCols && canGo("Right")){
            if(isBottom() || !canGo("Down")){
                doTowerSlide();
                lastAction--;
                if(lastAction > 0)
                    resetLastTimer();
            }
            block.moveRight();
            if(!isBottom() || canGo("Down"))
                doTowerSlide();
            repaint();
            checkSlime();
        }
    }

    public void moveLeft(){
        if(block.getX() > 0 && canGo("Left")){
            if(isBottom() || !canGo("Down")){
                doTowerSlide();
                lastAction--;
                if(lastAction > 0)
                    resetLastTimer();
            }
            block.moveLeft();
            if(!isBottom() || canGo("Down"))
                doTowerSlide();
            repaint();
            checkSlime();
        }
    }

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
            triggerLastTimer();
            repaint();
            checkSlime();
        }
        else{
            lockAndSpawnBlock();
        }
    }

    public void updateBackGround(){
        int x = block.getX();
        int y = block.getY();
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int[][] shape = block.getShape();
        Color color = block.getColor();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {             
                if(shape[row][col] == 1 && y+row < gridRows && x+col < gridCols && y+row >= 0 && x+col >= 0) {
                    if (block.getColor().equals(BlockTexture.Bubble.getColor())) {
                        if (backgroundBlock[y+row][x+col] == null ||
                            backgroundBlock[y+row][x+col] == (BlockTexture.PlacedBubble.getColor()) || 
                            backgroundBlock[y+row][x+col] == (BlockTexture.FadedBubble.getColor()) || 
                            backgroundBlock[y+row][x+col] == slimePuddle ) {
                            backgroundBlock[y+row][x+col] = color;              
                        }
                    }      
                else {
                    backgroundBlock[y+row][x+col] = color; 
                    }  
                }   
            }
        }
    }

    public void activeSpecialBlock() {
        Color color;
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                color = backgroundBlock[row][col];
                if(color != null){

                    if(color.equals(BlockTexture.Dynamite.getColor())){
                        backgroundBlock[row][col] = BlockTexture.PlacedDynamite.getColor();
                    }

                    else if(color.equals(BlockTexture.PlacedDynamite.getColor())){
                        backgroundBlock[row][col] = BlockTexture.PrimeDynamite.getColor();
                    }

                    else if(color.equals(BlockTexture.PrimeDynamite.getColor())){
                        for (int k = -radius; k <= radius; k++) {
                            for (int l = -radius; l <= radius; l++) {
                                if(row+k < gridRows && col+l < gridCols && row+k >= 0 && col+l >= 0){
                                    if (backgroundBlock[row+k][col+l] != null && 
                                        backgroundBlock[row+k][col+l] != BlockTexture.Dynamite.getColor() && 
                                        backgroundBlock[row+k][col+l] != BlockTexture.PlacedDynamite.getColor() && 
                                        backgroundBlock[row+k][col+l] != BlockTexture.PrimeDynamite.getColor())
                                        backgroundBlock[row+k][col+l] = null;
                                }
                            }
                        }
                        backgroundBlock[row][col] = null;
                        
                        if(!isExploded){
                            GameFrame.playSE(10);
                            isExploded = true;
                        }

                        BossPanel bossPanel = GameFrame.getBossPanel();
                        bossPanel.damageToBoss(dynamiteDamage);
        
                    }

                    else if(color.equals(BlockTexture.Bubble.getColor())){
                        backgroundBlock[row][col] = BlockTexture.PlacedBubble.getColor();
                    }

                    else if(color.equals(BlockTexture.PlacedBubble.getColor())){
                        backgroundBlock[row][col] = BlockTexture.FadedBubble.getColor();
                    }

                    else if(color.equals(BlockTexture.FadedBubble.getColor())){
                        backgroundBlock[row][col] = null;
                    }
                }
            }
        }
    }
    
    public void applySandGravity() {
        Color color;
        for (int row = gridRows-1; row >= 0; row--) {
            for (int col = 0; col < gridCols; col++) {
                color = backgroundBlock[row][col];
                if (color != null && color.equals(sand)){
                    int tempRow = row;
                    while(tempRow+1 < gridRows){
                        if(col+1 < gridCols && (backgroundBlock[tempRow][col+1] == slimeBlock)){
                            break;
                        }
                        else if(col-1 > 0 && (backgroundBlock[tempRow][col-1] == slimeBlock)){
                            break;
                        }
                        else if(tempRow-1 > 0 && (backgroundBlock[tempRow-1][col] == slimeBlock)){
                            break;
                        }
                        else if(backgroundBlock[tempRow+1][col] == null || backgroundBlock[tempRow+1][col].equals(slimePuddle)){
                                backgroundBlock[tempRow][col] = null;
                                backgroundBlock[tempRow+1][col] = sand;
                                tempRow++;
                        }
                        else{
                            break;
                        }
                    }
                }
            }
        }
        if(block != null){
            checkFullLine();
        }
        repaint();
    }

    public void checkSlime(){
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int[][] shape = block.getShape();
        Boolean isStuck = false;
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                //paint where block == 1
                if(shape[row][col] == 1 && !isStuck){
                    int x = block.getX() + col;
                    int y = block.getY() + row;
                    if(block.getColor() != honey){
                        if(x < gridCols && y < gridRows && y >= 0 && backgroundBlock[y][x] == slimePuddle){
                            hardDrop();
                            isStuck = true;
                        }
                        else if(x < gridCols && y + 1 < gridRows && y+1 >= 0 && (backgroundBlock[y + 1][x] == slimeBlock)){
                            lockAndSpawnBlock();
                            isStuck = true;
                        }
                        else if(x + 1 < gridCols && y < gridRows && y >= 0 && (backgroundBlock[y][x + 1] == slimeBlock)){
                            lockAndSpawnBlock();
                            isStuck = true;
                        }
                        else if(x < gridCols && y - 1 >= 0 && (backgroundBlock[y - 1][x] == slimeBlock)){
                            lockAndSpawnBlock();
                            isStuck = true;
                        }
                        else if(x - 1 >= 0 && y < gridRows && y >= 0 && (backgroundBlock[y][x - 1] == slimeBlock)){
                            lockAndSpawnBlock();
                            isStuck = true;
                        }
                    }
                    else{
                        if(x < gridCols && y < gridRows && y >= 0 && backgroundBlock[y][x] == slimePuddle){
                            hardDrop();
                            isStuck = true;
                        }
                        else if(x < gridCols && y + 1 < gridRows && y+1 >= 0 && (backgroundBlock[y + 1][x] == slimeBlock || backgroundBlock[y + 1][x] == honey)){
                            lockAndSpawnBlock();
                            isStuck = true;
                        }
                        else if(x + 1 < gridCols && y < gridRows && y >= 0 && (backgroundBlock[y][x + 1] == slimeBlock || backgroundBlock[y][x + 1] == honey)){
                            lockAndSpawnBlock();
                            isStuck = true;
                        }
                        else if(x < gridCols && y - 1 >= 0 && (backgroundBlock[y - 1][x] == slimeBlock || backgroundBlock[y - 1][x] == honey)){
                            lockAndSpawnBlock();
                            isStuck = true;
                        }
                        else if(x - 1 >= 0 && y < gridRows && y >= 0 && (backgroundBlock[y][x - 1] == slimeBlock || backgroundBlock[y][x - 1] == honey)){
                            lockAndSpawnBlock();
                            isStuck = true;
                        }
                    }
                }

            }
        }
        repaint();
    }

    public boolean haveBlock(int r,int c){
        int x = block.getX();
        int y = block.getY();
        if (y+r < gridRows && x+c < gridCols && y+r >= 0 && x+c >= 0 && (backgroundBlock[y+r][x+c] == null || backgroundBlock[y+r][x+c] == slimePuddle)){
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

    public void checkFullLine(){
        if(block.getName() == Tetris.T && isRotated){
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
                if(backgroundBlock[row][col] == null || backgroundBlock[row][col] == slimePuddle){
                    isFullRow = false;
                    break;
                }
            }
            if(isFullRow){
                fullLineAmount++;
                //GoalPanel.addGoal();
                //GoalPanel.getGoalScore().setText(""+GoalPanel.getGoal());
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackgroundImage(g);
        drawBackgroundBrightness(g);
        drawGridLine(g);
        drawPhantomBlock(g);
        drawPile(g);
        drawSlime(g);
        drawBlock(g);
        drawBossAttack(g);
        drawSnow(g);
        drawShadow(g);
        drawBorderImage(g);
        repaintPauseAndReward(g);
    }

    private void drawBackgroundImage(Graphics g){
        BossPanel bossPanel = GameFrame.getBossPanel();
        Image BGImage = new ImageIcon(bossPanel.getStage().getPlayZoneBGImagePath()).getImage();
        g.drawImage(BGImage, 0, 0, null);
    }

    private void drawBackgroundBrightness(Graphics g){
        int brightness = (int)(255 * (1 - this.brightness));
        g.setColor(new Color(0,0,0,brightness));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawBorderImage(Graphics g) {
        BossPanel bossPanel = GameFrame.getBossPanel();
        Image BGImage = new ImageIcon(bossPanel.getStage().getPlayZoneBorderImagePath()).getImage();
        g.drawImage(BGImage, 0, 0, null);
    }

    private void drawGridLine(Graphics g){
        Image BorderImage = new ImageIcon("Assets/Image/Background/PlayZone/Grid.png").getImage();
        g.drawImage(BorderImage, 0, 0, null);
    }

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
    
    private void drawPile(Graphics g){
        Color color;
        char[][] isAround = {{'0','0','0'},{'0','0','0'},{'0','0','0'}};
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                color = backgroundBlock[row][col];
                if(color != null){
                    for(int offRow = -1;offRow <= 1;offRow++){
                        for (int offCol = -1; offCol <= 1; offCol++) {
                            if(col+offCol>= 0 && col+offCol < gridCols && row+offRow >= 0 && row+offRow < gridRows){
                                Color offBGColor = backgroundBlock[row+offRow][col+offCol];
                                if(offBGColor != null){
                                    if(offBGColor.equals(color)){
                                        isAround[offRow+1][offCol+1] = '1';
                                    }
                                }
                            }
                        }
                    }
                    int x = col * blockSize; //coordinate + offset
                    int y = row * blockSize; //coordinate + offset
                    TetrisTexture.mergePileTexture(g, color, x, y, isAround); 
                    isAround = new char[][]{{'0','0','0'},{'0','0','0'},{'0','0','0'}};
                }
            }
        }
    }

    private void drawSlime(Graphics g){
        Color color;
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                color = backgroundBlock[row][col];
                if(color != null){
                    int x = col * blockSize; //coordinate + offset
                    int y = row * blockSize; //coordinate + offset
                    if(row + 1 < gridRows){
                        if (color == slimePuddle && (backgroundBlock[row+1][col] == null || backgroundBlock[row+1][col] == slimePuddle)){
                            color = null;
                        }
                    }
                    TetrisTexture.drawPuddleTexture(g, color, x, y);  
                }
            }
        }
    }

    private void drawBlock(Graphics g){
        Color color = block.getColor();
        char[][] isAround = {{'0','0','0'},{'0','0','0'},{'0','0','0'}};
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int[][] shape = block.getShape();
        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                //paint where block == 1
                if(shape[row][col] == 1){
                    int x = block.getX() + col;
                    int y = block.getY() + row;
                    for(int offRow = -1;offRow <= 1;offRow++){
                        for (int offCol = -1; offCol <= 1; offCol++) {
                            if(col+offCol >= 0 && col+offCol < blockWidth && row+offRow >= 0 && row+offRow < blockHeight){
                                if(shape[row+offRow][col+offCol] == 1){
                                    isAround[offRow+1][offCol+1] = '1';
                                }
                            }
                        }
                    }
                    x *= blockSize; //coordinate + offset
                    y *= blockSize; //coordinate + offset
                    TetrisTexture.mergePileTexture(g, color, x, y, isAround); 
                    //TetrisTexture.drawBlockTexture(g,block.getName(),block.getVariant(),block.getX()*blockSize,block.getY()*blockSize);
                    isAround = new char[][]{{'0','0','0'},{'0','0','0'},{'0','0','0'}};
                }   
            }
        }
        if(BossAttack.getConfuseCount()>0){
            Image bossAttackImage = new ImageIcon("Assets/Image/Background/Confuse.png").getImage();
            g.drawImage(bossAttackImage, (((block.getX())*25)+(block.getWidth()*25)/2)-8 , (block.getY()-1)*25-5, null); 
        }
    }

    private void drawBossAttack(Graphics g){
        BossAttack.drawBossAttack(g);
    }

    private void drawSnow(Graphics g){    
        BossPanel bossPanel = GameFrame.getBossPanel();
        if(bossPanel.getStage() == Theme.Snow || bossPanel.getStage() == Theme.DeerClops ){
            Image snowImage = new ImageIcon("Assets/Image/Background/SnowFall_" + snowFallFrame + ".png").getImage();
            g.drawImage(snowImage ,0, 0,null);
        }
    }

    private void drawShadow(Graphics g){
        BossPanel bossPanel = GameFrame.getBossPanel();
        int radius;
        int darkness;
        if (bossPanel.getStage() == Theme.Night || bossPanel.getStage() == Theme.EyeOfCthulhu || bossPanel.getStage() == Theme.Dungeon || bossPanel.getStage() == Theme.Skeletron){
            radius = gridRows;
            darkness = 4;
            int blockHeight = block.getHeight();
            int blockWidth = block.getWidth();
            int[][] shape = block.getShape();
            int x = block.getX();
            int y = block.getY();
    
            for (int row = 0; row < blockHeight; row++) {
                for (int col = 0; col < blockWidth; col++) {
                    if(shape[row][col] == 1){
                        for (int k = -radius; k <= radius; k++) {
                            for (int l = -radius; l <= radius; l++) {
                                if(!(k==0 && l==0)){
                                    g.setColor(new Color(0, 0,0,(Math.abs(k)+Math.abs(l))*darkness+blindness));
                                    g.fillRect((x+col+l)*blockSize,(y+row+k)*blockSize, blockSize , blockSize);
                                }
                            }
                        }
                    }
                }
            }
        }
        else if(bossPanel.getStage() == Theme.Snow || bossPanel.getStage() == Theme.DeerClops ){
            if(bossPanel.getBoss() != null){
                radius = 20 + (bossPanel.getBoss().getState()-1)*3;
                darkness = bossPanel.getBoss().getState()-1;
            }
            else{
                radius = 20;
                darkness = 0;
            }
            for (int row = 0; row < radius; row++) {
                if(row > 19){
                    break;
                }
                g.setColor(new Color(255, 255, 255, (150 + 50*darkness)/radius*(radius-row)));
                g.fillRect(0,row*25,250,25);
            }
        }
    }

    private void repaintPauseAndReward(Graphics g) {
        RewardPanel rewardPanel = GameFrame.getRewardPanel();
        rewardPanel.repaint();
        GameOverPanel gameOverPanel = GameFrame.getGameOverPanel();
        gameOverPanel.repaint();
    }

    public TetrisPiece getBlock() {
        return block;
    }

    public int getBlindness() {
        return blindness;
    }

    public void setBlindness(int blindness) {
        PlayZone.blindness = blindness;
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

    public static Gravity getGravity() {
        return gravity;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void addRadius() {
        radius++;
    }

    public int getDynamiteDamage() {
        return dynamiteDamage;
    }

    public void setDynamiteDamage(int dynamiteDamage) {
        this.dynamiteDamage = dynamiteDamage;
    }

    public void addDynamiteDamage() {
        dynamiteDamage += 200;
    }

    public float getCloudGravityScale() {
        return cloudGravityScale;
    }

    public void setCloudGravityScale(int cloudGravityScale) {
        this.cloudGravityScale = cloudGravityScale;
    }

    public void addCloudGravityScale() {
        cloudGravityScale++;
    }

    public void setNextHoneyBlock() {
        textureQueue.set(0, BlockTexture.Honey);
        NextPanel nextPanel = GameFrame.getNextPanel();
        nextPanel.setBlock(getNextPiece());
        nextPanel.repaint();
    }

    public void setHoldHoneyBlock() {
        HoldPanel holdPanel = GameFrame.getHoldPanel();
        holdPanel.setBlock(TetrisPiece.getBlock(holdPanel.getBlock().getName(),honey));
        holdPanel.getBlock().spawnTetris(gridCols);
        holdPanel.repaint();
    }

    public void swapHoldAndNext(){
        HoldPanel holdPanel = GameFrame.getHoldPanel();
        NextPanel nextPanel = GameFrame.getNextPanel();

        Tetris nextBlock = holdPanel.getBlock().getName();
        BlockTexture nextTexture = BlockTexture.colorToBlockTexture(holdPanel.getBlock().getColor());


        Tetris holdBlock = nextPanel.getBlock().getName();
        Color holdColor = nextPanel.getBlock().getColor();

        blockQueue.set(0, nextBlock);
        textureQueue.set(0, nextTexture);
        nextPanel.setBlock(getNextPiece());
        nextPanel.repaint();

        if(isUseHold){
            holdPanel.setBlock(TetrisPiece.getBlock(holdBlock,holdColor));
            isUseHold = true;
        }
        else{
            holdPanel.setBlock(TetrisPiece.getBlock(holdBlock,holdColor));
        }
        holdPanel.repaint();
    }

    public int getSnowFallFrame() {
        return snowFallFrame;
    }


    public void setSnowFallFrame(int snowFallFrame) {
        PlayZone.snowFallFrame = snowFallFrame;
    }
    
}