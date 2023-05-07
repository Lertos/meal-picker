package com.lertos.mealpicker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.lertos.mealpicker.model.DataManager;
import com.lertos.mealpicker.model.Meal;

import java.util.ArrayList;


public class FragmentAddMeal extends Fragment {

    private int mealIndex;
    private Button btnCreateMeal;
    private EditText etMealName;
    private EditText etPrepTime;
    private EditText etCookTime;
    private Spinner spinnerTimeToMake;
    private Spinner spinnerDifficulty;
    private Spinner spinnerMealType;
    private TextView tvOtherTagList;
    private ArrayList<Integer> tagList = new ArrayList<>();
    private boolean[] selectedTags;
    private String[] tagOptions;

    public FragmentAddMeal() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);

        //Setup the edit texts
        etMealName = view.findViewById(R.id.etMealName);
        etPrepTime = view.findViewById(R.id.etPrepTime);
        etCookTime = view.findViewById(R.id.etCookTime);

        //Setup the spinners
        spinnerTimeToMake = view.findViewById(R.id.spinnerTimeToMake);
        spinnerDifficulty = view.findViewById(R.id.spinnerDifficulty);
        spinnerMealType = view.findViewById(R.id.spinnerMealType);

        setupStringSpinner(view, DataManager.getInstance().getTags().getTagsTimeToMake(), spinnerTimeToMake);
        setupStringSpinner(view, DataManager.getInstance().getTags().getTagsDifficulty(), spinnerDifficulty);
        setupStringSpinner(view, DataManager.getInstance().getTags().getTagsMealType(), spinnerMealType);

        //Setup the button
        btnCreateMeal = view.findViewById(R.id.btnCreateMeal);

        setupCreateButtonListener();

        //Setup the "other tag" dropdown list
        tvOtherTagList = view.findViewById(R.id.tvOtherTagList);
        ArrayList<String> otherTags = DataManager.getInstance().getTags().getTagsOther();

        tagOptions = new String[otherTags.size()];
        tagOptions = otherTags.toArray(tagOptions);

        selectedTags = new boolean[tagOptions.length];

        setupOtherTagDropdownListeners();

        //Check for a meal index in the bundle; if it's supplied, then load
        if (getArguments() != null) {
            mealIndex = getArguments().getInt("MEAL_INDEX", -1);

            if (mealIndex != -1 && mealIndex < DataManager.getInstance().getMeals().getMeals().size())
                loadMealData();
        }

        return view;
    }

    private void setupStringSpinner(View view, ArrayList<String> stringList, Spinner spinnerToAttachTo) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, stringList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerToAttachTo.setAdapter(adapter);
    }

    private void setupCreateButtonListener() {
        btnCreateMeal.setOnClickListener((view) -> {
            String errorMessage = validateFields();

            if (!errorMessage.isEmpty()) {
                Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                return;
            }

            Meal newMeal = addNewMeal();
            boolean wasAdded = DataManager.getInstance().getMeals().addMeal(newMeal);

            Log.d("==Meals", DataManager.getInstance().getMeals().getMeals().toString());

            if (wasAdded)
                Toast.makeText(this.getContext(), "A new meal has been created!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this.getContext(), "That meal title already exists", Toast.LENGTH_SHORT).show();
        });
    }

    private Meal addNewMeal() {
        String mealTitle = etMealName.getEditableText().toString();
        int prepTime = Integer.parseInt(etPrepTime.getEditableText().toString());
        int cookTime = Integer.parseInt(etCookTime.getEditableText().toString());

        String tagTimeToMake = spinnerTimeToMake.getSelectedItem().toString();
        String tagDifficulty = spinnerDifficulty.getSelectedItem().toString();
        String tagMealType = spinnerMealType.getSelectedItem().toString();

        return new Meal(mealTitle, prepTime, cookTime, tagTimeToMake, tagDifficulty, tagMealType, getOtherTagList());
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

    private String validateFields() {
        if (etMealName.getEditableText().toString().isEmpty())
            return "The Meal Name field must be entered";
        else if (spinnerTimeToMake.getSelectedItem().toString().isEmpty())
            return "You must select a 'Time to Make' tag that isn't blank";
        else if (spinnerDifficulty.getSelectedItem().toString().isEmpty())
            return "You must select a 'Difficulty' tag that isn't blank";
        else if (spinnerMealType.getSelectedItem().toString().isEmpty())
            return "You must select a 'Meal Type' tag that isn't blank";
        else if (etPrepTime.getEditableText().toString().isEmpty())
            return "The Prep Time field must be entered";
        else if (etCookTime.getEditableText().toString().isEmpty())
            return "The Cook Time field must be entered";
        return "";
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
                builder.setPositiveButton("OK", (dialogInterface, i) -> {
                    //Display the tags in the text view after selection
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int j = 0; j < tagList.size(); j++) {
                        stringBuilder.append(tagOptions[tagList.get(j)]);

                        if (j != tagList.size() - 1)
                            stringBuilder.append(", ");
                    }
                    tvOtherTagList.setText(stringBuilder.toString());
                });

                //Cancel simply dismisses the alert dialogue
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

                //Handle the Clear All button
                builder.setNeutralButton("Clear All", (dialogInterface, i) -> {
                    for (int j = 0; j < selectedTags.length; j++) {
                        selectedTags[j] = false;
                        tagList.clear();
                        tvOtherTagList.setText("");
                    }
                });

                builder.show();
            }
        });
    }

    private void loadMealData() {
        Meal meal = DataManager.getInstance().getMeals().getMeals().get(mealIndex);

        etMealName.getEditableText().clear();
        etMealName.getEditableText().append(meal.getTitle());

        etPrepTime.getEditableText().clear();
        etPrepTime.getEditableText().append(String.valueOf(meal.getPrepTime().getTimeInMinutes()));

        etCookTime.getEditableText().clear();
        etCookTime.getEditableText().append(String.valueOf(meal.getCookTime().getTimeInMinutes()));

        setSpinnerValue(spinnerTimeToMake, meal.getTagTimeToMake());
        setSpinnerValue(spinnerDifficulty, meal.getTagDifficulty());
        setSpinnerValue(spinnerMealType, meal.getTagMealType());

        //TODO: Fill tvOtherTagList; will need to take care of the variables for bool, array list, etc at the top
    }

    private void setSpinnerValue(Spinner spinner, String tag) {
        Adapter adapter = spinner.getAdapter();

        for (int i = 0; i < adapter.getCount(); i++) {
            if (tag.equalsIgnoreCase(adapter.getItem(i).toString())) {
                spinner.setSelection(i);
                return;
            }
        }
    }
}