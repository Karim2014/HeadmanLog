package com.karimsabitov.headmanlog.database.Student;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.karimsabitov.headmanlog.database.Student.StudentDbSchema.*;

/**
 * Created by User on 31.05.2018.
 */

public class StudentBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "students.db";

    public StudentBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + StudentTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                StudentTable.Cols.UUID + ", " +
                StudentTable.Cols.NAME + ", " +
                StudentTable.Cols.PHONE + ", " +
                StudentTable.Cols.MARKID + ", " +
                StudentTable.Cols.BIRTHDAY +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
