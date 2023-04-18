import java.awt.Color;

public enum BlockTexture {
    Grass,
    Stone,
    Sand,
    Dynamite,
    PrimeDynamite,
    Cloud;

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
        //return new BlockTexture[]{Grass,Grass,Grass,Grass,Stone,Stone,Stone,Stone,Sand,Dynamite};
        return new BlockTexture[]{Grass,Grass,Grass,Grass,Dynamite};
    }
    
}
