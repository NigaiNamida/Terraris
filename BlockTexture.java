import java.awt.Color;

public enum BlockTexture {
    Grass,
    Stone,
    Sand;

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

    public static BlockTexture[] getNormalTexture(){
        return new BlockTexture[]{Grass,Stone};
    }
    
}
