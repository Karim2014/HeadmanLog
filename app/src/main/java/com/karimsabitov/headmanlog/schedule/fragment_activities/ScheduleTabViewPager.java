package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.karimsabitov.headmanlog.R;

/**
 * Created by User on 13.07.2018.
 */

public class ScheduleTabViewPager extends AppCompatActivity {

    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    FragmentManager mFragmentManager;

    // todo устанавливать результат

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_tab_pager);

        /*mToolbar = findViewById(R.id.schedule_pager_toolbar);
        setSupportActionBar(mToolbar);*/

       /* ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        mViewPager = findViewById(R.id.schedule_pager_view_pager);
        mFragmentManager = getSupportFragmentManager();
        mViewPagerAdapter = new ViewPagerAdapter(mFragmentManager);
        mViewPager.setAdapter(mViewPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.schedule_pager_tablayout);
        tabLayout.setupWithViewPager(mViewPager);
        setResult(RESULT_OK);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule_tab_view_pager, menu);
        return super.onCreateOptionsMenu(menu);
    }


    }*/

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       Fragment fragment = mViewPagerAdapter.getItem(mViewPager.getCurrentItem());
       if (!(fragment instanceof BellFragment)) {
           ((IListsFragment) fragment).addItem(getSupportFragmentManager());
       }
       return super.onOptionsItemSelected(item);
   }
    //setResult(ScheduleAboutFragment.RESULT_TOOLBAR);

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        BellFragment mBellFragment = BellFragment.newInstance(true);
        TeachersListFragment mTeachersListFragment = new TeachersListFragment();
        SubjectsListFragment mSubjectsListFragment = new SubjectsListFragment();
        RoomsListFragment mRoomsListFragment = new RoomsListFragment();

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return mBellFragment;
                case 1:
                    return mTeachersListFragment;
                case 2:
                    return mSubjectsListFragment;
                case 3:
                    return mRoomsListFragment;
                default:
                    return new Fragment();
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Звонки";
                case 1:
                    return "Преподаватели";
                case 2:
                    return "Дисциплины";
                case 3:
                    return "Аудитории";
                default:
                    return "";
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
