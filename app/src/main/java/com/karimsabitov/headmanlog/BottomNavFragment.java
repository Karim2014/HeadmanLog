package com.karimsabitov.headmanlog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.karimsabitov.headmanlog.attendance.fragments_activities.AttendanceEditFragment;
import com.karimsabitov.headmanlog.schedule.fragment_activities.ScheduleAboutFragment;
import com.karimsabitov.headmanlog.students.StudentsAboutFragment;

/**
 * Created by User on 30.01.2019.
 */

public class BottomNavFragment extends Fragment {

    private FragmentManager mFragmentManager;
    final Fragment mScheduleAboutFragment = new ScheduleAboutFragment();
    final Fragment mStudentsAboutFragment = new StudentsAboutFragment();
    final Fragment mAttendanceEditFragment = new AttendanceEditFragment();
    Fragment active = mAttendanceEditFragment;
    private boolean mIsAdded = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_group:
                    showFragment(mStudentsAboutFragment);
                    break;
                case R.id.navigation_schedule:
                    showFragment(mScheduleAboutFragment);
                    break;
                case R.id.navigation_attending:
                    showFragment(mAttendanceEditFragment);
                    break;
            }
            return true;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bottom_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mFragmentManager = getFragmentManager();

        int item = R.id.navigation_attending;

        if (!mIsAdded) {
            mFragmentManager.beginTransaction().add(R.id.content, mStudentsAboutFragment, "2").hide(mStudentsAboutFragment).commit();
            mFragmentManager.beginTransaction().add(R.id.content, mScheduleAboutFragment, "1").hide(mScheduleAboutFragment).commit();
            mFragmentManager.beginTransaction().add(R.id.content, mAttendanceEditFragment, "3")/*hide()*/.commit();
            mIsAdded = true;
        }

        BottomNavigationView navigation = view.findViewById(R.id.navigation);
        navigation.setSelectedItemId(item);
        navigation.setOnNavigationItemSelectedListener(mListener);

    }

    private void showFragment(Fragment fragment){
        mFragmentManager.beginTransaction()
                .hide(active)
                .show(fragment).commit();
        active = fragment;
        fragment.onResume();
    }
}
