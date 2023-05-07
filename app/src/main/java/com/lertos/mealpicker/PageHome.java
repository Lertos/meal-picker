package com.lertos.mealpicker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lertos.mealpicker.databinding.LayoutPageHomeBinding;
import com.lertos.mealpicker.model.DataManager;

public class PageHome extends AppCompatActivity {

    private LayoutPageHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LayoutPageHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Set the initial screen
        if (DataManager.getInstance().hasChangedDayNightTheme()) {
            DataManager.getInstance().setChangedDayNightTheme(false);
            Helper.replaceFragment(this, new FragmentSettings(), null);
        } else {
            Helper.replaceFragment(this, new FragmentSearchMeal(), null);
        }

        setupBindings();
    }

    private void setupBindings() {
        binding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navBtnSearch:
                    Helper.replaceFragment(this, new FragmentSearchMeal(), null);
                    break;
                case R.id.navBtnAdd:
                    Helper.replaceFragment(this, new FragmentAddMeal(), null);
                    break;
                case R.id.navBtnPlanner:
                    Helper.replaceFragment(this, new FragmentMealPlanner(), null);
                    break;
                case R.id.navBtnTags:
                    Helper.replaceFragment(this, new FragmentTagManager(), null);
                    break;
                case R.id.navBtnSettings:
                    Helper.replaceFragment(this, new FragmentSettings(), null);
                    break;
            }
            return true;
        });
    }

}
