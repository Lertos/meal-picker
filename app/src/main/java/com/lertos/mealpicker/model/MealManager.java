package com.lertos.mealpicker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MealManager {

    private ArrayList<Meal> mealList;

    public MealManager() {
        this.mealList = new ArrayList<>();

        //TODO: Debug meals
        Meal meal1 = new Meal("Burger Pasta", 30, 120, "Long", "Hard", "Supper", new String[]{"Pasta", "Beef"});
        Meal meal2 = new Meal("Pizza Salad", 15, 5, "Short", "Easy", "Lunch", new String[]{"Salad", "Beef", "Veggies"});

        mealList.add(meal1);
        mealList.add(meal2);

        loadSavedMeals();
    }

    private void loadSavedMeals() {
        //TODO: Load the meals from the local save file
    }

    public boolean addMeal(Meal newMeal) {
        for (Meal meal : mealList) {
            if (meal.getTitle().equalsIgnoreCase(newMeal.getTitle()))
                return false;
        }
        mealList.add(newMeal);

        return true;
    }

    public boolean removeMeal(String title) {
        for (Meal meal : mealList) {
            if (meal.getTitle().equalsIgnoreCase(title)) {
                mealList.remove(meal);
                return false;
            }
        }
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

            }
            filteredList.add(meal);
        }
        return filteredList;
    }

}
