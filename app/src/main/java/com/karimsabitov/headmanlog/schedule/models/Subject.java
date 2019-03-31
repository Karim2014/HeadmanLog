package com.karimsabitov.headmanlog.schedule.models;

import java.util.UUID;

/**
 * Created by User on 11.07.2018.
 */

public class Subject {

    private UUID mId;
    private String mName;

    public Subject() {
        mId = UUID.randomUUID();
    }

    public Subject(String name) {
        this();
        mName = name;
    }

    public Subject(UUID id, String name) {
        mId = id;
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

    public void setId(UUID id) {
        mId = id;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "mId=" + mId.toString() +
                ", mName='" + mName + '\'' +
                '}';
    }
}
