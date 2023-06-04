package com.lertos.mealpicker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MealManager implements Serializable {

    private ArrayList<Meal> mealList;

    public MealManager() {
        this.mealList = new ArrayList<>();

        //Sample meal for them to see how it works
        Meal meal = new Meal("Sample Meal", 42, 420, "Long", "Hard", "Supper", new String[]{"Treat"});

        mealList.add(meal);
    }

    public boolean addMeal(Meal newMeal) {
        for (Meal meal : mealList) {
            if (meal.getTitle().equalsIgnoreCase(newMeal.getTitle()))
                return false;
        }
        mealList.add(newMeal);
        DataManager.getInstance().saveMeals();

        return true;
    }

    public List<Meal> getMeals() {
        return mealList;
    }

    public List<Meal> getSortedMeals() {
        Collections.sort(mealList);
        return mealList;
    }

    public List<Meal> getFilteredMeals(String title, String tagTimeToMake, String tagDifficulty, String tagMealType, String[] otherTags) {
        ArrayList<Meal> filteredList = new ArrayList<>();

        for (Meal meal : mealList) {
            if (!title.isEmpty()) {
                if (!meal.getTitle().contains(title))
                    continue;
            }
            if (!tagTimeToMake.isEmpty()) {
                if (!meal.getTagTimeToMake().equalsIgnoreCase(tagTimeToMake))
                    continue;
            }
            if (!tagDifficulty.isEmpty()) {
                if (!meal.getTagDifficulty().equalsIgnoreCase(tagDifficulty))
                    continue;
            }
            if (!tagMealType.isEmpty()) {
                if (!meal.getTagMealType().equalsIgnoreCase(tagMealType))
                    continue;
            }
            if (otherTags.length > 0) {
                boolean wasFound = false;

                //Check each tag to filter on and make sure the meal has every one of them
                for (String filteredTag : otherTags) {
                    wasFound = false;

                    for (String mealTag : meal.getOtherTags()) {
                        if (filteredTag.equalsIgnoreCase(mealTag)) {
                            wasFound = true;
                            break;
                        }
                    }
                    //If any of the tags to filter on are not found - skip the meal
                    if (!wasFound)
                        break;
                }
                if (!wasFound)
                    continue;
            }
            filteredList.add(meal);
        }
        Collections.sort(filteredList);
        return filteredList;
    }

    public void removeAllMeals() {
        mealList.clear();
        DataManager.getInstance().saveMeals();
    }

    public void updateTagInMeals(EnumListType listType, String oldTag, String newTag) {
        switch (listType) {
            //Each single-tag String can be changed in a basic manner
            case TIME_TO_MAKE:
                for (Meal meal : mealList) {
                    if (meal.getTagTimeToMake().equalsIgnoreCase(oldTag))
                        meal.setTagTimeToMake(newTag);
                }
                break;
            case DIFFICULTY:
                for (Meal meal : mealList) {
                    if (meal.getTagDifficulty().equalsIgnoreCase(oldTag))
                        meal.setTagDifficulty(newTag);
                }
                break;
            case MEAL_TYPE:
                for (Meal meal : mealList) {
                    if (meal.getTagMealType().equalsIgnoreCase(oldTag))
                        meal.setTagMealType(newTag);
                }
                break;
            //The other tag list has a lot more edge cases so we separate the logic into it's own method
            case OTHER:
                updateOtherTagsInMeals(oldTag, newTag);
                break;
        }
    }

    private void updateOtherTagsInMeals(String oldTag, String newTag) {
        String otherTag;
        int oldTagIndex, newTagIndex;

        for (Meal meal : mealList) {
            oldTagIndex = -1;
            newTagIndex = -1;

            for (int i = 0; i < meal.getOtherTags().length; i++) {
                otherTag = meal.getOtherTags()[i];

                //If both tags were found, then break out as both cases have been dealt with
                if (oldTagIndex != -1 && newTagIndex != -1) {
                    break;
                }
                //If neither has been found, check for both
                else {
                    if (oldTagIndex == -1 && otherTag.equalsIgnoreCase(oldTag))
                        oldTagIndex = i;
                    if (newTagIndex == -1 && otherTag.equalsIgnoreCase(newTag))
                        newTagIndex = i;
                }
            }

            //If the old tag was found, we need to handle it
            if (oldTagIndex != -1) {
                //If both indices were found, then we remove the old tag only
                if (oldTagIndex != -1 && newTagIndex != -1) {
                    List<String> arrList = new ArrayList(Arrays.asList(meal.getOtherTags()));
                    arrList.remove(oldTagIndex);
                    meal.setOtherTags(arrList.toArray(new String[0]));
                }
                //If only the old tag was found, we update it with the new tag
                else
                    meal.getOtherTags()[oldTagIndex] = newTag;
            }
        }
    }

}
