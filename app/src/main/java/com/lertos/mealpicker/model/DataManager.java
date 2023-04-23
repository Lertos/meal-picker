package com.lertos.mealpicker.model;

public class DataManager {

    public static DataManager instance;
    private TagManager tagManager = new TagManager();

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    public TagManager getTags() {
        return tagManager;
    }

}
