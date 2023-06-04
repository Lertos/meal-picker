package com.lertos.mealpicker.model;

import java.io.Serializable;
import java.util.ArrayList;
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

}
