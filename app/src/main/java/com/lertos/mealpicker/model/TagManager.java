package com.lertos.mealpicker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TagManager implements Serializable {

    //Tags are essentially just Strings that are in different lists which serve different purposes
    private ArrayList<String> tagsTimeToMake;
    private ArrayList<String> tagsDifficulty;
    private ArrayList<String> tagsMealType;
    private ArrayList<String> tagsOther;

    public TagManager() {
        tagsTimeToMake = new ArrayList<>();
        tagsDifficulty = new ArrayList<>();
        tagsMealType = new ArrayList<>();
        tagsOther = new ArrayList<>();

        //Providing defaults that most people would use
        addDefaults();
    }

    private void addDefaults() {
        addTimeToMakeDefaults();
        addDifficultyDefaults();
        addMealTypeDefaults();
        addOtherDefaults();
    }

    private void addTimeToMakeDefaults() {
        tagsTimeToMake.add("Instant");
        tagsTimeToMake.add("Short");
        tagsTimeToMake.add("Medium");
        tagsTimeToMake.add("Long");
        tagsTimeToMake.add("Very Long");
    }

    private void addDifficultyDefaults() {
        tagsDifficulty.add("Easy");
        tagsDifficulty.add("Normal");
        tagsDifficulty.add("Hard");
    }

    private void addMealTypeDefaults() {
        tagsMealType.add("Breakfast");
        tagsMealType.add("Lunch");
        tagsMealType.add("Supper");
        tagsMealType.add("Snack");
    }

    private void addOtherDefaults() {
        tagsOther.add("Chicken");
        tagsOther.add("Beef");
        tagsOther.add("Veggies");
        tagsOther.add("Treat");
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

    public ArrayList<String> getTagsOther() {
        tagsOther.sort(Comparator.comparing(Object::toString));
        return tagsOther;
    }

    private boolean doesTagExist(List<String> list, String newTag) {
        for (String tag : list) {
            if (tag.equalsIgnoreCase(newTag))
                return true;
        }
        return false;
    }

    public boolean addTagToList(List<String> list, String newTag) {
        if (!doesTagExist(list, newTag)) {
            list.add(newTag);
            DataManager.getInstance().saveTags();
            return true;
        }
        return false;
    }

}

