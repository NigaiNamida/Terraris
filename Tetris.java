import java.awt.Color;

public enum Tetris {
    O,
    T,
    I,
    L,
    J,
    S,
    Z;
    
    private Color color;

    private Tetris(){
        this.color = Color.RED;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
