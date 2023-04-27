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
    SlimeBlock,
    Honey,
    Bone;

    private Color color;

    private static final BlockTexture[] vals = values();

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

    public static BlockTexture colorToBlockTexture(Color color) {
        for(int i = 0; i < vals.length; i++){
            if(vals[i].getColor() == color){
                return vals[i];
            }
        }
        return null;
    }
    
}
