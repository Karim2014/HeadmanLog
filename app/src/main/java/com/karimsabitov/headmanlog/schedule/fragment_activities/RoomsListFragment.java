package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.util.List;

/**
 * Created by User on 17.02.2019.
 */

public class RoomsListFragment extends AbstractListFragment {

    ScheduleSingle mSingle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSingle = ScheduleSingle.get(getContext());
    }

    @Override
    protected void addItem(String value) {
        mSingle.addRoom(value);
    }

    @Override
    protected List<String> getList() {
        return mSingle.getRooms();
    }

    @Override
    protected void update(String oldValue, String newValue) {
        mSingle.updateRoom(oldValue, newValue);
    }

    @Override
    protected String getTitle() {
        return "Аудитория"; // TODO: 24.02.2019 Должна быть ссылка на ресурс
    }

    @Override
    protected Fragment getTargetFragmentForDialog() {
        return this;
    }

    @Override
    protected void deleteItem(String item) {
        mSingle.deleteRoom(item);
    }
}
