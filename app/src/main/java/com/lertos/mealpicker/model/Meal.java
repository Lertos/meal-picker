package com.lertos.mealpicker.model;

import java.util.Arrays;
import java.util.UUID;

public class Meal implements Comparable<Meal> {

    //"uniqueId" will be used for saving/loading, as well as in the MealPlanner; instead of using "title" which can change
    private final UUID uniqueId;
    private String title;
    private Timing prepTime;
    private Timing cookTime;
    private String tagTimeToMake;
    private String tagDifficulty;
    private String tagMealType;
    private String[] otherTags;

    public Meal(UUID uniqueId, String title, int prepTime, int cookTime, String tagTimeToMake, String tagDifficulty, String tagMealType, String[] otherTags) {
        this.uniqueId = uniqueId;
        this.title = title;
        this.prepTime = new Timing(prepTime);
        this.cookTime = new Timing(cookTime);
        this.tagTimeToMake = tagTimeToMake;
        this.tagDifficulty = tagDifficulty;
        this.tagMealType = tagMealType;
        this.otherTags = otherTags;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timing getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime.setTimeInMinutes(prepTime);
    }

    public Timing getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.prepTime.setTimeInMinutes(cookTime);
    }

    public String getTagTimeToMake() {
        return tagTimeToMake;
    }

    public void setTagTimeToMake(String tagTimeToMake) {
        this.tagTimeToMake = tagTimeToMake;
    }

    public String getTagDifficulty() {
        return tagDifficulty;
    }

    public void setTagDifficulty(String tagDifficulty) {
        this.tagDifficulty = tagDifficulty;
    }

    public String getTagMealType() {
        return tagMealType;
    }

    public void setTagMealType(String tagMealType) {
        this.tagMealType = tagMealType;
    }

    public String[] getOtherTags() {
        Arrays.sort(otherTags);
        return otherTags;
    }

    public void setOtherTags(String[] otherTags) {
        this.otherTags = otherTags;
    }

    @Override
    public int compareTo(Meal meal) {
        return this.getTitle().compareTo(meal.getTitle());
    }

}
