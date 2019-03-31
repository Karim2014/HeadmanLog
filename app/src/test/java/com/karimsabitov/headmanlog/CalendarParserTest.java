package com.karimsabitov.headmanlog;

import com.karimsabitov.headmanlog.Utils.CalendarParser;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by User on 12.03.2019.
 */

public class CalendarParserTest {


    private static final String TAG = "Test";

    @Test
    public void sumTest() throws Exception {
        int hour1 = 11;
        int min1 = 25;
        int[] sum = CalendarParser.sumTime(hour1, min1, 1, 20);
        Assert.assertEquals(12, sum[0]);
        Assert.assertEquals(45, sum[1]);
    }
}
