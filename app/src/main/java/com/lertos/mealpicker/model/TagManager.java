package com.lertos.mealpicker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TagManager {

    //Tags are essentially just Strings
    //They are in different lists which serve different purposes. But they're still just Strings.
    private ArrayList<String> tagsTimeToMake;
    private ArrayList<String> tagsDifficulty;
    private ArrayList<String> tagsMealType;
    private ArrayList<String> tagsOther;

    public TagManager() {
        tagsTimeToMake = new ArrayList<>();
        tagsDifficulty = new ArrayList<>();
        tagsMealType = new ArrayList<>();
        tagsOther = new ArrayList<>();
    }

    public List<String> getTagsTimeToMake() {
        tagsTimeToMake.sort(Comparator.comparing(Object::toString));
        return Collections.unmodifiableList(tagsTimeToMake);
    }

    public List<String> getTagsDifficulty() {
        tagsDifficulty.sort(Comparator.comparing(Object::toString));
        return Collections.unmodifiableList(tagsDifficulty);
    }

    public List<String> getTagsMealType() {
        tagsMealType.sort(Comparator.comparing(Object::toString));
        return Collections.unmodifiableList(tagsMealType);
    }

    public List<String> getTagsOther() {
        tagsOther.sort(Comparator.comparing(Object::toString));
        return Collections.unmodifiableList(tagsOther);
    }

    private boolean doesTagExist(List<String> list, String newTag) {
        for (String tag : list) {
            if (tag.equalsIgnoreCase(newTag))
                return true;
        }
        return false;
    }

    private boolean addTagToList(List<String> list, String newTag) {
        if (!doesTagExist(list, newTag)) {
            list.add(newTag);
            return true;
        }
        return false;
    }

    public boolean addTimeToMakeTag(String newTag) {
        return addTagToList(tagsTimeToMake, newTag);
    }

    public boolean addDifficultyTag(String newTag) {
        return addTagToList(tagsDifficulty, newTag);
    }

    public boolean addMealTypeTag(String newTag) {
        return addTagToList(tagsMealType, newTag);
    }

    public boolean addOtherTag(String newTag) {
        return addTagToList(tagsOther, newTag);
    }

}

