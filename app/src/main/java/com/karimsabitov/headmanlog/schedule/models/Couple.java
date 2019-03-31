package com.karimsabitov.headmanlog.schedule.models;

/**
 * Created by User on 10.11.2018.
 */

public class Couple {


    private Schedule mUP;
    private Schedule mDOWN;
    private int mDayNum;
    private int mCoupleNum;

    public Couple(Schedule UP, Schedule DOWN, int dayNum, int coupleNum) {
        mUP = UP;
        mDOWN = DOWN;
        mDayNum = dayNum;
        mCoupleNum = coupleNum;
    }

    public Schedule getDOWN() {
        return mDOWN;
    }

    public Schedule getUP() {
        return mUP;
    }

    public int getDayNum() {
        return mDayNum;
    }

    public int getCoupleNum() {
        return mCoupleNum;
    }

    public void setUP(Schedule UP) {
        mUP = UP;
    }

    public void setDOWN(Schedule DOWN) {
        mDOWN = DOWN;
    }

    @Override
    public String toString() {
        return "Couple{" +
                "mUP=" + mUP.toString() +
                ", mDOWN=" + mDOWN.toString() +
                ", mDayNum=" + mDayNum +
                ", mCoupleNum=" + mCoupleNum +
                '}';
    }
}
