package com.lertos.mealpicker.model;

public class Settings {

    private boolean useMinutesOnly;
    private boolean useDarkMode;
    private boolean closeKeyboardAfterTagCreation;
    private boolean resetFieldsAfterMealCreation;

    public Settings() {
        loadSettings();
    }

    private void loadSettings() {
        //TODO: Load settings from the local config file/create new file if it doesn't exist with defaults
    }

}
