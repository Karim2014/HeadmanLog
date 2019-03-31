package com.karimsabitov.headmanlog.attendance.model;

import android.content.Context;
import android.support.annotation.Nullable;

import com.karimsabitov.headmanlog.Utils.CalendarParser;
import com.karimsabitov.headmanlog.Utils.Constants;
import com.karimsabitov.headmanlog.Utils.FileUtils;
import com.karimsabitov.headmanlog.students.StudentGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 07.09.2018.
 */

public class AttSingle {

    private static AttSingle sInstance;
    private Context mContext;

    public static AttSingle get(Context context) {
        if (sInstance == null){
            sInstance = new AttSingle(context);
        }
        return sInstance;
    }

    /*public void init(int year, int month, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        mDate = calendar.getTime();
    }*/

    public AttSingle(Context context) {
        mContext = context.getApplicationContext();
    }

    @Nullable
    public AttDay getAttDay(Date date){
        return FileUtils.getAttDay(date);
        /*String[] path = getFolderPath(date);



        try {
            attDay = new AttDay(ScheduleSingle.get(mContext).getBellsSize());
            JSONObject jsonObject = JSONUtils.getJsonFromFile(path[0]);
            attDay.setDate(date);
            attDay.setCouples(
                    getCoupleFromJsonObject(jsonObject)
            );
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }

        return attDay;

        *//*for (AttDay item : mDays) {
            if (CalendarParser.isTheSameDay(item.getDate(), date)){
                return item;
            }
        }*/
    }

    public void updateAttDay(AttDay attDay) {
        FileUtils.updateJson(attDay);
    }

    public void addAttDay(AttDay attDay) {
        attDay.setStudents(StudentGroup.get(mContext).getStudentsNames());
        FileUtils.addAttDay(attDay);
    }

    private String getFolderFullPath(Date date){
        int[] ymd = CalendarParser.getDates(date);
        String[] pathl = new String[] {
                String.valueOf(ymd[CalendarParser.YEAR]),
                String.valueOf(ymd[CalendarParser.MONTH]+1),
                CalendarParser.formatDate(Constants.ATT_FILE_PTRN, date)
        };
        return Constants.ATTENDANCE_FOLDER + pathl[CalendarParser.YEAR] + "/" + pathl[CalendarParser.MONTH] + "/" + pathl[CalendarParser.DAY_OF_MONTH] + Constants.JSON_EXT;
    }

    public List<AttDay> getAttDays(int year, int month) {
        List<AttDay> attDays = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);

        //Toast.makeText(mContext, String.valueOf(calendar.get(Calendar.MONTH)), Toast.LENGTH_SHORT).show();

        for (int i = 1; i < 31; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            AttDay day = getAttDay(calendar.getTime());
            if (day != null){
                attDays.add(day);
            }
        }

        return attDays;
    }

    public List<Date> getAttDaysDate(int year, int month){
        List<Date> dateList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);

        for (int i = 1; i < 31; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            if(FileUtils.isFileExist(getFolderFullPath(calendar.getTime()))){
                dateList.add(calendar.getTime());
            }
        }

        return dateList;
    }

    public void removeDay(Date attDay) {
        FileUtils.deleteAttDay(attDay);
    }
}
