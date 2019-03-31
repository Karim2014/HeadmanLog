package com.karimsabitov.headmanlog.attendance.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 07.09.2018.
 */

public class AttDay {

    private Date mDate;
    private List<String> mStudents; // список доступных студентов
    private HashMap<Integer, List<String>> mCouples;

    public AttDay(int coupleCount) throws IndexOutOfBoundsException {
        mCouples = new HashMap<>();
        mDate = new Date();
        if (coupleCount <= 0){
            throw new IndexOutOfBoundsException("пришел 0 в конструктор");
        }
        for (int i = 0; i < coupleCount; i++) {
            mCouples.put(i, new ArrayList<String>());
        }

    }

    public AttDay(Date date, HashMap<Integer, List<String>> couples) {
        mDate = date;
        mCouples = couples;
    }

    public AttDay(int coupleCount, Date date) {
        this(coupleCount);
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public HashMap<Integer, List<String>> getCouples() {
        return mCouples;
    }

    public void setCouples(HashMap<Integer, List<String>> couples) {
        mCouples = couples;
    }

    public void setStudents(List<String> students) {
        mStudents = students;
    }

    public List<String> getStudents() {
        return mStudents;
    }
}
