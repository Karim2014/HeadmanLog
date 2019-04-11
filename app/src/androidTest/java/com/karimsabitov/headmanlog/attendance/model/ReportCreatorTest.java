package com.karimsabitov.headmanlog.attendance.model;

import com.karimsabitov.headmanlog.Utils.CalendarParser;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 09.04.2019.
 */
public class ReportCreatorTest {

    @Test
    public void init() throws Exception {
        List<Date> dateList = new ArrayList<>();

        //dateList.add(new Date());
        for (int i = 1; i <= 24; i++) {
            dateList.add(CalendarParser.getDate(2018, 11, i)); // march
        }

        ReportCreator reportCreator = new ReportCreator();
        reportCreator.setDates(dateList);
        reportCreator.init("ПКС-15", "Декабрь", "2018");
        reportCreator.startSelectStudents();
        try {
            reportCreator.saveReport();
        }catch (IOException e){

        }
    }

}