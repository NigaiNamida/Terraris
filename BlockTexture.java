import java.awt.Color;

public enum BlockTexture {
    Grass,
    Stone,
    Sand,
    Sand_Lv1,
    Sand_Lv2,
    Sand_Lv3,
    Dynamite,
    Dynamite_Lv1,
    Dynamite_Lv2,
    Dynamite_Lv3,
    PlacedDynamite_Lv1,
    PlacedDynamite_Lv2,
    PlacedDynamite_Lv3,
    PrimeDynamite,
    PrimeDynamite_Lv1,
    PrimeDynamite_Lv2,
    PrimeDynamite_Lv3,
    Bubble,
    Bubble_Lv1,
    Bubble_Lv2,
    Bubble_Lv3,
    PlacedBubble,
    FadedBubble,
    Cloud,
    Cloud_Lv1,
    Cloud_Lv2,
    Cloud_Lv3,
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
        return new BlockTexture[]{Sand_Lv1,Cloud_Lv1,Dynamite_Lv1,Bubble_Lv1};
    }

    public static BlockTexture[] getUpgradableTexture() {
        return new BlockTexture[]{Sand_Lv1,Sand_Lv2,Cloud_Lv1,Cloud_Lv2,Dynamite_Lv1,Dynamite_Lv2,Bubble_Lv1,Bubble_Lv2};
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
