import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class TetrisPiece {
    private static ArrayList<String> blockSet;
    private String name;
    private int[][] shape;
    private Color color;
    private int x;
    private int y;
    private int variant;

    public TetrisPiece(String name,int[][] shape,Color color){
        variant = 0;
        this.name = name;
        this.shape = shape;
        this.color = color;
    }

    public static TetrisPiece getBlock(String name){
        //NORMAL PALLETE
        switch (name) {
            case "I":
                return new TetrisPiece("I",new int[][]{{1,1,1,1}},new Color(47,154,254,0));
            case "O":
                return new TetrisPiece("O",new int[][]{{1,1},{1,1}},new Color(254,174,1,0));
            case "S":
                return new TetrisPiece("S",new int[][]{{0,1,1},{1,1,0}},new Color(14,167,14,0));
            case "Z":
                return new TetrisPiece("Z",new int[][]{{1,1,0},{0,1,1}},new Color(221,43,46,0));
            case "T":
                return new TetrisPiece("T",new int[][]{{0,1,0},{1,1,1}},new Color(138,87,189,0));
            case "L":
                return new TetrisPiece("L",new int[][]{{0,0,1},{1,1,1}},new Color(244,95,6,0));
            default:
                return new TetrisPiece("J",new int[][]{{1,0,0},{1,1,1}},new Color(44,87,174,0));
        }
    }

    public static void queueBlock(ArrayList<String> blockQueue,boolean reset){
        if(blockSet == null){
            blockSet = new ArrayList<String>();
        }
        if(reset){
            blockQueue.clear();
            blockSet.clear();
        }
        if(blockSet.isEmpty()){
            blockSet.add("I");
            blockSet.add("O");
            blockSet.add("S");
            blockSet.add("Z");
            blockSet.add("T");
            blockSet.add("L");
            blockSet.add("J");
        }
        Collections.shuffle(blockSet);
        for (int i = 0; i < blockSet.size(); i++) {
            blockQueue.add(blockSet.get(i));
        }
    }

    public void spawnTetris(int gameColumns){
        x = (gameColumns-getWidth())/2;
        y = name == "I" ? 1 : 0;
    }

    public void setPosition(int x,int y){
        this.x = x;
        this.y = y;
    }

    public String getName(){
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
        return name;
    }
}