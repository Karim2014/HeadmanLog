package com.karimsabitov.headmanlog.schedule.models;

import java.sql.Time;

/**
 * Created by User on 11.07.2018.
 */

public class Bell {

    private int mCoupleNum;
    private Time mStartTime;
    private Time mEndTime;

    public Bell(int coupleNum, Time startTime, Time endTime) {
        mCoupleNum = coupleNum;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    @Override
    public String toString() {
        return "Couple_num = " +
                mCoupleNum +
                "\nStartTime = " +
                mStartTime.toString() +
                "\nEndTime = " +
                mEndTime.toString();
    }

    public int getCoupleNum() {
        return mCoupleNum;
    }

    public Time getStartTime() {
        return mStartTime;
    }

    public Time getEndTime() {
        return mEndTime;
    }

    public void setCoupleNum(int coupleNum) {
        mCoupleNum = coupleNum;
    }
}
