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

    private String BGMusicPath;

    private String BGPath;

    private String playZoneBGImagePath;
    private String BossPanelBGImagePath;
    private String DataPanelBGImagePath;

    private String playZoneBorderImagePath;
    private String BossPanelBorderImagePath;
    private String DataPanelBorderImagePath;

    private static final Theme[] vals = values();
    
    private Theme(){
        BGMusicPath = "Assets/Sound/" + this + ".wav";

        BGPath =  "Assets/Image/Background/";

        playZoneBGImagePath = BGPath + "PlayZone/" + this + ".png";
        BossPanelBGImagePath = BGPath + "BossPanel/" + this + ".png";
        DataPanelBGImagePath = BGPath + "DataPanel/" + Stage() + ".png";

        playZoneBorderImagePath = BGPath + "PlayZone/" + Stage() + "_Border.png";
        BossPanelBorderImagePath = BGPath + "BossPanel/" + Stage() + "_Border.png";
        DataPanelBorderImagePath = BGPath + "DataPanel/" + Stage() + "_Border.png";
    }

    public String Stage() {
        String stage = "";
        switch (this.ordinal()) {
            case 1:
            case 2:
            case 3:
            case 4:
                stage = "Normal";
                break;
            case 5:
            case 6:
                stage = "Corruption";
                break;
            case 7:
            case 8:
                stage = "Crimson";
                break;
            case 9:
            case 10:
                stage = "Jungle";
                break;
            case 11:
            case 12:
                stage = "Snow";
                break;
            case 13:
            case 14:
                stage = "Dungeon";
                break;
            case 15:
            case 16:
                stage = "Underworld";
                break;
            default:
                break;
        }
        return stage;
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
            previous = WallOfFlesh;
        }
        return vals[(this.ordinal() - 1) % vals.length];
    }

    public String getBGMusicPath() {
        return BGMusicPath;
    }

    public String getPlayZoneBGImagePath() {
        return playZoneBGImagePath;
    }

    public String getBossPanelBGImagePath() {
        return BossPanelBGImagePath;
    }

    public String getDataPanelBGImagePath() {
        return DataPanelBGImagePath;
    }

    public String getPlayZoneBorderImagePath() {
        return playZoneBorderImagePath;
    }

    public String getBossPanelBorderImagePath() {
        return BossPanelBorderImagePath;
    }

    public String getDataPanelBorderImagePath() {
        return DataPanelBorderImagePath;
    }
    
}
