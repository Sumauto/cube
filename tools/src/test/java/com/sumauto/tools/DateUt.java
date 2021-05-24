package com.sumauto.tools;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUt {
    public static boolean isYesterday(Date fromDate) {

        return isYesterday2(fromDate,new Date());
    }

    public static boolean isYesterday2(Date fromDate,Date today) {

        Calendar fromDay = Calendar.getInstance();
        fromDay.setTime(fromDate);
        fromDay.add(Calendar.DAY_OF_YEAR, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);


        String todayStr = sdf.format(today);
        String fromStr = sdf.format(fromDay.getTime());
        System.out.println("from +1:"+fromStr);
        System.out.println("today:"+todayStr);
        return todayStr.equals(fromStr);
    }
}
