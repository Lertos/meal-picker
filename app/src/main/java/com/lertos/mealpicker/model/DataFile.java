package com.lertos.mealpicker.model;

import java.util.ArrayList;
import java.util.List;

public class DataFile {

    private final String fileName;

    public DataFile(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean saveFile(List<?> list) {
        return false;
    }

    public List<?> loadFile(List<?> list) {
        return new ArrayList<String>();
    }
}
