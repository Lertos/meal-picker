package com.lertos.mealpicker.model;

import java.util.ArrayList;

public class DataManager {

    public static DataManager instance;
    private ArrayList<String> tagsTimeToMake = new ArrayList<>();

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    public void initialLoad() {
        tagsTimeToMake.add("Instant");
        tagsTimeToMake.add("Short");
        tagsTimeToMake.add("Medium");
        tagsTimeToMake.add("Long");
        tagsTimeToMake.add("Very Long");
    }

    public ArrayList<String> getTagsTimeToMake() {
        return tagsTimeToMake;
    }

}