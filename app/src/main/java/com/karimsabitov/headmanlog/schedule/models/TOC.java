package com.karimsabitov.headmanlog.schedule.models;

/**
 * Type of occupation class / Тип пары
 */

public class TOC {

    private String mTitle;

    public TOC(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        return "TOC{" +
                "mTitle='" + mTitle + '\'' +
                '}';
    }
}
