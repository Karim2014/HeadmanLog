package com.karimsabitov.headmanlog.attendance.fragments_activities;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karimsabitov.headmanlog.Utils.CalendarParser;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.adapters.AttendanceEditFragmentAdapterCouples;
import com.karimsabitov.headmanlog.attendance.model.AttDay;
import com.karimsabitov.headmanlog.attendance.model.AttSingle;

import java.util.Date;

/**
 * Created by User on 04.10.2018.
 */

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private Date mAttDay;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mTitle;

    private static final String ATTDAY = "ATTDAY";
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN){
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    private void instantiateFragment() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new AdapterCreaterTask().execute();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_attendance_edit, null);
        dialog.setContentView(view);

        mRecyclerView = view.findViewById(R.id.bottom_sheet_attendance_edit_recycler_v);
        mProgressBar = view.findViewById(R.id.bottom_sheet_attendance_edit_progress);
        mTitle = view.findViewById(R.id.bottom_sheet_attendance_edit_title);
        ImageView imageView = view.findViewById(R.id.bottom_sheet_attendance_edit_cancel);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        instantiateFragment();

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        params.height = CoordinatorLayout.LayoutParams.MATCH_PARENT;
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior dbehavior = ((BottomSheetBehavior) behavior);
            dbehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
            dbehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public static BottomSheetFragment newInstance(Date attDay) {

        Bundle args = new Bundle();

        args.putSerializable(ATTDAY, attDay);

        BottomSheetFragment fragment = new BottomSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class AdapterCreaterTask extends AsyncTask<Void, Void, AttendanceEditFragmentAdapterCouples>{

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected AttendanceEditFragmentAdapterCouples doInBackground(Void... params) {
            mAttDay = (Date) getArguments().getSerializable(ATTDAY);

            AttDay attDay = AttSingle.get(getContext()).getAttDay(mAttDay);

            if (attDay == null) {
                return null;
            }

            return new AttendanceEditFragmentAdapterCouples(getContext(), attDay.getCouples(), attDay.getStudents());
        }

        @Override
        protected void onPostExecute(AttendanceEditFragmentAdapterCouples attendanceEditFragmentAdapterCouples) {
            if (attendanceEditFragmentAdapterCouples == null) {
                showError();
            }
            mRecyclerView.setAdapter(attendanceEditFragmentAdapterCouples);
            mTitle.setText(CalendarParser.formatDate("dd.MM.yyyy EEEE", mAttDay));
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void showError() {
        // TODO: 13.12.2018 Сделать в макете сообщение об ошибке
    }

}
