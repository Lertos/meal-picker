package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lertos.mealpicker.adapters.AdapterMealList;
import com.lertos.mealpicker.model.DataManager;
import com.lertos.mealpicker.model.Meal;

import java.util.List;

public class FragmentSearchMeal extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdapterMealList adapterMealList;

    public FragmentSearchMeal() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_meal, container, false);

        recyclerView = view.findViewById(R.id.rvMealList);

        //TODO: Get a filtered list from the MealManager
        setAdapterMealList(DataManager.getInstance().getMeals().getMeals());

        return view;
    }

    private void setAdapterMealList(List<Meal> mealList) {
        adapterMealList = new AdapterMealList();
        adapterMealList.setDataList(mealList);

        recyclerView.setAdapter(adapterMealList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}