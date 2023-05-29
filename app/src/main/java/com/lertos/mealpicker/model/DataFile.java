package com.lertos.mealpicker.model;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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

    public <T extends Serializable> void saveToFile(T obj) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            ObjectOutput out = new ObjectOutputStream(fos);

            out.writeObject(obj);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.d(fileName, "FILE SAVED");
    }

    public <T extends Serializable> T loadFromFile() {
        T obj;

        try (FileInputStream fis = context.openFileInput(fileName)) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            obj = (T) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            Log.d(fileName, "CLASS NOT FOUND; LOADING DEFAULT");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(fileName, "IOException; LOADING DEFAULT");
            return null;
        }
        Log.d(fileName, "FILE LOADED");

        return obj;
    }
}

