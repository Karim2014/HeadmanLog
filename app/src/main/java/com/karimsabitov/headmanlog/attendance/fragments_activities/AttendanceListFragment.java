package com.karimsabitov.headmanlog.attendance.fragments_activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.karimsabitov.headmanlog.Utils.CalendarParser;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.UI.RecyclerViewEmptySupport;
import com.karimsabitov.headmanlog.adapters.AttendanceListFragmentAdapter;
import com.karimsabitov.headmanlog.attendance.model.AttSingle;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 26.09.2018.
 */

public class AttendanceListFragment extends Fragment{

    private int mSelYear = 2018;
    private int mSelMonth = 1;
    private TextView mMonthPick;
    private RecyclerViewEmptySupport mRecyclerView;
    private AttendanceListFragmentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_list, container, false);

        mRecyclerView = view.findViewById(R.id.fragment_attendance_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setEmptyView(view.findViewById(R.id.fragment_attendance_list_empty_view));
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fade_in);
        mRecyclerView.setLayoutAnimation(animationController);

        updateUI();

        Calendar today = Calendar.getInstance();
        mSelYear = today.get(Calendar.YEAR);
        mSelMonth = today.get(Calendar.MONTH);

        Toolbar toolbar = view.findViewById(R.id.fragment_attendance_list_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mMonthPick = view.findViewById(R.id.fragment_attendance_list_month_pick_tv);

        setSelMonthYear(Calendar.getInstance());

        mMonthPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        getContext(),
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                setSelMonthYear(selectedYear, selectedMonth);
                            }
                        },
                        mSelYear, mSelMonth);

                builder
                        .setMinYear(2018)
                        .setMaxYear(2099)
                        .build()
                        .show();
            }
        });

        return view;
    }

    private void updateUI() {
        List<Date> days = AttSingle.get(getContext()).getAttDaysDate(mSelYear, mSelMonth);
        if (mAdapter == null) {
            mAdapter = new AttendanceListFragmentAdapter(getContext(), days, getFragmentManager());
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setDays(days);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scheduleLayoutAnimation();
        }
    }

    private static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        dateFormatSymbols.setMonths(CalendarParser.MONTHS);
        sdf.setDateFormatSymbols(dateFormatSymbols);
        return sdf.format(date);
    }

    private void updateToolBar() {
        mMonthPick.setText(
                formatDate(CalendarParser.getDate(mSelYear, mSelMonth, 1))
        );
        updateUI();
    }

    private void setSelMonthYear(Calendar day) {
        mSelMonth = day.get(Calendar.MONTH);
        mSelYear = day.get(Calendar.YEAR);
        updateToolBar();
    }

    private void setSelMonthYear(int year, int month) {
        mSelMonth = month;
        mSelYear = year;
        updateToolBar();
    }

    public static AttendanceListFragment newInstance() {

            /*Bundle args = new Bundle();

            AttendanceListFragment fragment = new AttendanceListFragment();
            fragment.setArguments(args);*/
        return new AttendanceListFragment();
    }

}
