package com.karimsabitov.headmanlog.attendance.fragments_activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.Utils.CalendarParser;
import com.karimsabitov.headmanlog.adapters.AttendanceEditFragmentAdapterCouples;
import com.karimsabitov.headmanlog.attendance.model.AttDay;
import com.karimsabitov.headmanlog.attendance.model.AttSingle;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

import static com.karimsabitov.headmanlog.Utils.Utils.hideView;
import static com.karimsabitov.headmanlog.Utils.Utils.showView;

/**
 * Created by User on 07.09.2018.
 */

// TODO: 24.03.2019 Навести порядок в коде !!!
public class AttendanceEditFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_LIST = 0;
    private static final String ATTDAY_KEY = "ATTDAY_KEY";
    private RecyclerView mRecyclerView;
    private AttendanceEditFragmentAdapterCouples mAttendanceAdapter;
    private AttDay mAttDay;
    private Button mAddButton;
    private Date mSelectedDate;

    private AttSingle mAttSingle;
    private View mEmptyView;
    private Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attendance_edit, container, false);

        TextView datePickET = v.findViewById(R.id.fragment_attendance_edit_date_pick_edit_text);
        mRecyclerView = v.findViewById(R.id.fragment_attendance_edit_recycler_v);
        mEmptyView = v.findViewById(R.id.fragment_attendance_edit_empty_v);
        //mToolbar = v.findViewById(R.id.fragment_attendance_edit_tool_bar);
        mAddButton = v.findViewById(R.id.fragment_attendance_edit_add_btn);

        datePickET.setOnClickListener(this);
        datePickET.setText(formatDate(Calendar.getInstance()));

        mSelectedDate = new Date();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fade_in);
            mRecyclerView.setLayoutAnimation(animationController);
        }

        mAttSingle = AttSingle.get(getContext());

        mAttDay = mAttSingle.getAttDay(mSelectedDate);// TODO: 14.09.2018 брать из сериализации сохранения

        //updateToolbar();

        mAddButton.setOnClickListener(this);

        updateUI();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAttDay = mAttSingle.getAttDay(mSelectedDate);
        updateUI();
        //updateToolbar();
    }

    private void updateToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(mToolbar);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_attendance_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Все отметки")){
            startActivity(new Intent(getContext(), AttendanceListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        showAddButton(false);
        mRecyclerView.setVisibility(View.VISIBLE);
        if (mAttDay == null) {
            showAddButton(true);
            mRecyclerView.setVisibility(View.INVISIBLE);
            return;
        }
        HashMap<Integer, List<String>> uuids = mAttDay.getCouples();
        if (mAttendanceAdapter == null){
            new FrgCreateTask().execute();
            showView(mRecyclerView);
            showAddButton(false);
        } else {
            mAttendanceAdapter.setMap(uuids, mAttDay.getStudents());
            mAttendanceAdapter.notifyDataSetChanged();
            mRecyclerView.scheduleLayoutAnimation();
        }
    }

    public void updateAdapter(int coupleNum){
        mAttendanceAdapter.setMap(mAttDay.getCouples(), mAttDay.getStudents());
        mAttendanceAdapter.notifyItemChanged(coupleNum);
    }

    private String formatDate(int year, int monthOfYear, int dayOfMonth){
        Calendar date = Calendar.getInstance();
        date.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy EEEE");
        return sdf.format(date.getTime());
    }

    private String formatDate(Calendar date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy EEEE");
        return sdf.format(date.getTime());
    }

    private String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy EEEE");
        return sdf.format(date);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_LIST){
            List<String> uuids = (List<String>) data.getSerializableExtra(AttendanceEditDialogFragment.SELECTED_UUID_LIST);//AttendanceEditDialogFragment.createFromArray(uuids0);
            int coupleNum = data.getIntExtra(AttendanceEditDialogFragment.COUPLE_NUM, 0);
            mAttDay.getCouples().put(coupleNum, uuids);
            mAttSingle.updateAttDay(mAttDay);
            updateAdapter(coupleNum);
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.fragment_attendance_edit_date_pick_edit_text:
                int[] ymd = CalendarParser.getDates(mSelectedDate);
                DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        ((TextView) v).setText(formatDate(year, monthOfYear, dayOfMonth));
                        (com.wdullaer.materialdatetimepicker.Utils.getPulseAnimator(v, 1.1f, 1.1f)).start();
                        mSelectedDate = CalendarParser.getDate(year, monthOfYear, dayOfMonth);
                        mAttDay = mAttSingle.getAttDay(mSelectedDate);
                        updateUI();
                    }
                },
                        ymd[CalendarParser.YEAR], ymd[CalendarParser.MONTH], ymd[CalendarParser.DAY_OF_MONTH]
                );
                dialog.show(getActivity().getFragmentManager(), "DATE_PICK");
                break;
            case R.id.fragment_attendance_edit_add_btn:
                /*try {
                    checkAttDay(mSelectedDate);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                showAddButton(false);
                updateUI();*/
                showDialog(); // показать диалог и создать AttDay
                break;
        }
    }

    private void showAddButton(boolean state){
        mEmptyView.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    /**
     * Создает диалог для добавления нового дня отметок
     */
    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_num_picker, null);

        final MaterialNumberPicker numberPicker = view.findViewById(R.id.dialog_num_picker_picker);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setTitle(R.string.how_much_couples)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int N = numberPicker.getValue();
                        mAttDay = new AttDay(N, mSelectedDate);
                        mAttSingle.addAttDay(mAttDay);
                        updateUI();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();

    }


    public class FrgCreateTask extends AsyncTask<View, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hideView(mRecyclerView);
        }

        @Override
        protected Void doInBackground(View... params) {
            mAttendanceAdapter = new AttendanceEditFragmentAdapterCouples(getContext(), mAttDay.getCouples(), mAttDay.getStudents(), AttendanceEditFragment.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRecyclerView.setAdapter(mAttendanceAdapter);
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showView(mRecyclerView);
                }
            }, 100);
        }
    }

    /*public static AttendanceEditFragment newInstance(AttDay attDay) {

        Bundle args = new Bundle();
        args.putSerializable(ATTDAY_KEY, (Serializable) attDay);

        AttendanceEditFragment fragment = new AttendanceEditFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

}
