package com.lertos.mealpicker.model;

import android.content.Context;

public class DataManager {

    public static DataManager instance;
    private FileManager fileManager;
    private Settings settings;
    private TagManager tagManager;
    private MealManager mealManager;
    //This setting is to reload the settings page since the activity restarts after switching themes; yuck!
    private boolean changedDayNightTheme = false;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    public void initialLoad(Context context) {
        fileManager = new FileManager(context);

        //TODO: Get all the data from the file; or serve defaults
        settings = new Settings();
        tagManager = new TagManager();
        mealManager = new MealManager();
    }

    public FileManager getFile() {
        return fileManager;
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
