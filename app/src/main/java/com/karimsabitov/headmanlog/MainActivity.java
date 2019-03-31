package com.karimsabitov.headmanlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.karimsabitov.headmanlog.attendance.fragments_activities.AttendanceEditFragment;
import com.karimsabitov.headmanlog.schedule.fragment_activities.ScheduleAboutFragment;
import com.karimsabitov.headmanlog.students.StudentAboutActivity;

/**
 * Created by User on 23.03.2019.
 */

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    ViewPager mViewPager;
    FragmentAdapter mFragmentAdapter;
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager_main);
        mToolbar = findViewById(R.id.toolbar_main);
        TabLayout tabLayout = findViewById(R.id.tab_layout_main);

        setSupportActionBar(mToolbar);

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(this);
        setupTabs(tabLayout);
    }

    private void setupTabs(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_notifications_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_check_black_24dp);
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_group_black_24dp);
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                mToolbar.setTitle("Расписание занятий");
                break;
            case 1:
                mToolbar.setTitle("Посещаемость");
                break;
            /*case 2:
                mToolbar.setTitle("Группа");*/
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Fragment fragment = mFragmentAdapter.getItem(mViewPager.getCurrentItem());
        //if (fragment instanceof ScheduleAboutFragment) {
            getMenuInflater().inflate(R.menu.fragment_schedule_about, menu);
        //}
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.super_group) {
            startActivity(new Intent(this, StudentAboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private class FragmentAdapter extends FragmentPagerAdapter {

        Fragment mScheduleFragment = new ScheduleAboutFragment();
        Fragment mAttendanceFragment = new AttendanceEditFragment();
        //Fragment mStudentsFragment = new StudentsAboutFragment();

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0: return mScheduleFragment;
                case 1: return mAttendanceFragment;
                //case 2: return mStudentsFragment;
                default: return new Fragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
