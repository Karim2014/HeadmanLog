package com.karimsabitov.headmanlog.students;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.karimsabitov.headmanlog.database.Student.StudentBaseHelper;
import com.karimsabitov.headmanlog.database.Student.StudentCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.karimsabitov.headmanlog.database.Student.StudentDbSchema.StudentTable;

/**
 * Created by User on 01.02.2018.
 */

public class StudentGroup {
    private static StudentGroup sStudentGroup;

    private String mGroup; // TODO: 31.08.2018 Сохранять его где-нибудь
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public static StudentGroup get(Context context){
        if (sStudentGroup == null){
            sStudentGroup = new StudentGroup(context);
        }
        return sStudentGroup;
    }

    private StudentGroup(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new StudentBaseHelper(mContext)
                .getWritableDatabase();
        mGroup = "ПКС-15";
    }

    public void addStudent(Student student){
        ContentValues values = getContentValues(student);

        mDatabase.insert(StudentTable.NAME, null, values);
    }

    public void removeStudent(Student student) {
        mDatabase.delete(
                StudentTable.NAME,
                "uuid = ?",
                new String[] {  student.getId().toString() }
        );
    }

    public int getStudentsCount() {
        return queryStudents(null, null).getCount();
    }

    public List<String> getStudentsNames() {
        List<String> list = new ArrayList<>();

        StudentCursorWrapper cursor = new StudentCursorWrapper(mDatabase.query(StudentTable.NAME, new String[] {StudentTable.Cols.NAME}, null, null, null, null, null));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                list.add(cursor.getStudentName());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return list;
    }

    // region [Обработка запросов из БД]

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();

        StudentCursorWrapper cursor = queryStudents(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                students.add(cursor.getStudent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return students;
    }

    public Student getStudent(UUID id){
        StudentCursorWrapper cursor = queryStudents(
                StudentTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getStudent();
        } finally {
            cursor.close();
        }
    }

    public void updateStudent(Student student){
        String uuidSting = student.getId().toString();
        ContentValues values = getContentValues(student);

        mDatabase.update(StudentTable.NAME , values,
                StudentTable.Cols.UUID + " = ?",
                new String[] { uuidSting });
    }

    private static ContentValues getContentValues(Student student){
        ContentValues values = new ContentValues();
        values.put(StudentTable.Cols.UUID, student.getId().toString());
        values.put(StudentTable.Cols.NAME, student.getName());
        values.put(StudentTable.Cols.PHONE, student.getPhone());
        values.put(StudentTable.Cols.MARKID, student.getMarkId());
        values.put(StudentTable.Cols.BIRTHDAY, student.getBirthday().getTime());

        return values;
    }

    private StudentCursorWrapper queryStudents(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                StudentTable.NAME,
                null, //  Columns - null - выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                StudentTable.Cols.NAME //orderBy
        );

        return new StudentCursorWrapper(cursor);
    }

    // endregion
}
