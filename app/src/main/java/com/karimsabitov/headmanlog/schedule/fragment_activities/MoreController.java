package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.karimsabitov.headmanlog.Utils.CalendarParser;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.Utils.Constants;
import com.karimsabitov.headmanlog.Utils.Utils;

import java.util.Date;

/**
 * Created by User on 21.10.2018.
 */

public class MoreController {

    private TextView mTitle;
    private TextView mCouple;
    private TextView mTeacher;
    private Context mContext;
    private View mRoot;

    private String[] mStrings;

    public MoreController(Context context, View view) {
        mContext = context;
        mTitle = view.findViewById(R.id.fragment_schedule_day_more_title);
        mCouple = view.findViewById(R.id.fragment_schedule_day_more_couple);
        mTeacher = view.findViewById(R.id.fragment_schedule_day_more_teacher);
        //mStrings = CalendarParser.getSubjectAndTeacher(mContext, ScheduleSingle.get(mContext).getSchedules());
        mRoot = view;
        setup();
    }

    private void setup(){
        setTitle();
        setCouple();
        setTeacher();
    }

    private void setTitle() {
        boolean flag = Utils.prefReadBool(mContext, Constants.APP_NAME, Constants.NUMERIC_WEEK, true);

        mTitle.setText(mContext.getString(
                R.string.title_couple_num_day_week,
                CalendarParser.formatDate("dd.MM.yyyy (MMMM, E)", new Date()),
                flag ? CalendarParser.isUpWeek() ? "\nЧислитель" : "\nЗнаменатель" : ""
        ));
    }

    private void setCouple(){
        mCouple.setText(mStrings[CalendarParser.INDEX_SUBJECT]);
    }

    private void setTeacher(){
        if (mStrings[CalendarParser.INDEX_TEACHER] != null) {
            mTeacher.setText(mStrings[CalendarParser.INDEX_TEACHER]);
        }
    }
}
