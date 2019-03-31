package com.karimsabitov.headmanlog.UI;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.karimsabitov.headmanlog.R;

/**
 * Created by User on 20.03.2019.
 */

public class BaseMainActivity extends AppCompatActivity {

    public void initToolbar(Toolbar toolbar, boolean isBackEnabled) {
        setSupportActionBar(toolbar);

        if(isBackEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        }
    }

    public void initToolbar(Toolbar toolbar, String title, boolean isBackEnabled) {
        setSupportActionBar(toolbar);

        if(isBackEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        }

        getSupportActionBar().setTitle(title);
    }
}
