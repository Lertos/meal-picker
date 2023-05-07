package com.lertos.mealpicker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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

    @Override
    public void onBackPressed() {
        int stackCount = getSupportFragmentManager().getBackStackEntryCount();

        if (stackCount <= 1) {
            Helper.replaceFragment(this, new FragmentSearchMeal(), null);
            binding.bottomNav.setSelectedItemId(R.id.navBtnSearch);
            return;
        }

        //Make sure the bottom nav also switches to the correct button
        FragmentManager.BackStackEntry previousFragment = getSupportFragmentManager().getBackStackEntryAt(stackCount - 2);
        String fragmentName = previousFragment.getName();

        //Pop the stack so the fragment manager switches to the previous fragment
        getSupportFragmentManager().popBackStack();

        //I hate hardcoding, but I'm also not putting try/catch blocks to get the class names via Class.forName("com.etc.theActivity")
        switch (fragmentName) {
            case "FragmentMealPlanner":
                binding.bottomNav.setSelectedItemId(R.id.navBtnPlanner);
                break;
            case "FragmentTagManager":
                binding.bottomNav.setSelectedItemId(R.id.navBtnTags);
                break;
            case "FragmentSettings":
                binding.bottomNav.setSelectedItemId(R.id.navBtnSettings);
                break;
            case "FragmentAddMeal":
                binding.bottomNav.setSelectedItemId(R.id.navBtnAdd);
                break;
            default:
                binding.bottomNav.setSelectedItemId(R.id.navBtnSearch);
                break;
        }
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
