package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.karimsabitov.headmanlog.Utils.CalendarParser;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.schedule.models.Bell;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.util.Calendar;

/**
 * Created by User on 11.03.2019.
 */

public class RangeTimePickerDialog extends DialogFragment {

    private static final String KEY_COUPLE_NUM = "COUPLE_NUM";
    public static String HOUR_START = "hourStart";
    public static String MINUTE_START = "minuteStart";
    public static String HOUR_END = "hourEnd";
    public static String MINUTE_END = "minuteEnd";

    private TabLayout mTabLayout;
    private TimePicker mTimePickerStart, mTimePickerEnd;

    private boolean is24HourView = true;
    private boolean mValidateRange = true;

    private Calendar currentTime = Calendar.getInstance();
    private int initialStarHour = currentTime.get(Calendar.HOUR_OF_DAY);
    private int initialStartMinute = currentTime.get(Calendar.MINUTE);
    private int initialEndHour = currentTime.get(Calendar.HOUR_OF_DAY);
    private int initialEndMinute = currentTime.get(Calendar.MINUTE);
    private int mIconStart = R.drawable.ic_timer_black_24dp;
    private int mIconEnd = R.drawable.ic_timer_off_black_24dp;
    private InitialOpenedTab mInitialOpenedTab = InitialOpenedTab.START_CLOCK_TAB;
    private int mColorTabSelected = android.R.color.white;
    private int mColorTabUnSelected = android.R.color.darker_gray;
    private OnTimeSelectedListener mCallback;
    private String mMessageRangeError = "Ошибка. Время начала пары не должно быть позже времени конца";

    public enum InitialOpenedTab {
        START_CLOCK_TAB,
        END_CLOCK_TAB
    }

    public interface OnTimeSelectedListener {
        void onTimeSelected(int hourStart, int minStart, int hourEnd, int minEnd);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        int couple_num = getArguments().getInt(KEY_COUPLE_NUM);
        checkBell(couple_num);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_range_time_picker, null);
        mTabLayout = view.findViewById(R.id.tabLayout);
        /*tabItemStartTime = view.findViewById(R.id.tabStartTime);
        tabItemEndTime = view.findViewById(R.id.tabEndTime);*/
        mTimePickerStart = view.findViewById(R.id.timePickerStart);
        mTimePickerEnd = view.findViewById(R.id.timePickerEnd);

        mTimePickerStart.setIs24HourView(is24HourView);
        mTimePickerEnd.setIs24HourView(is24HourView);

