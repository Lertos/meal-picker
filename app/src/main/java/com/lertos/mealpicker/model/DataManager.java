package com.lertos.mealpicker.model;

public class DataManager {

    public static DataManager instance;
    private TagManager tagManager = new TagManager();
    //This setting is to reload the settings page since the activity restarts after switching themes; yuck!
    private boolean changedDayNightTheme = false;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    public TagManager getTags() {
        return tagManager;
    }

    public boolean hasChangedDayNightTheme() {
        return changedDayNightTheme;
    }

    public void setChangedDayNightTheme(boolean changedDayNightTheme) {
        this.changedDayNightTheme = changedDayNightTheme;
    }
}
