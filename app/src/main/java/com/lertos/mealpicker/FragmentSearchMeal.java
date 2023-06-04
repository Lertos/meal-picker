package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lertos.mealpicker.adapters.AdapterMealList;
import com.lertos.mealpicker.model.DataManager;
import com.lertos.mealpicker.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentSearchMeal extends Fragment {

    private Random rng;
    private View view;
    private RecyclerView recyclerView;
    private AdapterMealList adapterMealList;
    private Button btnHideFilters;
    private Button btnShowFilters;
    private Button btnSurpriseMe;
    private Button btnFilter;
    private Spinner spinnerTimeToMake;
    private Spinner spinnerDifficulty;
    private Spinner spinnerMealType;
    private EditText etMealName;
    private TextView tvOtherTagList;
    private ArrayList<Integer> tagList = new ArrayList<>();
    private boolean[] selectedTags;
    private String[] tagOptions;

    public FragmentSearchMeal() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_meal, container, false);

        rng = new Random();

        recyclerView = view.findViewById(R.id.rvMealList);

        etMealName = view.findViewById(R.id.etMealName);

        //Set up the buttons
        btnHideFilters = view.findViewById(R.id.btnHideFilters);
        btnShowFilters = view.findViewById(R.id.btnShowFilters);
        btnSurpriseMe = view.findViewById(R.id.btnSurpriseMe);
        btnFilter = view.findViewById(R.id.btnFilter);

        //Set up the spinners
        spinnerTimeToMake = view.findViewById(R.id.spinnerTimeToMake);
        spinnerDifficulty = view.findViewById(R.id.spinnerDifficulty);
        spinnerMealType = view.findViewById(R.id.spinnerMealType);

        setupStringSpinner(view, DataManager.getInstance().getTags().getTagsTimeToMake(), spinnerTimeToMake);
        setupStringSpinner(view, DataManager.getInstance().getTags().getTagsDifficulty(), spinnerDifficulty);
        setupStringSpinner(view, DataManager.getInstance().getTags().getTagsMealType(), spinnerMealType);

        //Setup the "other tag" dropdown
        tvOtherTagList = view.findViewById(R.id.tvOtherTagList);

        setupOtherTagDropdownList();
        setupOtherTagDropdownListeners();

        //Set up the button listeners
        addFilterSectionToggleListeners();
        addSurpriseMeButtonListener();
        addFilterButtonListener();

        setAdapterMealList(DataManager.getInstance().getMeals().getSortedMeals());

        return view;
    }

    private void setupStringSpinner(View view, ArrayList<String> stringList, Spinner spinnerToAttachTo) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, stringList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerToAttachTo.setAdapter(adapter);

        //To allow them to select "nothing"
        if (!adapter.getItem(0).isEmpty())
            adapter.insert("", 0);
    }

    private void setupOtherTagDropdownList() {
        ArrayList<String> otherTags = DataManager.getInstance().getTags().getTagsOther();

        tagOptions = new String[otherTags.size()];
        tagOptions = otherTags.toArray(tagOptions);

        selectedTags = new boolean[tagOptions.length];

        tagList.clear();
        tvOtherTagList.setText("");
    }

    private void setupOtherTagDropdownListeners() {
        tvOtherTagList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Select All Tags That Apply");
                //builder.setCancelable(true); //If needed, makes outside clicks impossible

                //Handle the individual checkboxes
                builder.setMultiChoiceItems(tagOptions, selectedTags, (dialogInterface, index, bool) -> {
                    if (bool)
                        tagList.add(index); //For the position clicked, add it to the list of tags
                    else
                        tagList.remove(Integer.valueOf(index)); //For the position clicked, remove it from the list of tags
                });

                //Handle the OK button
                builder.setPositiveButton("OK", (dialogInterface, i) -> setOtherTagText());

                //Cancel simply dismisses the alert dialogue
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

                //Handle the Clear All button
                builder.setNeutralButton("Clear All", (dialogInterface, i) -> {
                    for (int j = 0; j < selectedTags.length; j++) {
                        selectedTags[j] = false;
                    }
                    tagList.clear();
                    tvOtherTagList.setText("");
                });

                builder.show();
            }
        });
    }

    private void setOtherTagText() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < tagList.size(); j++) {
            stringBuilder.append(tagOptions[tagList.get(j)]);

            if (j != tagList.size() - 1)
                stringBuilder.append(", ");
        }
        tvOtherTagList.setText(stringBuilder.toString());
    }

    private void setAdapterMealList(List<Meal> mealList) {
        adapterMealList = new AdapterMealList();
        adapterMealList.setDataList(mealList);
        adapterMealList.setFragmentActivity(getActivity());

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

    private String[] getOtherTagList() {
        int count = 0;

        for (int i = 0; i < selectedTags.length; i++) {
            if (selectedTags[i])
                count++;
        }
        String[] otherTagList = new String[count];

        for (int i = 0; i < selectedTags.length; i++) {
            if (selectedTags[i]) {
                count--;
                otherTagList[count] = tagOptions[i];
            }
        }
        return otherTagList;
    }

    private void addSurpriseMeButtonListener() {
        btnSurpriseMe.setOnClickListener(btn -> {
            List<Meal> filteredList = getFilteredMeals();

            if (filteredList.isEmpty()) {
                Toast.makeText(this.getContext(), "No meals were returned for this filter", Toast.LENGTH_SHORT).show();
                return;
            }

            //Get the meal you want out of the filtered list
            Meal randomMeal = filteredList.get(rng.nextInt(filteredList.size()));

            //Now find that meal again in the normal list so you can pass the meal index to the existing meal page
            List<Meal> meals = DataManager.getInstance().getMeals().getMeals();

            for (int i = 0; i < meals.size(); i++) {
                if (meals.get(i).getTitle().equalsIgnoreCase(randomMeal.getTitle())) {
                    Bundle bundle = new Bundle();

                    bundle.putInt("MEAL_INDEX", i);
                    Helper.replaceFragment(this.getActivity(), new FragmentExistingMeal(), bundle);
                }
            }
        });
    }

    private void addFilterButtonListener() {
        btnFilter.setOnClickListener(btn -> {
            //Set the adapter to use the new list instead
            setAdapterMealList(getFilteredMeals());

            //Hide the keyboard if it's shown; mainly for when they're entering a title and want to filter afterwards
            Helper.hideKeyboard(getActivity());
        });
    }

    private List<Meal> getFilteredMeals() {
        String mealTitle = etMealName.getEditableText().toString();
        String tagTimeToMake = spinnerTimeToMake.getSelectedItem().toString();
        String tagDifficulty = spinnerDifficulty.getSelectedItem().toString();
        String tagMealType = spinnerMealType.getSelectedItem().toString();

        //Return the list of meals based on search filters
        return DataManager.getInstance().getMeals().getFilteredMeals(mealTitle, tagTimeToMake, tagDifficulty, tagMealType, getOtherTagList());
    }
}