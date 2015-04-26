package com.sngv.sunshine.weatherService;

import android.text.format.Time;

import com.sngv.sunshine.domain.LocationItem;
import com.sngv.sunshine.weatherService.FetchWeatherData;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

/**
 * Created by sngv on 08/04/15.
 */
public class WeatherUtility {
    public static String UnitMetric = "metric";
    public static String UnitImperial = "imperial";
    public static String API_WITH_CITY = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";

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
