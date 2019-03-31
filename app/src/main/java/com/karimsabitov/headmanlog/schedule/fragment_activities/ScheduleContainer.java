package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karimsabitov.headmanlog.R;

/**
 * Контейнер для отобржаения всех фргаментов расписания
 */

public class ScheduleContainer extends Fragment {

    private final ScheduleAboutFragment mScheduleAboutFragment = new ScheduleAboutFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frgs_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_frgs_container_container, mScheduleAboutFragment)
                    .commit();
        }
    }
}
