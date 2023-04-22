package com.lertos.mealpicker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lertos.mealpicker.databinding.LayoutPageHomeBinding;

public class PageHome extends AppCompatActivity {

    private LayoutPageHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LayoutPageHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Set the initial screen
        replaceFragment(new FragmentSearchMeal());

        setupBindings();
    }

    private void setupBindings() {
        binding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navBtnSearch:
                    replaceFragment(new FragmentSearchMeal());
                    break;
                case R.id.navBtnAdd:
                    replaceFragment(new FragmentAddMeal());
                    break;
                case R.id.navBtnPlanner:
                    replaceFragment(new FragmentMealPlanner());
                    break;
                case R.id.navBtnTags:
                    replaceFragment(new FragmentTagManager());
                    break;
                case R.id.navBtnSettings:
                    replaceFragment(new FragmentSettings());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}
