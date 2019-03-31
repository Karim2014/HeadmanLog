package com.karimsabitov.headmanlog.schedule.models;

import java.util.UUID;

/**
 * Created by User on 11.07.2018.
 */

public class Teacher {

    private UUID mId;
    private String mName;

    public Teacher(String name) {
        this(UUID.randomUUID(), name);
    }

    public Teacher(UUID uuid, String name) {
        mId = uuid;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "mId=" + mId.toString() +
                ", mName='" + mName + '\'' +
                '}';
    }
}
