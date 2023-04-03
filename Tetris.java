import java.awt.Color;

public enum Tetris {
    O(Color.YELLOW),
    T(Color.MAGENTA),
    I(Color.CYAN),
    L(Color.BLUE),
    J(Color.ORANGE),
    S(Color.GREEN),
    Z(Color.RED);
    
    private Color color;

    private Tetris(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
