package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.lertos.mealpicker.model.DataManager;

public class FragmentSettings extends Fragment {

    private ToggleButton tbDarkMode;

    public FragmentSettings() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        tbDarkMode = view.findViewById(R.id.tbDarkMode);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            tbDarkMode.setChecked(true);
        else
            tbDarkMode.setChecked(false);

        tbDarkMode.setOnClickListener(btnView -> {
            if (tbDarkMode.isChecked())
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            //This is to tell the main activity to reload the settings page since the activity restarts after switching themes; yuck!
            DataManager.getInstance().setChangedDayNightTheme(true);
        });

        return view;
    }
}