package com.karimsabitov.headmanlog.schedule.models;

import java.util.UUID;

/**
 * Модедель пары по числителю и знаменателю
 */

public class Schedule {

    private UUID mId; // id
    private String mSubject; // предмет
    private String mTeacher; // Препод
    private String mRoomOptional; // аудитория
    private int mCoupleNum; // номер пары
    private boolean mTOC; // тип пары (true - лекция, false - практика)
    private boolean mEmpty; // Окно или нет
    private boolean mNumeric; // нижняя ли неделя
    private int mDayOfWeek; // день недели 1-7

    public Schedule(UUID id, String subject, String teacher, String roomOptional, int coupleNum, boolean toc, boolean empty, boolean numeric, int dayOfWeek) {
        mId = id;
        mSubject = subject;
        mTeacher = teacher;
        mRoomOptional = roomOptional;
        mCoupleNum = coupleNum;
        mTOC = toc;
        mEmpty = empty;
        mNumeric = numeric;
        mDayOfWeek = dayOfWeek;
    }

    public Schedule(String subject, String teacher, String roomOptional, int coupleNum, boolean TOC, boolean empty, boolean numeric, int dayOfWeek) {
        this(UUID.randomUUID(), subject, teacher, roomOptional, coupleNum, TOC, empty, numeric, dayOfWeek);
    }

    public void setEmpty(boolean empty) {
        mEmpty = empty;
    }

    public void setNumeric(boolean numeric) {
        mNumeric = numeric;
    }

    public void setDayOfWeek(int dayOfWeek) {
        mDayOfWeek = dayOfWeek;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setTOC(boolean TOC) {
        mTOC = TOC;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getTeacher() {
        return mTeacher;
    }

    public void setTeacher(String teacher) {
        mTeacher = teacher;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getRoomOptional() {
        return mRoomOptional;
    }

    public void setRoomOptional(String roomOptional) {
        mRoomOptional = roomOptional;
    }

    public boolean isEmpty() {
        return mEmpty;
    }

    public boolean isNumeric() {
        return mNumeric;
    }

    public int getCoupleNum() {
        return mCoupleNum;
    }

    public void setCoupleNum(int coupleNum) {
        mCoupleNum = coupleNum;
    }

    public int getDayOfWeek() {
        return mDayOfWeek;
    }

    public boolean getTOC() {
        return mTOC;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "mId=" + mId +
                ", mSubject=" + mSubject +
                ", mTeacher=" + mTeacher +
                ", mRoomOptional=" + mRoomOptional +
                ", mCoupleNum=" + mCoupleNum +
                ", mTOC=" + mTOC +
                ", mEmpty=" + mEmpty +
                ", mNumeric=" + mNumeric +
                ", mDayOfWeek=" + mDayOfWeek +
                '}';
    }
}
