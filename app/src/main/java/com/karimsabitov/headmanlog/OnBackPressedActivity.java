package com.karimsabitov.headmanlog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by User on 28.10.2018.
 */

public class OnBackPressedActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener onBackPressedListener = null;
        for (Fragment fragment: fm.getFragments()){
            if (fragment instanceof OnBackPressedListener) {
                onBackPressedListener = (OnBackPressedListener) fragment;
                break;
            }
        }

        if (onBackPressedListener != null){
            onBackPressedListener.OnBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
