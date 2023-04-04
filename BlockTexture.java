import java.awt.Color;

public enum BlockTexture {
    Grass,
    Stone;

    private Color color;

    private BlockTexture(){
        int n = this.ordinal()+1;
        color = new Color(n, n, n);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
}
