package com.karimsabitov.headmanlog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.karimsabitov.headmanlog.UI.BaseMainActivity;
import com.karimsabitov.headmanlog.UI.TabFragment;
import com.karimsabitov.headmanlog.Utils.FileUtils;
import com.karimsabitov.headmanlog.attendance.fragments_activities.AttendanceEditFragment;
import com.karimsabitov.headmanlog.bottomnavigation.FragNavController;
import com.karimsabitov.headmanlog.bottomnavigation.FragmentHistory;
import com.karimsabitov.headmanlog.schedule.fragment_activities.ScheduleAboutFragment;
import com.karimsabitov.headmanlog.students.StudentsAboutFragment;

import io.fabric.sdk.android.Fabric;

public class BottomNavActivity extends BaseMainActivity/*SingleFragmentActivity*/
implements
        TabFragment.FragmentNavigation,
        FragNavController.RootFragmentListener,
        FragNavController.TransactionListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener{

    /*@Override
    protected Fragment createFragment() {
        Fabric.with(this, new Crashlytics());
        FileUtils.makeAppDirectory();
        return new BottomNavFragment();
    }*/

    private FragNavController mNavController;
    private FragmentHistory mFragmentHistory;
    private BottomNavigationView mNavigation;

    private final Fragment mScheduleAboutFragment = new ScheduleAboutFragment();
    private final Fragment mStudentsAboutFragment = new StudentsAboutFragment();
    private final Fragment mAttendanceEditFragment = new AttendanceEditFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FileUtils.makeAppDirectory();

        setContentView(R.layout.activity_bottom_nav);

        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(this);
        mNavigation.setOnNavigationItemReselectedListener(this);

        mFragmentHistory = new FragmentHistory();

        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.content)
                .transactionListener(this)
                .rootFragmentListener(this, 3)
                .build();
    }


    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case FragNavController.TAB1:
                return mScheduleAboutFragment;
            case FragNavController.TAB2:
                return mAttendanceEditFragment;
            case FragNavController.TAB3:
                return mStudentsAboutFragment;
            default: return new Fragment();
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        if (getSupportActionBar() != null && mNavController != null) {
            updateToolbar();
        }
    }

    private void updateToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setDisplayShowHomeEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        if (getSupportActionBar() != null && mNavController != null) {
            updateToolbar();
        }
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
        mNavController.clearStack();
        int position = getTabIndex(menuItem);
        switchTab(position);
    }

    private int getTabIndex(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navigation_group:
                return 2;
            case R.id.navigation_schedule:
                return 0;
            case R.id.navigation_attending:
                return 1;
            default: return -1;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int position = getTabIndex(menuItem);
        mFragmentHistory.push(position);
        switchTab(position);
        return true;
    }

    public void switchTab(int index) {
        mNavController.switchTab(index);
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {

            if (mFragmentHistory.isEmpty()) {
                super.onBackPressed();
            } else {


                if (mFragmentHistory.getStackSize() > 1) {

                    int position = mFragmentHistory.popPrevious();

                    switchTab(position);
                    mNavigation.setSelectedItemId(getItemId(position));

                } else {

                    switchTab(1);

                    mNavigation.setSelectedItemId(getItemId(1));

                    mFragmentHistory.emptyStack();
                }
            }

        }
    }

    private int getItemId(int position) {
        switch (position) {
            case 0:
                return R.id.navigation_schedule;
            case 1:
                return R.id.navigation_attending;
            case 2:
                return R.id.navigation_group;
            default: return -1;
        }
    }
}
