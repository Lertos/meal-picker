package com.lertos.mealpicker.model;

import java.util.ArrayList;

public class DataManager {

    public static DataManager instance;
    //TODO: Replace with a TagManager class
    private ArrayList<String> tagsTimeToMake = new ArrayList<>();
    private ArrayList<String> tagsDifficulty = new ArrayList<>();
    private ArrayList<String> tagsMealType = new ArrayList<>();

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    public void initialLoad() {
        //TODO: Replace with a TagManager class
        tagsTimeToMake.add("Instant");
        tagsTimeToMake.add("Short");
        tagsTimeToMake.add("Medium");
        tagsTimeToMake.add("Long");
        tagsTimeToMake.add("Very Long");

        tagsDifficulty.add("Easy");
        tagsDifficulty.add("Normal");
        tagsDifficulty.add("Hard");

        tagsMealType.add("Breakfast");
        tagsMealType.add("Lunch");
        tagsMealType.add("Supper");
        tagsMealType.add("Snack");
    }

    public ArrayList<String> getTagsTimeToMake() {
        return tagsTimeToMake;
    }

    public ArrayList<String> getTagsDifficulty() {
        return tagsDifficulty;
    }

    public ArrayList<String> getTagsMealType() {
        return tagsMealType;
    }

}
