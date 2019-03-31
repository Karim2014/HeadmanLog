package com.karimsabitov.headmanlog.schedule.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.karimsabitov.headmanlog.database.BaseHelper;
import com.karimsabitov.headmanlog.database.schedule.ScheduleBaseHelper;
import com.karimsabitov.headmanlog.database.schedule.ScheduleCursorWrapper;
import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.BellTable;
import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.RoomTable;
import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.ScheduleTable;
import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.SubjectTable;
import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.TeacherTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11.07.2018.
 */

public class ScheduleSingle {

    private static ScheduleSingle sScheduleSingle;

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private final String TAG = "Test";

    public static ScheduleSingle get(Context context){
        if (sScheduleSingle == null){
            sScheduleSingle = new ScheduleSingle(context);
        }
        return sScheduleSingle;
    }

    public ScheduleSingle(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BaseHelper(mContext)
                .getWritableDatabase();
    }

    //  region BELLS

    public List<Bell> getBells() {
        List<Bell> bells = new ArrayList<>();

        ScheduleCursorWrapper cursor = query(BellTable.NAME, null, null, null);

        Log.d(TAG, "getBells: " + cursor.getCount());

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                bells.add(cursor.getBell());
                Log.d(TAG, cursor.getBell().toString());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return bells;
    }

