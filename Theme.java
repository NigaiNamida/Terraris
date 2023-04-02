public enum Theme {
    Title,
    Day,
    KingSlime,
    Night,
    EyeOfCthulhu,
    Corruption;

    private String BGImagePath;
    private String BGMusicPath;
    private static final Theme[] vals = values();
    
    private Theme(){
        BGImagePath = "Assets/Image/Background/" + this + ".png";
        BGMusicPath = "Assets/Sound/" + this + ".wav";
    }

    public Theme next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public Theme previous() {
        return vals[(this.ordinal() - 1) % vals.length];
    }

    public String getBGImagePath() {
        return BGImagePath;
    }

    public String getBGMusicPath() {
        return BGMusicPath;
    }
}
