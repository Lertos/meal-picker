package com.lertos.mealpicker.model;

public class Meal {

    private String title;
    private int prepTime;
    private int cookTime;
    private String tagTimeToMake;
    private String tagDifficulty;
    private String tagMealType;
    private String[] otherTags;

    public Meal(String title, int prepTime, int cookTime, String tagTimeToMake, String tagDifficulty, String tagMealType, String[] otherTags) {
        this.title = title;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.tagTimeToMake = tagTimeToMake;
        this.tagDifficulty = tagDifficulty;
        this.tagMealType = tagMealType;
        this.otherTags = otherTags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
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
        return otherTags;
    }

    public void setOtherTags(String[] otherTags) {
        this.otherTags = otherTags;
    }
}