        // Set initial clock values
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            mTimePickerStart.setHour(initialStarHour);
            mTimePickerStart.setMinute(initialStartMinute);
            mTimePickerEnd.setHour(initialEndHour);
            mTimePickerEnd.setMinute(initialEndMinute);
        }
        else
        {
            mTimePickerStart.setCurrentHour(initialStarHour);
            mTimePickerStart.setCurrentMinute(initialStartMinute);
            mTimePickerEnd.setCurrentHour(initialEndHour);
            mTimePickerEnd.setCurrentMinute(initialEndMinute);
        }

        mTabLayout.getTabAt(0).setIcon(mIconStart);
        mTabLayout.getTabAt(1).setIcon(mIconEnd);

        // Set initial opened tab
        if (mInitialOpenedTab == InitialOpenedTab.START_CLOCK_TAB)
        {
            mTabLayout.getTabAt(0).select();
            int tabIconColor = ContextCompat.getColor(getActivity(), mColorTabSelected);
            mTabLayout.getTabAt(0).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            int tabIconColorUnSelected = ContextCompat.getColor(getActivity(), mColorTabUnSelected);
            mTabLayout.getTabAt(1).getIcon().setColorFilter(tabIconColorUnSelected, PorterDuff.Mode.SRC_IN);
            mTimePickerStart.setVisibility(View.VISIBLE);
            mTimePickerEnd.setVisibility(View.GONE);
        }
        else if (mInitialOpenedTab == InitialOpenedTab.END_CLOCK_TAB)
        {
            mTabLayout.getTabAt(1).select();
            int tabIconColor = ContextCompat.getColor(getActivity(), mColorTabSelected);
            mTabLayout.getTabAt(1).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            int tabIconColorUnselect = ContextCompat.getColor(getActivity(), mColorTabUnSelected);
            mTabLayout.getTabAt(0).getIcon().setColorFilter(tabIconColorUnselect, PorterDuff.Mode.SRC_IN);
            mTimePickerEnd.setVisibility(View.VISIBLE);
            mTimePickerStart.setVisibility(View.GONE);
        }

        builder
                .setView(view)
                .setNegativeButton(getString(R.string.cancel), null)
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean flagCorrect;
                        int hourStart, minuteStart, hourEnd, minuteEnd;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            hourStart = mTimePickerStart.getHour();
                            minuteStart = mTimePickerStart.getMinute();
                            hourEnd = mTimePickerEnd.getHour();
                            minuteEnd = mTimePickerEnd.getMinute();
                        } else {
                            hourStart = mTimePickerStart.getCurrentHour();
                            minuteStart = mTimePickerStart.getCurrentMinute();
                            hourEnd = mTimePickerEnd.getCurrentHour();
                            minuteEnd = mTimePickerEnd.getCurrentMinute();
                        }

                        flagCorrect = !mValidateRange || hourEnd > hourStart || hourEnd == hourStart && minuteEnd > minuteStart;

                        // TODO: 12.03.2019 Нет проверки на корректность с предыдущим временем

                        if (flagCorrect) {
                            // Check if this dialog was called by a fragment
                            if (getTargetFragment() != null) {
                                // Return value to Fragment
                                Bundle bundle = new Bundle();
                                bundle.putInt(HOUR_START, hourStart);
                                bundle.putInt(MINUTE_START, minuteStart);
                                bundle.putInt(HOUR_END, hourEnd);
                                bundle.putInt(MINUTE_END, minuteEnd);
                                Intent intent = new Intent().putExtras(bundle);
                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                            } else if (mCallback != null){
                                // Return value to activity
                                Log.d("Test", String.format("hs=%d, ms=%d, he=%d, me=%d", hourStart, minuteStart, hourEnd, minuteEnd));
                                mCallback.onTimeSelected(hourStart, minuteStart, hourEnd, minuteEnd);
                            }
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), mMessageRangeError, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        AlertDialog alertDialog = builder.create();
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
                {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab)
                    {
                        int tabIconColor = ContextCompat.getColor(getActivity(), mColorTabSelected);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        //tab.getIcon().setTint(Color.YELLOW);
                        if(tab.getPosition()==0)
                        {
                            mTimePickerStart.setVisibility(View.VISIBLE);
                            mTimePickerEnd.setVisibility(View.GONE);
                        }
                        else
                        {
                            mTimePickerStart.setVisibility(View.GONE);
                            mTimePickerEnd.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab)
                    {
                        int tabIconColor = ContextCompat.getColor(getActivity(), mColorTabUnSelected);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        //tab.getIcon().setTint(Color.WHITE);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab)
                    {

                    }
                });
            }
        });

        return alertDialog;
    }

    private void checkBell(int couple_num) {
        if (couple_num == 0) { // если первая пара, то установить в 8 часов начало
            setInitialStartClock(8,0);
            setInitialEndClock(9,20);
        } else {
            Bell bell = ScheduleSingle.get(getActivity()).getBell(couple_num-1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(bell.getEndTime());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            int[] total = CalendarParser.sumTime(hour, min, 0, 10);
            setInitialStartClock(total[0], total[1]);
            total = CalendarParser.sumTime(total[0], total[1], 1, 20);
            setInitialEndClock(total[0], total[1]);
        }
    }

    public static RangeTimePickerDialog newInstance(int couple_num) {

        Bundle args = new Bundle();
        args.putInt(KEY_COUPLE_NUM, couple_num);
        RangeTimePickerDialog fragment = new RangeTimePickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCallback(OnTimeSelectedListener listener) {
        mCallback = listener;
    }

    public void setInitialStartClock(int hour, int minute) {
        initialStarHour = hour;
        initialStartMinute = minute;
    }

    public void setInitialEndClock(int hour, int minute) {
        initialEndHour = hour;
        initialEndMinute = minute;
    }
}
