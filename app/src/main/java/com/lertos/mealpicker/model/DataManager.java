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

        //Load all of classes from saved data. If no save data exists, load defaults
        settings = fileManager.getSettingsFile().loadFromFile();

        if (settings == null) {
            settings = new Settings();
            fileManager.getSettingsFile().saveToFile(settings);
        }

        tagManager = fileManager.getTagFile().loadFromFile();

        if (tagManager == null) {
            tagManager = new TagManager();
            fileManager.getTagFile().saveToFile(tagManager);
        }

        mealManager = fileManager.getMealFile().loadFromFile();

        if (mealManager == null) {
            mealManager = new MealManager();
            fileManager.getMealFile().saveToFile(mealManager);
        }
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
