package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.lertos.mealpicker.model.DataManager;

import java.util.ArrayList;


public class FragmentAddMeal extends Fragment {

    private Spinner spinnerTimeToMake;
    private TextView tvOtherTagList;
    private ArrayList<Integer> tagList = new ArrayList<>();
    private boolean[] selectedTags;
    private String[] tagOptions = {"Chicken", "Beef", "Vegetables", "Fish", "Soup", "Treat"};

    public FragmentAddMeal() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);

        //Setup the spinners
        spinnerTimeToMake = view.findViewById(R.id.spinnerTimeToMake);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, DataManager.getInstance().getTagsTimeToMake());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTimeToMake.setAdapter(adapter);

        //Setup the "other tag" dropdown list
        tvOtherTagList = view.findViewById(R.id.tvOtherTagList);
        selectedTags = new boolean[tagOptions.length];

        setupOtherTagDropdownListeners();

        return view;
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
}