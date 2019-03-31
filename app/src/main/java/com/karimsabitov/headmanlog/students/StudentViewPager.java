package com.karimsabitov.headmanlog.students;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.karimsabitov.headmanlog.R;

import java.util.List;
import java.util.UUID;

/**
 * Created by User on 08.02.2018.
 */

public class StudentViewPager extends AppCompatActivity {

    public static final String EXTRA_STUDENT_ID = "com.firstproject.karim.test3.student_id";

    private ViewPager mViewPager;
    private List<Student> mStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pager);

        UUID studentId =  (UUID) getIntent().getSerializableExtra(EXTRA_STUDENT_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_student_pager_view_pager);
        mStudents = StudentGroup.get(this).getStudents();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Student student = mStudents.get(position);
                return StudentFragment.newInstance(student.getId());
            }

            @Override
            public int getCount() {
                return mStudents.size();
            }
        });

        for (int i = 0; i < mStudents.size(); i++) {
            if (mStudents.get(i).getId().equals(studentId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID studentID){
        Intent intent = new Intent(packageContext, StudentViewPager.class);
        intent.putExtra(EXTRA_STUDENT_ID, studentID);
        return intent;
    }
}
