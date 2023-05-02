package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lertos.mealpicker.adapters.AdapterMealList;
import com.lertos.mealpicker.model.DataManager;
import com.lertos.mealpicker.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearchMeal extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdapterMealList adapterMealList;
    private Button btnHideFilters;
    private Button btnShowFilters;
    private Spinner spinnerTimeToMake;
    private Spinner spinnerDifficulty;
    private Spinner spinnerMealType;

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
        btnHideFilters = view.findViewById(R.id.btnHideFilters);
        btnShowFilters = view.findViewById(R.id.btnShowFilters);

        //Setup the spinners
        spinnerTimeToMake = view.findViewById(R.id.spinnerTimeToMake);
        spinnerDifficulty = view.findViewById(R.id.spinnerDifficulty);
        spinnerMealType = view.findViewById(R.id.spinnerMealType);

        setupStringSpinner(view, DataManager.getInstance().getTags().getTagsTimeToMake(), spinnerTimeToMake);
        setupStringSpinner(view, DataManager.getInstance().getTags().getTagsDifficulty(), spinnerDifficulty);
        setupStringSpinner(view, DataManager.getInstance().getTags().getTagsMealType(), spinnerMealType);

        addFilterSectionToggleListeners();

        //TODO: Get a filtered list from the MealManager
        setAdapterMealList(DataManager.getInstance().getMeals().getMeals());

        return view;
    }

    private void setupStringSpinner(View view, ArrayList<String> stringList, Spinner spinnerToAttachTo) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, stringList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerToAttachTo.setAdapter(adapter);

        //To allow them to select "nothing"
        adapter.insert("", 0);
    }

    private void setAdapterMealList(List<Meal> mealList) {
        adapterMealList = new AdapterMealList();
        adapterMealList.setDataList(mealList);

        recyclerView.setAdapter(adapterMealList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void addFilterSectionToggleListeners() {
        btnHideFilters.setOnClickListener(btn -> {
            view.findViewById(R.id.linSearchFilters).setVisibility(View.GONE);
            view.findViewById(R.id.btnShowFilters).setVisibility(View.VISIBLE);
        });

        btnShowFilters.setOnClickListener(btn -> {
            view.findViewById(R.id.linSearchFilters).setVisibility(View.VISIBLE);
            view.findViewById(R.id.btnShowFilters).setVisibility(View.GONE);
        });
    }
}