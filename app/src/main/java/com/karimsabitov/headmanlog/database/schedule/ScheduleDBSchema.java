package com.karimsabitov.headmanlog.database.schedule;

/**
 * Created by User on 31.08.2018.
 */

public class ScheduleDBSchema {

    public static final class BellTable {
        public static final String NAME = "Bell";

        public static final class Cols{
            public static final String ID = "_id_b";
            public static final String COUPLE_NUM = "couple_num";
            public static final String START_TIME = "start_time";
            public static final String END_TIME = "end_time";
        }
    }

    public static final class RoomTable {
        public static final String NAME = "Room";

        public static final class Cols {
            public static final String ID = "_id_r";
            public static final String NUMBER = "number";
        }
    }

    public static final class ScheduleTable {
        public static final String NAME = "Schedule";

        public static final class Cols{
            public static final String ID = "_id_sch";
            public static final String UUID = "uuid_sch";
            public static final String SUBJECT = "subject_id";
            public static final String TEACHER = "teacher_id";
            public static final String ROOM_OPTIONAL = "room_optional";
            public static final String IS_EMPTY = "is_empty";
            public static final String IS_NUMERIC = "is_num";
            public static final String BELL = "bell_id";
            public static final String TOC = "toc_id";
            public static final String DAY_OF_WEEK = "day_of_week";
        }
    }

    public static final class SubjectTable {
        public static final String NAME = "Subject";

        public static final class Cols{
            public static final String ID = "_id_subj";
            public static final String TITLE = "title_subj";
        }
    }

    public static final class TeacherTable {
        public static final String NAME = "Teacher";

        public static final class Cols{
            public static final String ID = "_id_t";
            public static final String NAME = "name_t";
        }
    }

}
