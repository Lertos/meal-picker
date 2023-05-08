package com.lertos.mealpicker.model;

public class DataManager {

    public static DataManager instance;
    private Settings settings = new Settings();
    private TagManager tagManager = new TagManager();
    private MealManager mealManager = new MealManager();
    //This setting is to reload the settings page since the activity restarts after switching themes; yuck!
    private boolean changedDayNightTheme = false;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    public Settings getSettings() {
        return settings;
    }

    public TagManager getTags() {
        return tagManager;
    }

    public MealManager getMeals() {
        return mealManager;
    }

    public boolean hasChangedDayNightTheme() {
        return changedDayNightTheme;
    }

    public void setChangedDayNightTheme(boolean changedDayNightTheme) {
        this.changedDayNightTheme = changedDayNightTheme;
    }
}
