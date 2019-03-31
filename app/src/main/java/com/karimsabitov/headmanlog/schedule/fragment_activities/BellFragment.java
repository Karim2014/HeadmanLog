package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.karimsabitov.headmanlog.Utils.CalendarParser;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.UI.RecyclerViewEmptySupport;
import com.karimsabitov.headmanlog.adapters.BellFragmentAdapter;
import com.karimsabitov.headmanlog.schedule.models.Bell;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.sql.Time;
import java.util.List;

/**
 * Created by User on 11.07.2018.
 */

public class BellFragment extends Fragment implements RangeTimePickerDialog.OnTimeSelectedListener /*implements View.OnClickListener*/ {

    private static final int REQUEST_ADD_BELL = 0;
    private static final String HAS_OPTIONS_MENU = "Has options meun";
    private BellFragmentAdapter mBellAdapter;
    private RecyclerViewEmptySupport mRecyclerView;
    private ScheduleSingle mSingle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSingle = ScheduleSingle.get(getContext());
        boolean hasOptionsMenu = getArguments().getBoolean(HAS_OPTIONS_MENU);
        setHasOptionsMenu(hasOptionsMenu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_bell, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.schedule_bell_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setEmptyView(view.findViewById(R.id.empty_view));
        setupAdapter();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupAdapter() {
        List<Bell> bells = mSingle.getBells();
        if (mBellAdapter == null) {
            mBellAdapter = new BellFragmentAdapter(getContext(), bells);
            mRecyclerView.setAdapter(mBellAdapter);
        }else{
            mRecyclerView.setAdapter(mBellAdapter);
            mBellAdapter.setBells(bells);
            mBellAdapter.notifyDataSetChanged();
        }
    }

    public void addItem() {
        RangeTimePickerDialog timePickerDialog = RangeTimePickerDialog.newInstance(mBellAdapter.getItemCount());
        timePickerDialog.setCallback(this);
        timePickerDialog.show(getFragmentManager(), "");
    }

    public void deleteItem() {
        int index  = mBellAdapter.getItemCount()-1;
        mSingle.removeBell(index);
        mBellAdapter.removeBell(index);
        mBellAdapter.notifyItemRemoved(index);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.schedule_tab_view_pager_bell, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add:
                addItem();
                return false;
            case R.id.menu_item_delete:
                new AlertDialog.Builder(getContext())
                        .setTitle("Удалить?")
                        .setMessage("Вы действительно хотите удалить последнюю пару?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItem();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()
                        .show();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSelected(int hourStart, int minStart, int hourEnd, int minEnd) {
        Bell bell = new Bell(mBellAdapter.getItemCount(), new Time(CalendarParser.getTime(hourStart, minStart).getTime()), new Time(CalendarParser.getTime(hourEnd, minEnd).getTime()));
        mBellAdapter.addBell(bell);
        mSingle.addBell(bell);
        mBellAdapter.notifyItemInserted(mBellAdapter.getItemCount());
    }

    public static BellFragment newInstance(boolean hasOptionsMenu) {

        Bundle args = new Bundle();
        args.putBoolean(HAS_OPTIONS_MENU, hasOptionsMenu);
        BellFragment fragment = new BellFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*private Date mBellStartTime;
    private Date mBellEndTime;
    private boolean isAdapterCreated = false;

    private RecyclerView mRecyclerView;
    private BellFragmentAdapter mBellAdapter;
    private BottomSheetBehavior mBehavior;
    private CoordinatorLayout mCoordinatorLayout;

    SimpleDateFormat sdf;
    private ScheduleSingle mSingle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_bell, container, false);

        mRecyclerView = view.findViewById(R.id.schedule_bell_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSingle = ScheduleSingle.get(getContext());

        loadBells(); // загрузить расписание звонко

        LinearLayout llBottomSheet = view.findViewById(R.id.bottom_sheet_fragment_edit_schedule_root);
        mBehavior = BottomSheetBehavior.from(llBottomSheet);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        Button returnBack = view.findViewById(R.id.bottom_sheet_fragment_edit_schedule_add_btn);
        returnBack.setOnClickListener(this);

        Button add = view.findViewById(R.id.btn_fragment_edit_schedule_bottom_sheet_del);
        add.setOnClickListener(this);

        mCoordinatorLayout = view.findViewById(R.id.coord_lay_fragment_schedule_bell);

        sdf = new SimpleDateFormat("HH:mm");

        return view;
    }

    private void loadBells(){
        ScheduleSingle single = ScheduleSingle.get(getActivity());
        List<Bell> bells = single.getBells();
        if (mBellAdapter == null) {
            mBellAdapter = new BellFragmentAdapter(getContext(), bells);
            mRecyclerView.setAdapter(mBellAdapter);
            isAdapterCreated = true;
        }else{
            mBellAdapter.setBells(bells);
            mBellAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_sheet_fragment_edit_schedule_add_btn:
                if (mBellAdapter.getItemCount() >= 10){
                    Toast.makeText(getContext(), R.string.toast_exceed, Toast.LENGTH_SHORT).show();
                    return;
                }
                Bell bell = new Bell(mBellAdapter.getItemCount()+1, new Date(), new Date());
                mSingle.addBell(bell); //todo сделать тут, чтоб +10 минут
                mBellAdapter.addBell(bell);
                mBellAdapter.notifyItemInserted(mBellAdapter.getItemCount());
                break;
            case R.id.btn_fragment_edit_schedule_bottom_sheet_del:
                int index = mBellAdapter.getItemCount()-1;
                if (index >= 0) {
                    mSingle.removeBell(mBellAdapter.getBell(index));
                    mBellAdapter.removeBell(index);
                    mBellAdapter.notifyItemRemoved(index);
                }
                break;
        }
    }
*/
}
