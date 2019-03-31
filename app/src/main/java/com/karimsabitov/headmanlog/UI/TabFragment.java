package com.karimsabitov.headmanlog.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by User on 20.03.2019.
 */

public class TabFragment extends Fragment {

    public static final String ARGS_INSTANCE = "com.karimsabitov.ui";

    private FragmentNavigation mFragmentNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public FragmentNavigation getFragmentNavigation() {
        return mFragmentNavigation;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
    }

}
