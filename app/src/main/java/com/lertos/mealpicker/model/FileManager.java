package com.lertos.mealpicker.model;

import android.content.Context;

public class FileManager {
    private DataFile settingsFile;
    private DataFile tagFile;
    private DataFile mealFile;

    public FileManager(Context context) {
        this.settingsFile = new DataFile(context, "settings.ser");
        this.tagFile = new DataFile(context, "tags.ser");
        this.mealFile = new DataFile(context, "meals.ser");
    }

    public DataFile getSettingsFile() {
        return settingsFile;
    }

    public DataFile getTagFile() {
        return tagFile;
    }

    public DataFile getMealFile() {
        return mealFile;
    }
}
