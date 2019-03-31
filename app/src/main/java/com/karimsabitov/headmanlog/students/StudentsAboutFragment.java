package com.karimsabitov.headmanlog.students;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.UI.TabFragment;

import java.util.List;


/**
 * Created by User on 22.07.2018.
 */

// todo показывать сколько дисциплин, учителей читается

public class StudentsAboutFragment extends TabFragment {

    Toolbar mToolbar;
    TextView mStdCount,
        mStdExc,
        mStdGood,
        mStdBad;

    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students_about, container, false);

        mStdCount = view.findViewById(R.id.fragment_students_about_textview_std_count);
        mStdExc = view.findViewById(R.id.fragment_students_about_textview_std_exc);
        mStdGood = view.findViewById(R.id.fragment_students_about_textview_std_good);
        mStdBad = view.findViewById(R.id.fragment_students_about_textview_std_bad);

        mCollapsingToolbarLayout = view.findViewById(R.id.fragment_students_about_col_toolbar_lay);

        (view.findViewById(R.id.fragment_students_about_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StudentListActivity.class));
            }
        });

        mToolbar = (Toolbar) view.findViewById(R.id.fragment_students_about_toolbar);
        //setToolbar(mToolbar);
        //((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mToolbar.setTitle(StudentGroup.get(getContext()).getGroup());

        final TextView textView = view.findViewById(R.id.fragment_students_about_text_view_my_group);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StudentGroup single = StudentGroup.get(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_group, null);
                final EditText title = view.findViewById(R.id.dialog_group_title_edit_text);
                title.setText(single.getGroup());

                builder
                        .setTitle(R.string.title_enter_group_title)
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!(title.getText().toString().equals(""))) {
                                    single.setGroup(title.getText().toString());
                                    mCollapsingToolbarLayout.setTitle(title.getText());
                                }

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()
                        .show();
            }
        });

        updateCardAbout(StudentGroup.get(getContext()).getStudents());

        return view;
    }

    private void updateCardAbout(List<Student> students) {
        int[] arr = {0,0,0};
        mStdCount.setText(getString(R.string.title_students_count, students.size()));
        for (Student student: students) {
            switch (student.getMarkId()){
                case 0:
                    arr[0]++;
                    break;
                case 1:
                    arr[1]++;
                    break;
                case 2:
                    arr[2]++;
                    break;

            }
        }
        mStdExc.setText(getString(R.string.title_std_excelent, arr[0]));
        mStdGood.setText(getString(R.string.title_std_good, arr[1]));
        mStdBad.setText(getString(R.string.title_std_bad, arr[2]));
    }

}

