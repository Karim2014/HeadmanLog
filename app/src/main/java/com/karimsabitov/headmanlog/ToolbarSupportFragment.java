package com.karimsabitov.headmanlog;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

/**
 * Created by User on 29.10.2018.
 */

public class ToolbarSupportFragment extends Fragment {

    Toolbar mToolbar;

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
    }

}
