package com.lertos.mealpicker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lertos.mealpicker.databinding.LayoutPageHomeBinding;
import com.lertos.mealpicker.model.DataManager;

public class PageHome extends AppCompatActivity {

    private static boolean hasStarted = false;
    private LayoutPageHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!hasStarted) {
            //Adding for future use
        }

        binding = LayoutPageHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Set the initial screen
        if (DataManager.getInstance().hasChangedDayNightTheme()) {
            DataManager.getInstance().setChangedDayNightTheme(false);
            Helper.replaceFragment(this, new FragmentSettings());
        } else {
            Helper.replaceFragment(this, new FragmentSearchMeal());
        }

        setupBindings();

        if (!hasStarted)
            hasStarted = true;
    }

    private void setupBindings() {
        binding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navBtnSearch:
                    Helper.replaceFragment(this, new FragmentSearchMeal());
                    break;
                case R.id.navBtnAdd:
                    Helper.replaceFragment(this, new FragmentAddMeal());
                    break;
                case R.id.navBtnPlanner:
                    Helper.replaceFragment(this, new FragmentMealPlanner());
                    break;
                case R.id.navBtnTags:
                    Helper.replaceFragment(this, new FragmentTagManager());
                    break;
                case R.id.navBtnSettings:
                    Helper.replaceFragment(this, new FragmentSettings());
                    break;
            }
            return true;
        });
    }

}
