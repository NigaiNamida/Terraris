import java.awt.Color;

public enum BlockTexture {
    Grass,
    Stone,
    Sand,
    Dynamite,
    PlacedDynamite,
    PrimeDynamite,
    Bubble,
    PlacedBubble,
    FadedBubble,
    Cloud,
    SlimePuddle,
    SlimeBlock;

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

    public static BlockTexture[] getSpecialTexture() {
        return new BlockTexture[]{Sand,Cloud,Dynamite,Bubble};
    }
    
}
