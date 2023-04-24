public enum Theme {
    Title,
    Day,
    KingSlime,
    Night,
    EyeOfCthulhu,
    Corruption,
    EaterOfWorld,
    Crimson,
    BrainOfCthulhu,
    Jungle,
    QueenBee,
    Snow,
    DeerClops,
    Dungeon,
    Skeletron,
    UnderWorld,
    WallOfFlesh;

    private String BGImagePath;
    private String BGMusicPath;
    private static final Theme[] vals = values();
    
    private Theme(){
        BGImagePath = "Assets/Image/Background/PlayZone/" + this + ".png";
        BGMusicPath = "Assets/Sound/" + this + ".wav";
    }

    public Theme next() {
        Theme next = vals[(this.ordinal() + 1) % vals.length];
        if(next == Title){
            next = Day;
        }
        return next;
    }

    public Theme previous() {
        Theme previous = vals[(this.ordinal() - 1) % vals.length];
        if(previous == Title){
            previous = Corruption;
        }
        return vals[(this.ordinal() - 1) % vals.length];
    }

    public String getBGImagePath() {
        return BGImagePath;
    }

    public String getBGMusicPath() {
        return BGMusicPath;
    }
}
