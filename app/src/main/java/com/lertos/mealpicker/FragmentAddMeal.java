package com.lertos.mealpicker;

import android.os.Bundle;
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
import java.util.UUID;


public class FragmentAddMeal extends Fragment {

    private View view;
    private int mealIndex;
    private Button btnCreateMeal;
    private Button btnUpdateMeal;
    private Button btnResetFields;
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
        view = inflater.inflate(R.layout.fragment_add_meal, container, false);

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

        //Setup the buttons
        btnCreateMeal = view.findViewById(R.id.btnCreateMeal);
        btnUpdateMeal = view.findViewById(R.id.btnUpdateMeal);
        btnResetFields = view.findViewById(R.id.btnResetFields);

        setupCreateButtonListener();
        setupUpdateButtonListener();
        btnResetFields.setOnClickListener(btn -> resetFields());

        //Setup the "other tag" dropdown
        tvOtherTagList = view.findViewById(R.id.tvOtherTagList);

        setupOtherTagDropdownList();
        setupOtherTagDropdownListeners();

        //Check for a meal index in the bundle; if it's supplied, then load
        if (getArguments() != null) {
            mealIndex = getArguments().getInt("MEAL_INDEX", -1);

            if (mealIndex != -1 && mealIndex < DataManager.getInstance().getMeals().getMeals().size()) {
                loadMealData();
                hideCreateShowUpdateButton();
            }
        }

        return view;
    }

    private void setupStringSpinner(View view, ArrayList<String> stringList, Spinner spinnerToAttachTo) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, stringList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerToAttachTo.setAdapter(adapter);
    }

    private void setupOtherTagDropdownList() {
        ArrayList<String> otherTags = DataManager.getInstance().getTags().getTagsOther();

        tagOptions = new String[otherTags.size()];
        tagOptions = otherTags.toArray(tagOptions);

        selectedTags = new boolean[tagOptions.length];

        tagList.clear();
        tvOtherTagList.setText("");
    }

    private void hideCreateShowUpdateButton() {
        btnCreateMeal.setVisibility(View.GONE);
        btnUpdateMeal.setVisibility(View.VISIBLE);
    }

    private void resetFields() {
        etMealName.getEditableText().clear();
        etPrepTime.getEditableText().clear();
        etCookTime.getEditableText().clear();

        spinnerTimeToMake.setSelection(0);
        spinnerDifficulty.setSelection(0);
        spinnerMealType.setSelection(0);

        setupOtherTagDropdownList();

        etMealName.requestFocus();
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

            //Check if the user wants the fields to be reset for the next meal
            if (DataManager.getInstance().getSettings().useResetFieldsAfterMealCreation())
                resetFields();

            if (wasAdded)
                Toast.makeText(this.getContext(), "A new meal has been created!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this.getContext(), "That meal title already exists", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupUpdateButtonListener() {
        btnUpdateMeal.setOnClickListener((view) -> {
            String errorMessage = validateFields();

            if (!errorMessage.isEmpty()) {
                Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                return;
            }

            Meal existingMeal = DataManager.getInstance().getMeals().getMeals().get(mealIndex);

            existingMeal.setTitle(etMealName.getEditableText().toString());
            existingMeal.setPrepTime(Integer.parseInt(etPrepTime.getEditableText().toString()));
            existingMeal.setCookTime(Integer.parseInt(etCookTime.getEditableText().toString()));
            existingMeal.setTagTimeToMake(spinnerTimeToMake.getSelectedItem().toString());
            existingMeal.setTagDifficulty(spinnerDifficulty.getSelectedItem().toString());
            existingMeal.setTagMealType(spinnerMealType.getSelectedItem().toString());
            existingMeal.setOtherTags(getOtherTagList());

            Toast.makeText(this.getContext(), "Your meal has been updated", Toast.LENGTH_SHORT).show();
        });
    }

    private Meal addNewMeal() {
        String mealTitle = etMealName.getEditableText().toString();
        int prepTime = Integer.parseInt(etPrepTime.getEditableText().toString());
        int cookTime = Integer.parseInt(etCookTime.getEditableText().toString());

        String tagTimeToMake = spinnerTimeToMake.getSelectedItem().toString();
        String tagDifficulty = spinnerDifficulty.getSelectedItem().toString();
        String tagMealType = spinnerMealType.getSelectedItem().toString();

        UUID newId = UUID.randomUUID();

        return new Meal(newId, mealTitle, prepTime, cookTime, tagTimeToMake, tagDifficulty, tagMealType, getOtherTagList());
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

        //Fill the other tag list, setting up the array list and boolean values for which are picked
        for (String otherTag : meal.getOtherTags()) {
            for (int i = 0; i < tagOptions.length; i++) {
                if (tagOptions[i].equalsIgnoreCase(otherTag)) {
                    selectedTags[i] = true;
                    tagList.add(i);
                }
            }
        }
        setOtherTagText();
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