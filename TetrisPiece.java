import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TetrisPiece {
    private static Tetris[] blockSet = Tetris.values();
    private static BlockTexture[] textureSet = BlockTexture.getNormalTexture();
    private static ArrayList<BlockTexture> addedTextureSet = new ArrayList<>();
    private static ArrayList<BlockTexture> specialTextureSet = new ArrayList<>();
    private Tetris name;
    private int[][] shape;
    private Color color;
    private int x;
    private int y;
    private int variant;

    public TetrisPiece(Tetris name,int[][] shape,Color color){
        variant = 0;
        this.name = name;
        this.shape = shape;
        this.color = color;
    }

    public static TetrisPiece getBlock(Tetris tetris,Color color){
        int[][] blockShape = getBlockShape(tetris);
        return new TetrisPiece(tetris,blockShape,color);
    }

    public static TetrisPiece getBlock(Tetris tetris,BlockTexture texture){
        int[][] blockShape = getBlockShape(tetris);
        return new TetrisPiece(tetris,blockShape,texture.getColor());
    }

    private static int[][] getBlockShape(Tetris tetris){
        if(tetris == Tetris.I)
            return new int[][]{{1,1,1,1}};
        if(tetris == Tetris.O)
            return new int[][]{{1,1},{1,1}};
        if(tetris == Tetris.S)
            return new int[][]{{0,1,1},{1,1,0}};
        if(tetris == Tetris.Z)
            return new int[][]{{1,1,0},{0,1,1}};
        if(tetris == Tetris.T)
            return new int[][]{{0,1,0},{1,1,1}};
        if(tetris == Tetris.L)
            return new int[][]{{0,0,1},{1,1,1}};
        return new int[][]{{1,0,0},{1,1,1}};
    }

    public static void UnlockSpecialTexture(BlockTexture texture){
        specialTextureSet.add(texture);
    }

    public static void addTexture(ArrayList<BlockTexture> addedQueue){
		addedTextureSet.addAll(addedQueue);
    }

    public static void removeTextureIndex(int removeQueue) {
        addedTextureSet.remove(removeQueue);
    }

    public static void removeTextureBlock(BlockTexture removeQueue) {
        addedTextureSet.remove(removeQueue);
    }

    public static void queueTexture(ArrayList<BlockTexture> textureQueue,boolean reset){
        if(reset){
            textureQueue.clear();
            addedTextureSet = new ArrayList<>();
            specialTextureSet = new ArrayList<>();
        }
		List<BlockTexture> textureList = Arrays.asList(textureSet);
        List<BlockTexture> addedTextureList = addedTextureSet;
        List<BlockTexture> combinedTextureList = new ArrayList<BlockTexture>();
		combinedTextureList.addAll(textureList);
        combinedTextureList.addAll(addedTextureList);
        Collections.shuffle(combinedTextureList);
        textureQueue.addAll(combinedTextureList);
    }

    public static void queueBlock(ArrayList<Tetris> blockQueue,boolean reset){
        if(reset){
            blockQueue.clear();
        }
		List<Tetris> blockList = Arrays.asList(blockSet);
		Collections.shuffle(blockList);
        blockQueue.addAll(blockList);
    }

    public void spawnTetris(int gameColumns){
        x = (gameColumns-getWidth())/2;
        y = name == Tetris.I ? 1 : 0;
    }

    public void setPosition(int x,int y){
        this.x = x;
        this.y = y;
    }

    public Tetris getName(){
        return name;
    }

    public int[][] getShape(){
        return shape;
    }

    public Color getColor(){
        return color;
    }

    public int getWidth(){
        return shape[0].length;
    }

    public int getHeight(){
        return shape.length;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void moveDown(){
        y++;
    }

    public void moveRight(){
        x++;
    }

    public void moveLeft(){
        x--;
    }

    public int getVariant() {
        return variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }

    public void setShape(int[][] shape){
        this.shape = shape;
    }

    @Override
    public String toString() {
        return ""+name;
    }

    public static boolean containSpecialTexture(BlockTexture texture) {
        return specialTextureSet.contains(texture);
    }

    public static ArrayList<BlockTexture> getAddedTextureSet() {
        return addedTextureSet;
    }
}