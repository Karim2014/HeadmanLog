package com.karimsabitov.headmanlog.Utils;

import android.content.Context;
import android.widget.TextView;

import com.karimsabitov.headmanlog.schedule.models.Bell;
import com.karimsabitov.headmanlog.schedule.models.Schedule;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 18.08.2018.
 */

public class CalendarParser {

    private static final int NO_STUDY_TIME = -1;
    private static final int ERROR_CHANGE_BELLS = -2;
    private static final int WEEKEND = -3;
    public static final int INDEX_SUBJECT = 0; //Индекс в результирющем массиве
    public static final int INDEX_TEACHER = 1;
    private static final int PAUSE_TIME = -4;
    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY_OF_MONTH = 2;
    public static String[] MONTHS = {
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
    };
    public static String[] DAYS = {
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота",
            "Воскресенье"
    };
    //region UP / DOWN WEEK

    public static boolean isUpWeek(){
        return isUpWeek(Calendar.getInstance());
    }

    public static boolean isUpWeek(Calendar calendar) {
        int monthStartToStudy = 8;
        int dayStartStudyToStudy = 1;
        if (calendar.get(Calendar.MONTH) + 1 >= monthStartToStudy + 1) {
            Calendar september = Calendar.getInstance();
            september.set(calendar.get(Calendar.YEAR), monthStartToStudy, dayStartStudyToStudy);
            int firstSeptember = september.get(Calendar.WEEK_OF_YEAR);
            int currentWeekNumber = getCurrentWeekInYear(calendar);
            if (currentWeekNumber < firstSeptember) {
                currentWeekNumber = getNumberOfWeeksInYear(calendar);
            }
            return ((currentWeekNumber - firstSeptember) + 1) % 2 != 0;
        }
        Calendar september = Calendar.getInstance();
        september.set(calendar.get(Calendar.YEAR) - 1, monthStartToStudy, dayStartStudyToStudy);
        return ((getNumberOfWeeksInYear(calendar.get(Calendar.YEAR) - 1) - september.get(Calendar.WEEK_OF_YEAR)) + getCurrentWeekInYear()) % 2 != 0;
    }

