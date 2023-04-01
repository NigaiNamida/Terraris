public enum Theme {
    Title,
    Day,
    Night,
    Crimson;

    private String BGImagePath;
    private String BGMusicPath;

    private Theme(){
        BGImagePath = "Assets/Image/Background/" + this + ".png";
        BGMusicPath = "Assets/Sound/" + this + ".wav";
    }

    public String getBGImagePath() {
        return BGImagePath;
    }

    public String getBGMusicPath() {
        return BGMusicPath;
    }
}
