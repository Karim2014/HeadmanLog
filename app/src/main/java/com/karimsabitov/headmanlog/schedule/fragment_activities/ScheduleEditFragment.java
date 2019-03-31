package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karimsabitov.headmanlog.Utils.CalendarParser;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.adapters.ScheduleEditFragmentAdapter;
import com.karimsabitov.headmanlog.schedule.models.Schedule;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.util.List;

/**
 * Created by User on 10.08.2018.
 */


public class ScheduleEditFragment extends Fragment implements View.OnClickListener {


    public static final String EXTRA_SCHEDULE_ID = "EXTRA_SCHEDULE_ID";
    public static final String EXTRA_DAY_NUM = "EXTRA_DAY_NUM";
/*
    private boolean mChanged = false;
    private int mDayNum;

    */

    private ScheduleSingle mSingle;
    List<Schedule> mSchedulesUp;
    List<Schedule> mSchedulesDown;
    private ScheduleEditFragmentAdapter mAdapter;
    private int mDayNum;
    private RecyclerView mRecyclerView;
    private LinearLayout mLinearLayout;
    private TextView mDayOfTheWeekTV;
    private Button mAddBtn;
    private Button mDelBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDayNum = getArguments().getInt(EXTRA_SCHEDULE_ID);
        }
        mSingle = ScheduleSingle.get(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_schedule, container, false);
        mRecyclerView = view.findViewById(R.id.dialog_edit_schedule_recycler_view);
        mLinearLayout = view.findViewById(R.id.fragment_edit_schedule_coord_lay);
        mDayOfTheWeekTV = view.findViewById(R.id.fragment_edit_schedule_day_tv);
        mDelBtn = view.findViewById(R.id.bottom_sheet_fragment_edit_schedule_add_btn);
        mAddBtn = view.findViewById(R.id.btn_fragment_edit_schedule_bottom_sheet_del);

        new ContentLoader().execute();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (((AppCompatActivity) getActivity()) != null) {
            ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            toolbar.setTitle("Редактирование");
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDayOfTheWeekTV.setText(CalendarParser.DAYS[mDayNum]);
        mAddBtn.setOnClickListener(this);
        mDelBtn.setOnClickListener(this);
    }

    public static ScheduleEditFragment newInstance(int DayNum) {

        Bundle args = new Bundle();

        args.putInt(EXTRA_SCHEDULE_ID, DayNum);

        ScheduleEditFragment fragment = new ScheduleEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fragment_edit_schedule_bottom_sheet_del:
                final int index = mSchedulesUp.size()-1;
                if (index >= 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Удалить?")
                            .setMessage(getString(R.string.quest_del_couple, (index+1)))
                            .setNegativeButton(android.R.string.cancel, null)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSingle.removeSchedule(mSchedulesUp.get(index));
                                    mSingle.removeSchedule(mSchedulesDown.get(index));
                                    mAdapter.removeSchedule(index);
                                    mAdapter.notifyItemRemoved(index);
                                }
                            })
                    .create()
                    .show();
                }
                break;
            case R.id.bottom_sheet_fragment_edit_schedule_add_btn:
                if (mSchedulesUp.size() < 10 && mSchedulesDown.size() < 10){
                    Schedule schUp = new Schedule("", "", "", mSchedulesUp.size(), true, false, false, mDayNum);
                    Schedule schDown = new Schedule("", "", "", mSchedulesDown.size(), true, true, true, mDayNum);
                    mSingle.addSchedule(schUp);
                    mSingle.addSchedule(schDown);
                    mAdapter.addSchedule(schUp, schDown);
                    mAdapter.notifyItemInserted(mSchedulesUp.size() - 1);
                } else {
                    Snackbar.make(mLinearLayout, R.string.count_overflow, Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /*@Override
    public void OnBackPressed() {
        if (mAdapter.isChanged() || mChanged) {
            getActivity().setResult(Activity.RESULT_OK);
        }
        getActivity().finish();
    }*/


    private class ContentLoader extends AsyncTask<Void, Void, ScheduleEditFragmentAdapter> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ScheduleEditFragmentAdapter doInBackground(Void... params) {
            mSchedulesUp = mSingle.getSchedules(mDayNum, false);
            mSchedulesDown = mSingle.getSchedules(mDayNum, true);
            return new ScheduleEditFragmentAdapter(getContext(), mDayNum, mSchedulesUp, mSchedulesDown);
        }

        @Override
        protected void onPostExecute(ScheduleEditFragmentAdapter aVoid) {
            super.onPostExecute(aVoid);
            mAdapter = aVoid;
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
