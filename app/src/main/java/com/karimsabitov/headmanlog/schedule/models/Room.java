package com.karimsabitov.headmanlog.schedule.models;

/**
 * Created by User on 06.01.2019.
 */

public class Room {

    private int mNumber;

    public Room(int num) {
        mNumber = num;
    }

    public int getNumber() {
        return mNumber;
    }

    @Override
    public String toString() {
        return "Room{" +
                "mNumber=" + mNumber +
                '}';
    }

    public void setNumber(int number) {
        mNumber = number;
    }
}
