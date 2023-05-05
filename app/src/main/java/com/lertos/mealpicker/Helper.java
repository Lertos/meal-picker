package com.lertos.mealpicker;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.FragmentActivity;

public class Helper {

    public static void hideKeyboard(FragmentActivity activity) {
        //Checks if there is an active keyboard or not
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
