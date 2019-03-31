package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.ToolbarSupportFragment;
import com.karimsabitov.headmanlog.UI.RecyclerViewEmptySupport;
import com.karimsabitov.headmanlog.adapters.ScheduleDayFragmentAdapter;
import com.karimsabitov.headmanlog.schedule.models.Schedule;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.util.List;

/**
 * Класс для отображения списка и автомтического расписания.
 */

public class ScheduleDayFragment extends ToolbarSupportFragment {

    private static final String SCHEDULES = "Schedules";
    private static final String DAY_NUM = "DAY_NUM";
    private static final int REQUEST_UPDATE = 0;

    private RecyclerView mRecyclerView;
    private ScheduleDayFragmentAdapter mAdapter;
    private ScheduleSingle mSingle;

    private int mDayNum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) mDayNum = getArguments().getInt(DAY_NUM);
        mSingle = ScheduleSingle.get(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_day, container, false);
        mRecyclerView = view.findViewById(R.id.list_item_schedule_day_day_couples);

        if (mAdapter == null)
            new AdapterCreator().execute();
        else mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public void updateList() {
        new DataSelector().execute();
        //Toast.makeText(getContext(), "Обновление", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ((RecyclerViewEmptySupport) mRecyclerView).setEmptyView(view.findViewById(R.id.list_item_schedule_day_empty_view));

        ((TextView) view.findViewById(R.id.list_item_schedule_day_day_title)).setText(getContext().getResources().getStringArray(R.array.days_of_the_week)[mDayNum]);

        view.findViewById(R.id.list_item_schedule_day_options)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(ScheduleEditActivity.newIntent(getContext(), mDayNum), REQUEST_UPDATE);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE) {
            updateList();
        }
    }

    public static ScheduleDayFragment newInstance(int day) {

        Bundle args = new Bundle();

        args.putInt(DAY_NUM, day);

        ScheduleDayFragment fragment = new ScheduleDayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class AdapterCreator extends AsyncTask<Void, Void, ScheduleDayFragmentAdapter> {

        @Override
        protected ScheduleDayFragmentAdapter doInBackground(Void... params) {
            List<Schedule> schedulesUp = mSingle.getSchedules(mDayNum, false);
            List<Schedule> schedulesDown = mSingle.getSchedules(mDayNum, true);
            return new ScheduleDayFragmentAdapter(getContext(), schedulesUp, schedulesDown);
        }

        @Override
        protected void onPostExecute(ScheduleDayFragmentAdapter scheduleDayFragmentAdapter) {
            mAdapter = scheduleDayFragmentAdapter;
            mRecyclerView.setAdapter(mAdapter);
            //mCouple.animate().alpha(1).start();
            super.onPostExecute(scheduleDayFragmentAdapter);
        }
    }

    private class DataSelector extends AsyncTask<Void, Void, SparseArray<List<Schedule>>> {

        @Override
        protected SparseArray<List<Schedule>> doInBackground(Void... params) {
            SparseArray<List<Schedule>> map = new SparseArray<>();
            map.put(0, ScheduleSingle.get(getContext()).getSchedules(mDayNum, false));
            map.put(1, ScheduleSingle.get(getContext()).getSchedules(mDayNum, true));
            return map;
        }

        @Override
        protected void onPostExecute(SparseArray<List<Schedule>> listSparseArray) {
            mAdapter.setData(listSparseArray);
            mAdapter.notifyDataSetChanged();
        }
    }
    
}
