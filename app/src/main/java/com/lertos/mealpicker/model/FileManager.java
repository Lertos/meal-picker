package com.lertos.mealpicker.model;

import android.content.Context;

public class FileManager {

    private final String fileName = "data.txt";
    private final Context context;

    public FileManager(Context context) {
        this.context = context;
    }

}
