package com.karimsabitov.headmanlog.students;

import java.util.Date;
import java.util.UUID;

/**
 * Created by User on 30.01.2018.
 */

public class Student {

    private UUID mId;
    private String mName = "";
    private String mPhone = "";
    private int mMarkId = 1;
    private Date mBirthday;

    public Student(){
        this(UUID.randomUUID());
    }

    public Student(UUID id) {
        mId = id;
        mBirthday = new Date();
        mBirthday.setTime(0);
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public UUID getId() { return mId; }

    public String getName() { return mName; }

    public void setName(String name) {
        mName = name;
    }

    public int getMarkId() {
        return mMarkId;
    }

    public void setMarkId(int markId) {
        mMarkId = markId;
    }

    public Date getBirthday() {
        return mBirthday;
    }

    public void setBirthday(Date birthday) {
        mBirthday = birthday;
    }
}
