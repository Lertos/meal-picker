package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.lertos.mealpicker.model.DataManager;

import java.util.ArrayList;

public class FragmentSettings extends Fragment {

    private Spinner spinnerTimeFormat;
    private SwitchMaterial switchDarkMode;
    private SwitchMaterial switchCloseKeyboardOnAdd;
    private SwitchMaterial switchResetFieldsOnAdd;

    public FragmentSettings() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Assign the widgets
        spinnerTimeFormat = view.findViewById(R.id.spinnerTimeFormat);

        switchDarkMode = view.findViewById(R.id.switchUseDarkMode);
        switchCloseKeyboardOnAdd = view.findViewById(R.id.switchCloseKeyboardOnAdd);
        switchResetFieldsOnAdd = view.findViewById(R.id.switchResetFieldsOnAdd);

        //Setup default/configured starting states
        addTimeFormatListValues();
        //TODO: Load or set defaults from settings

        //Setup widget listeners
        setupButtonDarkMode();
        setupButtonCloseKeyboardOnAdd();
        setupButtonResetFieldsOnAdd();

        return view;
    }

    private void addTimeFormatListValues() {
        ArrayList<String> timeFormats = new ArrayList<>();

        timeFormats.add("200 mins");
        timeFormats.add("3 hrs 20 mins ");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, timeFormats);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeFormat.setAdapter(adapter);
    }

    private void setupButtonDarkMode() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            switchDarkMode.setChecked(true);
        else
            switchDarkMode.setChecked(false);

        switchDarkMode.setOnClickListener(btnView -> {
            if (switchDarkMode.isChecked())
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            //This is to tell the main activity to reload the settings page since the activity restarts after switching themes; yuck!
            DataManager.getInstance().setChangedDayNightTheme(true);
        });
    }

    private void setupButtonCloseKeyboardOnAdd() {
        switchCloseKeyboardOnAdd.setOnClickListener(btnView -> {
            DataManager.getInstance().getSettings().setCloseKeyboardAfterTagCreation(switchCloseKeyboardOnAdd.isChecked());
        });
    }

    private void setupButtonResetFieldsOnAdd() {
        switchResetFieldsOnAdd.setOnClickListener(btnView -> {
            DataManager.getInstance().getSettings().setResetFieldsAfterMealCreation(switchResetFieldsOnAdd.isChecked());
        });
    }
}