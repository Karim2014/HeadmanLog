package com.karimsabitov.headmanlog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.karimsabitov.headmanlog.database.schedule.ScheduleBaseHelper;

/**
 * Created by User on 10.11.2018.
 */

public class BaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "HeadmanLog.db";
    private static final int DB_VERSION = 1;
    private static final String TAG = "Test";

    public BaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.setForeignKeyConstraintsEnabled(true);

        // Create Bells
        db.execSQL(ScheduleBaseHelper.DDL.CREATE_BELL);
        Log.d(TAG, "onCreate: Bells created");

        db.execSQL(ScheduleBaseHelper.DDL.CREATE_ROOM);
        Log.d(TAG, "onCreate: Rooms created");

        db.execSQL(ScheduleBaseHelper.DDL.CREATE_SUBJECT);
        Log.d(TAG, "onCreate: Subject created");

        db.execSQL(ScheduleBaseHelper.DDL.CREATE_TEACHER);
        Log.d(TAG, "onCreate: Teachers created");

        db.execSQL(ScheduleBaseHelper.DDL.CREATE_SCHEDULE);
        Log.d(TAG, "onCreate: Schedule created");
/*
        // Create Couple
        db.execSQL(ScheduleBaseHelper.DDL.CREATE_ROOM);

        ;*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
