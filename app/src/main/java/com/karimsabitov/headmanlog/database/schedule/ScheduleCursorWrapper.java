package com.karimsabitov.headmanlog.database.schedule;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.BellTable;
import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.RoomTable;
import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.ScheduleTable;
import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.SubjectTable;
import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.TeacherTable;
import com.karimsabitov.headmanlog.schedule.models.Bell;
import com.karimsabitov.headmanlog.schedule.models.Schedule;

import java.sql.Time;
import java.util.UUID;

//import static com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.;


/**
 * Created by User on 31.08.2018.
 */

public class ScheduleCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ScheduleCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public String getSubject(){
        try {
            return getString(getColumnIndex(SubjectTable.Cols.TITLE));
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getTeacher(){
        try {
            return getString(getColumnIndex(TeacherTable.Cols.NAME));
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public Bell getBell(){
        int couple_num = getInt(getColumnIndex(BellTable.Cols.COUPLE_NUM));
        long start_time = getLong(getColumnIndex(BellTable.Cols.START_TIME));
        long end_time = getLong(getColumnIndex(BellTable.Cols.END_TIME));

        return new Bell(couple_num, new Time(start_time), new Time(end_time));
    }

    private int getCoupleNum(){
        try {
            return getInt(getColumnIndex(BellTable.Cols.COUPLE_NUM));
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public String getRoom() {
        try {
            return getString(getColumnIndex(RoomTable.Cols.NUMBER));
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public Schedule getSchedule(){
        String uuidString = getString(getColumnIndex(ScheduleTable.Cols.UUID));
        int isEmpty = getInt(getColumnIndex(ScheduleTable.Cols.IS_EMPTY));
        int isNumeric = getInt(getColumnIndex(ScheduleTable.Cols.IS_NUMERIC));
        int dayOfWeek = getInt(getColumnIndex(ScheduleTable.Cols.DAY_OF_WEEK));
        int toc = getInt(getColumnIndex(ScheduleTable.Cols.TOC));
        String room = getRoom();
        int coupleNum = getCoupleNum();
        String teacher = getTeacher();
        String subject = getSubject();

        return new Schedule(
                UUID.fromString(uuidString),
                subject,
                teacher,
                room,
                coupleNum,
                toc!=0,
                isEmpty!=0,
                isNumeric!=0,
                dayOfWeek
        );
    }
}
