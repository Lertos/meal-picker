package com.lertos.mealpicker.model;

public class Settings {

    private boolean useMinutesOnly = false;
    private boolean useDarkMode = false;
    private boolean closeKeyboardAfterTagCreation = false;
    private boolean resetFieldsAfterMealCreation = false;

    public Settings() {
        loadSettings();
    }

    private void loadSettings() {
        //TODO: Load settings from the local config file/create new file if it doesn't exist with defaults
    }

    public boolean useMinutesOnly() {
        return useMinutesOnly;
    }

    public void setUseMinutesOnly(boolean useMinutesOnly) {
        this.useMinutesOnly = useMinutesOnly;
    }

    public boolean useDarkMode() {
        return useDarkMode;
    }

    public void setUseDarkMode(boolean useDarkMode) {
        this.useDarkMode = useDarkMode;
    }

    public boolean useCloseKeyboardAfterTagCreation() {
        return closeKeyboardAfterTagCreation;
    }

    public void setCloseKeyboardAfterTagCreation(boolean closeKeyboardAfterTagCreation) {
        this.closeKeyboardAfterTagCreation = closeKeyboardAfterTagCreation;
    }

    public boolean useResetFieldsAfterMealCreation() {
        return resetFieldsAfterMealCreation;
    }

    public void setResetFieldsAfterMealCreation(boolean resetFieldsAfterMealCreation) {
        this.resetFieldsAfterMealCreation = resetFieldsAfterMealCreation;
    }
}
