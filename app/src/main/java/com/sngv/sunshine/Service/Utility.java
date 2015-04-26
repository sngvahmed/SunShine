package com.sngv.sunshine.Service;

import android.text.format.Time;

import java.text.SimpleDateFormat;

/**
 * Created by sngv on 08/04/15.
 */
public class Utility {

    public static String getReadableDateString(long time){
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        return shortenedDateFormat.format(time);
    }

    public boolean compareTime(String date){
        Time dayTime = new Time();
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
        dayTime = new Time();
        long dateTime;
        dateTime = dayTime.setJulianDay(julianStartDay);
        String current_day = getReadableDateString(dateTime);
        return current_day.equals(date);
    }

}
