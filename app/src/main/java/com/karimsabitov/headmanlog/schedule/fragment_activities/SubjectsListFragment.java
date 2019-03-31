package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.util.List;

/**
 * Created by User on 24.02.2019.
 */

public class SubjectsListFragment extends AbstractListFragment {

    private ScheduleSingle mSingle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSingle = ScheduleSingle.get(getContext());
    }

    @Override
    protected void addItem(String value) {
        mSingle.addSubject(value);
    }

    @Override
    protected List<String> getList() {
        return mSingle.getSubjects();
    }

    @Override
    protected void update(String oldValue, String newValue) {
        mSingle.updateSubject(oldValue, newValue);
    }

    @Override
    protected String getTitle() {
        return "Дисциплина"; // TODO: 24.02.2019 Подключить с ресурса
    }

    @Override
    protected Fragment getTargetFragmentForDialog() {
        return this;
    }

    @Override
    protected void deleteItem(String item) {
        mSingle.removeSubject(item);
    }
}
