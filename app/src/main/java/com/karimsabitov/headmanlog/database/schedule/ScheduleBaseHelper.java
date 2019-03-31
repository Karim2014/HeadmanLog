package com.karimsabitov.headmanlog.database.schedule;

import com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.RoomTable;

import static com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.BellTable;
import static com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.ScheduleTable;
import static com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.SubjectTable;
import static com.karimsabitov.headmanlog.database.schedule.ScheduleDBSchema.TeacherTable;

/**
 * Created by User on 31.08.2018.
 */

public class ScheduleBaseHelper {
    //private static final int VERSION = 1;

    public static final class DDL {

        public static final String CREATE_SUBJECT =
                "CREATE TABLE " + SubjectTable.NAME + "(" +
                        SubjectTable.Cols.ID + " integer primary key autoincrement, " +
                        SubjectTable.Cols.TITLE + " varchar(30) UNIQUE)";

        public static final String CREATE_TEACHER =
                "CREATE TABLE " + TeacherTable.NAME + "(" +
                        TeacherTable.Cols.ID + " integer primary key autoincrement, " +
                        TeacherTable.Cols.NAME + " varchar(30) UNIQUE)";

        private static final String FK_FORMAT = "%1$s INTEGER CONSTRAINT %4$s REFERENCES %2$s(%3$s) ON DELETE %5$s ON UPDATE CASCADE, ";
        private static final String CASCADE = "CASCADE";
        public static final String CREATE_SCHEDULE =
                "CREATE TABLE " + ScheduleTable.NAME + "(" +
                        ScheduleTable.Cols.ID + " integer primary key autoincrement," +
                        ScheduleTable.Cols.UUID + " VARCHAR(40), " +
                        String.format(FK_FORMAT,
                                ScheduleTable.Cols.SUBJECT, SubjectTable.NAME, SubjectTable.Cols.ID, "Sch_S_FK ", CASCADE) +
                        String.format(FK_FORMAT,
                                ScheduleTable.Cols.TEACHER, TeacherTable.NAME, TeacherTable.Cols.ID, "Sch_T_FK", CASCADE) +
                        String.format(FK_FORMAT,
                                ScheduleTable.Cols.BELL, BellTable.NAME, BellTable.Cols.ID, "Sch_B_FK", CASCADE) +
                        String.format(FK_FORMAT,
                                ScheduleTable.Cols.ROOM_OPTIONAL, RoomTable.NAME, RoomTable.Cols.ID, "Sch_R_FK", "SET NULL") +
                        ScheduleTable.Cols.TOC + " BOOL(1) DEFAULT 1," +
                        ScheduleTable.Cols.IS_EMPTY + " BOOL(1) DEFAULT 0," +
                        ScheduleTable.Cols.DAY_OF_WEEK + " int(1), " +
                        ScheduleTable.Cols.IS_NUMERIC + " BOOL(1) DEFAULT 0)";

        public static final String CREATE_BELL =
                "create table " + BellTable.NAME + "(" +
                        BellTable.Cols.ID + " integer primary key autoincrement, " +
                        BellTable.Cols.COUPLE_NUM + " integer UNIQUE," +
                        BellTable.Cols.START_TIME + " bigint," +
                        BellTable.Cols.END_TIME + " bigint)";

        public static final String CREATE_ROOM =
                "CREATE TABLE " + RoomTable.NAME + " (" +
                        RoomTable.Cols.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        RoomTable.Cols.NUMBER + " VARCHAR(15) UNIQUE)";

    }

    public static final class DML {

        /**
         * Запрос списка пар по параметрам:
         *      1 - День недели;
         *      2 - Числитель или знаменатель
         *  Сортирует по номеру пары
         */
        public static final String QUERY_SCHEDULE = "" +
                "SELECT sch."+ScheduleTable.Cols.UUID+", sch."+ScheduleTable.Cols.IS_EMPTY+", sch."+ScheduleTable.Cols.IS_NUMERIC+", sch."+ScheduleTable.Cols.TOC+", sch."+ScheduleTable.Cols.DAY_OF_WEEK+", \n" +
                "       t."+TeacherTable.Cols.NAME+",\n" +
                "       s."+SubjectTable.Cols.TITLE+",\n" +
                "       b."+BellTable.Cols.COUPLE_NUM+", b."+BellTable.Cols.START_TIME+", b."+BellTable.Cols.END_TIME+", \n" +
                "       r."+RoomTable.Cols.NUMBER+" \n" +
                "FROM "+ScheduleTable.NAME+" as sch \n" +
                "LEFT JOIN "+SubjectTable.NAME+" as s \n" +
                "     ON sch."+ScheduleTable.Cols.SUBJECT+" = s."+SubjectTable.Cols.ID+" \n" +
                "LEFT JOIN "+TeacherTable.NAME+" as t\n" +
                "     ON sch."+ScheduleTable.Cols.TEACHER+"=t."+TeacherTable.Cols.ID+" \n" +
                "LEFT JOIN "+RoomTable.NAME+" as r\n" +
                "     ON sch."+ScheduleTable.Cols.ROOM_OPTIONAL+"=r."+RoomTable.Cols.ID+" \n" +
                "LEFT JOIN "+BellTable.NAME+" as b\n" +
                "     ON sch."+ScheduleTable.Cols.BELL+"=b."+BellTable.Cols.ID+" \n" +
                "WHERE sch."+ScheduleTable.Cols.DAY_OF_WEEK+"=? and sch."+ScheduleTable.Cols.IS_NUMERIC+"=?\n" +
                "ORDER BY b."+BellTable.Cols.COUPLE_NUM;

    }

}
