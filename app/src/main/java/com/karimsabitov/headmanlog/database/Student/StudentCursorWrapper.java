package com.karimsabitov.headmanlog.database.Student;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.karimsabitov.headmanlog.students.Student;

import java.util.Date;
import java.util.UUID;

import static com.karimsabitov.headmanlog.database.Student.StudentDbSchema.*;


/**
 * Created by User on 01.06.2018.
 */

public class StudentCursorWrapper extends CursorWrapper {

    public StudentCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Student getStudent() {
        String uuidString = getString(getColumnIndex(StudentTable.Cols.UUID));
        String first_name = getString(getColumnIndex(StudentTable.Cols.NAME));
        String phone = getString(getColumnIndex(StudentTable.Cols.PHONE));
        int markId = getInt(getColumnIndex(StudentTable.Cols.MARKID));
        long birthday = getLong(getColumnIndex(StudentTable.Cols.BIRTHDAY));

        Student student = new Student(UUID.fromString(uuidString));
        student.setName(first_name);
        student.setPhone(phone);
        student.setMarkId(markId);
        student.setBirthday(new Date(birthday));

        return student;
    }

    public String getStudentName() {
        return getString(getColumnIndex(StudentTable.Cols.NAME));
    }
}
