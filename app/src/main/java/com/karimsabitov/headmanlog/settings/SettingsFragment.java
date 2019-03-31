package com.karimsabitov.headmanlog.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.Utils.Constants;
import com.karimsabitov.headmanlog.Utils.Utils;
import com.karimsabitov.headmanlog.schedule.fragment_activities.ScheduleAboutFragment;

/**
 * Created by User on 22.08.2018.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener{

    CheckBox room_with_teacher;
    Switch numeric_week;
    private ExpansionLayout mExpansionLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        setupLayouts(v);
        setupHeader(v);

        return v;
    }

    private void setupLayouts(View v){
        LinearLayout ll_room_teacher = v.findViewById(R.id.fragment_settings_room_with_teacher_lin_lay);
        LinearLayout ll_numeric_week = v.findViewById(R.id.fragment_settings_numeric_week_lin_lay);
        ll_room_teacher.setOnClickListener(this);
        ll_numeric_week.setOnClickListener(this);

        room_with_teacher = v.findViewById(R.id.fragment_settings_room_with_teacher_lin_lay_checkbox);
        room_with_teacher.setChecked(Utils.prefReadBool(getContext(), Constants.APP_NAME, Constants.ROOM_WITH_TEACHER, true));
        room_with_teacher.setOnCheckedChangeListener(this);

        numeric_week = v.findViewById(R.id.fragment_settings_expansion_header_switch);
        numeric_week.setChecked(Utils.prefReadBool(getContext(), Constants.APP_NAME, Constants.NUMERIC_WEEK, true));
        numeric_week.setOnCheckedChangeListener(this);
    }

    private void setupHeader(View v) {
        ExpansionHeader mHeader = v.findViewById(R.id.fragment_settings_expansion_header);
        mExpansionLayout = v.findViewById(R.id.fragment_settings_expansion_lay);


        mHeader.setExpansionLayout(mExpansionLayout);
        if (numeric_week.isChecked()) {
            mExpansionLayout.expand(false);
        } else {
            mExpansionLayout.collapse(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_settings_room_with_teacher_lin_lay:
                room_with_teacher.toggle();
                break;
            case R.id.fragment_settings_numeric_week_lin_lay:
                numeric_week.toggle();
                break;
        }
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.fragment_settings_room_with_teacher_lin_lay_checkbox:
                //Toast.makeText(getContext(), "toggled", Toast.LENGTH_SHORT).show();
                Utils.prefWriteBool(getContext(), Constants.APP_NAME, Constants.ROOM_WITH_TEACHER, isChecked);
                break;
            case R.id.fragment_settings_expansion_header_switch:
                Utils.prefWriteBool(getContext(), Constants.APP_NAME, Constants.NUMERIC_WEEK, isChecked);
                if (!isChecked) {
                    mExpansionLayout.collapse(true);
                } else {
                    mExpansionLayout.expand(true);
                }
                break;
        }
        getActivity().setResult(ScheduleAboutFragment.RESULT_TOOLBAR);
    }
}
