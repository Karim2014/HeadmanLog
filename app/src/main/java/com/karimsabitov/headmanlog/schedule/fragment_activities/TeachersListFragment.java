package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.util.List;

/**
 * Created by User on 24.02.2019.
 */

public class TeachersListFragment extends AbstractListFragment {

    private ScheduleSingle mSingle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSingle = ScheduleSingle.get(getContext());
    }

    @Override
    protected List<String> getList() {
        return mSingle.getTeachers();
    }

    @Override
    protected void update(String oldValue, String newValue) {
        mSingle.updateTeacher(oldValue, newValue);
    }

    @Override
    protected String getTitle() {
        return "Преподаватель"; // TODO: 24.02.2019 Подсоединить с ресурсов
    }

    @Override
    protected Fragment getTargetFragmentForDialog() {
        return this;
    }

    @Override
    protected void deleteItem(String item) {
        mSingle.removeTeacher(item);
    }

    @Override
    public void addItem(String value) {
        ScheduleSingle.get(getContext()).addTeacher(value);
    }

}
