package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lertos.mealpicker.model.DataManager;
import com.lertos.mealpicker.model.Meal;

import java.util.List;

public class FragmentExistingMeal extends Fragment {

    private View view;
    private int mealIndex;

    public FragmentExistingMeal() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_existing_meal, container, false);

        setupButtonListeners();

        //Get the meal and display the relevant data of it
        mealIndex = getArguments().getInt("MEAL_INDEX", -1);

        if (mealIndex != -1) {
            List<Meal> meals = DataManager.getInstance().getMeals().getMeals();

            if (mealIndex < meals.size())
                setupMealData(meals.get(mealIndex));
        }
        return view;
    }

    private void setupMealData(Meal meal) {
        ((TextView) view.findViewById(R.id.tvMealTitle)).setText(meal.getTitle());
        ((TextView) view.findViewById(R.id.tvTimeToMake)).setText(meal.getTagTimeToMake());
        ((TextView) view.findViewById(R.id.tvDifficulty)).setText(meal.getTagDifficulty());
        ((TextView) view.findViewById(R.id.tvMealType)).setText(meal.getTagMealType());
        ((TextView) view.findViewById(R.id.tvPrepTime)).setText(meal.getPrepTime().getDisplayTimeBasedOnSetting());
        ((TextView) view.findViewById(R.id.tvCookTime)).setText(meal.getCookTime().getDisplayTimeBasedOnSetting());

        String otherTagText = getOtherTagText(meal.getOtherTags());

        ((TextView) view.findViewById(R.id.tvOtherTags)).setText(otherTagText);
    }

    private String getOtherTagText(String[] otherTags) {
        StringBuilder sb = new StringBuilder();

        for (String str : otherTags)
            sb.append(str).append("\n");
        return sb.toString();
    }

    private void setupButtonListeners() {
        //Back button
        view.findViewById(R.id.ibBtnBack).setOnClickListener(btn -> {
            Helper.replaceFragment(this.getActivity(), new FragmentSearchMeal(), null);
        });

        //Delete button
        view.findViewById(R.id.ibBtnDelete).setOnClickListener(btn -> {
            //Delete the meal then go back to the search page
            DataManager.getInstance().getMeals().getMeals().remove(mealIndex);
            Helper.replaceFragment(this.getActivity(), new FragmentSearchMeal(), null);
        });

        //Edit button
        view.findViewById(R.id.ibBtnEdit).setOnClickListener(btn -> {
            //TODO: Simply load the Add Meal fragment, but have a bundle with meal index and load
            //it in the fields. Do this after everything has loaded - then replace "Create" btn
            //text with "Update"
        });
    }

}