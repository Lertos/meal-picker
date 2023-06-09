package com.lertos.mealpicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.lertos.mealpicker.model.DataManager;

import java.util.ArrayList;

public class FragmentSettings extends Fragment {

    private final String minutesOnlyText = "200 mins";
    private final String minutesAndHoursText = "3 hrs 20 mins";
    private Spinner spinnerTimeFormat;
    private SwitchMaterial switchDarkMode;
    private SwitchMaterial switchCloseKeyboardOnAdd;
    private SwitchMaterial switchResetFieldsOnAdd;
    private Button btnResetTimeToMake;
    private Button btnResetDifficulty;
    private Button btnResetMealTypes;
    private Button btnResetOthers;
    private Button btnDeleteMeals;

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

        btnResetTimeToMake = view.findViewById(R.id.btnResetTimeToMake);
        btnResetDifficulty = view.findViewById(R.id.btnResetDifficulty);
        btnResetMealTypes = view.findViewById(R.id.btnResetMealTypes);
        btnResetOthers = view.findViewById(R.id.btnResetOthers);
        btnDeleteMeals = view.findViewById(R.id.btnDeleteMeals);

        //Setup default/configured starting states
        addTimeFormatListValues();

        //--Time Format
        if (DataManager.getInstance().getSettings().useMinutesOnly())
            spinnerTimeFormat.setSelection(0);
        else
            spinnerTimeFormat.setSelection(1);

        //--Dark Mode
        if (DataManager.getInstance().getSettings().useDarkMode())
            switchDarkMode.setChecked(true);
        else
            switchDarkMode.setChecked(false);

        //--CloseKeyboard
        switchCloseKeyboardOnAdd.setChecked(DataManager.getInstance().getSettings().useCloseKeyboardAfterTagCreation());
        //--ResetFields
        switchResetFieldsOnAdd.setChecked(DataManager.getInstance().getSettings().useResetFieldsAfterMealCreation());

        //Setup widget listeners
        setupTimeFormatListener();
        setupButtonDarkMode();
        setupButtonCloseKeyboardOnAdd();
        setupButtonResetFieldsOnAdd();
        setupTagResetButtons();

        return view;
    }

    private void addTimeFormatListValues() {
        ArrayList<String> timeFormats = new ArrayList<>();

        timeFormats.add(minutesOnlyText);
        timeFormats.add(minutesAndHoursText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, timeFormats);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeFormat.setAdapter(adapter);
    }

    private void setupTimeFormatListener() {
        spinnerTimeFormat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String optionText = parent.getItemAtPosition(position).toString();

                if (optionText.equalsIgnoreCase(minutesOnlyText))
                    DataManager.getInstance().getSettings().setUseMinutesOnly(true);
                else
                    DataManager.getInstance().getSettings().setUseMinutesOnly(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupButtonDarkMode() {
        switchDarkMode.setOnClickListener(btnView -> {
            DataManager.getInstance().getSettings().setUseDarkMode(switchDarkMode.isChecked());

            Helper.setupDarkMode(switchDarkMode.isChecked());

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

    private void setupTagResetButtons() {
        btnResetTimeToMake.setOnClickListener(btnView -> {
            DataManager.getInstance().getTags().addTimeToMakeDefaults();
            Toast.makeText(this.getContext(), "Successfully reset tags for - Time to Make", Toast.LENGTH_SHORT).show();
        });

        btnResetDifficulty.setOnClickListener(btnView -> {
            DataManager.getInstance().getTags().addDifficultyDefaults();
            Toast.makeText(this.getContext(), "Successfully reset tags for - Difficulty", Toast.LENGTH_SHORT).show();
        });

        btnResetMealTypes.setOnClickListener(btnView -> {
            DataManager.getInstance().getTags().addMealTypeDefaults();
            Toast.makeText(this.getContext(), "Successfully reset tags for - Meal Types", Toast.LENGTH_SHORT).show();
        });

        btnResetOthers.setOnClickListener(btnView -> {
            DataManager.getInstance().getTags().addOtherDefaults();
            Toast.makeText(this.getContext(), "Successfully reset tags for - Others", Toast.LENGTH_SHORT).show();
        });

        btnDeleteMeals.setOnClickListener(btnView -> {
            DataManager.getInstance().getMeals().removeAllMeals();
            Toast.makeText(this.getContext(), "Successfully deleted all meals", Toast.LENGTH_SHORT).show();
        });
    }
}