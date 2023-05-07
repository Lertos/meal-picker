package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.lertos.mealpicker.model.DataManager;

public class FragmentSettings extends Fragment {

    private SwitchMaterial switchDarkMode;

    public FragmentSettings() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchDarkMode = view.findViewById(R.id.switchUseDarkMode);

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

        return view;
    }
}