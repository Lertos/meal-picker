package com.lertos.mealpicker.model;

import android.content.Context;

public class FileManager {
    private final Context context;
    private DataFile settingsFile;
    private DataFile tagFile;
    private DataFile mealFile;

    public FileManager(Context context) {
        this.context = context;

        this.settingsFile = new DataFile(context, "settings.txt");
        this.tagFile = new DataFile(context, "tags.txt");
        this.mealFile = new DataFile(context, "meals.txt");
    }

    private void createFileWithDefaults() {

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
