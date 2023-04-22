package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentAddMeal extends Fragment {

    private Spinner spinnerTimeToMake;


    public FragmentAddMeal() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);

        spinnerTimeToMake = view.findViewById(R.id.spinnerTimeToMake);

        ArrayList<String> timeValues = new ArrayList<>();

        timeValues.add("Short");
        timeValues.add("Medium");
        timeValues.add("Long");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, timeValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTimeToMake.setAdapter(adapter);

        return view;
    }
}