package com.lertos.mealpicker.model;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class DataFile {

    private final String fileName;
    private final Context context;

    public DataFile(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;

        if (!doesFileExist())
            createFile();
    }

    private boolean doesFileExist() {
        String[] files = context.fileList();

        for (String file : files) {
            if (file.equalsIgnoreCase(fileName))
                return true;
        }
        return false;
    }

    private void createFile() {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write("".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.d(fileName, "FILE CREATED");
    }

    public <T extends Serializable> boolean saveToFile(T obj) {
        return false;
    }

    public <T extends Serializable> T loadFromFile() {
        return null;
    }
}

