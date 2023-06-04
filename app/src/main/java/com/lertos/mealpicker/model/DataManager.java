package com.lertos.mealpicker.model;

import android.content.Context;

import com.lertos.mealpicker.Helper;

import java.util.List;

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

        //Check if dark theme should be applied
        Helper.setupDarkMode(settings.useDarkMode());
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

    public void saveSettings() {
        fileManager.getSettingsFile().saveToFile(settings);
    }

    public void saveTags() {
        fileManager.getTagFile().saveToFile(tagManager);
    }

    public void saveMeals() {
        fileManager.getMealFile().saveToFile(mealManager);
    }

    public boolean hasChangedDayNightTheme() {
        return changedDayNightTheme;
    }

    public void setChangedDayNightTheme(boolean changedDayNightTheme) {
        this.changedDayNightTheme = changedDayNightTheme;
    }

    //Removing the tag - meaning meals also need to update their data to replace the deleted tag with a new entry
    public void updateTag(EnumListType listType, int index, int newIndex) {
        List<String> tagList = getCorrectTagList(listType);

        //If the list is null or either index is out of bounds, return
        if (tagList == null || (index < 0 || index >= tagList.size() || newIndex < 0 || newIndex >= tagList.size()))
            return;

        //Iterate over all meals and update the tag reference
        String oldTag = tagList.get(index);
        String newTag = tagList.get(newIndex);

        mealManager.updateTagInMeals(listType, oldTag, newTag);

        saveMeals();
    }

    private List<String> getCorrectTagList(EnumListType listType) {
        switch (listType) {
            case TIME_TO_MAKE:
                return tagManager.getTagsTimeToMake();
            case DIFFICULTY:
                return tagManager.getTagsDifficulty();
            case MEAL_TYPE:
                return tagManager.getTagsMealType();
            case OTHER:
                return tagManager.getTagsOther();
        }
        return null;
    }
}
