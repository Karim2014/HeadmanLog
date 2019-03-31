package com.karimsabitov.headmanlog.database.Student;

/**
 * Created by User on 31.05.2018.
 */

public class StudentDbSchema {
    public static final class StudentTable{
        public static final String NAME = "students";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String PHONE = "phone";
            public static final String MARKID = "mark_id";
            public static final String BIRTHDAY = "birthday";
        }
    }
}
