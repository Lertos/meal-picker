package com.lertos.mealpicker.model;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

public class FileManager {
    private final Context context;
    private DataFile settingsFile = new DataFile("settings.txt");
    private DataFile tagFile = new DataFile("tags.txt");
    private DataFile mealFile = new DataFile("meals.txt");

    public FileManager(Context context) {
        this.context = context;
    }

    private boolean doesFileExist(String fileName) {
        List<String> files = Arrays.asList(context.fileList());

        if (files.contains(fileName))
            return true;
        return false;
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