    public Bell getBell(int coupleNum){
        ScheduleCursorWrapper cursor = new ScheduleCursorWrapper(query(
                BellTable.NAME,
                BellTable.Cols.COUPLE_NUM + " = ?",
                new String[]{String.valueOf(coupleNum)},
                null));
        try {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) return null;
            return cursor.getBell();
        }finally {
            cursor.close();
        }
    }

    public int getBellId(int coupleNum) {
        Cursor cursor = mDatabase.query(
                BellTable.NAME,
                new String[] {BellTable.Cols.ID},
                BellTable.Cols.COUPLE_NUM + " = ?",
                new String[] {String.valueOf(coupleNum)},
                null,
                null,
                null
        );

        try {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) return -1;
            return cursor.getInt(cursor.getColumnIndex(BellTable.Cols.ID));
        }finally {
            cursor.close();
        }
    }

    public long addBell(Bell bell){
        ContentValues values = getContentValues(bell);

        return mDatabase.insert(BellTable.NAME, null, values);
    }

    public int removeBell(Bell bell){
        return mDatabase.delete(
                BellTable.NAME,
                BellTable.Cols.COUPLE_NUM + " = ?",
                new String[] {String.valueOf(bell.getCoupleNum())}
        );
    }

    public int removeBell(int coupleNum) {
        return mDatabase.delete(
                BellTable.NAME,
                BellTable.Cols.COUPLE_NUM + " = ?",
                new String[]{String.valueOf(coupleNum)}
        );
    }

    public int updateBell(Bell bell) {
        ContentValues values = getContentValues(bell);

        return mDatabase.update(BellTable.NAME, values,
                BellTable.Cols.COUPLE_NUM + " = ?",
                new String[] {String.valueOf(bell.getCoupleNum())});
    }

    //endregion

    //region ROOMS

    public List<String> getRooms() {
        ScheduleCursorWrapper cursor = new ScheduleCursorWrapper(mDatabase.query(
                true,
                RoomTable.NAME,
                null, null, null, null, null, null, null
        ));

        List<String> rooms = new ArrayList<>();

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                rooms.add(cursor.getRoom());
                cursor.moveToNext();
            }
        } finally {
          cursor.close();
        }

        return rooms;
    }

    public long addRoom(String room) {
        ContentValues contentValues = getContentValues(RoomTable.Cols.NUMBER, room);

        return mDatabase.insert(RoomTable.NAME, null, contentValues);
    }

    public int updateRoom(String room, String newValue) {
        ContentValues contentValues = getContentValues(RoomTable.Cols.NUMBER, newValue);

        return mDatabase.update(
                RoomTable.NAME,
                contentValues,
                RoomTable.Cols.NUMBER + " = ?",
                new String[]{String.valueOf(room)}
        );
    }

    public int deleteRoom(String room) {
        return mDatabase.delete(
                RoomTable.NAME,
                RoomTable.Cols.NUMBER + " = ?",
                new String[]{room}
        );
    }

    public int getRoomId(String room) {
        Cursor cursor = mDatabase.query(
                RoomTable.NAME,
                new String[] {RoomTable.Cols.ID},
                RoomTable.Cols.NUMBER + " = ?",
                new String[] {room == null ? "" : room},
                null,
                null,
                null
        );

        try {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) return -1;
            return cursor.getInt(cursor.getColumnIndex(RoomTable.Cols.ID));
        }finally {
            cursor.close();
        }
    }

    //endregion

    // region SUBJECTS


    public List<String> getSubjects() {
        List<String> subjects = new ArrayList<>();

        ScheduleCursorWrapper cursor = query(SubjectTable.NAME, null, null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                subjects.add(cursor.getSubject());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return subjects;
    }

    public void addSubject(String subject){
        ContentValues values = getContentValues(SubjectTable.Cols.TITLE, subject);

        mDatabase.insert(SubjectTable.NAME, null, values);
    }

    public void removeSubject(String subject){
        mDatabase.delete(
                SubjectTable.NAME,
                SubjectTable.Cols.TITLE + " = ?",
                new String[] {subject}
        );
    }

    public void updateSubject(String subject, String newValue) {
        ContentValues values = getContentValues(SubjectTable.Cols.TITLE, newValue);

        mDatabase.update(SubjectTable.NAME, values,
                SubjectTable.Cols.TITLE + " = ?",
                new String[] { subject });
    }

    public int getSubjectId(String subject) {
        Cursor cursor = mDatabase.query(
                SubjectTable.NAME,
                new String[] {SubjectTable.Cols.ID},
                SubjectTable.Cols.TITLE + " = ?",
                new String[] {subject == null ? "" : subject},
                null,
                null,
                null
        );

        try {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) return -1;
            return cursor.getInt(cursor.getColumnIndex(SubjectTable.Cols.ID));
        }finally {
            cursor.close();
        }
    }

    public int getSubjectsSize(){
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + SubjectTable.NAME, null);
        try {
            return cursor.getCount();
        }finally {
            cursor.close();
        }
    }

    //endregion

    //  region TEACHERS

    public List<String> getTeachers(){
        List<String> teachers = new ArrayList<>();

        ScheduleCursorWrapper cursor = query(TeacherTable.NAME, null, null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String teacher = cursor.getTeacher();
                teachers.add(teacher);
                Log.d(TAG, "getTeachers " +cursor.getPosition() + ": " + teacher);
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return teachers;
    }

    public long addTeacher(String teacher){
        ContentValues values = getContentValues(TeacherTable.Cols.NAME, teacher);

        return mDatabase.insert(TeacherTable.NAME, null, values);
    }

    public long removeTeacher(String teacher){
        return mDatabase.delete(
                TeacherTable.NAME,
                TeacherTable.Cols.NAME + " = ?",
                new String[] {teacher}
        );
    }

    public void updateTeacher(String teacher, String newValue) {
        ContentValues values = getContentValues(TeacherTable.Cols.NAME, newValue);

        mDatabase.update(TeacherTable.NAME, values,
                TeacherTable.Cols.NAME + " = ?",
                new String[] { teacher });
    }

    public int getTeacherId(String teacher) {
        Cursor cursor = mDatabase.query(
                TeacherTable.NAME,
                new String[] {TeacherTable.Cols.ID},
                TeacherTable.Cols.NAME + " = ?",
                new String[] {teacher == null ? "" : teacher},
                null,
                null,
                null
        );

        try {
            cursor.moveToFirst();
            if (cursor.getCount()==0) return -1;
            return cursor.getInt(cursor.getColumnIndex(TeacherTable.Cols.ID));
        }finally {
            cursor.close();
        }
    }

    public int getTeachersSize(){
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TeacherTable.NAME, null);
        try {
            return cursor.getCount();
        }finally {
            cursor.close();
        }
    }

    /*public Teacher getTeacher(String uuidString){
        ScheduleCursorWrapper cursorWrapper = query(
                TeacherTable.NAME,
                TeacherTable.Cols.UUID + " = ?",
                new String[] {uuidString},
                null
        );

        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }

            cursorWrapper.moveToFirst();
            return cursorWrapper.getTeacher();
        }finally {
            cursorWrapper.close();
        }
    }*/
    //  endregion

    //region SCHEDULE
 /*

    *//*public HashMap<Integer, List<Schedule>> getSchedules() {

        HashMap<Integer, List<Schedule>> hashMap = new HashMap<>();
        hashMap.put(0, new ArrayList<Schedule>());
        hashMap.put(1, new ArrayList<Schedule>());
        hashMap.put(2, new ArrayList<Schedule>());
        hashMap.put(3, new ArrayList<Schedule>());
        hashMap.put(4, new ArrayList<Schedule>());

        ScheduleCursorWrapper cursor = query(ScheduleTable.NAME, null, null, null);

        Bundle args = new Bundle();


        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Schedule schedule = cursor.getSchedule(args);

                String[] ids = args.getStringArray(ScheduleCursorWrapper.KEY_ARGS_STRING);

                schedule.setSubject(getSubject(ids[ScheduleCursorWrapper.UUID_SUBJECT]));

                schedule.setSubjectDown(getSubject(ids[ScheduleCursorWrapper.UUID_SUBJECT_DOWN]));

                schedule.setTeacher((Teacher)getTeacher(ids[ScheduleCursorWrapper.UUID_TEACHER]));

                schedule.setTeacherDown(((Teacher) getTeacher(ids[ScheduleCursorWrapper.UUID_TEACHER_DOWN])));

                int day_of_week = Integer.parseInt(ids[ScheduleCursorWrapper.DAY_OF_WEEK]);

                hashMap.get(day_of_week).add(schedule);

                args.clear();
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return hashMap;
    }*/

    public List<Schedule> getSchedules(int day, boolean is_numeric){
        List<Schedule> list = new ArrayList<>(); // Список пар. 1 - (числ, знамен), 2 - (числ, знамен), ..., n - (ч, з)

        ScheduleCursorWrapper cursor = new ScheduleCursorWrapper(
                mDatabase.rawQuery(
                        ScheduleBaseHelper.DML.QUERY_SCHEDULE,
                        new String[]{
                                String.valueOf(day),
                                is_numeric ? "1" : "0"
                        })
        );

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Schedule schedule = cursor.getSchedule();
                list.add(schedule);
                Log.d(TAG, "getSchedules "+cursor.getPosition()+": " + schedule.toString());

                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return list;
    }

    //public Schedule getSchedule(int day_of_week, int couple_num)

    public long addSchedule(Schedule schedule){
        ContentValues values = getContentValues(schedule);

        return mDatabase.insert(ScheduleTable.NAME, null, values);
    }

    public void updateSchedule(Schedule schedule){
        String uuid_string = schedule.getId().toString();
        ContentValues values = getContentValues(schedule);

        mDatabase.update(
                ScheduleTable.NAME, values,
                ScheduleTable.Cols.UUID + " = ?",
                new String[] {uuid_string}
        );
    }

    public int removeSchedule(Schedule schedule){
        return mDatabase.delete(
                ScheduleTable.NAME, ScheduleTable.Cols.UUID + " = ?", new String[] {schedule.getId().toString()}
        );
    }

    //endregion

    // region ЗАПРОСЫ С БД


    public ContentValues getContentValues(String key, String value){
        ContentValues values = new ContentValues();
        values.put(key, value);
        return values;
    }

    public ContentValues getContentValues(String key, int value){
        ContentValues values = new ContentValues();
        values.put(key, value);
        return values;
    }

    private ContentValues getContentValues(Bell bell) {
        ContentValues values = new ContentValues();

        values.put(BellTable.Cols.COUPLE_NUM, bell.getCoupleNum());
        values.put(BellTable.Cols.START_TIME, bell.getStartTime().getTime());
        values.put(BellTable.Cols.END_TIME, bell.getEndTime().getTime());

        return values;
    }

    private ContentValues getContentValues(Room room) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RoomTable.Cols.NUMBER, room.getNumber());

        return contentValues;
    }

    private ContentValues getContentValues(Schedule schedule){
        ContentValues contentValues = new ContentValues();

        contentValues.put(ScheduleTable.Cols.UUID, schedule.getId().toString());
        contentValues.put(ScheduleTable.Cols.IS_EMPTY, schedule.isEmpty() ? 1 : 0);
        contentValues.put(ScheduleTable.Cols.IS_NUMERIC, schedule.isNumeric() ? 1 : 0);
        contentValues.put(ScheduleTable.Cols.TOC, schedule.getTOC() ? 1 : 0);
        contentValues.put(ScheduleTable.Cols.ROOM_OPTIONAL, getRoomId(schedule.getRoomOptional()));
        contentValues.put(ScheduleTable.Cols.SUBJECT, getSubjectId(schedule.getSubject()));
        contentValues.put(ScheduleTable.Cols.TEACHER, getTeacherId(schedule.getTeacher()));
        contentValues.put(ScheduleTable.Cols.BELL, getBellId(schedule.getCoupleNum()));
        contentValues.put(ScheduleTable.Cols.DAY_OF_WEEK, schedule.getDayOfWeek());

        return contentValues;
    }

    public ScheduleCursorWrapper query(String table, String whereClause, String[] whereArgs, String orderBy){
        Cursor cursor = mDatabase.query(
                table,
                null, //  Columns - null - выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                orderBy //orderBy
        );

        return new ScheduleCursorWrapper(cursor);
    }


}
