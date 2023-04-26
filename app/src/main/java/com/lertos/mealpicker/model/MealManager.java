package com.lertos.mealpicker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MealManager {

    private ArrayList<Meal> mealList;

    public MealManager(ArrayList<Meal> mealList) {
        this.mealList = new ArrayList<>();

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

}