    public static int getCurrentWeekInYear() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    }

    public static int getCurrentWeekInYear(Calendar calendar) {
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    private static int getNumberOfWeeksInYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 31);
        return ((calendar.get(Calendar.DAY_OF_YEAR) - (calendar.get(Calendar.DAY_OF_WEEK) - 1)) + 10) / 7;
    }

    private static int getNumberOfWeeksInYear(Calendar calendar) {
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 31);
        return ((calendar.get(Calendar.DAY_OF_YEAR) - (calendar.get(Calendar.DAY_OF_WEEK) - 1)) + 10) / 7;
    }

    // endregion

    public static int[] getCurrentCoupleIndex(Context ctx) {
        ScheduleSingle single = ScheduleSingle.get(ctx);
        List<Bell> bells = single.getBells();
        Date dayStartTime;
        Date dayEndTime;
        Date Time = getTime();

        try {
            dayStartTime = bells.get(0).getStartTime();
            dayEndTime = bells.get(bells.size()-1).getEndTime();
        }catch (IndexOutOfBoundsException indexException) {
            indexException.printStackTrace();
            return new int[] {ERROR_CHANGE_BELLS}; //  ошибка в звонках
        }

        if (isInRange(Time, dayStartTime, dayEndTime)){
            for (int i = 0; i < bells.size(); i++) {
                Bell bell_tmp = bells.get(i);
                Date bell_startTime = bell_tmp.getStartTime();
                Date bell_endTime = bell_tmp.getEndTime();
                if (isInRange(Time, bell_startTime, bell_endTime)){
                    return new int[]{i}; // номер пары
                }
                if (i < bells.size()-1) {
                    bell_startTime = bells.get(i + 1).getStartTime();
                    if (isInRange(Time, bell_endTime, bell_startTime)) {
                        return new int[]{PAUSE_TIME, i}; // перемена и какая следующая пара
                    }
                }
            }
        }
        return new int[]{NO_STUDY_TIME};// неучебное время
    }

    private static boolean isInRange(Date date, Date range_low, Date range_high){
        int[] mins = getMins(date, range_low, range_high);
        return (mins[0] >= mins[1] && mins[0] <= mins[2]);
    }

    private static int[] getMins(Date date, Date range_low, Date range_high) {
        int[] mins = new int[3];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        mins[0] = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);

        calendar.setTime(range_low);
        mins[1] = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);

        calendar.setTime(range_high);
        mins[2] = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);

        return mins;
    }

    public static Date getTime(){
        return Calendar.getInstance().getTime();
    }

    public static Date getTime(int hour, int min){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private static Schedule getScheduleByNum(Context ctx, int num, int day, HashMap<Integer, List<Schedule>> hashMap) throws IndexOutOfBoundsException{
        return hashMap
                .get(day-2)// hash
                .get(num); //list
    }

    public static int[] sumTime(int hour1, int min1, int hour2, int min2){
        int totalHour = hour1 + hour2 + (min1 + min2) / 60;
        int totalMin = (min1 + min2) % 60;
        return new int[] { totalHour, totalMin};
    }

    /*public static String[] getSubjectAndTeacher(Context ctx, HashMap<Integer, List<Schedule>> hashMap){
        boolean flag = Utils.prefReadBool(ctx, Constants.APP_NAME, Constants.ROOM_WITH_TEACHER, true);
        boolean upWeek = isUpWeek();
        int dayOfWeek = getDayOfWeek();
        int[] CoupleIndex = {WEEKEND, 0};
        if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
            CoupleIndex = getCurrentCoupleIndex(ctx);
        }

        switch (CoupleIndex[0]){
            case NO_STUDY_TIME:
                return new String[] {ctx.getString(R.string.no_study_time), null };
            case ERROR_CHANGE_BELLS:
                return new String[] {ctx.getString(R.string.error_change_bells_schedule), null};
            case WEEKEND:
                return new String[] {ctx.getString(R.string.title_weekend), null};
            case PAUSE_TIME:
                Schedule schedule = ;
                try {
                    schedule = getScheduleByNum(ctx, CoupleIndex[1] + 1, dayOfWeek, hashMap);
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
                Subject subject = schedule.getSubject(upWeek);
                String subjectStr = subject.getName();
                return new String[] {ctx.getString(
                        R.string.title_pause_time,
                        (CoupleIndex[1]+1),
                        (CoupleIndex[1]+2)),
                        (schedule.getTeacher().getRoom().isEmpty() || (schedule.getRoomOptional().isEmpty() && !flag)) ? //  если нетпервой или второй, если включена
                                ctx.getString(R.string.title_next_couple, subjectStr) :
                                ctx.getString(R.string.title_next_couple_with_room, subjectStr, flag ? schedule.getTeacher().getRoom() : schedule.getRoomOptional())};
            default:
                schedule = null;
                try {
                    schedule = getScheduleByNum(ctx, CoupleIndex[0], dayOfWeek, hashMap);
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
                if (schedule == null) { // ессли расписание не найдено, то скорее всего нет пар по расписанию
                    return new String[] {ctx.getString(
                            R.string.title_couple_num_title,
                            CoupleIndex[0] + 1,
                            ctx.getString(R.string.no_couples)
                            ) ,null};
                }

                Teacher teacher = upWeek? schedule.getTeacher() : schedule.getTeacherDown();
                subject = upWeek ? schedule.getSubject() : schedule.getSubjectDown();

                return new String[]{
                        ctx.getString(R.string.title_couple_num_title,
                                CoupleIndex[0] + 1, // номер пары
                                subject.getName())  // назвнаие предмета
                        ,
                        (teacher.getRoom().isEmpty() || (schedule.getRoomOptional().isEmpty() && !flag)) ? // в каком кабинете
                                teacher.getName() : ctx.getString(
                                        R.string.title_teacher_num,
                                teacher.getName(),
                                flag ?
                                teacher.getRoom() : schedule.getRoomOptional()
                        )};
        }
    };*/

    /*
    * Возвращает номер дня недели
    */
    public static int getDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static void checkBells(Context ctx, Bell bell, TextView v, int position) {
        Date startTime = bell.getStartTime();
        Date endTime = bell.getEndTime();

        if (startTime.before(endTime) && endTime.after(startTime)){
            v.setError(null);
        }
        else {
            v.setError("Ошибка ввода. Проверьте правильность набора");

        }
        final List<Bell> bells = ScheduleSingle.get(ctx).getBells();
        try{
            if (startTime.after(bells.get(position-1).getEndTime())) {
                v.setError(null);
            }else{
                v.setError("Ошибка ввода. Проверьте правильность набора");
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public static boolean isTheSameDay(Date date1, Date date2){
        int[] day1 = getDates(date1), day2 = getDates(date2);
        return (day1[0] == day2[0] && day1[1] == day2[1] && day1[2] == day2[2]);
    }

    public static int[] getDates(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new int[] {
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        };
    }

    public static Date getDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        return calendar.getTime();
    }

    public static String formatDate(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static boolean isTheSameWeek(Date date, int week) {
        return getWeekInMonth(date) == week;
    }

    public static int getWeekInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }
}
