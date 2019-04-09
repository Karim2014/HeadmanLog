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

        dateList.add(new Date());
        dateList.add(CalendarParser.getDate(2019, 3, 8));
        dateList.add(CalendarParser.getDate(2019, 3, 7));
        dateList.add(CalendarParser.getDate(2019, 3, 6));
        dateList.add(CalendarParser.getDate(2019, 3, 5));
        dateList.add(CalendarParser.getDate(2019, 3, 4));

        ReportCreator reportCreator = new ReportCreator();
        reportCreator.setDates(dateList);
        reportCreator.init();
        try {
            reportCreator.saveReport();
        }catch (IOException e){

        }
    }

}